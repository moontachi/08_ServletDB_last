package svy;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.nio.sctp.SendFailedNotification;

import oracle.net.ns.SessionAtts;


/**
 * Servlet implementation class SurveyServlet
 */
//@WebServlet("*.sv")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServletContext context;
	String driver;
	String url;
	String user;
	String password;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SurveyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
		driver = config.getInitParameter("driver");
		url = config.getInitParameter("url");
		user = config.getInitParameter("user");
		password = config.getInitParameter("password");
		
		System.out.println("driver" + driver);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doProcess(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		doProcess(request,response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		int len = contextPath.length();
		String command = uri.substring(len);
		String viewPage = null;
		SurveyDao dao = new SurveyDao(driver,url,user,password);
		if(command.equals("/insert.sv")) {
			System.out.println("/insert.sv 요청 들어옴");

			String flag = (String)context.getAttribute("flag");
			System.out.println("flag :" + flag);
			request.setCharacterEncoding("UTF-8");
			String name = request.getParameter("name");
			String company = request.getParameter("company");
			String email = request.getParameter("email");
			String satisfaction = request.getParameter("satisfaction");
			String[] part_imsi = request.getParameterValues("part");
			String part = "";
			if(part_imsi == null) {
				part = "관심분야 없음";
			}else {
				for(int i=0; i < part_imsi.length; i++) {
					part += part_imsi[i];

					if(i != part_imsi.length-1) {
						part += ",";
					}
				}
			}
			String howto = request.getParameter("howto");
			String agree_imsi = request.getParameter("agree");
			int agree;
			if(agree_imsi == null) {
				agree = 0;
			}else {
				agree = 1;
			}
			SurveyBean sb = new SurveyBean(0,name,company,email,satisfaction,part,howto,agree);
			if(flag.equals("false")) {
				dao.insertSurvey(sb);
				context.setAttribute("flag","true");
				viewPage = "/list.sv";
			}else {
				viewPage = "/list.sv";
			}
			
			

			



		}else if(command.equals("/list.sv")) {



			System.out.println("/list.sv 요청 들어옴");
			ArrayList<SurveyBean> lists = dao.getSurveyList();
			request.setAttribute("lists", lists);
			/*
			HttpSession session = request.getSession(false);
			if(session == null) {
				session =request.getSession();
				
			}
			System.out.println("session" + session);
			*/
			viewPage = "Ex02_surveyList.jsp";

		}else if(command.equals("/updateForm.sv")) {
			System.out.println("/updateForm 요청 들어옴");
			String no = request.getParameter("no");
			SurveyBean sb = dao.getOneSelect(no);
			request.setAttribute("sb", sb);
			viewPage = "Ex02_survey_update_form.jsp";
		}else if(command.equals("/update.sv")) {
			request.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			int no = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("name");
			String company = request.getParameter("company");
			String email = request.getParameter("email");
			String satisfaction = request.getParameter("satisfaction");
			String[] part_imsi = request.getParameterValues("part");
			String part = "";
			if(part_imsi == null) {
				part = "관심분야 없음";
			}else {
				for(int i=0; i < part_imsi.length; i++) {
					part += part_imsi[i];

					if(i != part_imsi.length-1) {
						part += ",";
					}
				}
			}
			String howto = request.getParameter("howto");
			String agree_imsi = request.getParameter("agree");
			int agree;
			if(agree_imsi == null) {
				agree = 0;
			}else {
				agree = 1;
			}
			SurveyBean sb = new SurveyBean(no,name,company,email,satisfaction,part,howto,agree);

			int cnt = dao.updateSurvey(sb);
			System.out.println("update cnt :" + cnt);
			
			

			viewPage = "/list.sv";
			
		
		}else if(command.equals("/delete.sv")) {
			System.out.println("/delete 요청 들어옴");
			
			String no = request.getParameter("no");
			System.out.println("no :" + no);
			int cnt = dao.deleteSurvey(no);
			System.out.println("cnt :" + cnt);
			viewPage= "/list.sv";
		}

		 RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		 
		 dis.forward(request, response);
		 
		
		//response.sendRedirect(contextPath + "/" + viewPage);
	}

}
