package es.udc.asi.restexample.web;

import java.util.List;

import javax.validation.Valid;

import es.udc.asi.restexample.web.exceptions.MessageBodyExceedsTheMaximumAllowed;
import es.udc.asi.restexample.web.exceptions.TitlePostExceedsTheMaximumAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.asi.restexample.model.domain.Post;
import es.udc.asi.restexample.model.exception.NotFoundException;
import es.udc.asi.restexample.model.exception.OperationNotAllowed;
import es.udc.asi.restexample.model.service.PostService;
import es.udc.asi.restexample.model.service.dto.PostDTO;
import es.udc.asi.restexample.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.restexample.web.exceptions.RequestBodyNotValidException;

@RestController
@RequestMapping("/api/posts")
public class PostResource {

  @Autowired
  private PostService postService;

  @GetMapping
  public List<PostDTO> findAll() {
    return postService.findAll();
  }

  @GetMapping("/{id}")
  public PostDTO findOne(@PathVariable Long id) throws NotFoundException {
    return postService.findById(id);
  }

  @PostMapping

  public PostDTO create(@RequestBody @Valid PostDTO post, Errors errors) throws RequestBodyNotValidException, MessageBodyExceedsTheMaximumAllowed, TitlePostExceedsTheMaximumAllowed {

    if (post.getTitle().length() > 50) {

      throw new TitlePostExceedsTheMaximumAllowed(post.getTitle().toString());

    } else if (post.getBody().length() > 300) {

      throw new MessageBodyExceedsTheMaximumAllowed();

    } else {

      if (errors.hasErrors()) {

        throw new RequestBodyNotValidException(errors);

      }

    }

    return postService.create(post);

  }

  @PutMapping("/{id}")
  public PostDTO update(@PathVariable Long id, @RequestBody @Valid PostDTO post, Errors errors)
    throws IdAndBodyNotMatchingOnUpdateException, RequestBodyNotValidException {
    if (errors.hasErrors()) {
      throw new RequestBodyNotValidException(errors);
    }

    if (id != post.getId()) {
      throw new IdAndBodyNotMatchingOnUpdateException(Post.class);
    }
    return postService.update(post);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    {
      postService.deleteById(id);
    }

  }
}
