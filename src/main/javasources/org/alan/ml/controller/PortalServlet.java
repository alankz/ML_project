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
import org.alan.ml.domain.ExclusionSite;
import org.alan.ml.domain.User;
import org.alan.ml.domain.UserRole;
import org.alan.ml.services.ConfigService;
import org.alan.ml.services.DataService;
import org.alan.ml.services.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "PortalServlet", urlPatterns = { "" })
public class PortalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6235866703670860360L;

	final static Logger logger = LogManager.getLogger(PortalServlet.class);

	public static final int defaultPageRecords = ConfigService.getConfig().getInt("web.dataPage.pageRecords");

	public static final String defaultQueryDate = ConfigService.getConfig().getString("web.dataPage.queryDate");

	public PortalServlet() {
		super();

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 boolean authenticated = false;
		 Object sessionUserRoleObject = request.getSession().getAttribute("userRole");
		 String sessionUserRole = "";
		 LoginService loginService = new LoginService();
		 
		 if (sessionUserRoleObject==null) {
			 
			 String userName = request.getParameter("userName");
			 String password = request.getParameter("password");
			 authenticated = loginService.authenticate(userName, password);
			 if (authenticated) {
				 User user = loginService.getUserByUserName(userName);
				 if (user!=null) {
					 request.getSession().setAttribute("userName", user.getUserName());
					 request.getSession().setAttribute("userRole", user.getUserRole());
				 }
			 }
			 
		 } else {
			 sessionUserRole = sessionUserRoleObject.toString();
			 if(sessionUserRole.contentEquals(UserRole.ADMIN.getRole())||sessionUserRole.contentEquals(UserRole.USER.getRole())) {
				 authenticated = true;
			 }
		 }
	
		 if(authenticated == true){
			 
			 processDataPage(request, response);
			
		 }
		 else{
			 response.sendRedirect("login.jsp");
		 }

	}
	
	protected void processDataPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionUserRole = "";
		
		Object sessionUserRoleObject = request.getSession().getAttribute("userRole");
		 if (sessionUserRoleObject!=null) {
			 sessionUserRole  = sessionUserRoleObject.toString();
		 }
		
		String queryDate = request.getParameter("queryDate");
		if (queryDate == null) {
			queryDate = defaultQueryDate;
		}

		String sessionId = request.getSession().getId();

		logger.info("sessionId[" + sessionId + "],queryDate[" + queryDate + "]");
		DataService dataService = new DataService();
		List<Data> dataList = dataService.getDataTableOrderByVisit(queryDate, defaultPageRecords);
		dataList = dataService.applyExclusionListOnDataList(dataList, ExclusionSite.getExclusionMap());

		request.setAttribute("dataList", dataList);
		request.setAttribute("queryDate", queryDate);
		
		logger.info("PortalServlet: Session UserRole["+sessionUserRole+"]");
		boolean adminRight = sessionUserRole.contentEquals(UserRole.ADMIN.getRole());
		logger.info("PortalServlet: adminRight["+adminRight+"]");
		request.setAttribute("admin", adminRight);

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
