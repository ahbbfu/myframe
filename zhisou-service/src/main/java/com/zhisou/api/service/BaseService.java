package com.zhisou.api.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T> {

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	T selectByPrimaryKey(Long id);

	/**
	 * 
	 * 查询（根据条件查询）
	 * 
	 **/
	List<T> selectList(Map<String,Object> map);

	/**
	 * 
	 * 查询数据总数
	 * 
	 */
	int selectListCount(Map<String,Object> map);
	
	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(T entity);

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update(T entity);
	
	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(Long id);
}
