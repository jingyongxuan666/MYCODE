package com.jing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.jing.utils.DBUtils;

public class ProjectDao {
	Connection conn = DBUtils.getConnection();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public int queryNumOfFinish(int id){
		String sql = "SELECT COUNT(*) FROM requirement WHERE stateid = 3 AND projectid in(SELECT projectid FROM requirement WHERE id=?)";
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
	public int queryNumOfRequire(int id){
		String sql = "SELECT COUNT(*) FROM requirement WHERE projectid in(SELECT projectid FROM requirement WHERE id=?)";
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
	public int updateProgressFromProject(int id){
		double finish = queryNumOfFinish(id);
		double unFinish = queryNumOfRequire(id);
		double i = finish/unFinish;
		//保留两位小数并换算成百分比
		DecimalFormat df = new DecimalFormat("0.00");
		int result = (int) (Double.parseDouble(df.format(i))*100);
		System.out.println("完成"+finish);
		System.out.println("总共"+unFinish);
		String sql = "update project set progress = ? where id in(select projectid from requirement where id=?)";
		int a=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, result);
			pstmt.setInt(2, id);
			a = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("更新进度"+a);
		return a;
	}

}
