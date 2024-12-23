package com.cms.services;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cms.entity.Food;
import com.cms.repository.FoodRepository;
import com.cms.exceptions.*;

@Service
public class FoodService {
	
	@Autowired
	private FoodRepository foodRepository;
	
	// directory to save image
//	public String uploadDirectory = "src/main/resources/static/img";
	
	//getting directory from application.properties
	@Value("${food.upload-dir}")
	private String uploadDirectory;
	
    //check if food already exists
	public boolean isFoodExists(String foodName)
	{
		if (foodRepository.findByName(foodName) != null) 
		{
			System.out.println("Food Name "+foodName+"alreadyExists");
            return true;
		}
		else
			return false;
	}
	//add new food to the database 
	public Food addNewFood(Food food,MultipartFile file)
	{
		if(isFoodExists(food.getName()))
			return null;
		
		Food newFood = new Food();

		try {
			String imageName = this.generateFoodImageId(file);
			food.setImageId(imageName);
			newFood = foodRepository.save(food);
		}
		catch(Exception ex)
		{
			System.out.println("Failed To Create New Food "+food.getName()+ex);
			return null;
		}
		return newFood;

		}
	
	//update food already existing in databse
	public Food updateFood(Food food,MultipartFile file)
	{
		try {
		if(!file.isEmpty()) //for new image
		{
			String imageName = this.generateFoodImageId(file);
			food.setImageId(imageName);
		}
		else     //for old image
		{
			Food oldFood = foodRepository.findById(food.getId());
			food.setImageId(oldFood.getImageId());
		}
		return foodRepository.save(food);
		}
		catch(Exception ex)
		{
			System.out.println("Failed to update food "+food.toString()+" Exception:"+ex);
			return null;
		}
	}
	
	//delete existing food
	public boolean deleteFood(int id)
	{
		try {
			  foodRepository.delete(foodRepository.findById(id));
			  return true;
		    }
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	
	//generate food image new url
	public String generateFoodImageId(MultipartFile file)
	{
		try {
			 // Ensure directory exists
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);  // Create directory if not exists
            }
			
		String originalFileName = file.getOriginalFilename();
		//get unique file name
		String uniqueFileName = System.currentTimeMillis()+"_"+originalFileName;
		//set file name with  the default directory
		Path fileNameWithPath = Paths.get(uploadDirectory,uniqueFileName);
		//save file to the directory
        Files.write(fileNameWithPath, file.getBytes());
		return uniqueFileName;
		}
		catch(Exception ex)
		{
			System.out.println("Image File Not Uploaded ");
			ex.printStackTrace();
			return "";
		}
		
	}
	
	//View all food from database 
	public List<Food> getAllFood()
	{
		try {
			return foodRepository.findAll();
		}
		catch(Exception ex)
		{
			System.out.println("No Food Rows Retrieved");
			return null;
		}
	}
	
	//get food object by id
	public Food getFood(int id)
	{
		try {
		return foodRepository.findById(id);
		}
		catch(Exception ex)
		{
			System.out.println("No food available with id"+id+" "+ex);
			return null;
		}
	}

			
	
}
