package es.udc.asi.restexample.model.repository;

import java.util.List;

import es.udc.asi.restexample.model.domain.User;

public interface UserDao {
  List<User> findAll();

  User findById(Long id);

  User findByLogin(String login);

  void create(User user);

  void update(User user);

  void deleteById(Long id);


}
