package com.cms.cookies;



import java.util.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieGenerator {
	
	static List<String> ids = new ArrayList<>();
	
	
	static String sample = "ABCDEFGHIJKLMNOPQRSTabcdefghijklmnopqrstuvwxyz0123456789@#$&";
	
	
	public String generateID()
	{
		String result="";
		while(ids.contains(result) || result=="")
		{
		StringBuilder sb = new StringBuilder("");
		Random random = new Random();
		int index = 0;
		for(int i=1;i<=10;i++)
		{
			index = random.nextInt(sample.length());
			sb.append(sample.charAt(index));
		}
		 result = sb.toString();
		 System.out.println("Result generated");
		 System.out.println("Result "+result);
		
		}
		ids.add(result);
			return result;
	}
	
	public void setCookieId(HttpServletRequest req, HttpServletResponse resp,String cookieId)
	{
		Cookie cookie = new Cookie("id",cookieId);
		cookie.setPath("/");
		cookie.setMaxAge(2400);
		resp.addCookie(cookie);
		
	}
	
	public static String getCookieId(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		
		String cookieID = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("id")) {
					cookieID = cookie.getValue();
//					System.out.println(String.format(" %s -> %s", cookie.getName(), cookie.getValue()));
					break;
				}
			}
		}
		else
			System.out.println("Cookie not found");
                              
		return cookieID;   

	}
	
	

}
