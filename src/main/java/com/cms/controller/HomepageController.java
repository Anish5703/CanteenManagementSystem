package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cms.entity.User;
import com.cms.entity.UserType;
import com.cms.services.AuthenticationService;
import com.cms.services.FoodService;
import com.cms.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomepageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private FoodService foodService;
	
	//return homepage
	@GetMapping("/home")
	public String getHomePage(Model model)
	{
		model.addAttribute("foodList", foodService.getAllFood());

		return "homepage.html";
	}
	
	//return view all food 
	@GetMapping("/viewFood")
	public String getViewFoodPage(Model model)
	{
		model.addAttribute("foodList", foodService.getAllFood());
        return "viewfood.html";
	}
	
	//get signup page
	@GetMapping("/signup")
	public String getSignupPage(Model model)
	{
		if(!model.containsAttribute("user"))
		model.addAttribute("user",new User());
		
		return "signup.html";
	}


	
    //get page not found 
	@GetMapping("/pageNotFound")
	public String showPageNOtFound()
	{
		return "pageNOtFound.html";
	}
	
}
