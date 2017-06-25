package com.paramati.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 
 * @author Prateek
 *
 */
public class TextScanner implements FileScanner {

	/**
	 * This method scans the given file and print the number of words,character
	 * and sentences in the file.
	 * 
	 * @param File
	 *            object
	 */
	@Override
	public void scanFile(File file) {

		// counter for character , words & sentences.
		int charCount = 0;

		// assuming that the file don't have any space in the start and end
		int wordsCount = 0;

		int sentenceCount = 0;

		int c = 0;

		// get the file reader to read character by character from the file
		try {
			FileReader rd = new FileReader(file);

			while ((c = rd.read()) != -1) {
				char ch = (char) c;

				// as sentences are always treminated by full stop " . "
				if (ch == '.') {
					sentenceCount++;
				} else if (ch == ' ') {
					wordsCount++;
				}
				// this condition will only count the alphabets as character.
				else if (ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122) {
					charCount++;
				}
			}
			rd.close();// closing input stream reader.
		} catch (IOException e) {
			e.printStackTrace();
		}// while ends

		System.out.println("sentence Count =" + sentenceCount);
		System.out.println("words Count     =" + wordsCount);
		System.out.println("character Count=" + charCount);

	}// scan file method ends.

	// ===============================================================//

	/**
	 * This method scans for a given pattern in that file giving out information
	 * like the line nos. of all matching entries.
	 * 
	 * @param continuous
	 *            string of characters as pattern
	 * @param File
	 *            object
	 * @throws FileNotFoundException
	 */
	@Override
	public LinkedHashMap<Integer, ArrayList<Integer>> scanPattern(File file,
			String pattern) throws FileNotFoundException {

		// get the file reader to read character by character from the file
		FileReader fr = new FileReader(file);

		// get line number reader
		LineNumberReader lnr = new LineNumberReader(fr);

		// using linked hashmap to maintain order
		LinkedHashMap<Integer, ArrayList<Integer>> map = new LinkedHashMap<Integer, ArrayList<Integer>>();

		String line = "";// to hold the line returned
		try {
			while ((line = lnr.readLine()) != null) {
				//System.out.println("LNO " + lnr.getLineNumber() + "=>" + line);
				if (line.length() > 0) {
					ArrayList<Integer> indexList = KmpSearch(line, pattern);
					map.put(lnr.getLineNumber(), indexList);
				} /*else
					System.out.println("Empty line");*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				lnr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return map;
	}// scan pattern method ends.

	private ArrayList<Integer> KmpSearch(String line, String pattern) {

		int lpls[] = CalculateLPLS(pattern);// now we have our prefix array

		ArrayList<Integer> patternIndexonLine = new ArrayList<Integer>();

		int line_len = line.length();
		int p_len = pattern.length();

		// take two pointers
		int i = 0; // for line
		int j = 0; // for pattern
		while (i < line_len) {

			if (pattern.charAt(j) == line.charAt(i)) {
				j++;
				i++;
			}
			// check if pattern index is equal to pattern
			if (j == p_len) {

				//System.out.println("pattern found at index:" + (i - j));
				patternIndexonLine.add(i - j);

				j = lpls[j - 1];// index t start next comparison
			}
			// if line is not finished and pattern and line not matching
			else if (i < line_len && pattern.charAt(j) != line.charAt(i)) {
				if (j != 0)
					j = lpls[j - 1];
				else
					i = i + 1;
			}

		}// while loop

		return patternIndexonLine;
	}// method ends

	
	private int[] CalculateLPLS(String pat) {
		int len = pat.length();
		int lpls[] = new int[len];
		char[] pattern = pat.toCharArray();

		// index for lpls array
		int index = 0;

		for (int i = 1; i < len; i++) {

			if (pattern[i] == pattern[index]) {
				// store the index in array
				lpls[i] = index + 1;

				// Increment both pointer as we got a match, to find max prefix
				// which is also a suffix.
				i++;
				index++;
			} else {// if didn't match, check if the index is not zero
				if (index != 0) {

					// jump to the previous index of largest prefix which is
					// also a suffix
					index = lpls[index - 1];
				} else {// if index is zero store zero in the lpls array
					lpls[i] = 0;
					i++;
				}
			}
		}// for loop ends
		return lpls;
	}// CalculateLPLS method ends
}// class ends
