package com.rifat.method.identifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rifat.method.analyzer.Method;
import com.rifat.method.call.MethodCallSequnceAnalyzer;

public class Main {

	private static String BASE_PATH="F:\\Ananda DU\\android projects\\Test Smell Finder\\bin\\com\\rifat\\method\\identifier\\";
	
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(new File(BASE_PATH+"GitTestCase.txt"));
		List<String>classCode = new ArrayList<String>();
		
		while(scanner.hasNext())
		{
			classCode.add(scanner.nextLine().trim());
		}
		
		MethodIdentifier methodIdentifier = new MethodIdentifier();
		Method setUpMethod = methodIdentifier.getSetUpMethod(classCode);
		System.out.println(setUpMethod);
		
		MethodCallSequnceAnalyzer methodCallSequnceAnalyzer = new MethodCallSequnceAnalyzer();
		System.out.println(methodCallSequnceAnalyzer.getAllInvokedMethods(setUpMethod, classCode, "null"));
		
	}
}
