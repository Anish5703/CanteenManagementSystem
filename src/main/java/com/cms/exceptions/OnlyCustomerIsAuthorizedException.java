package com.cms.exceptions;

public class OnlyCustomerIsAuthorizedException extends Exception{
	
	public OnlyCustomerIsAuthorizedException()
	{
		super("This Page is only allowed to customer");
	}

}
