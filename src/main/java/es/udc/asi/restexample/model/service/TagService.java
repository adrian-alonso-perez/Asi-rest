package es.udc.asi.restexample.model.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import es.udc.asi.restexample.model.exception.OperationNotAllowed;
import es.udc.asi.restexample.model.service.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.asi.restexample.model.domain.Post;
import es.udc.asi.restexample.model.domain.Tag;
import es.udc.asi.restexample.model.exception.NotFoundException;
import es.udc.asi.restexample.model.repository.PostDao;
import es.udc.asi.restexample.model.repository.TagDao;
import es.udc.asi.restexample.model.service.dto.TagDTO;

@Service
@Transactional(readOnly = true)
public class TagService {

  @Autowired
  private TagDao tagDAO;

  @Autowired
  private PostDao postDAO;



  public List<TagDTO> findAll() {
    return tagDAO.findAll().stream().sorted(Comparator.comparing(Tag::getName)).map(TagDTO::new)
        .collect(Collectors.toList());
  }


  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException {
    List<Post> posts = postDAO.findAllByTag(id);
    Tag theTag = tagDAO.findById(id);
    if (theTag == null) {
      throw new NotFoundException(id.toString(), Tag.class);
    }
    posts.forEach(post -> {
      post.getTags().remove(theTag);
      postDAO.update(post);
    });
    tagDAO.delete(theTag);
  }

  // Con estas anotaciones evitamos que usuarios no autorizados accedan a ciertas
  // funcionalidades
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public TagDTO create(TagDTO tag) throws OperationNotAllowed {
    Tag bdTag = new Tag(tag.getName());
    List<Tag> tags = tagDAO.findAll();

    if (tag.getName() != null) {
      for (int i=0; i<tags.size(); i++){
        if (tags.get(i).getName() == tag.getName()){
          throw new OperationNotAllowed("This tag already exists");
        }
      };
    }
    tagDAO.create(bdTag);
    return new TagDTO(bdTag);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public TagDTO update(TagDTO tag) throws OperationNotAllowed {
    Tag bdTag = tagDAO.findById(tag.getId());
    bdTag.setName(tag.getName());

    tagDAO.update(bdTag);
    return new TagDTO(bdTag);
  }

  public TagDTO findById(Long id) throws NotFoundException {
    Tag tag = tagDAO.findById(id);
    if (tag == null) {
      throw new NotFoundException(id.toString(), Tag.class);
    }
    return new TagDTO(tagDAO.findById(id));
  }


}
