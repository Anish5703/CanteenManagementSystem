package com.cms.exceptions;

public class FoodFailedToCreateException extends RuntimeException{

	
	FoodFailedToCreateException(String msg)
	{
	super("Food Failed To Create "+msg);
	}
}
