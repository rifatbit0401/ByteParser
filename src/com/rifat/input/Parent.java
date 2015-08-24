package com.rifat.input;

import java.util.ArrayList;
import java.util.List;

public class Parent {

	public String property1;
	public String property2;
	 String property3;
	private List<String> list=new ArrayList<String>();
	
	
	private void ParentMethod()
	{
		property1="this is property1";
	}
	public void ParentMethod(String str)
	{
		property3="this is property1";
	}
}
