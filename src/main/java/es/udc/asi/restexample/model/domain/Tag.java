package es.udc.asi.restexample.model.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_generator")
  @SequenceGenerator(name = "tag_generator", sequenceName = "tag_seq")
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
  private Set<Post> posts = new HashSet<>();

  public Tag() {
  }

  public Tag(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Tag tag = (Tag) o;
    return id.equals(tag.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }


  public Tag(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Post> getPosts() {
    return posts;
  }

  public void setPosts(Set<Post> posts) {
    this.posts = posts;
  }
}
