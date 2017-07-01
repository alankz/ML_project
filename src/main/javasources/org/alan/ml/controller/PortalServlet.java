package org.alan.ml.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alan.ml.domain.Data;
import org.alan.ml.services.DataService;

@WebServlet(name = "PortalServlet", urlPatterns = { "/home" })
public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PortalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String userId = request.getParameter("userId");
		// String password = request.getParameter("password");
		// LoginService loginService = new LoginService();
		// boolean result = loginService.authenticate(userId, password);
		// User user = loginService.getUserByUserId(userId);
		// if(result == true){
		// request.getSession().setAttribute("user", user);
		// response.sendRedirect("home.jsp");
		// }
		// else{
		// response.sendRedirect("login.jsp");
		// }
		DataService dataService = new DataService();
		List<Data> dataList = dataService.getDataTable();
		request.setAttribute("dataList", dataList);
		RequestDispatcher view = request.getRequestDispatcher("dataTable.jsp");
		view.forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
