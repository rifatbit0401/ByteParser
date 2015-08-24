package com.rifat.test.method;
/*
 * test method convention is each test method must start with "test" key word.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;
import com.rifat.method.call.MethodCallSequnceAnalyzer;

public class TestMethodFinder {

	private MethodFinder methodFinder = new MethodFinder();
	private MethodCallSequnceAnalyzer methodCallSequnceAnalyzer = new MethodCallSequnceAnalyzer();
	 
	
	public List<Method> getAllTestMethod(List<String>classCode)
	{
		List<Method>testMethods = new ArrayList<Method>();
		
		for (Method method : methodFinder.getDeclaredMethods(classCode)) {
			
			if(method.methodName.startsWith("test"))
				testMethods.add(method);
		}
		return testMethods;
	}
	
	public List<String> getTestMethodBody(Method testMethod, List<String>ownClassCode, List<String>superClassCode, String superClassName) throws Exception
	{
		List<String>testMethodBody = new ArrayList<String>();
		//testMethodBody.addAll(methodFinder.extractDeclaredMethodBody(testMethod.getSignature(), ownClassCode));
		List<Method>invokedMethods = methodCallSequnceAnalyzer.getAllInvokedMethods(testMethod, ownClassCode, superClassName);
		for (Method method : invokedMethods) {
			if(method.className.equals("this"))
			{
			//	System.out.println(method);
				testMethodBody.addAll(methodFinder.getMethodBodyByMethodName(method.methodName, method.parameterString, ownClassCode));
			}
			
			if(method.className.equals(superClassName))
			{
				//System.out.println(method);
				testMethodBody.addAll(getParentClassMethodBody(method, superClassCode));
			}
		}
		
		return testMethodBody;
	}
	
	private List<String> getParentClassMethodBody (Method method, List<String>classCode) throws Exception
	{
		List<String>methodBody = new ArrayList<>();
		List<Method> invokedMethods = methodCallSequnceAnalyzer.getAllInvokedMethods(method, classCode, "null");
		//System.out.println(method);
		methodBody.addAll(methodFinder.getMethodBodyByMethodName(method.methodName, method.parameterString, classCode));
		
		for (Method m : invokedMethods) {
			if(m.className.equals("this"))
			methodBody.addAll(methodFinder.getMethodBodyByMethodName(m.methodName, m.parameterString, classCode));
		}
		
		return methodBody;
	}

	
}
