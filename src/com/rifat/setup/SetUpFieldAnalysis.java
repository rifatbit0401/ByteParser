package com.rifat.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rifat.field.analyzer.Field;
import com.rifat.field.analyzer.FieldFinder;
import com.rifat.field.analyzer.HeaderFieldFinder;

public class SetUpFieldAnalysis {

	private FieldFinder fieldFinder = new FieldFinder();
	private HeaderFieldFinder headerFieldFinder = new HeaderFieldFinder();
	private SetUpMethodAnalysis setUpMethodAnalysis = new SetUpMethodAnalysis();
	
	
	public List<Field> findAllSetUpFields(List<String>parentClassCode, List<String>childClassCode, String parentClassName) throws Exception
	{
		List<Field>setUpFields = new ArrayList<Field>();
		List<Field>allFields = new ArrayList<Field>();
		
		if(!parentClassName.equals("null")&& !parentClassCode.equals("")){ // when the test class is child class and it has no parent class
			allFields.addAll(fieldFinder.getAllFields(parentClassCode));
		}
		allFields.addAll(fieldFinder.getAllFields(childClassCode));
		setUpFields.addAll(getAllHeaderFields(allFields, parentClassCode));
		setUpFields.addAll(getAllHeaderFields(allFields, childClassCode));
		
		setUpFields.addAll(getAllFieldsUsedInSetUpMethod(allFields, parentClassCode, childClassCode, parentClassName));
		return getUniqueFields(setUpFields);
	}
	
	private List<Field>getUniqueFields(List<Field>fields)
	{
		Set<Field>fieldSet = new HashSet<Field>();
		
		for (Field field : fields) {
			fieldSet.add(field);
		}
		
		List<Field> fieldList = new ArrayList<Field>();
		for (Field field : fieldSet) {
			fieldList.add(field);
		}
		return fieldList;
	}
	
	private List<Field> getAllFieldsUsedInSetUpMethod(List<Field>allFields, List<String>parentClassCode, List<String>childClassCode, String parentClassName) throws Exception
	{
		List<Field>usedFields = new ArrayList<Field>();
		List<String>setUpMethodBody = setUpMethodAnalysis.getMergedSetUpMethodAndInvokedMethodsBodyForChildClass(parentClassCode, childClassCode, parentClassName);
		
		for (String line : setUpMethodBody) {
			for (Field field : allFields) {
				if(line.contains(field.name))
					usedFields.add(field);
			}
		}
		return usedFields;
	}
	
	private List<Field> getAllHeaderFields (List<Field>fields, List<String>classCode)
	{
		List<Field>headerFields = new ArrayList<Field>();
		for (Field field : fields) {
			if(headerFieldFinder.isHeaderField(classCode, field))
				headerFields.add(field);
		}
		return headerFields;
	}
	
}
