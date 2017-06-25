package com.paramati.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class DriverClass {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		
		int totalLines = 0;
		int totaloccurences = 0;
		Scanner sc = new Scanner(System.in);
		LinkedHashMap<Integer, ArrayList<Integer>> LineMap =null;
		Set<Integer> keys = null;
		
		System.out.println("Enter The pattern to search:");
		String pattern = sc.nextLine();

		System.out.println("You need to specify a path!");

		String s = sc.nextLine();
		File CP_file = new File(s);

		// this file FILE SCANNER Interface reference can wrap any object implementing File Scanner interface in future.
		FileScanner ts = new TextScanner();

		// counting the words and sentences
		ts.scanFile(CP_file);// new File("C:/Users/Prateek/Desktop/test.txt")

		// Find the give pattern and store in map
		LineMap = ts.scanPattern(CP_file, pattern);

		//get he keys to iterate over them
		keys = LineMap.keySet();
		for (Integer i : keys) {
			if (LineMap.get(i).size() >= 1) {

				int arraysize = LineMap.get(i).size();
				System.out.println("Line no =>" + i + " Has " + arraysize
						+ " Occurences of Pattern=>" + pattern);
				totalLines++;
				totaloccurences += arraysize;
			}
		}
		System.out.println("Total count of the lines with Pattern =>"
				+ totalLines);
		System.out.println("Total Occurences of the Pattern =>"
				+ totaloccurences);

		sc.close();

	}// main ends
}// class ends
