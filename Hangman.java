package Assignment4;
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	
	// Add canvas to console
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		/* You fill this in */
		
		// clear canvas 
		canvas.reset();
		
		// in console
		println("Welcome to Hangman!");
		
		// why setSeed does not work for me
		//rgen.setSeed(5);		
		
		//choose random word in the list 
		int option = rgen.nextInt(mylexicon.getWordCount());
		keyword = mylexicon.getWord(option);
		println(keyword);
		int wordLength = keyword.length() ;
		numDash = keyword.length();
		int numGuesses = 8;

		// display ----
		String display="";
		for (int i = 0 ; i < wordLength; i ++){
			display +="-";
		}
		
		// start game
		do {
			
			print("The word now looks like this: ");
			println(display); 			
			canvas.displayWord(display);
			
			println("You have " + numGuesses + " guesses left.");

			// ask user to enter a character (upper or lower)
			char charGuess;
			while(true){
				
				// that is the only to get a character from user
				String yourGuess = readLine("Your guess: ");
				charGuess = yourGuess.charAt(0);
				
				// make sure that user only enters a letter(not anything else)
				if ( yourGuess.length() == 1 && Character.isLetter(charGuess)){
					//println(wordGuess);
					break;
				}else{
					println("Your guess is illegal.");
				}
			}
			
			// check lower case
			if (Character.isLowerCase(charGuess)){
				charGuess = (char) ((char)charGuess - 'a' + 'A');
			}

			// check that character is in the keyword or not
			int position = keyword.indexOf(charGuess);

			if (position == -1){
				println("There are no " +charGuess+ "'s in the word.");
				numGuesses--;
				
				// write incorrect word in canvas
				canvas.noteIncorrectGuess(charGuess);
			}else{
				println("That guess is correct.");
				
				// write correct word in canvas
				display = replaceDashByCharGuess(keyword,charGuess,display);			
			}
			
			// print winning greeting
			if (numDash == 0){
				printWinning();
				break;
			}
		}while(numGuesses >= 1 );

		// print losing greeting
		if (numGuesses == 0){
			printLosing();
		}
	}

	private void printWinning(){
		println("You guessed the word " + keyword +".");
		println("You win.");
	}

	private void printLosing(){
		println("You are completely hung.");
		println("The word was "+ keyword +".");
		println("You lose.");
	}

	private String replaceDashByCharGuess(String keyword, char charGuess, String display){
		//int count = 0;
		String result = display;

		for (int i = 0; i < keyword.length() ; i ++){
			if (keyword.charAt(i) == charGuess){
				String sub1 = result.substring(0,i);
				String sub2 = result.substring(i+1);
				result = sub1 + charGuess + sub2;
				numDash = numDash - 1;
				//println(numDash);
			}
		}
		return result;
	}
	
	private HangmanLexicon mylexicon = new HangmanLexicon();
	private RandomGenerator rgen = RandomGenerator.getInstance(); 
	private int numDash;
	private String keyword;
	private HangmanCanvas canvas;
}
