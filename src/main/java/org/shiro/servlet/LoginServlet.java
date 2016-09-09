package org.shiro.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		String emsg = null;
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			// TODO Auto-generated catch block
			emsg = "用户名出错";
		} catch (IncorrectCredentialsException e) {
			emsg = "用户密码出错";
		} catch (AuthenticationException e) {
			emsg = "其他异常:" + e.getMessage();
		}
		if (emsg != null) {
			request.setAttribute("emsg", emsg);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(
					request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}

}
