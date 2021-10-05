package es.udc.asi.restexample.model.repository;

import java.util.List;

import es.udc.asi.restexample.model.domain.Post;
import org.springframework.stereotype.Repository;

import es.udc.asi.restexample.model.domain.Tag;
import es.udc.asi.restexample.model.repository.util.GenericDaoJpa;

@Repository
public class TagDaoJpa extends GenericDaoJpa implements TagDao {

  @Override
  public List<Tag> findAll() {
    return entityManager.createQuery("from Tag", Tag.class).getResultList();
  }

  @Override
  public Tag findById(Long id) {
    return entityManager.find(Tag.class, id);
  }

  @Override
  public void create(Tag tag) {
    entityManager.persist(tag);
  }

  @Override
  public void update(Tag tag) {
    entityManager.merge(tag);
  }


  @Override
  public void delete(Tag tag) {
    entityManager.remove(tag);
  }

}
