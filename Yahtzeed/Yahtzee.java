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
		for (int i = 1; i <= nPlayers ; i++){
		
			display.printMessage(playerNames[i-1]+"'s turn. Click \"Roll Dice\" button to roll dice.");
			display.waitForPlayerToClickRoll(i);
			
			
			rollDice(dice);
			display.displayDice(dice);
			
			// reroll
			for (int j = 0; j < 2 ; j++){
				display.printMessage("Select dice you want to re-roll and click \"Roll Again\"");
				display.waitForPlayerToSelectDice();
				for (int k = 0; k < N_DICE ; k++){
					if (display.isDieSelected(k)){
						dice[k] = rgen.nextInt(1, 6);						
					}
				}
				display.displayDice(dice);
			}
			
			
			category = display.waitForPlayerToSelectCategory();
			
			updateScore(i);
			
		} //end of each player turn 
	}
	
	private void updateScore(int player){
		boolean answer = checkDiceStatus(category, dice);
		
		if (answer == true && countAlready[player -1][category-1] == false){ // chose correctly for first time
			//update score
			countAlready[0][category-1] = true;
			//display.printMessage("WELL");
			display.updateScorecard(category, 1, earningPoint);
		}else if (answer == true && countAlready[0][category -1]== true){ // chose correctly more than one
			display.printMessage("You select the this category more than one. Select another category.");
		}else {
			display.printMessage("You select the wrong category.You earn 0 score.");
			display.updateScorecard(category, 1, 0);
		}
		
	}
	// this method return the status of 5 dice (corresponding to constant integer
	// in YahtzeeConstants)
	private boolean checkDiceStatus(int category, int[] dice){
		if (category == ONES){			
			if (numOfRepetition1(ONES) != 0){
				earningPoint = numOfRepetition1(ONES)*ONES;
				//countAlready[ONES -1]= true;
				return true;
			}else{
				return false;
			}			
		}else if (category == TWOS){
			if (numOfRepetition1(TWOS) != 0){
				earningPoint = numOfRepetition1(TWOS)*TWOS;
				//countAlready[TWOS-1]= true;
				return true;
			}else{
				return false;
			}			
			
		}else if (category == THREES){
			if (numOfRepetition1(THREES) != 0){
				earningPoint = numOfRepetition1(THREES)*THREES;
				//countAlready[THREES-1]= true;
				return true;
			}else{
				return false;
			}		
		}else if (category == FOURS){
			if (numOfRepetition1(FOURS) != 0){
				earningPoint = numOfRepetition1(FOURS)*FOURS;
				//countAlready[FOURS-1]= true;
				return true;
			}else{
				return false;
			}		
		}else if (category == FIVES){
			if (numOfRepetition1(FIVES) != 0){
				earningPoint = numOfRepetition1(FIVES)*FIVES;
				//countAlready[FIVES-1]= true;
				return true;
			}else{
				return false;
			}		
		}else if (category == SIXES){
			if (numOfRepetition1(SIXES) != 0){
				earningPoint = numOfRepetition1(SIXES)*SIXES;
				//countAlready[SIXES-1]= true;
				return true;
			}else{
				return false;
			}		
		}else if (category == THREE_OF_A_KIND){//9
			if (numOfRepetition2()== 6){
				earningPoint = sumOfDice();
				//countAlready[THREE_OF_A_KIND-1]= true;
				return true;
			}else{
				return false;
			}
			
		}else if (category == FOUR_OF_A_KIND ){//10
			if (numOfRepetition2()== 12){
				earningPoint = sumOfDice();
				//countAlready[FOUR_OF_A_KIND-1]= true;
				return true;
			}else{
				return false;
			}
		}else if (category == FULL_HOUSE){//11
			if (numOfRepetition2()== 8){
				earningPoint = 25;
				//countAlready[FULL_HOUSE-1]= true;
				return true;
			}else{
				return false;
			}
		}else if (category == SMALL_STRAIGHT){//12
			if (numOfRepetition2()== 0){
				earningPoint = 30;
				//countAlready[SMALL_STRAIGHT-1]= true;
				return true;
			}else{
				return false;
			}
		}else if (category == LARGE_STRAIGHT){//13
			if (numOfRepetition2()== 8){
				earningPoint = 40;
				//countAlready[LARGE_STRAIGHT-1]= true;
				return true;
			}else{
				return false;
			}
		}else if (category == YAHTZEE){//14
			if (numOfRepetition2()== 20){
				earningPoint = 50;
				//countAlready[YAHTZEE-1]= true;
				return true;
			}else{
				return false;
			}
		}else if (category == CHANCE){//15
			earningPoint = sumOfDice();
			//countAlready[CHANCE-1]= true;
			return true;
		}else{
			return false;
		}
	}
	
	private int sumOfDice(){
		int sum = 0;
		for (int i = 0; i < N_DICE ; i++){
			sum += dice[i];
		}
		return sum;
	}

	private int numOfRepetition1(int refrenceValue){
		int counter = 0;
		for ( int n : dice){
			if (n == refrenceValue)
				counter ++;			
		}
		return counter; 
	}
	
	private int numOfRepetition2(){
		int counter = 0;
		for (int i = 0 ; i < N_DICE; i++){
			int reference = dice[i];
			for (int j = 0; j < N_DICE; j++){
				if (i!=j && reference == dice[j]){
					counter++;
				}
			}
		}
		return counter;
	}
	
	// this method generate random value for 5 dice
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
	private int earningPoint;
	private boolean[][] countAlready = new boolean[2][17];
}
