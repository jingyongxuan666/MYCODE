package com.jing.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.jing.dao.UsersDao;
import com.jing.domain.Users;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String dept = request.getParameter("dept");
		
		username = new String(username.getBytes("iso8859-1"), "utf-8");
		password = new String(password.getBytes("iso8859-1"), "utf-8");
		dept = new String(dept.getBytes("iso8859-1"), "utf-8");
		
		System.out.println("������"+username);
		System.out.println("���룺"+password);
		System.out.println("���ţ�"+dept);
		int deptId;
		if(dept.equals("��Ŀ����")){
			deptId = 1;
		}else if(dept.equals("������")){
			deptId = 2;
		}else{
			deptId = 3;
		}
		UsersDao udao = new UsersDao();
		List<Users> ulist = udao.queryItemFromUsers(username);
		
		if(ulist.get(0).getName().equals(username) && ulist.get(0).getPassword().equals(password) && ulist.get(0).getDeptId() == deptId){
			response.getOutputStream().write("��¼�ɹ�".getBytes());
		}else{
			response.getOutputStream().write("��¼��Ϣ����".getBytes());
		}
		System.out.println("���ݿ�������"+ulist.get(0).getName());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		doGet(request, response);
		
	}

}
