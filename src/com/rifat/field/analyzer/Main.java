package com.rifat.field.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner childClassScanner = new Scanner(
				new File(
						"F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\method\\analyzer\\input2.txt"));
		List<String> linesOfChildClassCode = new ArrayList<String>();
		while (childClassScanner.hasNext()) {

			linesOfChildClassCode.add(childClassScanner.nextLine()); 
		}
		
		FieldFinder fieldFinder = new FieldFinder();
		for (String fieldName : fieldFinder.getAllUsedFieldName(linesOfChildClassCode)) {
			System.err.println(fieldName);
		}
	}
}
