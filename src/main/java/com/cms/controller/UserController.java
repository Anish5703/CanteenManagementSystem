package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cms.entity.User;
import com.cms.services.UserService;

@Controller
public class UserController {

	
	@Autowired
	private UserService userService;
	
		
	//signup new user
	@PostMapping("/signup/user")
	public String signupUser(@ModelAttribute User user,@RequestParam(name="confirm-password")String confirmPassword,
		@RequestParam(name="userImage")MultipartFile file,Model model)
	{
		if(!confirmPassword.equals(user.getPassword()))
		{
			model.addAttribute("passwordStatus","Password doesn't match");
			return "/signup";
		}
		
		if((userService.isUsernameExists(user.getUsername()) == true)    //check if user details already exists
			|| (userService.isPhoneExists(user.getPhone()) == true)	)
	{
		if((userService.isUsernameExists(user.getUsername()) == true))
			model.addAttribute("usernameStatus","Username already exists");
		if(userService.isPhoneExists(user.getPhone()) == true)
			model.addAttribute("phoneStatus","Phone already exists");
		return "signup";  //return to the signup page 
	}
		else
		{
			User newUser= userService.addNewUser(user,file);    //add new user to the database
			if(newUser != null)
			{
				model.addAttribute("addStatus","New User Created");
				return "redirect:/login";   //return to the login page
			}
			else
			{
				model.addAttribute("addStatus","Failed to create new user");
				return "redirect:/signup";   //return to the sign up
			}
			
		}
	
}
	

}
