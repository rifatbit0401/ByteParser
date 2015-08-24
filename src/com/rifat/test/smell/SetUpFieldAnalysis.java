package com.rifat.test.smell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.rifat.field.analyzer.Field;
import com.rifat.field.analyzer.FieldFinder;
import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class SetUpFieldAnalysis {

	public void getAllSetUpFields(List<String>linesOfChildClassCode, List<String>linesOfParentClassCode)
	{
		
		List<String>setUpFieldsInChildClass = getAllFieldNameInSetUpMethod(linesOfChildClassCode);
		List<String>setUpFieldsInParentClass = getAllFieldNameInSetUpMethod(linesOfParentClassCode);
		
		FieldFinder fieldFinder = new FieldFinder();
		List<Field>fieldsInChildClass = fieldFinder.getAllFields(linesOfChildClassCode);
		List<Field>fieldsInParentClass = fieldFinder.getAllFields(linesOfParentClassCode);
		
		List<Field>totalFields=new ArrayList<Field>();
		totalFields.addAll(fieldsInParentClass);
		totalFields.addAll(fieldsInChildClass);
		
		List<String>totalFieldsInSetUpMethod = new ArrayList<String>();
		totalFieldsInSetUpMethod.addAll(setUpFieldsInParentClass);
		totalFieldsInSetUpMethod.addAll(setUpFieldsInChildClass);
		
		for (Field field : totalFields) {
			if(totalFieldsInSetUpMethod.contains(field.name))
			{
				System.err.println(field.name);
			}
		}
		System.err.println(fieldsInParentClass);
		
		
	}
	
	List<String> getAllFieldNameInSetUpMethod(List<String>linesOfClass)
	{
		MethodFinder methodFinder = new MethodFinder();
		List<String>bodyOfSetUpMethod = new ArrayList<>();
				
		for (Method method : methodFinder.getDeclaredMethods(linesOfClass)) {
			if(method.methodName.equals("setUp"))
			{
				bodyOfSetUpMethod=methodFinder.extractDeclaredMethodBody(method.getSignature(), linesOfClass);
				break;
			}
		}
		
		FieldFinder fieldFinder = new FieldFinder();
		Set<String>fieldNames=fieldFinder.getAllUsedFieldName(bodyOfSetUpMethod);
		return convertToList(fieldNames);
	}
	
	private List<String>convertToList(Set<String>set)
	{
		List<String>list=new ArrayList<String>();
		for (String str : set) {
			list.add(str);
		}
		return list;
	}


}
