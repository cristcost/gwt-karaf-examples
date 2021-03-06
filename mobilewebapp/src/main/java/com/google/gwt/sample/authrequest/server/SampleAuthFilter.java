/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.sample.authrequest.server;

import com.google.gwt.sample.authrequest.shared.AuthHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A servlet filter that handles sample user authentication.
 */
public class SampleAuthFilter implements Filter {

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    if (!isUserLoggedIn(request)) {
      String redirectUrl = request.getHeader(AuthHelper.REDIRECT_URL_HTTP_HEADER_NAME);
      if (redirectUrl == null || redirectUrl.length() == 0) {
        // Default to the root page if the redirecturl isn't specified in the
        // request.
        redirectUrl = "/";
      }
      response.setHeader("login", createLoginURL(request.getContextPath(), redirectUrl));
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private boolean isUserLoggedIn(HttpServletRequest req) {
    HttpSession session = req.getSession();
    String userId = (String) session.getAttribute("userId");
    return userId != null && userId.length() > 0;
  }

  public String createLoginURL(String contextPath, String redirectUrl) {
    try {
      return contextPath + "/login?redirectUrl=" + URLEncoder.encode(redirectUrl, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("UTF-8 encoding not supported", e);
    }
  }

  @Override
  public void init(FilterConfig config) {
  }
}
