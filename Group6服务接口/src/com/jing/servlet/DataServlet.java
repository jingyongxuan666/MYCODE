package com.jing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jing.dao.Dao;
import com.jing.dao.ProjectDao;
import com.jing.dao.RequireDao;
import com.jing.dao.UsersDao;
import com.jing.domain.Project;
import com.jing.domain.Requirement;
import com.jing.domain.Users;
import com.jing.service.JsonService;
import com.jing.utils.DataToJsonUtils;

/**
 * Servlet implementation class DataServlet
 */
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String order = request.getParameter("order");
		
		Dao dao = new Dao();
		RequireDao rdao = new RequireDao();
		UsersDao udao = new UsersDao();
		ProjectDao pdao = new ProjectDao();
		if("queryFromProject".equals(order)){//获取所有项目
			PrintWriter out = response.getWriter();
			List<Project> projectList = JsonService.getListProject();
			StringBuffer sb = DataToJsonUtils.projectToJson(projectList);
			out.write(new String(sb));
			out.flush();
			out.close();
			System.out.println("查询成功");
		}else if("deleteFormProject".equals(order)){//删除项目
			String sid = request.getParameter("id");
			int id = Integer.parseInt(sid);
			int isDel = dao.deleteFromProject(id);
			response.getOutputStream().write(isDel);
		}else if("queryFromRequirement".equals(order)){//查询所有需求
			PrintWriter out = response.getWriter();
			List<Requirement> requirementList = JsonService.getListRequirement();
			StringBuffer sb = DataToJsonUtils.requirementToJson(requirementList);
			out.write(new String(sb));
			out.flush();
			out.close();
			System.out.println("查询需求成功");
		}else if("queryFromUsers".equals(order)){//获取所有员工
			PrintWriter out = response.getWriter();
			List<Users> userList = JsonService.getListUsers();
			System.out.println("查询用户成功"+userList.size());
			StringBuffer sb = DataToJsonUtils.userToJson(userList);
			out.write(new String(sb));
			out.flush();
			out.close();
			
		}else if("updateBeginDate".equals(order)){//设置任务开始时间
			String sid = request.getParameter("id");
			System.out.println("更新"+sid);
			int rid = Integer.parseInt(sid);
			int u = rdao.updateRequirement("begindate", rid);
			response.getOutputStream().write(u);
		}else if("updateEndDate".equals(order)){//设置任务结束时间
			String sid = request.getParameter("id");
			int rid = Integer.parseInt(sid);
			int u = rdao.updateRequirement("enddate", rid);
			response.getOutputStream().write(u);
			System.out.println("结束项目");
		}else if("insertToRequire".equals(order)){//插入需求
			String name = request.getParameter("name");
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			int priorityId = Integer.parseInt(request.getParameter("priorityId"));
			String detail = request.getParameter("detail");
			int userId = Integer.parseInt(request.getParameter("userId"));
			
			int result = rdao.insertToRequire(new Requirement(0, projectId, toUtf(name), priorityId, toUtf(detail), "", "", 0, 1, userId));
			response.getOutputStream().write(result);
			
		}else if("insertToUser".equals(order)){//新增用户
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			
			int result = udao.insertToUser(new Users(0, toUtf(name), toUtf(pwd), deptId, 0));
			response.getOutputStream().write(result);
		}else if("queryNumOfRequire".equals(order)){//获取某个项目的需求数量
			String pid = request.getParameter("id");
			int id = Integer.parseInt(pid);
			int result = rdao.queryNumOfThisId(id);
			response.getOutputStream().write(result);
		}else if("queryFromDevUsers".equals(order)){//获取所有开发人员
			PrintWriter out = response.getWriter();
			List<Users> userList = JsonService.getListDevUsers();
			System.out.println("查询用户成功"+userList.size());
			StringBuffer sb = DataToJsonUtils.userToJson(userList);
			out.write(new String(sb));
			out.flush();
			out.close();
		}
//		response.getOutputStream().write(new String(sb).getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private String toUtf(String data) throws UnsupportedEncodingException{
		String newData = new String(data.getBytes("iso8859-1"), "utf-8");
		return newData;
	}

}
