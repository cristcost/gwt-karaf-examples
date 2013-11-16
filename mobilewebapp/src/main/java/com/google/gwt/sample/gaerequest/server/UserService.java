package com.google.gwt.sample.gaerequest.server;

public class UserService {
  
  private static String userId = null;

  public boolean isUserLoggedIn() {
    return userId != null;
  }

  public String createLoginURL() {

    return "/Login.html";
  }

  public void loginUser(String loginUserId) {
    this.userId = loginUserId;
  }
}
