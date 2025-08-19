package com.cms.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cms.entity.Food;
import com.cms.entity.User;
import com.cms.entity.UserType;
import com.cms.exceptions.OnlyAdminIsAuthorizedException;
import com.cms.exceptions.UserNotFoundException;
import com.cms.services.AuthenticationService;
import com.cms.services.FoodService;
import com.cms.services.OrderService;
import com.cms.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class AdminController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private AuthenticationService authService;
	
	//add food page
	@GetMapping("/admin/addfood")
	public String showAddFoodPage(HttpServletResponse resp,
			             HttpServletRequest req,Model model) throws Exception
	{
	   //authorization
		if(authService.identifyUserRole(resp, req) != UserType.admin)
			throw new OnlyAdminIsAuthorizedException();
		
		model.addAttribute("food",new Food());
		return "admin/addfood.html";
	}
	
	//admin login page
	@GetMapping("/admin/login")
	public String showAdminLoginPage(HttpServletResponse resp,
            HttpServletRequest req,Model model) throws Exception
	{
		   //authorization
			if(authService.identifyUserRole(resp, req) != UserType.admin)
				throw new OnlyAdminIsAuthorizedException();
			
			
	    model.addAttribute("foodList",foodService.getAllFood());
		return "admin/adminlogin.html";
	}
	
	//food dashboard
	@GetMapping("/admin/viewfood")
	public String showViewFoodPage(HttpServletResponse resp,
            HttpServletRequest req,Model model) throws Exception
	{
		   //authorization
			if(authService.identifyUserRole(resp, req) != UserType.admin)
				throw new OnlyAdminIsAuthorizedException();
		
		model.addAttribute("foodList",foodService.getAllFood());
		model.addAttribute("updatedFood",new Food());
		return "admin/viewfood.html";
	}
	//update food menu
	@GetMapping("/admin/updatefood")
	public String showUpdateFoodPage(HttpServletResponse resp,
            HttpServletRequest req) throws Exception
	{
		   //authorization
			if(authService.identifyUserRole(resp, req) != UserType.admin)
				throw new OnlyAdminIsAuthorizedException();
		
		return "admin/updatefood.html";
	}

	//view all foods order
	@GetMapping("/admin/foodorder")
	public String showFoodOrder(HttpServletResponse resp,
            HttpServletRequest req,Model model) throws Exception
	{
		//authorization
		if(authService.identifyUserRole(resp, req) != UserType.admin)
			throw new OnlyAdminIsAuthorizedException();
		model.addAttribute("orders",orderService.getOrderList());
		model.addAttribute("statusType",Arrays.asList("pending", "ready", "delivered", "cancelled"));
		return "admin/foodorder.html";
	}
	
	//update order status
	
	@PostMapping("/admin/updateOrderStatus")
	public String updateOrderStatus(@RequestParam("orderId")String orderId, 
	                                 @RequestParam Map<String, String> allParams, 
	                                 Model model) {
	    try {
	    	
	        System.out.println("All parameters: " + allParams);

	        // Get the specific status using the orderId
	        String statusKey = "status_" +orderId;
	        String newStatus = allParams.get(statusKey);
    		System.out.println("Ready to Update orderstatus_"+newStatus);


	        if (newStatus != null) {
	            // Update order status
	            orderService.updateOrderStatus(Integer.parseInt(orderId), newStatus);
	    		System.out.println("Update orderstatus "+newStatus);

	        }


	    } catch (Exception e) {
	        model.addAttribute("error", "Failed to update order status.");
	        System.out.println("Failed to update status ");
	    }
		return "redirect:/admin/foodorder";
	}


	  
	//view admin profile
	@GetMapping("/admin/profile")
	public String showAdminProfile(HttpServletResponse resp,
            HttpServletRequest req,Model model) throws Exception
	{
		//authorization
		if(authService.identifyUserRole(resp, req) != UserType.admin)
			throw new OnlyAdminIsAuthorizedException();
		
		User user = userService.getByCookieId(resp,req);
		if(user != null)
		{
		model.addAttribute("user",user);
		return "admin/adminprofile.html";
		}
		else
		 throw new UserNotFoundException();
	}
	


	

}
