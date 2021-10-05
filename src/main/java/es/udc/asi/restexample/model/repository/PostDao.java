package es.udc.asi.restexample.model.repository;

import java.util.List;

import es.udc.asi.restexample.model.domain.Post;

public interface PostDao {
  List<Post> findAll();

  Post findById(Long id);

  void create(Post post);

  void update(Post post);

  void deleteById(Long id);

  List<Post> findAllByTag(Long tagId);

  List<Post>  findAllByUser(Long id);
}
