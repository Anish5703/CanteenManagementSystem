package com.cms.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cms.entity.User;
import com.cms.entity.UserType;
import com.cms.exceptions.OnlyAdminIsAuthorizedException;
import com.cms.exceptions.OnlyCustomerIsAuthorizedException;
import com.cms.exceptions.UserNotFoundException;
import com.cms.services.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomerController {
	
@Autowired
private UserService userService;

@Autowired
private AuthenticationService authService;

@Autowired
private FoodService foodService;

@Autowired
private OrderService orderService;

@Autowired 
private CartService cartService;

@GetMapping("/customer/home")
public String getCustomerHomepage(Model model,HttpServletRequest req ,HttpServletResponse resp) throws Exception
{
	//Layer 1 Validation for role authorization
	if(authService.identifyUserRole(resp, req) != UserType.customer)
		throw new OnlyCustomerIsAuthorizedException();
	
	model.addAttribute("foodList", foodService.getAllFood());
	return "/customer/homepage.html";
}

@GetMapping("/customer/viewFood")
public String getCustomerViewFoodPage(HttpServletRequest req ,
		HttpServletResponse resp,Model model) throws Exception
{
	//Layer 1 Validation for role authorization
	if(authService.identifyUserRole(resp, req) != UserType.customer)
		throw new OnlyCustomerIsAuthorizedException();
	
	model.addAttribute("foodList", foodService.getAllFood());
	return "/customer/viewFood.html";
}

@GetMapping("/customer/cart")
public String getCustomerCartPage(Model model,
		HttpServletRequest req ,HttpServletResponse resp) throws Exception
{
	//Layer 1 Validation for role authorization
	if(authService.identifyUserRole(resp, req) != UserType.customer)
		throw new OnlyCustomerIsAuthorizedException();
	
	User user = userService.getByCookieId(resp, req);
	if(user == null)
		throw new UserNotFoundException();
    model.addAttribute("cart",cartService.getCartByUser(user));
    return "/customer/cart.html";
}

@GetMapping("/customer/profile")
public String getCustomerProfilePage(HttpServletResponse resp
		,HttpServletRequest req,Model model) throws Exception
{
	//Layer 1 Validation for role authorization
	if(authService.identifyUserRole(resp, req) != UserType.customer)
		throw new OnlyCustomerIsAuthorizedException();
	
	User user = userService.getByCookieId(resp,req);
	if(user != null)
	{
	model.addAttribute("user",user);
	return "customer/profile.html";
	}
	else
	 throw new UserNotFoundException();
}

@GetMapping("/customer/orderConfirmation")
public String getCustomerOrderConfirmationPage()
{
	return "/customer/orderconfirmation.html";
}

//view all foods order
@GetMapping("/customer/foodorder")
public String showFoodOrder(HttpServletResponse resp,
        HttpServletRequest req,Model model) throws Exception
{
	//authorization
	if(authService.identifyUserRole(resp, req) != UserType.customer)
		throw new OnlyCustomerIsAuthorizedException();
	
	User user = userService.getByCookieId(resp, req);
	model.addAttribute("orders",orderService.getOrderListByUser(user));
	return "/customer/foodorder.html";
}

}