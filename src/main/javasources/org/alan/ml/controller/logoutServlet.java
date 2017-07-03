package org.alan.ml.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "Logout", urlPatterns = { "/logout.do" })
public class logoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724651418142528992L;
	
	final static Logger logger = LogManager.getLogger(logoutServlet.class);

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		logger.info("Session ID: ["+req.getSession().getId()+"] is logout");
		resp.sendRedirect("logout.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

}
