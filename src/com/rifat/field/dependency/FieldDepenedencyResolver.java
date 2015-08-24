package com.rifat.field.dependency;

import java.util.ArrayList;
import java.util.List;

import com.rifat.field.analyzer.Field;
import com.rifat.field.analyzer.FieldFinder;

public class FieldDepenedencyResolver {

	private final String PUT_FIELD = "putfield";
	private final String GET_FIELD = "getfield";
	private final String END_OF_METHOD = "return";
	
	public List<FieldNode> resolveFieldDependency(List<String>code, List<FieldNode>fieldNodes)
	{
		List<FieldNode>dependentFields = new ArrayList<FieldNode>();
		for (String line : code) {
			if(line.contains(GET_FIELD))
			{
				FieldNode node = findFieldNode(line, fieldNodes);
				if(node != null)
				{
					dependentFields.add(node);
					dependentFields.addAll(node.dependentFields);
				}
			}
			
			if(line.contains(PUT_FIELD))
			{
				FieldNode node = findFieldNode(line, fieldNodes);
				if(node !=null)
				{
					node.dependentFields.addAll(dependentFields);
					dependentFields.clear();
				}
			}
			
			if(line.contains(END_OF_METHOD)) // new methods code will start after it's execution
			{
				dependentFields.clear();
			}
		}
		
		/*for (FieldNode fieldNode : fieldNodes) {
			System.err.print(fieldNode.getField().name+"->");
			for (FieldNode nd : fieldNode.dependentFields) {
				System.err.print(nd.getField().name+",");
			}
			System.err.println();
		}*/
		
		return fieldNodes;
		
	}
	
	private FieldNode findFieldNode(String codeLine, List<FieldNode>fieldNodes)
	{
		for (FieldNode fieldNode : fieldNodes) {
			if(codeLine.contains(fieldNode.getField().name))
				return fieldNode;
		}
		return null;
	}
	
	
	
}
