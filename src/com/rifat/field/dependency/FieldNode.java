package com.rifat.field.dependency;

import java.util.ArrayList;
import java.util.List;

import com.rifat.field.analyzer.Field;

public class FieldNode {

	private Field field;
	public List<FieldNode>dependentFields;
	
	public FieldNode(Field field) {
		
		this.field = field;
		dependentFields = new ArrayList<FieldNode>();
	}
	
	public Field getField()
	{
		return field;
	}
}
