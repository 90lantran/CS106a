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

		countAlready = new boolean[nPlayers][N_CATEGORIES];
		score = new int[N_CATEGORIES][nPlayers];

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

		// calculate total
		calcualteTotal();
		printWinner();

	}
	private void printWinner(){
		int winner = 0; // index of the winner
		for (int i = 0; i < nPlayers ; i ++){
			if (score[TOTAL-1][winner] < score[TOTAL-1][i]){
				winner = i;
			}
		}
		display.printMessage("Congrat to "+ playerNames[winner]+ ".You are the winner with "+
				score[TOTAL-1][winner]+" score.");
		
	}

	private void calcualteTotal(){
		for (int player = 0; player < nPlayers ; player ++){

			score[UPPER_SCORE -1][player]= score[ONES-1][player]+score[TWOS-1][player]+
			score[THREES-1][player]+score[FOURS-1][player]+
			score[FIVES-1][player]+score[SIXES-1][player];
			if (score[UPPER_SCORE -1][player] > 63){
				score[UPPER_BONUS -1][player] = 35;
			}else{
				score[UPPER_BONUS -1][player] = 0;
			}
			for(int i = THREE_OF_A_KIND; i <= CHANCE; i++){
				score[LOWER_SCORE-1][player] += score[i-1][player]; 
			}
			score[TOTAL-1][player] = score[LOWER_SCORE-1][player] + score[UPPER_SCORE -1][player]+score[UPPER_BONUS -1][player];
			
			display.updateScorecard(UPPER_SCORE, player+1, score[UPPER_SCORE-1][player]);
			display.updateScorecard(UPPER_BONUS, player+1, score[UPPER_BONUS-1][player]);
			display.updateScorecard(LOWER_SCORE, player+1, score[LOWER_SCORE-1][player]);
			display.updateScorecard(TOTAL, player+1, score[TOTAL-1][player]);

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
				display.updateScorecard(category, player, score[category-1][player -1]);
				break;
			}else if (answer == true && countAlready[player -1][category -1]== true){ // chose correctly more than one
				display.printMessage("You select this category more than one. Select another category.");
				category = display.waitForPlayerToSelectCategory();
			}else {
				countAlready[player -1][category-1] = true;
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
				score[ONES-1][player-1] = numOfRepetition1(ONES)*ONES;
				return true;
			}else{
				return false;
			}			
		}else if (category == TWOS){
			if (numOfRepetition1(TWOS) != 0){
				
				score[TWOS-1][player-1] = numOfRepetition1(TWOS)*TWOS;
				return true;
			}else{
				return false;
			}			

		}else if (category == THREES){
			if (numOfRepetition1(THREES) != 0){
				//earningPoint = numOfRepetition1(THREES)*THREES;
				score[THREES-1][player-1] = numOfRepetition1(THREES)*THREES;
				return true;
			}else{
				return false;
			}		
		}else if (category == FOURS){
			if (numOfRepetition1(FOURS) != 0){
				//earningPoint = numOfRepetition1(FOURS)*FOURS;
				score[FOURS-1][player-1] = numOfRepetition1(FOURS)*FOURS;
				return true;
			}else{
				return false;
			}		
		}else if (category == FIVES){
			if (numOfRepetition1(FIVES) != 0){
				//earningPoint = numOfRepetition1(FIVES)*FIVES;
				score[FIVES-1][player-1] = numOfRepetition1(FIVES)*FIVES;
				return true;
			}else{
				return false;
			}		
		}else if (category == SIXES){
			if (numOfRepetition1(SIXES) != 0){
				//earningPoint = numOfRepetition1(SIXES)*SIXES;
				score[SIXES-1][player-1] = numOfRepetition1(SIXES)*SIXES;
				return true;
			}else{
				return false;
			}		
		}else if (category == THREE_OF_A_KIND){//9
			if (numOfRepetition2()== 6){
				//earningPoint = sumOfDice();
				score[THREE_OF_A_KIND-1][player-1] = sumOfDice();
				return true;
			}else{
				return false;
			}

		}else if (category == FOUR_OF_A_KIND ){//10
			if (numOfRepetition2()== 12){
				//earningPoint = sumOfDice();
				score[FOUR_OF_A_KIND-1][player-1] = sumOfDice();
				return true;
			}else{
				return false;
			}
		}else if (category == FULL_HOUSE){//11
			if (numOfRepetition2()== 8){
				//earningPoint = 25;
				score[FULL_HOUSE-1][player-1] = 25;
				return true;
			}else{
				return false;
			}
		}else if (category == SMALL_STRAIGHT){//12
			if ((numOfRepetition2()== 0) || (numOfRepetition2()== 2 && (min() == 1 || min() == 2))){
				//earningPoint = 30;
				score[SMALL_STRAIGHT-1][player-1] = 30;
				return true;
			}else{
				return false;
			}
		}else if (category == LARGE_STRAIGHT){//13
			if (numOfRepetition2()== 0){
				//earningPoint = 40;
				score[LARGE_STRAIGHT-1][player-1] = 40;
				return true;
			}else{
				return false;
			}
		}else if (category == YAHTZEE){//14
			if (numOfRepetition2()== 20){
				//earningPoint = 50;
				score[YAHTZEE-1][player-1] = 50;
				return true;
			}else{
				return false;
			}
		}else if (category == CHANCE){//15
			//earningPoint = sumOfDice();
			score[CHANCE-1][player-1] = sumOfDice();			
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
	//private int earningPoint;
	private boolean[][] countAlready ;
	private int[][] score ;
}
