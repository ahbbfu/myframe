package com.zhisou.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhisou.api.model.SysUser;

/**
 * @author cuixiang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class SysUserServiceTest {

    @Autowired
    private SysUserService userService;

    @Test
    public void testFindAllUser(){

    	SysUser user = new SysUser();
    	
    	user.setLoginName("abc");
    	user.setRealName("测试的");
    	user.setPassword("123456");
    	userService.insertTwo(user);
    	System.out.println(user.getId());
    }
}