// method signature format "java/lang/Integer.valueOf:(I)Ljava/lang/Integer;"
package com.rifat.method.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InvokedMethodParser implements IByteCodedMethodParser {

	@Override
	public String getMethodName(String methodSignature) {
		if (isOwnMethodOftheClass(methodSignature))
			return methodSignature.substring(0, methodSignature.indexOf(':'));
		else
			return methodSignature.substring(methodSignature.indexOf('.') + 1,
					methodSignature.indexOf(':'));
	}

	@Override
	public String getMethodReturnType(String methodSignature) {
		return getStandardReturnType(methodSignature.substring(methodSignature
				.indexOf(')') + 1));
	}

	private String getStandardReturnType(String returnType) {
		String standardReturnType = "";
		if (returnType.equals("V"))
			standardReturnType = "void";
		else {
			if (returnType.length() == 1)
				standardReturnType = returnType.replace("I", "int")
						.replace("D", "double").replace("F", "float")
						.replace("C", "char");
			else
				standardReturnType = returnType.replace("L", "");
		}
		
		if(standardReturnType.charAt(0)=='[') //check for array ex.[java.lang.String
		{
			standardReturnType = standardReturnType.replace("[", "");
			standardReturnType+="[]";
		}
		
		return standardReturnType.replace(";", "").replace("/", ".").trim();
	}

	@Override
	public String getMethodParameterString(String methodSignature) {
			
		return getStandardParameterString(methodSignature.substring(
				methodSignature.indexOf('('), methodSignature.indexOf(')') + 1));
	}

	private String getStandardParameterString(String parameterString) {

		parameterString = formatPrimitiveTypeParameters(parameterString)
				.replace("(", "").replace(")", "").trim();
		
		List<String> parameters = Arrays.asList(parameterString.split(";"));
		String formattedParameter = "(";
		
		for (String parameter : parameters) {
			if(parameter.equals("")){ // parameter null
				continue;
			}
			if (parameters.indexOf(parameter) != 0 || parameters.get(0)!=parameter) { //initially "parameters.indexOf(parameter) != 0" to check wether it's the first element
				formattedParameter += ", ";
				
			} else {
				
			}
			if (parameter.length() > 0 && parameter.charAt(0) == 'L') {
				parameter = new StringBuilder(parameter).deleteCharAt(0)
						.toString();
			}

			if(parameter.charAt(0)=='[') //check for array ex.[java.lang.String
			{
				parameter = parameter.replace("[", "");
				parameter+="[]";
				if(parameter.charAt(0)=='L')
					parameter = parameter.replace("L", "");
			}
			
			formattedParameter += parameter;
		}

		formattedParameter += ")";
		return formattedParameter.replace("/", ".").trim();
	}

	private String formatPrimitiveTypeParameters(String paramString) {
		while (paramString.contains(";I") || paramString.contains(";D")
				|| paramString.contains(";F") || paramString.contains(";C")
				|| paramString.contains("(I") || paramString.contains("(D")
				|| paramString.contains("(F") || paramString.contains("(C")) {

			paramString = paramString.replace(";I", ";int;")
					.replace(";D", ";double;").replace(";F", ";float;")
					.replace(";C", ";char;").replace("(I", "(int;")
					.replace("(D", "(double;").replace("(F", "(float;")
					.replace("(C", "(char;");
		}
		return paramString;
	}

	@Override
	public String getMethodClassName(String methodSignature) {
		if (isOwnMethodOftheClass(methodSignature))
			return "this";
		else
			return methodSignature.substring(0, methodSignature.indexOf('.'))
					.replace("/", ".").trim();
	}

	private boolean isOwnMethodOftheClass(String methodSignature) {
		return (methodSignature.contains(".") == false);
	}

}
