package es.udc.asi.restexample.web;

import java.util.List;

import es.udc.asi.restexample.model.domain.Post;
import es.udc.asi.restexample.model.domain.Tag;
import es.udc.asi.restexample.model.exception.NotFoundException;
import es.udc.asi.restexample.model.exception.OperationNotAllowed;
import es.udc.asi.restexample.model.service.dto.PostDTO;
import es.udc.asi.restexample.web.exceptions.IdAndBodyNotMatchingOnUpdateException;
import es.udc.asi.restexample.web.exceptions.MessageBodyExceedsTheMaximumAllowed;
import es.udc.asi.restexample.web.exceptions.RequestBodyNotValidException;
import es.udc.asi.restexample.web.exceptions.TitlePostExceedsTheMaximumAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import es.udc.asi.restexample.model.service.TagService;
import es.udc.asi.restexample.model.service.dto.TagDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tags")
public class TagResource {

  @Autowired
  private TagService tagService;

  @GetMapping
  public List<TagDTO> findAll() {
    return tagService.findAll();
  }


  @GetMapping("/{id}")
  public TagDTO findOne(@PathVariable Long id) throws NotFoundException {
    return tagService.findById(id);
  }


  @PostMapping
  public TagDTO create(@RequestBody @Valid TagDTO tag, Errors errors) throws OperationNotAllowed {

    return tagService.create(tag);

  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException{
    tagService.deleteById(id);
  }

  @PutMapping("/{id}")
  public TagDTO update(@PathVariable Long id, @RequestBody @Valid TagDTO tag, Errors errors)
    throws OperationNotAllowed {
    if (errors.hasErrors()) {
      throw new OperationNotAllowed("error updating");
    }

    if(id != tag.getId()){
      throw new OperationNotAllowed("tag already exists");
    }

    return tagService.update(tag);
  }

}
