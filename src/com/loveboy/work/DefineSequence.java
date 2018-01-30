package com.loveboy.work;

import org.apache.log4j.Logger;

import com.loveboy.dao.dc.DCDaoMapper;
import com.loveboy.util.InitMain;
import com.loveboy.util.SpringUtil;
import com.loveboy.vo.DCDepartmentVo;
import com.loveboy.vo.DCRoseVo;
import com.loveboy.vo.DCUserVo;

/**
 * 获取字段的identity值
 * 原理
 * 	1.初始化一个起始值
 * 	2.运行时先查看数据表中最大的identity值
 *		2.1  若大于初始值,则修改初始值加1
 *		2.2 若小于初始值,则使用当前的初始值累加
 * @author Administrator
 *
 */
public class DefineSequence {
	
	protected static  Logger log = Logger.getLogger(DefineSequence.class);
	
	//用户表did自增的identity初始值
	private static int userDidIdentity = 0;
	
	//角色表did自增的identity初始值
	private static int roseDidIdentity = 0;
	
	//部门表did自增的identity初始值
	private static int deptDidIdentity = 0;
	
	private static DCDaoMapper dcm;
	
	static{
		dcm = (DCDaoMapper) SpringUtil.getBean("DCDaoMapper");	
		try {
			userDidIdentity = Integer.parseInt(InitMain.getInitProp("userDidIdentity"));
			roseDidIdentity = Integer.parseInt(InitMain.getInitProp("roseDidIdentity"));
			deptDidIdentity = Integer.parseInt(InitMain.getInitProp("deptDidIdentity"));
		} catch (Exception e) {
			userDidIdentity = 5000;
			roseDidIdentity = 5000;
			deptDidIdentity = 5000;
			log.info("设置自增值错误，或未设置."+SpringUtil.getExceptionMsg(e));
		}
	}
	/**
	 * 根据表标记获取其did字段下一个自增值
	 * 0  部门表
	 * 1  用户表
	 * 2 角色表
	 * @param tableFlag
	 * @return  Increment identity
	 */
	public static int getNextDidByTableFlag(int tableFlag){
		int identity = 0;
		switch (tableFlag) {
			case 0:
				identity = getNextDeptDidIdentity();
				break;
			case 1:
				identity = getNextUserDidIdentity();
				break;
			case 2:
				identity = getNextRoseDidIdentity();
				break;
			default:
				log.warn("获取did字段下一个自增值错误,没有表标记："+tableFlag);
				break;
		}
		//log.info("tableFlag :"+ tableFlag + "\t identity:"+identity);
		return identity;
	}
	

	/**
	 * 获取用户表did字段下一个自增值
	 * @return
	 */
	private static int getNextUserDidIdentity(){
		Integer maxUserDid = dcm.selectMaxDid(DCUserVo.tableName, "did");
		if(maxUserDid!=null && maxUserDid>=userDidIdentity){
			userDidIdentity = maxUserDid+1;
		}else{
			userDidIdentity++;
		}
		return userDidIdentity;
	}
	
	
	/**
	 * 获取角色表did字段下一个自增值
	 * @return
	 */
	private static int getNextRoseDidIdentity(){
		Integer maxRoseDid = dcm.selectMaxDid(DCRoseVo.tableName, "did");
		if(maxRoseDid!=null && maxRoseDid>=roseDidIdentity){
			roseDidIdentity = maxRoseDid+1;
		}else{
			roseDidIdentity++;
		}
		return roseDidIdentity;
	}
	
	/**
	 * 获取部门表did字段下一个自增值
	 * @return
	 */
	private static int getNextDeptDidIdentity(){
		Integer maxDeptDid = dcm.selectMaxDid(DCDepartmentVo.tableName, "did");
		if(maxDeptDid!=null && maxDeptDid>=roseDidIdentity){
			deptDidIdentity = maxDeptDid+1;
		}else{
			deptDidIdentity++;
		}
		return deptDidIdentity;
	}
}
