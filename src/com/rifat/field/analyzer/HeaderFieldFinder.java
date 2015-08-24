package com.rifat.field.analyzer;
/*  
 * example: 
 * 	protected final org.eclipse.egit.core.test.TestUtils testUtils;
 *  5: new           #16                 // class org/eclipse/egit/core/test/TestUtils
 * */

import java.util.ArrayList;
import java.util.List;

import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;
import com.rifat.setup.Main;

public class HeaderFieldFinder {

	private MethodFinder methodFinder = new MethodFinder();
	private final String CONSTRUCTOR_IDENTIFIER = ""; 
	
	public boolean isHeaderField(List<String>classCode, Field field)
	{
		List<Method>constructors = getAllConstructor(classCode);
		
		for (Method constructor : constructors) {
			if(isFieldInitialized(constructor, field, classCode))
				return true;
		}
		return false;
	}
	
	private boolean isFieldInitialized(Method constructor, Field field, List<String>classCode)
	{
		List<String>constructorBody = methodFinder.extractDeclaredMethodBody(constructor.getSignature(), classCode);
		for (String line : constructorBody) {
			
			if((line.contains("putfield") || line.contains("putstatic")) && line.contains(field.name))//putstatic added for static field initialization
				return true;
		}
		return false;
	}
	private List<Method> getAllConstructor(List<String>classCode)
	{
		List<Method> declaredMethods = methodFinder.getDeclaredMethods(classCode);
		List<Method>constructors = new ArrayList<Method>();
		
		for (Method method : declaredMethods) {
			if(method.methodName.equals(CONSTRUCTOR_IDENTIFIER))
			{
				constructors.add(method);
			}
		}
		return constructors;
	}
	
	
	/*public static void main(String[] args) throws Exception {
		FieldFinder fieldFinder = new FieldFinder();
		List<Field> fields = fieldFinder.getAllFields(Main.getClassCode(Main.BASE_PATH+"GitTestCase.txt"));
		boolean isHeaderField = new HeaderFieldFinder().isHeaderField("org.eclipse.egit.core.test.GitTestCase", Main.getClassCode(Main.BASE_PATH+"GitTestCase.txt"), fields.get(0));
		System.out.println(isHeaderField+" "+fields.get(0));
	}*/
	
}
