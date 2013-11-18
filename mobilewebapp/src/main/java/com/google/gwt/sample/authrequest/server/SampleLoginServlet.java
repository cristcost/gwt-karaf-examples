package com.google.gwt.sample.authrequest.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SampleLoginServlet extends HttpServlet {
  private static final Logger logger = Logger.getLogger(SampleLoginServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {

    String redirectUrl = req.getParameter("redirectUrl");
    if (redirectUrl != null) {
      HttpSession session = req.getSession();
      session.setAttribute("redirectUrl", redirectUrl);
      logger.info("Setting " + redirectUrl + " as redirect url");
    }

    if (req.getParameter("logout") != null) {
      HttpSession session = req.getSession();
      session.invalidate();
      logger.info("Invalidating session");
      resp.sendRedirect(req.getContextPath());
      return;
    }

    forward(req, resp, "Login.html");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
    IOException {

    String userId = req.getParameter("userId");
    if (userId != null && userId.length() > 0) {

      logger.info("Logging in as user " + userId);

      HttpSession session = req.getSession();
      session.setAttribute("userId", userId);

      String redirectUrl = (String) session.getAttribute("redirectUrl");
      if (redirectUrl == null) {
        redirectUrl = req.getContextPath();
      }

      logger.info("Redirecting to " + redirectUrl);

      resp.sendRedirect(redirectUrl);
      return;
    }

    forward(req, resp, "Login.html");
  }

  /**
   * Forwards request and response to given path. Handles any exceptions caused
   * by forward target by printing them to logger.
   * 
   * @param request
   * @param response
   * @param path
   */
  protected void forward(HttpServletRequest request, HttpServletResponse response, String path) {
    try {
      RequestDispatcher rd = request.getRequestDispatcher(path);
      rd.forward(request, response);
    } catch (Throwable tr) {
      logger.severe("Cought Exception: " + tr.getMessage());
    }
  }
}
