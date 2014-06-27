package Assignment4;

/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Font;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		/* You fill this in */
		removeAll();
		GLine scalffold = new GLine(getWidth()/4,getHeight()-130 ,getWidth()/4,getHeight()-70-SCAFFOLD_HEIGHT  );
		GLine beam = new GLine(getWidth()/4,getHeight()-70-SCAFFOLD_HEIGHT , getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT );
		GLine rope = new GLine(getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT,getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH);
		
		add(scalffold);
		add(beam);
		add(rope);
	
	
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		/* You fill this in */
		//GLabel keyword = new GLabel(word);
		//GLabel guessword = new GLabel("");
		keyword.setLabel(word);
		keyword.setFont("TimesNewRoman-20");
		add(keyword,(getWidth()- word.length())/2,getHeight()-50 );
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.																																																																
 */
	public void noteIncorrectGuess(char letter) {
		
		/* You fill this in */
		
		guesswordString += letter;
		guessword.setLabel(guesswordString);
		guessword.setFont("TimesNewRoman-20");
		add(guessword,(getWidth()- guesswordString.length())/2,getHeight()-20 );
		
		//int numberDrawing = guesswordString.indexOf(letter);
		int numberDrawing = guesswordString.lastIndexOf(letter);
		
		if (numberDrawing == 0){
			drawHead();
		}else if (numberDrawing == 1){
			drawBody();
		}else if (numberDrawing == 2){
			drawLeftArm();
		}else if (numberDrawing == 3){
			drawRightArm();
		}else if (numberDrawing == 4){
			drawLeftLeg();
		}else if (numberDrawing == 5){
			drawRightLeg();
		}else if (numberDrawing == 6){
			drawLeftFoot();
		}else {
			drawRightFoot();
		}		
		
		
	}
	
	private void drawHead(){
		GOval head = new GOval(getWidth()/4 + BEAM_LENGTH-HEAD_RADIUS,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH,HEAD_RADIUS*2,HEAD_RADIUS*2);
		add(head);
	}
	
	private void drawBody(){
		GLine body = new GLine(getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS,getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH );
		add(body);
		
	}
	
	private void drawLeftArm(){
		GLine leftUpperArm = new GLine(getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD,getWidth()/4 + BEAM_LENGTH - UPPER_ARM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD);
		GLine leftLowerArm = new GLine(getWidth()/4 + BEAM_LENGTH - UPPER_ARM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD,getWidth()/4 + BEAM_LENGTH - UPPER_ARM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD+LOWER_ARM_LENGTH);
		add(leftUpperArm);
		add(leftLowerArm);
		
	}
	
	private void drawRightArm(){
		GLine rightUpperArm = new GLine(getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD,getWidth()/4 + BEAM_LENGTH + UPPER_ARM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD);
		GLine rightLowerArm = new GLine(getWidth()/4 + BEAM_LENGTH + UPPER_ARM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD,getWidth()/4 + BEAM_LENGTH + UPPER_ARM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS +ARM_OFFSET_FROM_HEAD+LOWER_ARM_LENGTH);
		add(rightUpperArm);
		add(rightLowerArm);
		
	}
	
	private void drawLeftLeg(){
		GLine leftUpperLeg = new GLine(getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH ,getWidth()/4 + BEAM_LENGTH - HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH );
		GLine leftLowerLeg = new GLine(getWidth()/4 + BEAM_LENGTH - HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH,getWidth()/4 + BEAM_LENGTH - HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftUpperLeg);
		add(leftLowerLeg);
	}
	
	private void drawRightLeg(){
		GLine rightUpperLeg = new GLine(getWidth()/4 + BEAM_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH ,getWidth()/4 + BEAM_LENGTH + HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH );
		GLine rightLowerLeg = new GLine(getWidth()/4 + BEAM_LENGTH + HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH,getWidth()/4 + BEAM_LENGTH + HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightUpperLeg);
		add(rightLowerLeg);
		
	}
	
	private void drawLeftFoot(){
		GLine leftFoot = new GLine(getWidth()/4 + BEAM_LENGTH - HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,getWidth()/4 + BEAM_LENGTH - HIP_WIDTH - FOOT_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);
	}
	
	private void drawRightFoot(){
		GLine rightFoot = new GLine(getWidth()/4 + BEAM_LENGTH + HIP_WIDTH ,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,getWidth()/4 + BEAM_LENGTH + HIP_WIDTH +FOOT_LENGTH,getHeight()-70-SCAFFOLD_HEIGHT+ ROPE_LENGTH + 2*HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);
	}
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private GLabel keyword = new GLabel("");
	private GLabel guessword = new GLabel("");
	private String guesswordString = "";
}
