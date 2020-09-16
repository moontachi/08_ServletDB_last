package myPkg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MymemServlet
 */
//@WebServlet("*.mem")
public class MymemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MymemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
		System.out.println("doGet()");
		process(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		System.out.println("doPost()");
		process(request,response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("process()");
		 
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		int len = contextPath.length();
		
		System.out.println("uri:" + uri);
		System.out.println("contextPath:" + contextPath);
		System.out.println("len:" + len);
		
		String command =  uri.substring(len); // // /insert.mem or /select.mem 
		System.out.println("command : " + command);
		
		MymemDao dao = new MymemDao();
		String viewPage = null; 
		
		if(command.equals("/insert.mem")) {
			System.out.println("insert 요청 들어옴");
			
			request.setCharacterEncoding("UTF-8"); 
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			MymemBean bean = new MymemBean(0,name,password);
			int cnt = dao.insertMymem(bean);
			System.out.println("insert cnt : " + cnt );
			
			viewPage = "Ex01_mymemVia.jsp";
		}
		else if(command.equals("/select.mem")) {
			System.out.println("select 요청 들어옴");
			
			ArrayList<MymemBean> lists = dao.getMymemList();
			
			request.setAttribute("lists", lists);
			
			viewPage = "Ex01_mymemList.jsp";
		}
		else if(command.equals("/update.mem")) {
			System.out.println("update 요청 들어옴");
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			dao.updateMymem(id,name,password);
			
			viewPage= "/select.mem";
			
		}
		else if(command.equals("/delete.mem")) {
			System.out.println("delete 요청 들어옴");
			
			String id = request.getParameter("id");
			dao.deleteMymem(id);
			//viewPage = "Ex01_mymemList.jsp";  lists를 챙겨가야 가기 때문에 바로 넘어가면 오류뜸. 주의 할 것.
			viewPage= "/select.mem";
		}
		else if(command.equals("/updateForm.mem")) {
			System.out.println("updateForm 요청 들어옴");
			
			String id = request.getParameter("id");
			
			MymemBean mb = dao.getOneSelect(id);
			request.setAttribute("mb", mb);
			viewPage = "Ex01_mymemUpdateForm.jsp";
		}
		 
		RequestDispatcher dis = request.getRequestDispatcher(viewPage);
		dis.forward(request, response); 
		
		
	}
}















