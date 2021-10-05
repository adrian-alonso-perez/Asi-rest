package es.udc.asi.restexample.model.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
  @SequenceGenerator(name = "post_generator", sequenceName = "post_seq")
  private Long id;

  private String title;

  @Column(length = 10485760)
  private String body;

  private LocalDateTime timestamp = LocalDateTime.now();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private User author;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Tag> tags = new HashSet<>();

  public Post() {
  }

  public Post(String title, String body) {
    this.title = title;
    this.body = body;
    this.timestamp = getTimestamp(); //modificado
  }

  public Post(String title, String body, User author) {
    this(title, body);
    this.author = author;
    this.timestamp = getTimestamp();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }
}
