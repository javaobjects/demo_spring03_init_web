package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("welcome!!!!");
		//假设：请求传递过来username:password
		//那么接收参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");

//		UserService userService = new UserService();//之前的写法
		//使用userservice不需要servlet自己实例化
		//获取web片的spring容器
		WebApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()); 
		UserService userService = (UserService) act.getBean("userService");
		//调用底层service
		boolean result = userService.login(username, password);
		//根据调用结果返回响应
		response.setContentType("text/html;charset=utf-8");
		if(result) {
			response.getWriter().write("<script>alert('login success');</script>");
		}else {
			response.getWriter().write("<script>alert('login fail');</script>");
		}
	}
}
