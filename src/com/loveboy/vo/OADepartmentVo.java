package com.loveboy.vo;

import java.util.Date;

import com.loveboy.util.InitMain;


/**
 * OA系统部门对象
 * @author chenes
 *
 */
public class OADepartmentVo {
	
	//db部门视图名
	public final static String tableName = "Dept_DETAIL";// "MYOADEPT" ;// ;
	
	//ID
	private String fdId; 
	
	//上级部门ID
	private String fdParentid; 
	
	//不猛编号
	private String fdOrgType;
	
	//部门名称
	private String fdName;
	
	//最后修改时间
	private Date fdAlterTime;

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

	public String getFdOrgType() {
		return fdOrgType;
	}

	public void setFdOrgType(String fdOrgType) {
		this.fdOrgType = fdOrgType;
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
