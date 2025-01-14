package com.cms.exceptions;

public class OnlyAdminIsAuthorizedException extends Exception{

	public OnlyAdminIsAuthorizedException()
	{
		super("Access Denied !! This Page is Only for admin !!");
	}
}
