package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cms.entity.Cart;
import com.cms.entity.CartItem;
import com.cms.entity.User;
import com.cms.services.CartService;
import com.cms.services.OrderService;
import com.cms.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/customer/cart/add/{id}")
    public String addItemToCart(@PathVariable("id")String foodId,
                                RedirectAttributes redirect,
                                HttpServletResponse resp,HttpServletRequest req) throws Exception {
    	//Default Quantity to add in cart
    	int quantity = 1;
    	
    	User user = userService.getByCookieId(resp, req);
        cartService.addItemToCart(user, Integer.parseInt(foodId),quantity);
        return "redirect:/customer/cart"; // Redirect to the cart page
    }
 // Decrease quantity of an item in the cart
    @GetMapping("/customer/cart/decrease/{itemId}")
    public String decreaseItemQuantity(@PathVariable("itemId") int itemId,
    		HttpServletResponse resp, HttpServletRequest req) throws Exception {
        User user = userService.getByCookieId(resp, req);
        cartService.decreaseItemQuantity(user, itemId);
        return "redirect:/customer/cart"; // Redirect to the cart page after updating quantity
    }

    // Increase quantity of an item in the cart
    @GetMapping("/customer/cart/increase/{itemId}")
    public String increaseItemQuantity(@PathVariable("itemId") int itemId, 
    		HttpServletResponse resp, HttpServletRequest req) throws Exception {
        User user = userService.getByCookieId(resp, req);
        cartService.increaseItemQuantity(user, itemId);
        return "redirect:/customer/cart"; // Redirect to the cart page after updating quantity
    }
    
    //Remove item from cart
    @GetMapping("/customer/cart/delete/{itemId}")
    public String deleteItemFromCart(@PathVariable("itemId")int itemId,
    		HttpServletResponse resp,HttpServletRequest req) throws Exception
    {
    	User user = userService.getByCookieId(resp, req);
    	 cartService.removeItem(itemId);
    	return "redirect:/customer/cart";
    }
    
    @GetMapping("/customer/checkout")
    public String checkout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // Get the current user from the session or cookies
        User user = userService.getByCookieId(response, request);

        // Place the order for the user
        orderService.placeOrder(user);

        // Redirect to the order confirmation page (or cart page)
        return "redirect:/customer/orderConfirmation";
    }
}
