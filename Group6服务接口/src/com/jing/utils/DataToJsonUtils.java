package com.jing.utils;

import java.util.List;


import com.jing.domain.Project;
import com.jing.domain.Requirement;
import com.jing.domain.Users;

public class DataToJsonUtils {
	public static StringBuffer projectToJson(List<Project> projectList) {
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		for (Project project : projectList) {
			sb.append("{").append("\"id\":")
					.append("\"" + project.getId() + "\"").append(",");
			sb.append("\"name\":").append("\"" + project.getName() + "\"")
					.append(",");
			sb.append("\"client\":").append("\"" + project.getClient() + "\"")
					.append(",");
			sb.append("\"description\":")
					.append("\"" + project.getDescription() + "\"").append(",");
			sb.append("\"beginDate\":")
					.append("\"" + project.getBeginDate() + "\"").append(",");
			sb.append("\"endDate\":")
					.append("\"" + project.getEndDate() + "\"").append(",");
			sb.append("\"stateId\":")
					.append("\"" + project.getStateId() + "\"").append(",");
			sb.append("\"progress\":").append(project.getProgress());
			sb.append("}").append(",");

		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");

		return sb;
	}

	public static StringBuffer requirementToJson(
			List<Requirement> requirementList) {
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		for (Requirement requirement : requirementList) {
			sb.append("{").append("\"id\":")
					.append("\"" + requirement.getId() + "\"").append(",");
			sb.append("\"name\":").append("\"" + requirement.getName() + "\"")
					.append(",");
			sb.append("\"projectId\":").append("\"" + requirement.getProjectid() + "\"")
					.append(",");
			sb.append("\"priorityId\":")
					.append("\"" + requirement.getPriorityid() + "\"").append(",");
			sb.append("\"beginDate\":")
					.append("\"" + requirement.getBegindate() + "\"").append(",");
			sb.append("\"endDate\":")
					.append("\"" + requirement.getEnddate() + "\"").append(",");
			sb.append("\"stateId\":")
					.append("\"" + requirement.getStateid() + "\"").append(",");
			sb.append("\"detail\":")
					.append("\"" + requirement.getDetail()+ "\"").append(",");
			sb.append("\"laborHour\":")
					.append("\"" + requirement.getLaborHour()+ "\"").append(",");
			sb.append("\"userId\":").append(requirement.getUserid());
			sb.append("}").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		return sb;
	}
	public static StringBuffer userToJson(
			List<Users> userList) {
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		for (Users user : userList) {
			sb.append("{").append("\"id\":").append("\"" + user.getId() + "\"").append(",");
			sb.append("\"name\":").append("\"" + user.getName() + "\"").append(",");
			sb.append("\"password\":").append("\"" + user.getPassword() + "\"").append(",");
			sb.append("\"deptId\":").append("\"" + user.getDeptId() + "\"").append(",");
			sb.append("\"numOfRs\":").append(user.getNumOfRs());
			sb.append("}").append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		
		return sb;
	}
	
}
