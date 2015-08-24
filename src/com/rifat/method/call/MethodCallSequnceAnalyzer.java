package com.rifat.method.call;

import java.util.ArrayList;
import java.util.List;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class MethodCallSequnceAnalyzer {
	
	private MethodFinder methodFinder = new MethodFinder();
	private String ownClassName;
	
	public MethodCallSequnceAnalyzer() {
		ownClassName = "this";
	}
	
	public List<Method>getAllInvokedMethods(Method method, List<String>ownClassCode, String superClassName)
	{
		List<Method> invokedMethods = new ArrayList<Method>();
		findAllChainedMethod(method, invokedMethods, superClassName, ownClassCode);
		return invokedMethods;
	}
	
	private void findAllChainedMethod(Method method, List<Method>calledMethods, String superClassName, List<String>ownClassCode)
	{
		if(!(method.className.equals(ownClassName) || method.className.equals(superClassName))){
			return ;
		}
		
		calledMethods.add(method);
	
		List<Method>invokedMethods = new ArrayList<>();
		
		if(method.className.equals(ownClassName)){
			try {
				invokedMethods = methodFinder.getInvokedMethods(methodFinder.getMethodBodyByMethodName(method.methodName, method.parameterString, ownClassCode));
			} catch (Exception e) {
				// method body not found means it's body is in the super class
				//e.printStackTrace();
				method.className = superClassName;
			//	return;
			}
		}
		for (Method m : invokedMethods) {
			findAllChainedMethod(m, calledMethods, superClassName, ownClassCode);
			//break;
		}
	}
	
}
