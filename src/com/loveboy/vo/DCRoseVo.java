package com.loveboy.vo;

import com.loveboy.util.InitMain;


/**
 * 档案系统用户角色对象
 * @author chenes
 *
 */
public class DCRoseVo {
	
	//角色表名
	public final static String tableName = "S_USERROLE"; // "MYDCROSE" ;// 
	
	//默认角色值
	public final static Integer defaultJSId = 5;	
	
	//ID
	private Integer did;
	
	//用户ID
	private Integer yhid;
	
	//角色ID
	private Integer jsid;
	
	//用于saveOrUpdate 角色信息查询是否存在记录变量
	private Integer count;

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getYhid() {
		return yhid;
	}

	public void setYhid(Integer yhid) {
		this.yhid = yhid;
	}

	public Integer getJsid() {
		return jsid;
	}

	public void setJsid(Integer jsid) {
		this.jsid = jsid;
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
	
	
	
}
