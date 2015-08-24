package com.rifat.method.analyzer;

public class DeclaredMethodParser implements IByteCodedMethodParser {

	@Override
	public String getMethodName(String methodSignature) {
		String methodReturnType = getMethodReturnType(methodSignature);
		try {
			return methodSignature.substring(
					methodSignature.indexOf(methodReturnType)
							+ methodReturnType.length() + 1,
					methodSignature.indexOf('('));
		} catch (Exception exception) {
			return ""; // in case of constructor
		}
	}

	@Override
	public String getMethodReturnType(String methodSignature) {
		if (methodSignature.trim().split(" ")[1].equals("static"))
			return methodSignature.trim().split(" ")[2];
		return methodSignature.trim().split(" ")[1];
	}

	@Override
	public String getMethodParameterString(String methodSignature) {
		try {					//only for static block of code e.g. static{//code goes here}
			methodSignature.substring(methodSignature.indexOf('('),
					methodSignature.indexOf(')') + 1);
		} catch (Exception e) {
			return "static";
		}
		return methodSignature.substring(methodSignature.indexOf('('),
				methodSignature.indexOf(')') + 1);
	}

	@Override
	public String getMethodClassName(String methodSignature) {
		return "this";
	}

}
