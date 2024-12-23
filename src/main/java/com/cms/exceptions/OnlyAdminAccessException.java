package com.cms.exceptions;

public class OnlyAdminAccessException extends Exception{

	public OnlyAdminAccessException()
	{
		super("Access Denied !! This Page is Only for admin !!");
	}
}
