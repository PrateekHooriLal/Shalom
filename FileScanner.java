package com.paramati.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author Prateek
 *
 */
public interface FileScanner {

	// 1st method to find character, sequences and sentences.
	public void scanFile(File file) ;

	// 2nd method to find the pattern and the line number they are on
	public LinkedHashMap<Integer, ArrayList<Integer>> scanPattern(File file, String pattern) throws FileNotFoundException;

}// interface ends
