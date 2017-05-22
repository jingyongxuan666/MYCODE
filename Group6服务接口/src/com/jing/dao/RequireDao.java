package com.jing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jing.domain.Project;
import com.jing.domain.Requirement;
import com.jing.utils.DBUtils;

public class RequireDao {
	Connection conn = DBUtils.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public List<Requirement> queryAllFromRequirement(){
		List<Requirement> myList = new ArrayList<Requirement>();
		String sql = "select * from Requirement";
		try {
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 while(rs.next()){
				 Requirement requirement = new Requirement();
				 requirement.setId(rs.getInt("id"));
				 requirement.setName(rs.getString("name"));
				 requirement.setProjectid(rs.getInt("projectid"));
				 requirement.setPriorityid(rs.getInt("priorityid"));
				 requirement.setBegindate(rs.getString("beginDate"));
				 requirement.setEnddate(rs.getString("endDate"));
				 requirement.setStateid(rs.getInt("stateid"));
				 requirement.setUserid(rs.getInt("userid"));
				 requirement.setDetail(rs.getString("detail"));
				 requirement.setLaborHour(rs.getInt("laborHour"));
				 myList.add(requirement);
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
	
	public int insertToRequire(Requirement requirement){
		String sql = "insert into requirement(projectid,name,priorityid,detail,stateid,userid) values(?,?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, requirement.getProjectid());
			pstmt.setString(2, requirement.getName());
			pstmt.setInt(3, requirement.getPriorityid());
			pstmt.setString(4, requirement.getDetail());
			pstmt.setInt(5, requirement.getStateid());
			pstmt.setInt(6, requirement.getUserid());
			int i = pstmt.executeUpdate();
			if(i==1){
				UsersDao udao = new UsersDao();
				int u = udao.updateUserNumofRs(requirement.getUserid());
				System.out.println("增加任务"+u);
			}
			System.out.println(i);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	public int deleteFromRequirement(int id){
		String sql = "delete from requirement where id = ?";
		
		int isDel = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			isDel = pstmt.executeUpdate();
			System.out.println("删除结果"+isDel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isDel;
	}
	public int updateRequirement(String col,int id){
		String sql=null;
		if(col.equals("begindate")){
			sql = "update requirement set stateid = 2,"+col+"=sysdate() where id = ?";
			System.out.println("开始时间");
		}else if(col.equals("enddate")){
			try {
				UsersDao udao = new UsersDao();
				ResultSet r = null;
				PreparedStatement pt = conn.prepareStatement("select * from requirement where id = ?");
				pt.setInt(1, id);
				r = pt.executeQuery();
				if(r != null && r.next()){
					int u = udao.reduceUserNumofRs(r.getInt("userid"));
					System.out.println("减少需求"+u);
				}
				//实时更新进度
				ProjectDao pdao = new ProjectDao();
				pdao.updateProgressFromProject(id);
				
				pt.close();
				r.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			sql = "update requirement set stateid = 3,"+col+"=sysdate() where id = ?";
		}
		int isUpdate = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			isUpdate = pstmt.executeUpdate();
			System.out.println("更新结果"+isUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isUpdate;
	}
	public int queryNumOfThisId(int id){
		String sql = "SELECT COUNT(*) FROM requirement WHERE projectid =?";
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
