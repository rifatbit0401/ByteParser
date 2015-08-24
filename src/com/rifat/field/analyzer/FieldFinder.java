package com.rifat.field.analyzer;
/*
 * used field example: // Field toClose:Ljava/util/List;
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FieldFinder {

	private final String CODE_KEY_WORD = "Code:";
	private final String FIELD_MATCHING_KEY_WORD = "// Field ";
	
	public List<Field> getAllFields(List<String> linesOfByteCodeClass) {
	
		linesOfByteCodeClass=trimList(linesOfByteCodeClass);
		int indexOfConstructor=linesOfByteCodeClass.indexOf(CODE_KEY_WORD)-1;
		List<Field>fields=new ArrayList<Field>();
		
		for(int i=2;i<indexOfConstructor;i++)
		{
			if(isEmptyLine(linesOfByteCodeClass.get(i)))
				continue;
			fields.add(new Field(linesOfByteCodeClass.get(i)));
		}
		return fields;
		
	}
	
	private boolean isEmptyLine(String line)
	{
		return line.length()==0;
	}
	
	private List<String>trimList(List<String>stringList)
	{
		List<String>trimedStringList=new ArrayList<String>();
		for (String str : stringList) {
			if(str.trim().length()==0){
				continue;
			}
			trimedStringList.add(str.trim());
		}
		return trimedStringList;
	}
	
	public Set<String> getAllUsedFieldName(List<String>linesOfByteCode)
	{
		Set<String>fieldNames = new HashSet<String>();
		linesOfByteCode = trimList(linesOfByteCode);
		
		for (String line : linesOfByteCode) {
			if(line.contains(FIELD_MATCHING_KEY_WORD))
			{
				fieldNames.add(extractFieldName(line));
			}
		}
		return fieldNames;
	}
	
	private String extractFieldName(String byteCodeUsedFieldLine)
	{
		return byteCodeUsedFieldLine.split(FIELD_MATCHING_KEY_WORD)[1].trim().split(":")[0];
	}
	
	
}
