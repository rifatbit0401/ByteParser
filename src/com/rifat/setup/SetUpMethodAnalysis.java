package com.rifat.setup;

import java.util.ArrayList;
import java.util.List;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;
import com.rifat.method.call.MethodCallSequnceAnalyzer;
import com.rifat.method.identifier.MethodIdentifier;

public class SetUpMethodAnalysis {

	private MethodIdentifier methodIdentifier = new MethodIdentifier();
	private MethodCallSequnceAnalyzer methodCallSequnceAnalyzer = new MethodCallSequnceAnalyzer();
	private MethodFinder methodFinder = new MethodFinder();
	
	public List<String> getMergedSetUpMethodAndInvokedMethodsBodyForChildClass(List<String>parentClassCode, List<String>childClassCode, String parentClassName) throws Exception{
		List<String>mergedCode = new ArrayList<String>();
		
		//mergedCode.addAll(getParentClassSetUpMethodBody(parentClassCode));
		mergedCode.addAll(getChildClassSetUpMethodBody(parentClassName, parentClassCode, childClassCode));
		
		return mergedCode;
	}
	
	private List<String> getParentClassSetUpMethodBody(List<String>parentClassCode) throws Exception
	{
		List<String>parentSetUpMethodBody = methodIdentifier.getSetUpMethodBody(parentClassCode);
		List<Method>invokedMethods = methodCallSequnceAnalyzer.getAllInvokedMethods(methodIdentifier.getSetUpMethod(parentClassCode), parentClassCode, "null");
		
		for (Method method : invokedMethods) {
			if(method.className.equals("this"))
			{
				parentSetUpMethodBody.addAll(methodFinder.getMethodBodyByMethodName(method.methodName, method.parameterString, parentClassCode));
			}
		}
		return parentSetUpMethodBody;
	}
	
	private List<String> getChildClassSetUpMethodBody(String parentClassName, List<String>parentClassCode, List<String>childClassCode) throws Exception
	{
		List<String>setUpMethodBody = methodIdentifier.getSetUpMethodBody(childClassCode);
		List<Method>invokedMethods = methodCallSequnceAnalyzer.getAllInvokedMethods(methodIdentifier.getSetUpMethod(childClassCode), childClassCode, parentClassName);
		
		for (Method method : invokedMethods) {
			if(method.className.equals("this"))
			{
				setUpMethodBody.addAll(methodFinder.getMethodBodyByMethodName(method.methodName, method.parameterString, childClassCode));
			}
			if(method.className.equals(parentClassName))
			{
				setUpMethodBody.addAll(getParentClassMethodBody(method, parentClassCode));
			}
			
		}
		return setUpMethodBody;
	}
	
	private List<String> getParentClassMethodBody (Method method, List<String>classCode) throws Exception
	{
		List<String>methodBody = new ArrayList<>();
		List<Method> invokedMethods = methodCallSequnceAnalyzer.getAllInvokedMethods(method, classCode, "null");
		methodBody.addAll(methodFinder.getMethodBodyByMethodName(method.methodName, method.parameterString, classCode));
		
		for (Method m : invokedMethods) {
			if(m.className.equals("this"))
			methodBody.addAll(methodFinder.getMethodBodyByMethodName(m.methodName, m.parameterString, classCode));
		}
		
		return methodBody;
	}
	
}
