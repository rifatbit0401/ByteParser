package com.rifat.method.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MethodFinder {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input.txt"));
		List<String> linesOfClassCode = new ArrayList<String>();
		while (scanner.hasNext()) {

			linesOfClassCode.add(scanner.nextLine());
		}
		MethodFinder methodFinder = new MethodFinder();
		for (Method method : methodFinder.getDeclaredMethods(linesOfClassCode)) {

			System.out.println("method is: " + method.getSignature());
			System.out.println("invoked methods: ");
			for (Method m : methodFinder.getInvokedMethods(methodFinder
					.extractDeclaredMethodBody(method.getSignature(),
							linesOfClassCode))) {
				System.out.println(m.getSignature());
				//System.out.println(m.className);
			}
		}

	}

	public List<Method> getDeclaredMethods(List<String> byteCodeLines) {

		byteCodeLines = trimByteCodeLines(byteCodeLines);
		List<Method> declaredMethods = new ArrayList<Method>();
		String previousLine = "";

		for (String currentLine : byteCodeLines) {
			if (currentLine.trim().equals("Code:")) {
				String byteCodeMethodSignatureLine = previousLine;
				IByteCodedMethodParser declaredMethodParser = new DeclaredMethodParser();
				Method declaredMethod = new Method(byteCodeMethodSignatureLine,
						new DeclaredMethodParser());
				declaredMethods.add(declaredMethod);
			}
			previousLine = currentLine;
		}

		return declaredMethods;
	}

	public List<String> extractDeclaredMethodBody(
			String byteCodeMethodSignature, List<String> linesOfByteCodeClass) {

		linesOfByteCodeClass = trimByteCodeLines(linesOfByteCodeClass);
		int indexOfMethod = linesOfByteCodeClass
				.indexOf(byteCodeMethodSignature);
		
		if(indexOfMethod==-1)
			return new ArrayList<>();
		
		List<String> byteCodeOfMethodBody = new ArrayList<String>();
		boolean foundBodyCode = false;

		for (int i = indexOfMethod + 2; i < linesOfByteCodeClass.size(); i++) {
			char firstCharacter = linesOfByteCodeClass.get(i).trim().charAt(0);
			if (firstCharacter >= '0' && firstCharacter <= '9') {
				byteCodeOfMethodBody.add(linesOfByteCodeClass.get(i));
			}
			if (linesOfByteCodeClass.get(i).contains("return"))
				break;
		}
		return byteCodeOfMethodBody;
	}

	// 36: invokevirtual #38 // Method
	// myMethod1:(Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/String;Lmethod/analyzer/ParentClass;IDFC)V

	public List<Method> getInvokedMethods(List<String> byteCodeLines) {

		byteCodeLines = trimByteCodeLines(byteCodeLines);
		List<Method> invokedMethods = new ArrayList<Method>();
		String methodIdentifierKeyword = "// Method";
		
		for (String line : byteCodeLines) {
			if (line.contains(methodIdentifierKeyword)
					&& !line.contains("\"<init>\"")) {
				String invokedMethodCallString = line.substring(
						line.indexOf(methodIdentifierKeyword)
								+ methodIdentifierKeyword.length()).trim();

				invokedMethods.add(new Method(invokedMethodCallString,
						new InvokedMethodParser()));
			}
		}
		
		return invokedMethods;
	}

	public static List<String> getMethodBodyByMethodName(String name, String parameterStringWithBracets, List<String> classCode) throws Exception
	{
		int indexOfMethodFirstLine=-1;
		
		for (String line : classCode) {
			if(line.contains(name+parameterStringWithBracets))
			{
				indexOfMethodFirstLine = classCode.indexOf(line);
				break;
			}
		}
	//	System.out.println(name+parameterStringWithBracets);
		if(indexOfMethodFirstLine == -1)
			 throw new Exception("Method not found");
		
		List<String>methodBody = new ArrayList<String>();
		
		for(int i=indexOfMethodFirstLine+2;;i++)
		{
			methodBody.add(classCode.get(i));
			if(classCode.get(i).contains("return"))
			{
				break;
			}
		}
		
		return methodBody;
	}

	
	private List<String> trimByteCodeLines(List<String> byteCodeLines) {
		List<String> formattedByteCodeLines = new ArrayList<String>();
		for (String line : byteCodeLines) {
			formattedByteCodeLines.add(line.trim());
		}
		return formattedByteCodeLines;
	}
}