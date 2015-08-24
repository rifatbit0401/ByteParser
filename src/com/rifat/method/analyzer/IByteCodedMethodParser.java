package com.rifat.method.analyzer;

public interface IByteCodedMethodParser {

	public abstract String getMethodName(String methodSignature);
	public abstract String getMethodReturnType(String methodSignature);
	public abstract String getMethodParameterString(String methodSignature);
	public abstract String getMethodClassName(String methodSignature);
}
