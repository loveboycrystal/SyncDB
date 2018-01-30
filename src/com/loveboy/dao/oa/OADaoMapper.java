package com.loveboy.dao.oa;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.loveboy.vo.DCDepartmentVo;
import com.loveboy.vo.DCUserVo;
import com.loveboy.vo.OADepartmentVo;
import com.loveboy.vo.OAUserVo;

@Mapper
@Repository
public interface OADaoMapper {
	

	//查询出OA系统部门视图数据，并将记录转化为档案系统部门对象
	@Select("SELECT * FROM "+OADepartmentVo.tableName+" order by fd_alter_time desc")
	@Results(
		value = {
			@Result(property="fdId",column="fd_id"),
			@Result(property="fdParentid",column="fd_parentid"),
			@Result(property="gid",column="fd_org_type"),
			@Result(property="gname",column="fd_name"),
			@Result(property="fdAlterTime",column="fd_alter_time"),
			@Result(property="fdIsAvailable",column="fd_is_available")
		}	
	)
	public  java.util.List<DCDepartmentVo> getOADepartList();
	
	
	//查询出OA系统用户视图数据，并将记录转化为档案系统用户对象
	@SelectProvider(type=OAUserVo.class, method="selectOAUserList")
	@Results(
			value = {
				@Result(property="fdId",column="fd_id"),
				@Result(property="fdParentid",column="fd_parentid"),
				@Result(property="usercode",column="fd_login_name"),
				@Result(property="username",column="fd_name"),
				@Result(property="fdAlterTime",column="fd_alter_time"),
				@Result(property="fdIsAvailable",column="fd_is_available")
			}	
		)
	public java.util.List<DCUserVo> getOAUserList();
	

}














