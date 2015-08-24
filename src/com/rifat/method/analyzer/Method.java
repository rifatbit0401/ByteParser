package com.rifat.method.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method {

	public String className;
	public String methodName;
	public String parameterString;
	public String returnType;
	public String accessModifier;
	public String staticString;

	private IByteCodedMethodParser declaredMethodParser;
	private String signature;

	/*
	 * public Method(String methodSignature) { methodSignature =
	 * methodSignature.trim(); declaredMethodParser = new
	 * DeclaredMethodParser(); this.className = declaredMethodParser
	 * .getMethodClassName(methodSignature); this.methodName =
	 * declaredMethodParser.getMethodName(methodSignature); this.parameterString
	 * = declaredMethodParser .getMethodParameterString(methodSignature);
	 * this.staticString = declaredMethodParser
	 * .getMethodReturnType(methodSignature); this.signature = methodSignature;
	 * }
	 */

	public Method() {

	}

	public Method(String methodSignature,
			IByteCodedMethodParser byteCodedMethodParser) {
		methodSignature = methodSignature.trim();
		this.className = byteCodedMethodParser
				.getMethodClassName(methodSignature);
		
		this.methodName = byteCodedMethodParser.getMethodName(methodSignature);
		
		this.parameterString = byteCodedMethodParser
				.getMethodParameterString(methodSignature);

		this.returnType = byteCodedMethodParser
				.getMethodReturnType(methodSignature);
		
		if (byteCodedMethodParser instanceof DeclaredMethodParser)
			this.signature = methodSignature;
		else
			setSignature();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "className: " + this.className + "\n methodName: "
				+ this.methodName + "\n parameterString: " + parameterString
				+ "\n returnType: " + this.returnType;
	}

	/*
	 * public String getSignature() {
	 * 
	 * if (this.signature != null) return this.signature;
	 * 
	 * String signature = getTrimmedString(accessModifier) + " " +
	 * getTrimmedString(staticString) + (staticString == null ? "" : " ") +
	 * getTrimmedString(getStandardReturnType()) + " " +
	 * getTrimmedString(methodName) +
	 * getTrimmedString(getStandardParameterString());
	 * 
	 * signature = signature.replaceAll("/", ".").replace(";", ", ")
	 * .replace(":", "").concat(";").trim();
	 * 
	 * return signature; }
	 */

	public void setSignature() {
		String formattedSignature = accessModifier + " " + staticString
				+ (staticString == null ? "" : " ") + returnType + " "
				+ methodName + " " + parameterString + ";";
		this.signature=formattedSignature;
	}

	public String getSignature() {

/*		if (this.signature != null)
			return this.signature;

		String signature = accessModifier + " " + staticString
				+ (staticString == null ? "" : " ") + returnType + " "
				+ methodName + " " + parameterString + ";";
*/
		return signature;
	}

	/*
	 * private String getTrimmedString(String str) { if (str == null) return "";
	 * else return str.trim(); }
	 * 
	 * private String getStandardParameterString() {
	 * 
	 * parameterString = formatPrimitiveTypeParameters(parameterString)
	 * .replace(")", "").trim();
	 * 
	 * List<String> parameters = Arrays.asList(parameterString.split(";"));
	 * String formattedParameter = "(";
	 * 
	 * for (String parameter : parameters) { if (parameters.indexOf(parameter)
	 * == 0) formattedParameter += parameter; else formattedParameter += ", " +
	 * parameter; }
	 * 
	 * formattedParameter += ")"; return formattedParameter; }
	 * 
	 * private String formatPrimitiveTypeParameters(String paramString) { while
	 * (paramString.contains(";I") || paramString.contains(";D") ||
	 * paramString.contains(";F") || paramString.contains(";C") ||
	 * paramString.contains("(I") || paramString.contains("(D") ||
	 * paramString.contains("(F") || paramString.contains("(C")) {
	 * 
	 * paramString = paramString.replace(";I", ";int;") .replace(";D",
	 * ";double;").replace(";F", ";float;") .replace(";C",
	 * ";char;").replace("(I", "(int;") .replace("(D", "(double;").replace("(F",
	 * "(float;") .replace("(C", "(char;"); } return paramString; }
	 * 
	 * private String getStandardReturnType() { String standardReturnType = "";
	 * 
	 * if (returnType.equals("V")) standardReturnType = "void"; else { if
	 * (returnType.length() == 1) standardReturnType = returnType.replace("I",
	 * "int") .replace("D", "double").replace("F", "float") .replace("C",
	 * "char"); else standardReturnType = returnType.replace("L", ""); } return
	 * standardReturnType.replace(";", ""); }
	 */
}
