package com.rifat.method.dependency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rifat.method.analyzer.MethodFinder;
import com.rifat.method.analyzer.Method;

public class DependencyFinder {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input2.txt"));
		List<String> linesOfClassCode = new ArrayList<String>();
		while (scanner.hasNext()) {

			linesOfClassCode.add(scanner.nextLine());
		}

		MethodFinder methodFinder = new MethodFinder();
		List<Method> methods = methodFinder
				.getDeclaredMethods(linesOfClassCode);
		List<DeclaredMethod>declaredMethods = new ArrayList<DeclaredMethod>();
		
		for (Method method : methods) {
		   declaredMethods.add(new DeclaredMethod(linesOfClassCode, method));	
		}
		
		for (DeclaredMethod declaredMethod : declaredMethods) {
			System.out.println(declaredMethod.method.getSignature());
			System.out.println("invoked methods\n");
			System.out.println(declaredMethod.getInvokedMethods());
		}
	}

}
