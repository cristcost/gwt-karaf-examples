package com.google.gwt.sample.authrequest.shared;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UserService {

  private static String userId = null;

  public boolean isUserLoggedIn() {
    return userId != null;
  }

  public String createLoginURL(String redirectUrl) {

    try {
      return "/login?redirectUrl=" + URLEncoder.encode(redirectUrl, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("UTF-8 encoding is not supported", e);
    }
  }

  public void loginUser(String loginUserId) {
    this.userId = loginUserId;
    System.out.println("Logging user: " + this.userId);
  }
}
