package es.udc.asi.restexample.model.service.dto;

import es.udc.asi.restexample.model.domain.User;

public class UserDTOPublic {
  private Long id;
  private String login;
  private boolean active = true;

  public UserDTOPublic() {
  }

  public UserDTOPublic(User user) {
    this.id = user.getId();
    this.login = user.getLogin();
    this.setActive(user.isActive());
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

  public boolean isActive() { return active; }

  public void setActive(boolean active) { this.active = active; }

}
