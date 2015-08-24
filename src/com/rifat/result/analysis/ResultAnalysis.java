package com.rifat.result.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rifat.field.analyzer.Field;
import com.rifat.field.analyzer.FieldFinder;
import com.rifat.field.dependency.FieldDependencyManager;
import com.rifat.method.analyzer.Method;
import com.rifat.setup.SetUpFieldAnalysis;
import com.rifat.test.method.TestMethodFinder;
import com.rifat.utility.Utility;

public class ResultAnalysis {

	private FieldFinder fieldFinder = new FieldFinder();
	private SetUpFieldAnalysis setUpFieldAnalysis = new SetUpFieldAnalysis();
	private TestMethodFinder testMethodFinder = new TestMethodFinder();
	private Utility utility= new Utility();
	FieldDependencyManager fieldDependencyManager = new FieldDependencyManager();
	
	
	public void writeResult(String outputFilePath, String parentClassName, List<String>parentClassCode, List<String>childClassCode) throws Exception
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new  File(outputFilePath)));
		List<Field>parentClassFields = fieldFinder.getAllFields(parentClassCode);
		writeString(bufferedWriter, "parentClassFields:");
		writeFields(bufferedWriter, parentClassFields);
		writeString(bufferedWriter, "==========================================");
		
		List<Field>childClassFields = fieldFinder.getAllFields(childClassCode);
		writeString(bufferedWriter, "child class fields:");
		writeFields(bufferedWriter, childClassFields);
		writeString(bufferedWriter, "==========================================");
		
		List<Field>setUpFields = setUpFieldAnalysis.findAllSetUpFields(parentClassCode, childClassCode, parentClassName);
		List<Field>dependentFields = getAllDependentFields(setUpFields, parentClassCode, childClassCode, parentClassName);
		setUpFields.addAll(dependentFields);
		setUpFields = utility.getUniqueListofFields(setUpFields);
		
		writeString(bufferedWriter, "setup fields");
		writeFields(bufferedWriter, setUpFields);
		writeString(bufferedWriter, "==========================================");
		
		List<Method>testMethods = testMethodFinder.getAllTestMethod(childClassCode);
		
		List<Field>usedSetUpFields = new ArrayList<Field>();
		
		for (Method testMethod : testMethods) {
			writeMethod(bufferedWriter, testMethod);
			List<Field>usedFields = findUsedFields(setUpFields, testMethod, childClassCode, parentClassCode, parentClassName);
			writeString(bufferedWriter, "used fields");
			writeFields(bufferedWriter, usedFields);
			writeString(bufferedWriter, "==========================================");
			
			usedSetUpFields.addAll(usedFields);
		//	bufferedWriter.write(testMethodFinder.getTestMethodBody(testMethod, childClassCode, parentClassCode, parentClassName).toString());
		//	break;
		}
		
		writeString(bufferedWriter, "+++++++++++++++++++++++++++");
		writeString(bufferedWriter, "Dead Fields");
		usedSetUpFields.addAll(getAllDependentFields(usedSetUpFields, parentClassCode, childClassCode, parentClassName));//including all dependent fields
		writeFields(bufferedWriter, getDeadFields(setUpFields, usedSetUpFields));
		
		bufferedWriter.close();
	}
	
	private List<Field>getDeadFields(List<Field>setUpFields, List<Field>usedSetUpFields)
	{
		List<Field>deadFields = new ArrayList<Field>();
		
		usedSetUpFields = utility.getUniqueListofFields(usedSetUpFields);
		for (Field field : setUpFields) {
			if(!isFieldUsed(field, usedSetUpFields))
			{
				deadFields.add(field);
			}
		}
		return deadFields;
	}
	
	private boolean isFieldUsed(Field field, List<Field>usedSetUpFields)
	{
		for (Field fld : usedSetUpFields) {
			if(field.name.equals(fld.name))
				return true;
		}
		return false;
	}
	
	private List<Field> getAllDependentFields(List<Field>setUpFields, List<String>parentClassCode, List<String>childClassCode, String parentClassName) throws Exception
	{
		List<Field>dependentFields = new ArrayList<Field>();
		for (Field field : setUpFields) {
			dependentFields.addAll(fieldDependencyManager.getDepenedentFields(field, parentClassCode, childClassCode, parentClassName));
		}
		return dependentFields;
	}
	
	private void writeString(BufferedWriter bufferedWriter, String string) throws Exception
	{
		bufferedWriter.write(string);
		bufferedWriter.newLine();
	}
	
	private void writeFields(BufferedWriter bufferedWriter, List<Field> fields) throws Exception
	{
		for (Field field : fields) {
			bufferedWriter.write(field.name);
			bufferedWriter.newLine();
		}
	}
	
	private void writeMethods(BufferedWriter bufferedWriter, List<Method> methods) throws Exception
	{
		for (Method method : methods) {
			bufferedWriter.write(method.methodName);
			bufferedWriter.newLine();}
	}
	
	private void writeMethod(BufferedWriter bufferedWriter, Method method) throws Exception
	{
		bufferedWriter.write(method.methodName);
		bufferedWriter.newLine();
	}
	
	private List<Field> findUsedFields(List<Field>setUpFields, Method testMethod, List<String>ownClassCode, List<String>superClassCode, String superClassName) throws Exception
	{
		List<Field>usedFields = new ArrayList<Field>();
		List<String>testMethodBody = testMethodFinder.getTestMethodBody(testMethod, ownClassCode, superClassCode, superClassName);
		
		for (Field field : setUpFields) {
			if(isUsed(field, testMethodBody))
				usedFields.add(field);
		}
		return usedFields;
	}
	
	private boolean isUsed(Field field, List<String>code)
	{
		for (String line : code) {
			if(line.contains(field.name))
				return true;
		}
		
		return false;
	}
}
