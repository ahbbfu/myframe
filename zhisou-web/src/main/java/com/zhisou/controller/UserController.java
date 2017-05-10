package com.zhisou.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhisou.api.model.User;
import com.zhisou.api.service.UserService;

/**
 * 
 * @author cuixiang
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/all")
	public String helloUser(Model model) {
		System.out.println(User.HH);
		List<User> list = userService.findAllUser();
		model.addAttribute("users", list);
		return "/user_list";
	}
	
}
