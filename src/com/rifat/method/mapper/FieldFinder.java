package com.rifat.method.mapper;

import java.util.ArrayList;
import java.util.List;

public class FieldFinder {

	public List<String> getAllFields(List<String> linesOfByteCodeClass) {

		linesOfByteCodeClass=trimList(linesOfByteCodeClass);
		int indexOfConstructor=linesOfByteCodeClass.indexOf("Code:")-1;
		List<String>fields=new ArrayList<String>();
		
		for(int i=2;i<indexOfConstructor;i++)
		{
			if(linesOfByteCodeClass.get(i).length()==0)
				continue;
			fields.add(linesOfByteCodeClass.get(i));
		}
		return fields;
	}
	
	private List<String>trimList(List<String>stringList)
	{
		List<String>trimedStringList=new ArrayList<String>();
		for (String str : stringList) {
			if(str.trim().length()==0){
				continue;
			}
			trimedStringList.add(str.trim());
		}
		return trimedStringList;
	}
}
