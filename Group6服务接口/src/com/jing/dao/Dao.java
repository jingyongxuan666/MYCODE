package com.jing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jing.domain.Project;
import com.jing.utils.DBUtils;

public class Dao {
	Connection conn = DBUtils.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public List<Project> queryAllFromProject(){
		List<Project> myList = new ArrayList<Project>();
		String sql = "select * from project";
		try {
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next()){
				 Project project = new Project();
				 project.setId(rs.getInt("id"));
				 project.setName(rs.getString("name"));
				 project.setClient(rs.getString("client"));
				 project.setBeginDate(rs.getString("beginDate"));
				 project.setEndDate(rs.getString("endDate"));
				 project.setStateId(rs.getInt("stateid"));
				 project.setProgress(rs.getInt("progress"));
				 project.setDescription(rs.getString("description"));
				 myList.add(project);
			 }
			 return myList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				DBUtils.closeAll(rs, pstmt, conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public int insertToProject(Project project){
		String sql = "insert into project(name,client,description,begindate,enddate,stateid,progress) values(?,?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, project.getId());
			pstmt.setString(1, project.getName());
			pstmt.setString(2, project.getClient());
			pstmt.setString(3, project.getDescription());
			pstmt.setString(4, project.getBeginDate());
			pstmt.setString(5, project.getEndDate());
			pstmt.setInt(6, project.getStateId());
			pstmt.setInt(7, project.getProgress());
			int i = pstmt.executeUpdate();
			System.out.println(i);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	public int deleteFromProject(int id){
		String sql = "delete from project where id = ?";
		
		int isDel = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			isDel = pstmt.executeUpdate();
			System.out.println("É¾³ý½á¹û"+isDel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isDel;
	}
	
}
