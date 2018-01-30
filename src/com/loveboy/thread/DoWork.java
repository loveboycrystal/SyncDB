package com.loveboy.thread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.loveboy.dao.dc.DCDaoMapper;
import com.loveboy.dao.oa.OADaoMapper;
import com.loveboy.util.InitMain;
import com.loveboy.util.MultipleDataSource;
import com.loveboy.util.SpringUtil;
import com.loveboy.vo.DCDepartmentVo;
import com.loveboy.vo.DCUserVo;
import com.loveboy.work.RelationDbData;
import com.loveboy.work.SyncDbData;

public class DoWork extends Thread{

	
	protected static  Logger log = Logger.getLogger(DoWork.class);
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static int synchRate = 1;
	
	//开启程序执行的次数
	public static int doCount = 0;
	
	//开启程序的执行时间
	public static final Date firstWorkingDate = new Date();
	
	private  void workCount(){
		doCount++;
	}
	
	//是否第一次执行
	public static boolean isFirstWork(){
		if(DoWork.doCount == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取下次更新时间
	 * 档案系统的fd_alter_time要大于此时间的数据，则是要更新的数据
	 * 此时间 = firstWorkingDate + doCount(执行次数) * synchRate(执行频率)
	 * @return
	 */
	public static Date getNextSyncTime(){
		if(isFirstWork()){
			return firstWorkingDate;
		}else{
			Calendar ca = Calendar.getInstance();
			ca.setTime(DoWork.firstWorkingDate);
			ca.add(Calendar.HOUR, (doCount) * synchRate);
			
			return ca.getTime();
		}
		
	}
	
	public enum RecordStatu{
		
		//1 正常，0 删除
		normalStatu(1),deleteStatu(0);
		
		
		private RecordStatu(Integer value){
			this.value = value;
		}
		
		public  Integer value;

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
		
		
	}
	
	static{
		try {
			//获取改线程同步频率
			synchRate = Integer.parseInt(InitMain.getInitProp("synchRate")); 
		} catch (Exception e) {
			synchRate = 1;
		}
	}
	
	
	
	
	@Override
	public void run() {
		log.info(" Start run program...");
		StringBuffer discript = new StringBuffer();
		//userCount(U/D):1/0 records,deptCount(U/D)：2/0 records,roseCount(U/D)：1/0 records
		discript.append("\r\n\r\n");
		discript.append("********************同步部门表和用户表结果说明************************\r\n");
		discript.append("*\tuserCount(U/D) 用户同步数量(更新/删除)				*\r\n");
		discript.append("*\tdeptCount(U/D) 部门同步数量(更新/删除)				*\r\n");
		discript.append("*\troseCount(U/D) 角色同步数量(更新/删除)				*\r\n");
		discript.append("********************更新关联关系结果结果说明*************************\r\n");
		discript.append("*\tuserRelationCount 用户更新关联数量				*\r\n");
		discript.append("*\tdeptRelationCount 部门更新关联数量				*\r\n");
		discript.append("*************************************************************\r\n");
		log.info(discript.toString());
		//OA系统db持久化bean
		OADaoMapper oam = (OADaoMapper) SpringUtil.getBean("OADaoMapper");	
		//档案系统db持久化bean
		DCDaoMapper dcm = (DCDaoMapper) SpringUtil.getBean("DCDaoMapper");	
		while (true) {
			try {
				Long st = System.currentTimeMillis();
				String doId = UUID.randomUUID().toString();
				
				//先查询OA系统视图
				MultipleDataSource.setDataSourceKey(MultipleDataSource.oaDataSourceKey);
				List<DCUserVo> oaUserList = oam.getOAUserList();
				List<DCDepartmentVo> oaDepartList = oam.getOADepartList();
				
				
				//切换至档案数据库
				MultipleDataSource.setDataSourceKey(MultipleDataSource.dcDataSourceKey);
				//同步OA系统数据
				new SyncDbData().syncDb(doId,oaUserList,oaDepartList,dcm);
				//更新档案系统部门表、用户表的关联关系
				new RelationDbData().relationDb(doId,oaUserList,oaDepartList,dcm);
				Long ed = System.currentTimeMillis();
				log.info(doId+" 第["+(DoWork.doCount+1)+"]次同步数据共耗时："+ ((ed-st)) +"毫秒.\r\n\r\n");
				workCount();
				
				log.info("waiting for the next synchronization at "+DoWork.sdf.format(DoWork.getNextSyncTime())+"...");
				Thread.sleep(1000 * 60 * 60 * synchRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	
}
