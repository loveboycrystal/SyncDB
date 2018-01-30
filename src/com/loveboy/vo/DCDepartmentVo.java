package com.loveboy.vo;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;

import com.loveboy.util.InitMain;

/**
 * 档案系统部门对象
 * @author chenes
 *
 */
public class DCDepartmentVo {
	//db部门表名
	public final static  String tableName = "S_GROUP";//"MYDCDEPT";//;
	
	//全宗号默认值
	public final static String defaultQzh = "WLJ";	
	
	
	//OA系统字符串 ID 
	private String fdId; 
	
	//OA系统字符串 上级部门ID
	private String fdParentid; 
	
	//ID
	private Integer did;
	
	//上级部门ID
	private Integer pid;
	
	//全宗号
	private String qzh;
	
	//部门编号
	private Integer gid;
	
	//部门名称
	private String gname;

	//最后修改时间
	private Date fdAlterTime;
	
	//是否删除1 正常，0删除
	private Integer fdIsAvailable=1;
	
	//用于saveOrUpdate 部门信息查询是否存在记录变量
	private Integer count;
	
	public String updateDCDepartment(final DCDepartmentVo dept){
		return new SQL(){
			{
				UPDATE(DCDepartmentVo.tableName);
				
				if( dept.getFdAlterTime() != null) {
					SET("fd_alter_time = #{fdAlterTime}");
				}
				
				//上级部门
				if( dept.getPid() != null) {
					SET("pid = #{pid}");
				}
				
				WHERE("fd_Id = #{fdId}");
			}
		}.toString();
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

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getQzh() {
		return qzh;
	}


	public void setQzh(String qzh) {
		this.qzh = qzh;
	}


	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}


	public Date getFdAlterTime() {
		return fdAlterTime;
	}


	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}


	public static String getTablename() {
		return tableName;
	}


	public Integer getFdIsAvailable() {
		return fdIsAvailable;
	}


	public void setFdIsAvailable(Integer fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}


	public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}

	
	
	
}
