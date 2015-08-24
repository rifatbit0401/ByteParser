package com.rifat.method.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class Mapper {

	public static void main(String[] args) throws FileNotFoundException {
		/*Scanner scanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input.txt"));
		List<String> linesOfClassCode = new ArrayList<String>();
		while (scanner.hasNext()) {

			linesOfClassCode.add(scanner.nextLine());
		}
		for (String field : new FieldFinder().getAllFields(linesOfClassCode)) {
			String name = field.split(" ")[field.split(" ").length-1];
			System.out.println(name);
		}
*/		
		Scanner childClassScanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input.txt"));
		List<String> linesOfChildClassCode = new ArrayList<String>();
		while (childClassScanner.hasNext()) {

			linesOfChildClassCode.add(childClassScanner.nextLine());
		}
	
		Scanner parentClassScanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input2.txt"));
		List<String> linesOfParentClassCode = new ArrayList<String>();
		while (parentClassScanner.hasNext()) {

			linesOfParentClassCode.add(parentClassScanner.nextLine());
		}
		
		FieldFinder fieldFinder = new FieldFinder();
		List<String> parentClassFields=fieldFinder.getAllFields(linesOfParentClassCode);
		//System.err.println(parentClassFields);
		MethodFinder methodFinder = new MethodFinder();
		List<String>wholeBodyOfTheClass=new ArrayList<String>();
		
		for (Method method : methodFinder.getDeclaredMethods(linesOfChildClassCode)) {
		//System.err.println(method.getSignature()+": ");
		//	System.out.println(method.methodName);
		//	System.out.println(methodFinder.extractDeclaredMethodBody(method.getSignature(), linesOfChildClassCode));
		//	System.out.println(methodFinder.getInvokedMethods(linesOfChildClassCode));
			wholeBodyOfTheClass.addAll(methodFinder.extractDeclaredMethodBody(method.getSignature(), linesOfChildClassCode));
		}
		List<Method>invokedMethodsInChildClass=methodFinder.getInvokedMethods(linesOfChildClassCode);
		List<Method>declaredMethodsInParentClass=methodFinder.getDeclaredMethods(linesOfParentClassCode);
		
		//System.err.println(invokedMethodsInChildClass);
		//System.err.println(invokedMethodsInChildClass.size());
		//int i=0;
		
		
		for (Method invokedMethod : invokedMethodsInChildClass) {
			if(invokedMethod.methodName.equals("setUp")){
				System.err.println("ok");
				continue;
				}
			/*if(i==30)
				break;
			i++;
			System.err.println(invokedMethod.getSignature()+" ->"+invokedMethod.className);
			*///List<String>wholeBodyOfTheClass=new ArrayList<String>();//=methodFinder.extractDeclaredMethodBody(invokedMethod.getSignature(), linesOfChildClassCode);
			if(invokedMethod.className.contains("org.eclipse.jgit.junit.LocalDiskRepositoryTestCase")){
					for (Method declaredMethod : declaredMethodsInParentClass) {
						if(invokedMethod.methodName.equals(declaredMethod.methodName)&& invokedMethod.parameterString.equals(declaredMethod.parameterString))
						{
							//System.err.println(invokedMethod.toString());// // Field property1:
							List<String>parentClassMethodBody=methodFinder.extractDeclaredMethodBody(declaredMethod.getSignature(), linesOfParentClassCode);
							wholeBodyOfTheClass.addAll(parentClassMethodBody);
						}
			 }
			}
		}
		System.out.println(wholeBodyOfTheClass.toString());
		System.out.println("++++++++++++++++++++++");
	
		for (String line : wholeBodyOfTheClass) {
			for (String field : parentClassFields) {
				String fieldName = field.split(" ")[field.split(" ").length-1].replace(';', ' ').trim();
				String matchingString="// Field "+fieldName+":";
				//System.out.println(line+"=="+matchingString);
				if(line.contains(matchingString))
				{
					System.out.println(fieldName);
				}
			}
		}
		printSetupFields(linesOfParentClassCode);
		
	}
	
	public static void printSetupFields(List<String>parentClassCode)
	{
		MethodFinder methodFinder = new MethodFinder();
		List<Method>declaredMethods = methodFinder.getDeclaredMethods(parentClassCode);
		
		List<String>setUpMethodBody=methodFinder.extractDeclaredMethodBody(declaredMethods.get(1).getSignature(), parentClassCode);
		List<String>parentClassFields=new FieldFinder().getAllFields(parentClassCode);
		Set<String>fieldNameSet = new HashSet<String>();
		
		for (String line : setUpMethodBody) {
			for (String field : parentClassFields) {
				String fieldName = field.split(" ")[field.split(" ").length-1].replace(';', ' ').trim();
				String matchingString="// Field "+fieldName+":";
				//System.out.println(line+"=="+matchingString);
				if(line.contains(matchingString))
				{
					fieldNameSet.add(fieldName);
					//System.out.println(fieldName);
				}
			}
		}
		
		System.out.println(fieldNameSet);
	}
	
}
