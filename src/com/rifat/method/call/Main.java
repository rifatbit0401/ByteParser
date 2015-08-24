package com.rifat.method.call;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class Main {
	
	private static String BASE_PATH = "F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\input\\";
	
	public static void main(String[] args) throws Exception {
		
		Scanner parentClassScanner = new Scanner(new File(BASE_PATH+"A.txt"));
		//Scanner childClassScanner = new Scanner(new File(BASE_PATH+"B.txt"));
		
		List<String>parentClassCode = new ArrayList<String>();
		
		while (parentClassScanner.hasNext()) {
			parentClassCode.add(parentClassScanner.nextLine());
		}
		
		MethodCallSequnceAnalyzer methodCallSequnceAnalyzer = new MethodCallSequnceAnalyzer();
	//	System.err.println(new MethodFinder().getDeclaredMethods(parentClassCode));
		Method method = new MethodFinder().getDeclaredMethods(parentClassCode).get(1);
		Method m = new MethodFinder().getInvokedMethods(new MethodFinder().extractDeclaredMethodBody(method.getSignature(), parentClassCode)).get(0);
		List<Method>invokedMethods = new ArrayList<Method>();
		invokedMethods = methodCallSequnceAnalyzer.getAllInvokedMethods(method, parentClassCode, "com.rifat.input.B");//(method, invokedMethods, "com.rifat.input.B", parentClassCode);
		System.err.println(invokedMethods);
	}
	
	
	
}
