package com.rifat.test.smell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rifat.field.analyzer.FieldFinder;
import com.rifat.method.analyzer.Method;
import com.rifat.method.analyzer.MethodFinder;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner childClassScanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input.txt"));
		List<String> linesOfChildClassCode = new ArrayList<String>();
		while (childClassScanner.hasNext()) {

			linesOfChildClassCode.add(childClassScanner.nextLine());
		}
	
		Scanner parentClassScanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input2.txt"));
		List<String> linesOfParentClassCode = new ArrayList<String>();
		while (parentClassScanner.hasNext()) {

			linesOfParentClassCode.add(parentClassScanner.nextLine());
		}
		
		SetUpFieldAnalysis setUpFieldAnalysis = new SetUpFieldAnalysis();
		setUpFieldAnalysis.getAllSetUpFields(linesOfChildClassCode, linesOfParentClassCode);
		//System.err.println(setUpFieldAnalysis.getAllFieldNameInSetUpMethod(linesOfParentClassCode));
		
	}
}
