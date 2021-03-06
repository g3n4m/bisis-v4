package com.gint.app.bisis4web.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gint.app.bisis4web.formatters.RecordFormatterFactory;
import com.gint.app.bisis4web.web.beans.ErrorInfo;
import com.gint.app.bisis4web.web.beans.WebUser;

/**
 * Servlet Class
 *
 * @web.servlet              
 *   name="DetailFormat"
 *   display-name="DetailFormat"
 *   description="Switches to the detailed display format"
 * @web.servlet-mapping      
 *   url-pattern="/DetailFormat"
 */
@SuppressWarnings("serial")
public class DetailFormatServlet extends HttpServlet {

  public static final String NEXT_PAGE = "/display.jsp";
  public static final String ERROR_PAGE = "/error.jsp";

  public DetailFormatServlet() {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    response.setContentType("text/html; charset=utf-8");
    request.setCharacterEncoding(response.getCharacterEncoding());
    ErrorInfo errorInfo = (ErrorInfo)request.getSession().getAttribute(
        "errorInfo");
    if (errorInfo == null) {
      getServletConfig().getServletContext().getRequestDispatcher(
          ERROR_PAGE).forward(request, response);
      return;
    }
    errorInfo.setErrorOccured(false);
    errorInfo.setErrorMessage("");
  
    try {
      WebUser webUser = (WebUser)request.getSession().getAttribute("user");
      webUser.setDisplayMode(RecordFormatterFactory.FORMAT_DETAIL);
      getServletConfig().getServletContext().getRequestDispatcher(
          NEXT_PAGE).forward(request, response);
    } catch (Exception ex) {
      ex.printStackTrace();
      errorInfo.setException(ex);
      getServletConfig().getServletContext().getRequestDispatcher(
          ERROR_PAGE).forward(request, response);
    }
  }
}