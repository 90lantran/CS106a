package Assignment4;

/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import acm.program.*;
import java.util.*;

public class HangmanLexicon {

	private ArrayList<String> myList;

	// This is the HangmanLexicon constructor
	public HangmanLexicon() {	
		
		myList = new ArrayList<String>();
		BufferedReader myFile = openFile();	
		
		try{			
			while (true){
				String line = myFile.readLine();
				if (line == null) break;
				myList.add(line);
			}
			myFile.close();
		}catch(IOException ex){
			throw new ErrorException(ex);
		}
	}


	public BufferedReader openFile(){
		BufferedReader rd = null;		
		while (rd == null){
			try {
				String filename = "HangmanLexicon.txt";
				rd = new BufferedReader(new FileReader(filename));

			}catch(IOException ex){
				//println("Nice try punk.That file does not exist.");
				throw new ErrorException(ex);
			}
		}
		return rd;
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		//return 10;
		return myList.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		//		switch (index) {
		//			case 0: return "BUOY";
		//			case 1: return "COMPUTER";
		//			case 2: return "CONNOISSEUR";
		//			case 3: return "DEHYDRATE";
		//			case 4: return "FUZZY";
		//			case 5: return "HUBBUB";
		//			case 6: return "KEYHOLE";
		//			case 7: return "QUAGMIRE";
		//			case 8: return "SLITHER";
		//			case 9: return "ZIRCON";
		//			default: throw new ErrorException("getWord: Illegal index");
		//		}

		return myList.get(index);

	}
}
