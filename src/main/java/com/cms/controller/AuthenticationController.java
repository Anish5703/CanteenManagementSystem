package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cms.entity.User;
import com.cms.entity.UserType;
import com.cms.services.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authService;
	
	//User Login
	@GetMapping("/login")
	public String loginUser(HttpServletRequest req,HttpServletResponse resp,Model model)
	{
		
     User user = authService.loginByCookie(req, resp);
     if(user != null)
     {
    	 model.addAttribute("user",user);
    	 if(user.getRole() == UserType.admin)
    	 return "admin/adminlogin";  //return 
    	 else
    		 return "homepage"; //return to user homepage
     }
     else
        return "login";      //return to the log in page
	}
	
	
	//validate login
	@PostMapping("login/user")
	public String validateLogin(@RequestParam(name="username") String username,
			                    @RequestParam(name="password") String password,
			                    HttpServletResponse resp,HttpServletRequest req,Model model)
	{

		User user = authService.authenticateUser(username, password, req, resp);

		if(user == null)
		{
			model.addAttribute("loginStatus","Invalid credentials");
			return loginUser(req,resp,model); //return to the login page
		}
		else
		{
			model.addAttribute("loginStatus","Welcome "+user.getUsername());
			if(user.getRole() == UserType.admin)
			return "admin/adminLogin"; //return to the admin homepage
			else
				return "homepage"; //return to the user page
		}
	}
	
	//logout user
	@GetMapping("logout/user")
	public String logoutUser(HttpServletResponse resp,HttpServletRequest req)
	{
		authService.logoutByCookie(req,resp);   
        return "redirect:/home";		
	}

}
