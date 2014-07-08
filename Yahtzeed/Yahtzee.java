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
		for (int round = 0; round < N_SCORING_CATEGORIES; round++){

			for (int i = 1; i <= nPlayers ; i++){

				display.printMessage(playerNames[i-1]+"'s turn. Click \"Roll Dice\" button to roll dice.");

				display.waitForPlayerToClickRoll(i);
				rollDice(dice);
				display.displayDice(dice);

				reroll();

				category = display.waitForPlayerToSelectCategory();

				updateScore(i);

			} //end of each player turn 
		}
	}

	private void reroll(){
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
	}

	private void updateScore(int player){
		boolean answer = checkDiceStatus(category, dice,player);
		while (true){
			if (answer == true && countAlready[player -1][category-1] == false){ // chose correctly for first time
				
				countAlready[player -1][category-1] = true;
				//display.printMessage("WELL");
				display.updateScorecard(category, player, earningPoint);
				break;
			}else if (answer == true && countAlready[player -1][category -1]== true){ // chose correctly more than one
				display.printMessage("You select this category more than one. Select another category.");
				category = display.waitForPlayerToSelectCategory();
			}else {
				display.printMessage("You select the wrong category.You earn 0 score.");
				display.updateScorecard(category, player, 0);
				break;
			}
		}

	}
	// this method return the status of 5 dice (corresponding to constant integer
	// in YahtzeeConstants)
	private boolean checkDiceStatus(int category, int[] dice, int player){
		if (category == ONES){			
			if (numOfRepetition1(ONES) != 0){
				//earningPoint = numOfRepetition1(ONES)*ONES;
				score[ONES-1][player-1] = numOfRepetition1(ONES)*ONES;;
				
				return true;
			}else{
				return false;
			}			
		}else if (category == TWOS){
			if (numOfRepetition1(TWOS) != 0){
				earningPoint = numOfRepetition1(TWOS)*TWOS;
				
				return true;
			}else{
				return false;
			}			

		}else if (category == THREES){
			if (numOfRepetition1(THREES) != 0){
				earningPoint = numOfRepetition1(THREES)*THREES;
				
				return true;
			}else{
				return false;
			}		
		}else if (category == FOURS){
			if (numOfRepetition1(FOURS) != 0){
				earningPoint = numOfRepetition1(FOURS)*FOURS;
				
				return true;
			}else{
				return false;
			}		
		}else if (category == FIVES){
			if (numOfRepetition1(FIVES) != 0){
				earningPoint = numOfRepetition1(FIVES)*FIVES;
				
				return true;
			}else{
				return false;
			}		
		}else if (category == SIXES){
			if (numOfRepetition1(SIXES) != 0){
				earningPoint = numOfRepetition1(SIXES)*SIXES;
				
				return true;
			}else{
				return false;
			}		
		}else if (category == THREE_OF_A_KIND){//9
			if (numOfRepetition2()== 6){
				earningPoint = sumOfDice();
			
				return true;
			}else{
				return false;
			}

		}else if (category == FOUR_OF_A_KIND ){//10
			if (numOfRepetition2()== 12){
				earningPoint = sumOfDice();
				
				return true;
			}else{
				return false;
			}
		}else if (category == FULL_HOUSE){//11
			if (numOfRepetition2()== 8){
				earningPoint = 25;
			
				return true;
			}else{
				return false;
			}
		}else if (category == SMALL_STRAIGHT){//12
			if ((numOfRepetition2()== 0) || (numOfRepetition2()== 2 && (min() == 1 || min() == 2))){
				earningPoint = 30;
				
				return true;
			}else{
				return false;
			}
		}else if (category == LARGE_STRAIGHT){//13
			if (numOfRepetition2()== 0){
				earningPoint = 40;
			
				return true;
			}else{
				return false;
			}
		}else if (category == YAHTZEE){//14
			if (numOfRepetition2()== 20){
				earningPoint = 50;
				
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

	private int min(){
		int min = dice[0];
		for (int num : dice){
			if (min > num){
				min = num;
			}
		}
		return min;
	}
	//	private void initCountAlready(){
	//		for (int i = 0; i < countAlready.length ; i ++){
	//			for (int j = 0; j < countAlready[i].length; j++){
	//				countAlready[i][j]= false;
	//			}
	//		}
	//	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
	private int category;
	private int earningPoint;
	private boolean[][] countAlready = new boolean[2][17];
	private int[][] score = new int[17][2];
}
