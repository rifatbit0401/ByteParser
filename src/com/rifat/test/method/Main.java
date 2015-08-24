package com.rifat.test.method;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rifat.method.analyzer.Method;

public class Main {

	public static void main(String[] args) throws Exception {
		List<String>parentClassCode = com.rifat.setup.Main.getClassCode(com.rifat.setup.Main.BASE_PATH+"GitTestCase.txt");
		List<String>childClassCode = com.rifat.setup.Main.getClassCode(com.rifat.setup.Main.BASE_PATH+"indexDiffCacheTest.txt");
		TestMethodFinder testMethodFinder = new TestMethodFinder();
		List<Method>testMethods = testMethodFinder.getAllTestMethod(childClassCode);
		System.out.println(testMethods.size());
		
		for (Method method : testMethods) {
			List<String>testMethodBody = testMethodFinder.getTestMethodBody(method, childClassCode, parentClassCode, "org.eclipse.egit.core.test.GitTestCase");
			System.err.println(testMethodBody);	
		}
	}
	
}
