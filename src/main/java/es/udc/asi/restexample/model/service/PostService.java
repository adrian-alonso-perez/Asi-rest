package es.udc.asi.restexample.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.restexample.model.domain.Post;
import es.udc.asi.restexample.model.exception.NotFoundException;
import es.udc.asi.restexample.model.exception.OperationNotAllowed;
import es.udc.asi.restexample.model.repository.PostDao;
import es.udc.asi.restexample.model.repository.TagDao;
import es.udc.asi.restexample.model.repository.UserDao;
import es.udc.asi.restexample.model.service.dto.PostDTO;
import es.udc.asi.restexample.model.service.dto.UserDTOPrivate;


@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PostService {

  @Autowired
  private PostDao postDAO;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private TagDao tagDAO;

  @Autowired
  private UserService userService;


  public List<PostDTO> findAll() {
    return postDAO.findAll().stream().map(post -> new PostDTO(post)).collect(Collectors.toList());
  }

  public PostDTO findById(Long id) throws NotFoundException {
    Post post = postDAO.findById(id);
    if (post == null) {
      throw new NotFoundException(id.toString(), Post.class);
    }
    return new PostDTO(postDAO.findById(id));
  }

  // Con estas anotaciones evitamos que usuarios no autorizados accedan a ciertas
  // funcionalidades
  @Transactional(readOnly = false)
  public PostDTO create(PostDTO post) {
    Post bdPost = new Post(post.getTitle(), post.getBody());
    bdPost.setAuthor(userDAO.findById(post.getAuthor().getId()));
    if (post.getTags() != null) {
      post.getTags().forEach(tag -> {
        bdPost.getTags().add(tagDAO.findById(tag.getId()));
      });
    }
    postDAO.create(bdPost);
    return new PostDTO(bdPost);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public PostDTO update(PostDTO post) {
    Post bdPost = postDAO.findById(post.getId());
    bdPost.setTitle(post.getTitle());
    bdPost.setBody(post.getBody());
    bdPost.setAuthor(userDAO.findById(post.getAuthor().getId()));
    bdPost.getTags().clear();
    post.getTags().forEach(tag -> {
      bdPost.getTags().add(tagDAO.findById(tag.getId()));
    });
    postDAO.update(bdPost);
    return new PostDTO(bdPost);
  }


  @PreAuthorize("isAuthenticated()")
  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException, OperationNotAllowed {
    Post post = postDAO.findById(id);
    if (post == null) {
      throw new NotFoundException(id.toString(), Post.class);
    }

    UserDTOPrivate currentUser = userService.getCurrentUserWithAuthority();
    if (!currentUser.getId().equals(post.getAuthor().getId())) {
      throw new OperationNotAllowed("Current user is not the post creator");
    }

    LocalDateTime halfAnHourAgo = LocalDateTime.now().minusMinutes(30);
    if (post.getTimestamp().isBefore(halfAnHourAgo)) {
      throw new OperationNotAllowed("More than half an hour has passed since the post creation");
    }
  postDAO.deleteById(id);
  }

}
