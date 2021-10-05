package es.udc.asi.restexample.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.asi.restexample.model.domain.Tag;
import es.udc.asi.restexample.model.domain.Post;
import es.udc.asi.restexample.model.domain.User;
import es.udc.asi.restexample.model.domain.UserAuthority;
import es.udc.asi.restexample.model.exception.NotFoundException;
import es.udc.asi.restexample.model.exception.OperationNotAllowed;
import es.udc.asi.restexample.model.exception.UserLoginExistsException;
import es.udc.asi.restexample.model.repository.PostDao;
import es.udc.asi.restexample.model.repository.UserDao;
import es.udc.asi.restexample.model.service.dto.UserDTOPrivate;
import es.udc.asi.restexample.model.service.dto.UserDTOPublic;
import es.udc.asi.restexample.security.SecurityUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDao userDAO;

  @Autowired
  private PostDao postDAO;

  public List<UserDTOPublic> findAll() {
    return userDAO.findAll().stream().map(user -> new UserDTOPublic(user)).collect(Collectors.toList());
  }

  @Transactional(readOnly = false)
  public void registerUser(String login, String password) throws UserLoginExistsException {
    registerUser(login, password, false);
  }

  @Transactional(readOnly = false)
  public void registerUser(String login, String password, boolean isAdmin) throws UserLoginExistsException {
    if (userDAO.findByLogin(login) != null) {
      throw new UserLoginExistsException(login);
    }

    User user = new User();
    String encryptedPassword = passwordEncoder.encode(password);

    user.setLogin(login);
    user.setPassword(encryptedPassword);
    user.setAuthority(UserAuthority.USER);
    if (isAdmin) {
      user.setAuthority(UserAuthority.ADMIN);
    }

    userDAO.create(user);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public UserDTOPublic updateActive(Long id, boolean active) throws NotFoundException, OperationNotAllowed {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(user.getId())) {
      throw new OperationNotAllowed("The user cannot activate/deactive itself");
    }

    user.setActive(active);
    userDAO.update(user);
    return new UserDTOPublic(user);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public UserDTOPublic updateDesactive(Long id, boolean active) throws NotFoundException, OperationNotAllowed {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), User.class);
    }

    UserDTOPrivate currentUser = getCurrentUserWithAuthority();
    if (currentUser.getId().equals(user.getId())) {
      throw new OperationNotAllowed("The user cannot activate/deactive itself");
    }

    user.setDesctive(active);
    userDAO.update(user);
    return new UserDTOPublic(user);
  }

  // H.U 16 Borrar un usuario y su lista de publicaciones
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional(readOnly = false)
  public void deleteById(Long id) throws NotFoundException {

    List<Post> posts = postDAO.findAllByUser(id);

    // Buscamos a dicho usuario que queremos eliminar
    //User theUser = userDAO.findById(id);

    // Si el usuario que se intenta borrar no existe se lanza una excepción.
    //if (theUser == null) {
    //    throw new NotFoundException(id.toString(), User.class);
    //}

    posts.forEach(post -> {
      postDAO.deleteById(post.getId());
    });

    userDAO.deleteById(id);
  }

  public UserDTOPublic findById(Long id) throws NotFoundException {
    User user = userDAO.findById(id);
    if (user == null) {
      throw new NotFoundException(id.toString(), Tag.class);
    }
    return new UserDTOPublic(userDAO.findById(id));

  }


  public UserDTOPrivate getCurrentUserWithAuthority() {
    String currentUserLogin = SecurityUtils.getCurrentUserLogin();
    if (currentUserLogin != null) {
      return new UserDTOPrivate(userDAO.findByLogin(currentUserLogin));
    }
    return null;
  }
}
