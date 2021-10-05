package es.udc.asi.restexample.model.repository;

import java.util.List;

import es.udc.asi.restexample.model.domain.Post;
import es.udc.asi.restexample.model.domain.Tag;

public interface TagDao {

  List<Tag> findAll();

  Tag findById(Long id);

  void create(Tag tag);

  void delete(Tag tag);

  void update(Tag tag);
}
