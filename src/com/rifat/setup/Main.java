package com.rifat.setup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.rifat.field.analyzer.Field;

public class Main {

	public final static String BASE_PATH = "F:\\Ananda DU\\android projects\\Test Smell Finder\\bin\\com\\rifat\\setup\\";
	
	public static void main(String[] args) throws Exception{
	
		List<String>parentClassCode = getClassCode(BASE_PATH + "GitTestCase.txt");
		List<String>childClassCode = getClassCode(BASE_PATH + "IndexDiffCacheTest.txt");
		
		/*SetUpMethodAnalysis setUpMethodAnalysis = new SetUpMethodAnalysis();
		List<String>list = setUpMethodAnalysis.getMergedSetUpMethodAndInvokedMethodsBodyForChildClass(parentClassCode, childClassCode, "org.eclipse.egit.core.test.GitTestCase");
		System.out.println(list.size());
		System.out.println(list);
		*/
		
		SetUpFieldAnalysis setUpFieldAnalysis = new SetUpFieldAnalysis();
		System.err.println(setUpFieldAnalysis.findAllSetUpFields(parentClassCode, childClassCode, "org.eclipse.egit.core.test.GitTestCase"));
		
	}
	
	public static List<String> getClassCode(String path) throws Exception
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
