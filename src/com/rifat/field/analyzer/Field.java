package com.rifat.field.analyzer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * byte code field example: private final static java.util.List<org.eclipse.jgit.lib.Repository> toClose;
 * used field example: // Field toClose:Ljava/util/List;
 * */
public class Field {

	public String name;
	public String accessModifier;
	public boolean isStatic = false;
	public String type;
	public boolean isFinal = false;

	private final String FINAL_KEY_WORD = "final";
	private final String STATIC_KEY_WORD = "static";
	private final int INDEX_OF_ACCESS_MODIFIER = 0;
	private final int INDEX_OF_TYPE = 1;
	private final int INDEX_OF_NAME = 2;
	private final String DEFAULT_ACCESS_MODIFIER = "default";

	public Field() {

	}

	public Field(String byteCodeFieldLine) {
		byteCodeFieldLine = getSemicloneFreeString(byteCodeFieldLine);
		
		if(hasDefaultAccessModifier(byteCodeFieldLine))
		{
			byteCodeFieldLine = DEFAULT_ACCESS_MODIFIER+" "+byteCodeFieldLine;
		}
		
		List<String> fragmentsOfByteCodeFieldLine = new LinkedList<>(
				Arrays.asList(byteCodeFieldLine.split(" ")));
		
		if (fragmentsOfByteCodeFieldLine.contains(FINAL_KEY_WORD)) {
			isFinal = true;
			fragmentsOfByteCodeFieldLine.remove(fragmentsOfByteCodeFieldLine
					.indexOf(FINAL_KEY_WORD));
		}
		
		if (fragmentsOfByteCodeFieldLine.contains(STATIC_KEY_WORD)) {
			isStatic = true;
			fragmentsOfByteCodeFieldLine.remove(fragmentsOfByteCodeFieldLine
					.indexOf(STATIC_KEY_WORD));
		}
		
		accessModifier = fragmentsOfByteCodeFieldLine
				.get(INDEX_OF_ACCESS_MODIFIER);
		type = fragmentsOfByteCodeFieldLine.get(INDEX_OF_TYPE);
		name = fragmentsOfByteCodeFieldLine.get(INDEX_OF_NAME);

	}

	private boolean hasDefaultAccessModifier(String byteCodeFieldline)
	{
		if(byteCodeFieldline.startsWith("public") || byteCodeFieldline.startsWith("private") || byteCodeFieldline.startsWith("protected"))
		{
			return false;
		}
		else
			return true;
	}

	private String getSemicloneFreeString(String str) {
		return str.replaceAll(";", "");
	}

	@Override
	public String toString() {
		return accessModifier + " " + type + " " + name;
	}

}
