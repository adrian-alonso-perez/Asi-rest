package es.udc.asi.restexample.model.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "theUser")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_seq")
  private Long id;

  @Column(unique = true)
  private String login;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserAuthority authority;

  @OneToMany(mappedBy = "author")
  private List<Post> posts = new ArrayList<>();

  private boolean active = true;

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAuthority authority) {
    this.authority = authority;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public boolean isActive() { return active; }

  public void setActive(boolean active) { this.active = active; }

  public void setDesactive(boolean active) { this.active = !active; }

  public void setDesctive(boolean active) {
    this.active=false;
  }
}
