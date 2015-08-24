package com.rifat.field.dependency;

import java.util.List;

import com.rifat.field.analyzer.Field;
import com.rifat.field.analyzer.FieldFinder;
import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;
import com.rifat.utility.Utility;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		List<String>childClassCode = com.rifat.setup.Main
				.getClassCode(com.rifat.setup.Main.BASE_PATH
						+ "CommitOperationTest.txt");
		List<String>parentClassCode = com.rifat.setup.Main
				.getClassCode(com.rifat.setup.Main.BASE_PATH
						+ "GitTestCase.txt");
		
		
		MethodFinder methodFinder = new MethodFinder();
		List<Method>declaredMethods = methodFinder.getDeclaredMethods(childClassCode);
		System.out.println(declaredMethods.get(2));
		
		FieldFinder fieldFinder = new FieldFinder();
		List<Field>fields = fieldFinder.getAllFields(childClassCode);
		fields.addAll(fieldFinder.getAllFields(parentClassCode));
		//System.out.println(fieldFinder.getAllFields(childClassCode));
		
		//FieldDepenedencyResolver fieldDepenedencyResolver = new FieldDepenedencyResolver();
		/*fieldDepenedencyResolver.resolveFieldDependency(methodFinder
				.extractDeclaredMethodBody(declaredMethods.get(2)
						.getSignature(), childClassCode), fields);*/
		
		FieldDependencyManager fieldDependencyManager = new FieldDependencyManager();
		Field field = fields.get(0);
		List<Field>dependentFields = fieldDependencyManager.getDepenedentFields(field, parentClassCode, childClassCode, "org.eclipse.egit.core.test.GitTestCase");
		
		System.out.println(field+"===");
		Utility utility = new Utility();
		
		for (Field dependentField : dependentFields) {
			System.out.println(dependentField.name);
		}
	}
}
