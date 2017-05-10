package com.zhisou.api.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhisou.api.model.User;
import com.zhisou.api.service.UserService;

/**
 * @author cuixiang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testFindAllUser(){

    	 List<User> users = userService.findAllUser();
    	 System.out.println(users.size());
    }
}