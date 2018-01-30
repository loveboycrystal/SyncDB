package com.loveboy.vo;

import java.util.Date;

import org.apache.ibatis.jdbc.SQL;

import com.loveboy.util.InitMain;


/**
 * 档案系统用户对象
 * @author chenes
 *
 */
public class DCUserVo {
	
	//用户表名
	public final static String tableName =  "S_USER";//"MYDCUSER";//
	
	//用户默认密码
	public final static String defaultPwd = "123";	
	
	//OA系统字符串 ID
	private String fdId; 
	
	//OA系统字符串 部门ID
	private String fdParentid; 
	
	//ID
	private Integer did;
	
	//部门ID
	private Integer pid;
	
	//用户代码
	private String usercode;
	
	//用户名称
	private String username;
	
	//用户密码
	private String passwd;
	
	//最后修改时间
	private Date fdAlterTime;
	
	//是否删除1 正常，0删除
	private Integer fdIsAvailable=1;
	
	
	//用于saveOrUpdate 用户信息查询是否存在记录变量
	private Integer count;
	
	/**
	 * 查询OA全部用户对象
	 * @return
	 */
	public String selectDCUserList(){
		return new SQL() {{
			SELECT(" * ");
			FROM(DCUserVo.tableName );  
			ORDER_BY(" did desc");
		}}.toString();
	}
	
	public String updateDCUser(final DCUserVo user){
		return new SQL(){
			{
				UPDATE(DCUserVo.tableName);
				if( user.getPid()!=null && user.getPid()!=0 ){ //用户部门id
					SET("pid = #{pid}");
				}
				
				if( user.getFdAlterTime() != null) {
					SET("fd_alter_time = #{fdAlterTime}");
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

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
	
	
	
}
