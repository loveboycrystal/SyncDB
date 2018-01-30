package com.loveboy.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.loveboy.dao.dc.DCDaoMapper;
import com.loveboy.thread.DoWork;
import com.loveboy.util.InitMain;
import com.loveboy.util.SpringUtil;
import com.loveboy.vo.DCDepartmentVo;
import com.loveboy.vo.DCUserVo;

public class RelationDbData{
	
	protected static  Logger log = Logger.getLogger(RelationDbData.class);
	/*
	 * 维护部门表和用户表关联关系
	 * 1.查询出所有部门记录和用户记录
	 * 2.遍历档案系统部门表和用户表
	 *   部门表
	 *      1.根据fd_parentid的值查询部门表fd_id得到did
	 *		2.更新本记录pid
	 *		  2.1 无记录pid为null
	 *		  2.2 有记录pid为did
	 *   用户表
	 *   	2.2 根据fd_id的值查询部门表，得到did
	 *    	2.3 更新pid值   
	 */
	
	public void relationDb(String doId,List<DCUserVo> dcUserList,List<DCDepartmentVo> dcDepartList,DCDaoMapper dcm){
		log.info(doId+" relationDb starting...");
		int deptRelationCount =  0;
		int userRelationCount =  0;
		List<String> fdidList = new ArrayList<String>();
		//***更新部门信息数据关联父部门关系******
		//1.获取需要更新的部门数据集合
		for (DCDepartmentVo  dept : dcDepartList) {
			String fdParentid = dept.getFdId() == null ? "null" : dept.getFdId();
			if(DoWork.isFirstWork()){
				fdidList.add(fdParentid);
			}else{
				if(dept.getFdAlterTime().after(DoWork.getNextSyncTime())){//OA未有最新修改部门信息，不需要同步
					if(dept.getFdIsAvailable() != null && dept.getFdIsAvailable() == DoWork.RecordStatu.normalStatu.getValue()){
						fdidList.add(fdParentid);
					}else{
						log.info(doId+" OA dept data del status is not value [fdid="+dept.getFdId()+"  isAvailable="+ (dept.getFdIsAvailable()) +"]");
					}
				}else{
					break;
				}
			}
		}
		//2.更新部门数据关系
		//log.info(doId+" 第["+(DoWork.doCount+1)+"]次 需更新部门关系数量有(update dept relation records)："+fdidList.size());
		
		if(fdidList.size() > 0 ){
			List<DCDepartmentVo> updateDeptList = dcm.selectDeptByFdids(fdidList);
			for (DCDepartmentVo  dept : updateDeptList) {
				try {
					DCDepartmentVo parentDept = dcm.selectDeptIdbyFdid(dept.getFdParentid());
					if(parentDept==null){
						dept.setPid(0);
					}else{
						dept.setPid(parentDept.getDid()); //更新父级部门id
					}
					dept.setFdAlterTime(new Date());  //更新修改时间
					dcm.updateDepartmentData(dept);
					deptRelationCount++;
				} catch (Exception e) {
					e.printStackTrace();
					log.warn(doId+" deptId:["+dept.getFdId()+"]更新部门关系失败！("+SpringUtil.getExceptionMsg(e)+")");
				}
			}
		}
		//***更新用户信息数据关联部门关系******
		//1.获取需要更新的用户数据集合
		fdidList = new ArrayList<String>();
		for (DCUserVo  user : dcUserList) {
			String fdId = user.getFdId() == null ? "null" : user.getFdId();
			if(DoWork.isFirstWork()){
				fdidList.add(fdId);
			}else{
				if(user.getFdAlterTime().after(DoWork.getNextSyncTime())){//OA未有最新修改部门信息，不需要同步
					if(user.getFdIsAvailable() != null && user.getFdIsAvailable() == DoWork.RecordStatu.normalStatu.getValue()){
						fdidList.add(fdId);
					}else{
						log.info(doId+" OA user data del status is not value [fdid："+user.getFdId()+"  isAvailable："+ (user.getFdIsAvailable()) +"]");
					}
				}else{
					break;
				}
			}
		}
		
		//2.更新用户数据关系
		//log.info(doId+" 第["+(DoWork.doCount+1)+"]次 需更新用户关系数量有(update user relation records)："+fdidList.size());
		
		if(fdidList.size() > 0 ){
			List<DCUserVo> updateUserList = dcm.selectUsertByFdids(fdidList);
			for (DCUserVo  user : updateUserList) {
				try {
					DCDepartmentVo parentDept = dcm.selectDeptIdbyFdid(user.getFdParentid());
					if(parentDept!=null){
						user.setPid(parentDept.getDid());
						user.setFdAlterTime(new Date());
						
						dcm.updateUserData(user);
						
						userRelationCount++;
					}else{
						log.warn(doId+" 无法更新用户(fdid："+user.getFdId()+")的关联部门,不存在fdId的部门："+user.getFdParentid());
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.warn(doId+" deptId:["+user.getFdId()+"]更新部门关系失败！("+SpringUtil.getExceptionMsg(e)+")");
				}
			}
		}
		
		log.info(doId+"第["+(DoWork.doCount+1)+"]次更新关联关系结果 userRelationCount:"+userRelationCount+" records,deptRelationCount:"+deptRelationCount+" records.");
	}
	
	
}





















