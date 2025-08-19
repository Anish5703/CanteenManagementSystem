package com.cms.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cms.entity.Food;
import com.cms.entity.Order;
import com.cms.entity.StatusType;
import com.cms.repository.FoodRepository;
import com.cms.services.FoodService;
import com.cms.services.OrderService;

@Controller
public class FoodController {

@Autowired 
private FoodService foodService;

@Autowired 
private OrderService orderService;

//add new food item to the database 
@PostMapping("/admin/addfood/save")
public String saveNewFood(@ModelAttribute Food food,
		@RequestParam(name="foodImage")MultipartFile file,Model model)
{
	
	Food newFood = foodService.addNewFood(food, file);
	if(newFood != null)
	{
		model.addAttribute("addStatus","New Food Added Successfully");
		model.addAttribute("foodList",foodService.getAllFood());
		return "redirect:/admin/viewfood";
	}
	else
	{
		model.addAttribute("addStatus","Failed To Add New Food");
		return "redirect:/admin/addfood";
	}
}

//update food
@GetMapping("/admin/updatefood/{id}")
public String showUpdateFood(@PathVariable("id") String id, Model model) {
	int foodId = Integer.parseInt(id);
	Food food = foodService.getFood(foodId);
	if(food != null)
	{
		model.addAttribute("food",food);
		model.addAttribute("updatedFood",food);
		return "redirect:/admin/updatefood";
	}
	else
    return "redirect:/admin/viewfood"; // Thymeleaf template name
}

//save updated food
@PostMapping("/admin/updatefood/{id}/saved")
public String saveUpdateFood(@PathVariable("id")String id,@ModelAttribute("updatedFood") Food updatedFood,
	@RequestParam(name="foodImage")MultipartFile file,Model model)
{
	//set food id
	updatedFood.setId(Integer.parseInt(id));
	//update in repository
	Food food = foodService.updateFood(updatedFood,file);
	if(food != null)
	{
		model.addAttribute("updateStatus","Update Successfully");
		return "redirect:/admin/viewfood";
	}
	else
	{
		model.addAttribute("updateStatus","Update Failed");
		return "redirect:/admin/update/{id}";
	}
}

//delete existing food
@GetMapping("/admin/deletefood/{id}")
public String deleteFood(@PathVariable("id")String id,Model model)
{
	boolean isFoodDeleted = foodService.deleteFood(Integer.parseInt(id));
	if(isFoodDeleted)
	{
		model.addAttribute("deleteStatus","Food is deleted");
		return "redirect:/admin/viewfood";
	}
	else
	{
		model.addAttribute("deleteStatus","Food deletion failed");
		return "redirect:/admin/login";
	}
}


}
