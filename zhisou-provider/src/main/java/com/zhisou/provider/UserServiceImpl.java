package com.zhisou.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhisou.api.model.User;
import com.zhisou.api.service.UserService;
import com.zhisou.mapper.UserMapper;

/**
 * @author cuixiang
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	public List<User> findAllUser() {

		return userMapper.findAllUser();
	}

}
