package es.udc.asi.restexample.model.service.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import es.udc.asi.restexample.model.domain.Post;

public class PostDTO {
  private Long id;
  //@NotEmpty
  private String title;
  @NotEmpty
  private String body;
  @NotNull
  private LocalDateTime timestamp;
  @NotNull
  private UserDTOPublic author;
  private List<TagDTO> tags = new ArrayList<>();

  public PostDTO() {
  }

  public PostDTO(Post post) {

    this.id = post.getId();
    this.title = post.getTitle();
    this.timestamp = post.getTimestamp();
    this.body = post.getBody();
    this.author = new UserDTOPublic(post.getAuthor());
    post.getTags().forEach(t -> {
      this.tags.add(new TagDTO(t));
    });
    this.tags.sort(Comparator.comparing(TagDTO::getName));
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

  public UserDTOPublic getAuthor() {
    return author;
  }

  public void setAuthor(UserDTOPublic author) {
    this.author = author;
  }

  public List<TagDTO> getTags() {
    return tags;
  }

  public void setTags(List<TagDTO> tags) {
    this.tags = tags;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}
