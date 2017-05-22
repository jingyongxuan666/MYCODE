package com.jing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jing.domain.Project;
import com.jing.domain.Users;
import com.jing.utils.DBUtils;

public class UsersDao {
	Connection conn = DBUtils.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public List<Users> queryAllFromUsers(){
		List<Users> myList = new ArrayList<Users>();
		String sql = "select * from user";
		try {
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next()){
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setDeptId(rs.getInt("departmentid"));
				user.setNumOfRs(rs.getInt("numofrs"));
				myList.add(user);
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
	public List<Users> queryDevFromUsers(){
		List<Users> myList = new ArrayList<Users>();
		String sql = "select * from user where departmentid = 3";
		try {
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next()){
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setDeptId(rs.getInt("departmentid"));
				user.setNumOfRs(rs.getInt("numofrs"));
				myList.add(user);
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
	public List<Users> queryItemFromUsers(String name){
		List<Users> myList = new ArrayList<Users>();
		String sql = "select * from user where name = ?";
		try {
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, name);
			 rs = pstmt.executeQuery();
			 if(rs !=null && rs.next()){
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setDeptId(rs.getInt("departmentid"));
				user.setNumOfRs(rs.getInt("numofrs"));
				myList.add(user);
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
	
	public int insertToUser(Users user){
		String sql = "insert into user(name,password,departmentid) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, project.getId());
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.getDeptId());
			int i = pstmt.executeUpdate();
			System.out.println(i);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	public int updateUserNumofRs(int id){
		String sql = "update user set numofrs = numofrs + 1 where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, project.getId());
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			System.out.println(i);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	public int reduceUserNumofRs(int id){
		String sql = "update user set numofrs = numofrs - 1 where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, project.getId());
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			System.out.println(i+"¼õÒ»");
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
