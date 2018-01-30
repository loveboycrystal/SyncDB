package com.loveboy.work;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.loveboy.dao.dc.DCDaoMapper;
import com.loveboy.thread.DoWork;
import com.loveboy.util.InitMain;
import com.loveboy.util.SpringUtil;
import com.loveboy.vo.DCDepartmentVo;
import com.loveboy.vo.DCRoseVo;
import com.loveboy.vo.DCUserVo;

/**
 * 同步OA系统部门和用户数据到档案系统的用户表，部门表，角色表（自己生成）
 * @author chenes
 *
 */
public class SyncDbData{
	
	
	protected static  Logger log = Logger.getLogger(SyncDbData.class);
	

	public void syncDb(String doId,List<DCUserVo> dcUserList,List<DCDepartmentVo> dcDepartList,DCDaoMapper dcm){
		log.info(doId+" syncDb starting...");
		try {
			int deptSyncCount[] =  new int[2];
			//插入部门信息数据
			for (DCDepartmentVo  dept : dcDepartList) {
				try {
					if(DoWork.isFirstWork() || dept.getFdAlterTime().after(DoWork.getNextSyncTime()) ){//OA未有最新修改部门信息，不需要同步
						if(dept.getFdIsAvailable() != null && dept.getFdIsAvailable() == DoWork.RecordStatu.deleteStatu.getValue()){
							int deleteCount = dcm.deleteDept(dept.getFdId());
							deptSyncCount[0] += deleteCount;
						}else{
							dept.setDid(DefineSequence.getNextDidByTableFlag(0));
							dept.setQzh(DCDepartmentVo.defaultQzh);
							dept.setFdIsAvailable(1);
							int saveOrUpdateDeptCount = dcm.saveOrUpdateDept(dept);
							deptSyncCount[1] += saveOrUpdateDeptCount;
						}
					}else{
						if(deptSyncCount[1]!=0 && deptSyncCount[0]!=0){
							log.info(doId+" OA部门信息最新修改时间为(dept not need synchron)："+DoWork.sdf.format(dept.getFdAlterTime())+" 不需要更新！！！");
						}
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.warn(doId+" deptId:["+dept.getFdId()+"]插入部门失败！("+SpringUtil.getExceptionMsg(e)+")");
				}
			}

			int userSyncCount[] = new int[2];
			int roseSyncCount[] = new int[2];
			//插入用户信息数据
			for (DCUserVo  user : dcUserList) {
				try {
					if(DoWork.isFirstWork() || user.getFdAlterTime().after(DoWork.getNextSyncTime())){ //OA未有最新修改用户信息，不需要同步
						if(user.getFdIsAvailable() != null && user.getFdIsAvailable() ==  DoWork.RecordStatu.deleteStatu.getValue()){
							
							//先删除角色信息
							DCUserVo userTmp = dcm.selectUserIdbyFdid( user.getFdId());
							int deleteRoseCount = 0;
							if(userTmp!=null && userTmp.getDid() != null){
								deleteRoseCount = dcm.deleteRose(userTmp.getDid());
							}
							roseSyncCount[0] += deleteRoseCount;
							
							//删除用户信息
							int deleteCount = dcm.deleteUser(user.getFdId());
							userSyncCount[0] += deleteCount;
							
						}else{
							user.setDid(DefineSequence.getNextDidByTableFlag(1));
							user.setPasswd(DCUserVo.defaultPwd);
							//user.setFdAlterTime(new Date());
							//更新用户信息
							int updateCount = dcm.saveOrUpdateUser(user);
							userSyncCount[1]++;
							
							//更新角色信息
							if(updateCount == 1 && user.getFdId()!= null){
								DCUserVo userTmp = dcm.selectUserIdbyFdid( user.getFdId());
								if(userTmp!=null && userTmp.getDid() != null){
									user.setDid(userTmp.getDid()); //引用传递，为下一步更新关联关系使用
									
									DCRoseVo dCRoseVo = new DCRoseVo();
									dCRoseVo.setDid(DefineSequence.getNextDidByTableFlag(2));
									dCRoseVo.setYhid(userTmp.getDid());
									dCRoseVo.setJsid(DCRoseVo.defaultJSId);
									dcm.saveOrUpdateRose(dCRoseVo);
									roseSyncCount[1]++;
								}
							}
						}
					}else{
						if(userSyncCount[0]!=0 && userSyncCount[1] != 0){
							log.info(doId+" OA用户信息最新修改时间为(user not need synchron)："+DoWork.sdf.format(user.getFdAlterTime())+" 不需要更新！！！");
						}
						break;
					}
				} catch (Exception e) {
					log.warn(doId+" userId:["+user.getFdId()+"]插入用户失败！("+SpringUtil.getExceptionMsg(e)+")");
				}
			}
			
			log.info(doId+"第["+(DoWork.doCount+1)+"]次同步部门表和用户表结果,userCount(U/D):"+userSyncCount[1]+"/"+userSyncCount[0]+" records,deptCount(U/D)："+deptSyncCount[1]+"/"+ deptSyncCount[0] +" records,roseCount(U/D)："+roseSyncCount[1]+"/"+roseSyncCount[0]+" records.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
