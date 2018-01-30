package com.loveboy.dao.dc;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.loveboy.vo.DCDepartmentVo;
import com.loveboy.vo.DCRoseVo;
import com.loveboy.vo.DCUserVo;

@Mapper
@Repository
public interface DCDaoMapper {
	
	
	/**
	 * 获取最大的主键id值
	 * @param tableName 表名
	 * @param fieldName 主键int字段名称
	 * @return 
	 */
	@Select("SELECT max(${fieldName}) count  FROM ${tableName}")
	public Integer selectMaxDid(@Param("tableName") String tableName,@Param("fieldName") String fieldName);
	
	/**
	 * 更新部门信息
	 * @param dCDepartmentVo
	 */
	@UpdateProvider(type=DCDepartmentVo.class, method="updateDCDepartment")
	public void updateDepartmentData(DCDepartmentVo dCDepartmentVo);
	
	/**
	 * 根fdId查询部门信息（fiId， fd_parent_id）
	 * @param fdId
	 * @return
	 */
	@Select("SELECT * FROM "+DCDepartmentVo.tableName+" WHERE fd_id=#{fdId}")
	public DCDepartmentVo selectDeptIdbyFdid(@Param("fdId") String fdId);
	
	/**
	 * 更新用户信息
	 * @param dCUserVo
	 */
	@UpdateProvider(type=DCUserVo.class, method="updateDCUser")
	public void updateUserData(DCUserVo dCUserVo);
	
	
	/**
	 * 插入部门信息
	 * did主键自增
	 * 部门pid，在单独程序中维护关系
	 */
	@Insert("insert into "+DCDepartmentVo.tableName+"(fd_id,fd_parentid,gid,qzh,gname) values(#{fdId},#{fdParentid},#{gid},#{qzh},#{gname})")
	public void insertDepartMentData(DCDepartmentVo dCDepartmentVo);
	
	/**
	 * 插入用户信息
	 * pid主键自增
	 * 部门gid和上级部门pid，在单独程序中维护关系
	 */
	@Insert("insert into  "+DCUserVo.tableName+"(fd_id,fd_parentid,usercode,username,passwd) values (#{fdId},#{fdParentid},#{usercode},#{username},#{passwd})")
	@Options(useGeneratedKeys=true, keyProperty="did", keyColumn="did")  //此注解可以返回插入数据成功条目数,并可以通过获取的dcUserVo对象拿到自增长的did值
	public Integer insertUserData(DCUserVo dCUserVo);
	
	
	/**
	 * 插入用户角色信息，jsid默认值为4
	 * did主键自增
	 */
	@Insert("insert into  "+DCRoseVo.tableName+"(yhid,jsid) values (#{yhid},#{jsid})")
	public void insertRoseData(DCRoseVo dCRoseVo);
	
	/**
	 * 更新角色信息
	 * @param DCRoseVo dCRoseVo
	 */
	public void saveOrUpdateRose(DCRoseVo dCRoseVo);
	
	
	/**
	 * 更新部门信息
	 * @param DCDepartmentVo dCDepartmentVo
	 */
	public Integer saveOrUpdateDept(DCDepartmentVo dCDepartmentVo);
	
	/**
	 * 更新用户信息
	 * @param DCUserVo dCUserVo
	 */
	public Integer saveOrUpdateUser(DCUserVo dCUserVo);
	
	/**
	 * 删除部门信息
	 * @param fdId
	 */
	@Delete("DELETE FROM "+DCDepartmentVo.tableName+" WHERE fd_id=#{fdId}")
	public Integer deleteDept(@Param("fdId") String fdId);
	
	/**
	 * 删除用户信息
	 * @param fdId
	 */
	@Delete("DELETE FROM "+DCUserVo.tableName+" WHERE fd_id=#{fdId}")
	public Integer deleteUser(@Param("fdId") String fdId);
	
	/**
	 * 删除用户角色信息
	 * @param fdId
	 */
	@Delete("DELETE FROM "+DCRoseVo.tableName+" WHERE yhid=#{yhid}")
	public Integer deleteRose(@Param("yhid") Integer yhid);

	/**
	 * 根据fdId查询用户信息
	 * @param fdId
	 * @return
	 */
	@Select("SELECT * FROM "+DCUserVo.tableName+" WHERE fd_id=#{fdId}")
	public DCUserVo selectUserIdbyFdid(@Param("fdId") String fdId);
	
	/**
	 * 根据list集合fids查询用户信息
	 * @param fdids
	 * @return  List<DCUserVo> 
	 */
	public List<DCUserVo> selectUsertByFdids(List<String> fdids);
	
	/**
	 * 根据list集合fids查询部门信息
	 * @param fdids
	 * @return List<DCDepartmentVo>
	 */
	public List<DCDepartmentVo> selectDeptByFdids(List<String> fdids);
}














