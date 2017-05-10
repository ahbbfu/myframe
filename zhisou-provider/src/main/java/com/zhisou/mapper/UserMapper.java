package com.zhisou.mapper;

import java.util.List;

import com.zhisou.api.model.User;

/**
 * @author cuixiang
 *
 */
public interface UserMapper {
	List<User> findAllUser();
}
