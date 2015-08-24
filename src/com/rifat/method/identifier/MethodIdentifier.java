package com.rifat.method.identifier;

import java.util.ArrayList;
import java.util.List;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class MethodIdentifier {

	private MethodFinder methodFinder = new MethodFinder();
	private final String SET_UP_METHOD_NAME = "setUp";
	private final String SET_UP_METHOD_PARAMETER_STRING = "()";
	
	public Method getSetUpMethod(List<String>classCode) throws Exception
	{
		List<Method>declaredMethods = methodFinder.getDeclaredMethods(classCode);
		for (Method method : declaredMethods) {
			if(method.methodName.equals(SET_UP_METHOD_NAME) && method.parameterString.equals(SET_UP_METHOD_PARAMETER_STRING))
				return method;
		}
		throw new Exception("Method not found");
	}
	
	public List<String> getSetUpMethodBody (List<String> classCode) throws Exception
	{
		return methodFinder.getMethodBodyByMethodName(SET_UP_METHOD_NAME, SET_UP_METHOD_PARAMETER_STRING, classCode);
	}
	
}
