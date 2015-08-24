package com.rifat.main;

import com.rifat.result.analysis.ResultAnalysis;
import com.rifat.utility.Utility;

public class Main {

	public static String BASE_PATH = "F:\\Ananda DU\\android projects\\Test Smell Finder\\src\\com\\rifat\\input\\";

	public static void main(String[] args) throws Exception {

		ResultAnalysis resultAnalysis = new ResultAnalysis();
		Utility utility = new Utility();

		resultAnalysis.writeResult("I:\\output.txt",
				"org.eclipse.egit.core.test.GitTestCase",
				utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
				utility.getClassCode(BASE_PATH + "IndexDiffCacheTest.txt"));

		resultAnalysis.writeResult(
				"I:\\output1.txt",
				"org.eclipse.jgit.junit.LocalDiskRepositoryTestCase",
				utility.getClassCode(BASE_PATH
						+ "LocalDiskRepositoryTestCase.txt"),
				utility.getClassCode(BASE_PATH + "ThreeWayDiffEntryTest.txt"));

		resultAnalysis.writeResult("I:\\output2.txt",
				"org.eclipse.egit.core.test.GitTestCase",
				utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
				utility.getClassCode(BASE_PATH + "CommitOperationTest.txt"));

		resultAnalysis.writeResult("I:\\output3.txt",
				"org.eclipse.egit.core.test.GitTestCase",
				utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
				utility.getClassCode(BASE_PATH + "AddOperationTest.txt"));

		resultAnalysis.writeResult(
				"I:\\output4.txt",
				"org.eclipse.egit.core.test.GitTestCase",
				utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
				utility.getClassCode(BASE_PATH
						+ "RemoveFromIndexOperationTest.txt"));

		// single file
		resultAnalysis.writeResult("I:\\output5.txt", "null",
				utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
				utility.getClassCode(BASE_PATH + "EGitSecureStoreTest.txt"));

		// single file
		resultAnalysis.writeResult(
				"I:\\output6.txt",
				"null",
				utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
				utility.getClassCode(BASE_PATH
						+ "GitProjectSetCapabilityTest.txt"));

		resultAnalysis
				.writeResult(
						"I:\\output7.txt",
						"org.eclipse.egit.core.test.GitTestCase",
						utility.getClassCode(BASE_PATH + "GitTestCase.txt"),
						utility.getClassCode(Main.BASE_PATH
								+ "GitBlobStorageTest.txt"));

		
	}
}
