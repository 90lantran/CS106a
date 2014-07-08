/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		/* You fill this in */
		//for (int i = 1; i <= nPlayers ; i++){
			display.waitForPlayerToClickRoll(1);
			//display.printMessage(playerNames[1-1]+"s turn. Click Roll Dice button to roll die.");
			
			rollDice(dice);
			display.displayDice(dice);
			
			// reroll
			for (int j = 0; j < 2 ; j++){
				display.waitForPlayerToSelectDice();
				for (int k = 0; k < N_DICE ; k++){
					if (display.isDieSelected(k)){
						dice[k] = rgen.nextInt(1, 6);						
					}
				}
				display.displayDice(dice);
			}
			
			//
			
		//} //end of each player turn 
	}
	
	private void rollDice(int[] dice){
		for (int i = 0; i < N_DICE ; i++){
			dice[i] = rgen.nextInt(1, 6);
			
		}
	}
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
	private int category;

}
