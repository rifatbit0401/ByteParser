package com.rifat.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.rifat.field.analyzer.Field;

public class Utility {

	public <E> List<E> toList(Set<E>set)
	{
		List<E>list = new ArrayList<E>();
		
		for (E e : set) {
			list.add(e);
		}
		return list;
	}
	
	public <E> Set<E> toSet(List<E>list)
	{
		Set<E>set = new HashSet<E>();
		for (E e : list) {
			set.add(e);
		}
		return set;
	}
	
	public List<Field>getUniqueListofFields(List<Field>fields)
	{
		List<Field>uniqueFieldList = new ArrayList<Field>();
		
		for (Field field : fields) {
			
			boolean found = false;
			for (Field ufield : uniqueFieldList) {
				if(field.name.equals(ufield.name))
				{
					found = true;
				}
			}
			if(!found)
				uniqueFieldList.add(field);
		}
		return uniqueFieldList;
	}
	
	public List<String> getClassCode(String path) throws Exception
	{
		List<String>classCode = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(path));
		
		while(scanner.hasNext())
		{
			classCode.add(scanner.nextLine());
		}
		
		return classCode;
	}

}
