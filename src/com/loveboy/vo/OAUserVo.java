package com.loveboy.vo;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;

import com.loveboy.util.InitMain;


/**
 * OA系统用户对象
 * @author chenes
 *
 */
public class OAUserVo {
	
	//用户视图名
	public static final String tableName = "USER_DETAIL"; // "MYOAUSER" ;//
	
	//ID
	private String fdId; 
	
	//部门ID
	private String fdParentid; 
	
	//用户代码
	private String fdLoginName;
	
	//用户名称
	private String fdName;
	
	//最后修改时间
	private Date fdAlterTime;

	
	
	/**
	 * 查询OA全部用户对象
	 * @return
	 */
	public String selectOAUserList(){
		return new SQL() {{
			SELECT(" * ");
			FROM( tableName );  
			ORDER_BY(" fd_alter_time desc");
		}}.toString();
	}
	
	
	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getFdParentid() {
		return fdParentid;
	}

	public void setFdParentid(String fdParentid) {
		this.fdParentid = fdParentid;
	}

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}


	public Date getFdAlterTime() {
		return fdAlterTime;
	}


	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}
	
	
	
}
