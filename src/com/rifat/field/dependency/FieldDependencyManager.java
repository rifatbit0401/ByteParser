package com.rifat.field.dependency;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.rifat.field.analyzer.Field;
import com.rifat.field.analyzer.FieldFinder;
import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;
import com.rifat.setup.SetUpMethodAnalysis;
import com.rifat.utility.Utility;

public class FieldDependencyManager {

	private FieldFinder fieldFinder = new FieldFinder();
	private MethodFinder methodFinder = new MethodFinder();
	private SetUpMethodAnalysis setUpMethodAnalysis = new SetUpMethodAnalysis();
	private FieldDepenedencyResolver fieldDepenedencyResolver = new FieldDepenedencyResolver();
	private Utility utility = new Utility();
	
	private final String CONSTRUCTOR_IDENTIFIER = "";
	
	public List<Field> getDepenedentFields(Field field, List<String>parentClassCode, List<String>childClassCode, String parentClassName) throws Exception
	{
		List<Field>allFields = getAllFields(parentClassCode, childClassCode);
		List<FieldNode>fieldNodes = prepareFieldNode(allFields);
		
		List<String> totalCodeSegment = getTotalCodeSegment(parentClassCode,
				childClassCode, parentClassName);
		
		List<FieldNode>resolvedFieldNodes = fieldDepenedencyResolver.resolveFieldDependency(totalCodeSegment, fieldNodes);
		List<Field> dependentFields = getDependentFields(field,
				resolvedFieldNodes);
		
		return utility.toList(utility.toSet(dependentFields));
	}

	private List<Field> getDependentFields(Field field,
			List<FieldNode> resolvedFieldNodes) {
		List<Field>dependentFields = new ArrayList<Field>();
		
		for (FieldNode fieldNode : resolvedFieldNodes) {
			if(fieldNode.getField().name.equals(field.name))
			{
				for (FieldNode node : fieldNode.dependentFields) {
					dependentFields.add(node.getField());
				}
			}
		}
		return dependentFields;
	}

	private List<String> getTotalCodeSegment(List<String> parentClassCode,
			List<String> childClassCode, String parentClassName)
			throws Exception {
		List<String>totalCodeSegment = new ArrayList<String>();
		totalCodeSegment.addAll(getConstructorBody(parentClassCode));
		totalCodeSegment.addAll(getConstructorBody(childClassCode));
		totalCodeSegment.addAll(setUpMethodAnalysis.getMergedSetUpMethodAndInvokedMethodsBodyForChildClass(parentClassCode, childClassCode, parentClassName));
		return totalCodeSegment;
	}
	
	private List<String>getConstructorBody(List<String>classCode)
	{
		return getMethodBody(classCode, CONSTRUCTOR_IDENTIFIER);
	}
	
	private List<String>getMethodBody(List<String>classCode, String methodName)
	{
		List<String>constructorBody = new ArrayList<String>();
		List<Method>declaredMethods = methodFinder.getDeclaredMethods(classCode);
		
		for (Method method : declaredMethods) {
			if(method.methodName.equals(methodName))
				constructorBody.addAll(methodFinder.extractDeclaredMethodBody(method.getSignature(), classCode));
		}
		
		return constructorBody;
	}
	

	private List<Field> getAllFields(List<String> parentClassCode, List<String> childClassCode) {
		
		List<Field>parentClassFields = fieldFinder.getAllFields(parentClassCode);
		List<Field>childClassFields = fieldFinder.getAllFields(childClassCode);
		
		List<Field>allFields = new LinkedList<Field>();
		allFields.addAll(parentClassFields);
		allFields.addAll(childClassFields);
		
		return allFields;
	}
	
	private List<FieldNode> prepareFieldNode(List<Field>fields)
	{
		List<FieldNode>fieldNodes = new ArrayList<FieldNode>();
		
		for (Field field : fields) {
			fieldNodes.add(new FieldNode(field));
		}
		
		return fieldNodes;
	}
	
}
