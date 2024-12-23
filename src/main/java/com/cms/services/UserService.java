package com.cms.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cms.cookies.CookieGenerator;
import com.cms.entity.User;
import com.cms.entity.UserType;
import com.cms.exceptions.UserFailedToCreateException;
import com.cms.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	//getting user image directory from application.properties
	@Value("${user.upload-dir}")
	private String uploadDirectory;
	
	@Autowired
	private UserRepository userRepository;
	
	//get user by user name
	public User getByUsername(String username)
	{
		return userRepository.findByUsername(username);
		
	}
	
	//check if username exists
	public boolean isUsernameExists(String username)
	{
		return (this.getByUsername(username) != null) ? true : false;
	}
	
	//get user by email
	public User getByPhone(String phone)
	{
		return userRepository.findByPhone(phone);
	}
	
	//check if phone exists
	public boolean isPhoneExists(String phone)
	{
		return (this.getByPhone(phone) != null) ? true : false;
	}
	
	//add new user to the database
	public User addNewUser(User user,MultipartFile file)
	{
		user.setRole(UserType.customer);
		String imageId = null;
		
		if(!file.isEmpty())
			 imageId = this.generateUserImageId(file);

	  user.setImageId(imageId);
      User newUser = userRepository.save(user);
      if(newUser == null)
      {
    	  throw new UserFailedToCreateException(user.toString());
    	  
      }
      return newUser;
	}
		
	
	//generate user image new url
		public String generateUserImageId(MultipartFile file)
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
				return null;
			}
			
		}
		
	//get user by cookie
	public User getByCookieId(HttpServletResponse resp, HttpServletRequest req) throws Exception
	{
		String cookieId = CookieGenerator.getCookieId(req, resp);
		if(cookieId == null)
			throw new Exception("Login First !!"+" No Cookie Found on the browser");
		
		User user = userRepository.findByCookieId(cookieId);
			
		return user;
	}
	}
