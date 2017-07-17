package com.zhisou.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhisou.api.model.SysUser;
import com.zhisou.api.service.SysUserService;

/**
 * 
 * @author cuixiang
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	SysUserService userService;
	
	@RequestMapping(value = "/login.html")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/doLogin.html")
	public String doLogin(Model model) {
		
		UsernamePasswordToken token = new UsernamePasswordToken("cuixiang","123456");
//		if (null!=request.getParameter("rememberMe") && !"".equals(request.getParameter("rememberMe"))){
//			token.setRememberMe(true);
//		}
		return "/user_list";
	}

}
