package com.cms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cms.exceptions.OnlyAdminIsAuthorizedException;
import com.cms.exceptions.OnlyCustomerIsAuthorizedException;
import com.cms.exceptions.OnlyCustomerIsAuthorizedException;
import com.cms.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex,RedirectAttributes redirectAttributes) {
        // Log the exception details
        logger.error("Exception caught: {}", ex.getMessage(), ex);

        // Add an error message to the model
        redirectAttributes.addFlashAttribute("errorMsg", "No user login detected. Please log in to access the page.");

        return "redirect:/login";
    }
    
    @ExceptionHandler(OnlyAdminIsAuthorizedException.class)
    public String handleOnlyHostIsAuthorizedException(OnlyAdminIsAuthorizedException ex,RedirectAttributes redirectAttributes)
    {
    	// Log the exception details
        logger.error("Exception caught: {}", ex.getMessage(), ex);
        
        redirectAttributes.addFlashAttribute("errorMsg", "This Page is only available for admin");
        
        return "redirect:/pageNotFound";
    	
    }
    
    @ExceptionHandler(OnlyCustomerIsAuthorizedException.class)
    public String handleOnlyGuestIsAuthorizedException(OnlyCustomerIsAuthorizedException ex,RedirectAttributes redirectAttributes)
    {
    	// Log the exception details
        logger.error("Exception caught: {}", ex.getMessage(), ex);
        
        redirectAttributes.addFlashAttribute("errorMsg", "This Page is only available for customers");
        
        return "redirect:/pageNotFound";
    	
    }
}
