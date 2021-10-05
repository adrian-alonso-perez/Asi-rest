package es.udc.asi.restexample.web;

import java.util.List;

import es.udc.asi.restexample.model.exception.NotFoundException;
import es.udc.asi.restexample.model.exception.OperationNotAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.udc.asi.restexample.model.service.UserService;
import es.udc.asi.restexample.model.service.dto.UserDTOPublic;

@RestController
@RequestMapping("/api/users")
public class UserResource {

  @Autowired
  private UserService userService;

  @GetMapping
  public List<UserDTOPublic> findAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public UserDTOPublic findOne(@PathVariable Long id) throws NotFoundException {
    return userService.findById(id);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) throws NotFoundException {
    userService.deleteById(id);
  }
  @PutMapping("/{id}/activate")
  public void activate(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    userService.updateActive(id,true);
  }

  @PutMapping("/{id}/deactivate")
  public void deactivate(@PathVariable Long id) throws NotFoundException, OperationNotAllowed {
    userService.updateDesactive(id, false);
  }

}
