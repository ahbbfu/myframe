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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author cuixiang
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	SysUserService userService;
	
	@RequestMapping("/all")
	public String helloUser(Model model) {
		
		UsernamePasswordToken token = new UsernamePasswordToken("cuixiang","123456");
//		if (null!=request.getParameter("rememberMe") && !"".equals(request.getParameter("rememberMe"))){
//			token.setRememberMe(true);
//		}
		SecurityUtils.getSubject().login(token);
		List<SysUser> list = userService.selectList(null);
		
		model.addAttribute("users", list);
		return "/user_list";
	}
	
}
