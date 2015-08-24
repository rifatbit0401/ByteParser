package com.rifat.method.dependency;

import java.util.List;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class DeclaredMethod {

	private List<Method> invokedMethods;
	private MethodFinder methodFinder;
	public Method method;

	public DeclaredMethod(List<String> sourceCodeOfTheClass, Method method) {
		this.method = method;
		methodFinder = new MethodFinder();
		methodFinder.getInvokedMethods(methodFinder.extractDeclaredMethodBody(
				method.getSignature(), sourceCodeOfTheClass));

	}

	public List<Method> getInvokedMethods() {
		return invokedMethods;
	}

}
