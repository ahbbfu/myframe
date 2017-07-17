package com.zhisou.mapper;

import com.zhisou.api.model.SysUser;
import java.util.Map;
import java.util.ArrayList;

/**
 * 
 * SysUserMapper数据库操作接口类
 * 
 * @author cuixiang
 * @since 2017-07-17
 * 
 **/
public interface SysUserMapper{

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	SysUser selectByPrimaryKey(Long id);

	/**
	 * 
	 * 查询（根据条件查询）
	 * 
	 **/
	int selectListCount(Map<String,Object> map);

	/**
	 * 
	 * 查询（根据条件查询）
	 * 
	 **/
	ArrayList<SysUser> selectList(Map<String,Object> map);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(SysUser entity);

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update(SysUser entity);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(Long id);

}