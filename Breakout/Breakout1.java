import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*; // why
import java.awt.event.*; // why mouse event?
import java.net.URL;

public class Breakout1 extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
		(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Delay*/
	private static final int DELAY = 30;
	
	//AudioClip bounceSound = MediaTools.loadAudioClip("bounce.au");

	
	public void run(){
		
		setupPaddle();
		//bounceClip = MediaTools.loadAudioClip("bounce.au");
		//bounceClip = getAudioClip(getCodeBase(),"bounce.au");
		//bounceSound.play();
		//AudioClip bounceSound = MediaTools.loadAudioClip("bounce.au");
		for (turn = 0; turn < NTURNS; turn++ ){
			
			setupBrick();			
			setupBall();
			
			setupGreeting();
						
			waitForClick();
			
			remove(greeting);
			
			// setupScore, separate later
			point = new GLabel("",getWidth()/2 + 120,getHeight()/2 + 270);
			point.setFont("Times New Roman-20");
			add(point);
			
			while ( ball.getY() < getHeight()){
				playGame();
				updateSpeed();
				if (countBrick == NBRICK_ROWS * NBRICKS_PER_ROW) break;				
				pause(DELAY);
			}			
			if (countBrick == NBRICK_ROWS * NBRICKS_PER_ROW) break;
			point.setLabel("Point: 0");
		}
		
		printResult();
		
	}
	
	private void setupGreeting(){
		greeting = new GLabel(" Click to play !");
		add(greeting, (getWidth()-greeting.getWidth())/2, (getHeight() - greeting.getHeight())/2 + 100 );
	}

	private void setupBrick(){

		// complicated calculation to make sure that all bricks are centered in the middle after having spaces among them
		int x_init = (WIDTH -(BRICK_WIDTH * NBRICKS_PER_ROW + (NBRICKS_PER_ROW - 1) * BRICK_SEP))/2;

		// it should be BRICK_Y_OFFSET
		int y_init = (HEIGHT /2 -(BRICK_HEIGHT * NBRICK_ROWS + (NBRICK_ROWS -1 )* BRICK_SEP))/2 ;

		for(int row = 0; row < NBRICK_ROWS ; row++){			
			int x = x_init;
			int y = y_init + row * (BRICK_HEIGHT + BRICK_SEP);

			for (int col = 0 ; col < NBRICKS_PER_ROW ; col++){
				brick = new GRect(BRICK_WIDTH ,BRICK_HEIGHT);
				brick.setFilled(true);
				if ( row==0 || row ==1  ){
					brick.setColor(Color.red);
					brick.setFillColor(Color.red);
				}else if( row == 2 || row == 3 ){
					brick.setColor(Color.orange);
					brick.setFillColor(Color.orange);
				}else if (row == 4 || row == 5){
					brick.setColor(Color.yellow);
					brick.setFillColor(Color.yellow);
				}else if (row== 6|| row== 7){
					brick.setColor(Color.green);
					brick.setFillColor(Color.green);
				}else if (row== 8|| row== 9){
					brick.setColor(Color.cyan);
					brick.setFillColor(Color.cyan);
				}
				add(brick,x + col*(BRICK_WIDTH + BRICK_SEP),y);
			}
		}
	}

	private void setupPaddle(){
		paddle = new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
		add(paddle,(WIDTH - PADDLE_WIDTH)/2,getHeight() - PADDLE_Y_OFFSET-PADDLE_HEIGHT);
		paddle.setFilled(true);
		addMouseListeners();
	}

	public void mouseMoved(MouseEvent e){
		// getX return position of mouse
		if ((e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	
	

	private void setupBall(){


		// ball position
		ball = new GOval((getWidth()/2 - BALL_RADIUS),getHeight()/2 - BALL_RADIUS,BALL_RADIUS*2,BALL_RADIUS*2 );
		ball.setFilled(true);
		add(ball);

		// ball velocity
		vy = 6.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx; 
		}
	}

	private void playGame(){
		ball.move(vx, vy);
		if (ball.getY() <= 0) vy*=-1; // reverses the y direction when the ball top hits the wall
		if (ball.getX() + BALL_RADIUS*2 >= getWidth() || ball.getX()<= 0) vx*=-1; // reverses the x direction when the ball hits the wall
	}
	private void updateSpeed(){
		collider = getCollidingObject();
		
		
		if (collider == paddle){
			vy*=-1;
		
			playSound();
		}else if (collider == point || collider == null){
			//nothing
		}else if (collider != null) {
			vy*=-1;
			//bounceClip.play();
			playSound();
			remove(collider);
			countBrick++;		
			point.setLabel("Point: " + countBrick);
			
		}

	}
	private GObject getCollidingObject(){
		if (getElementAt(ball.getX(), ball.getY()) != null)  
			return getElementAt(ball.getX(), ball.getY());
		else if (getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY()) != null) 
			return getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY()); 
		else if (getElementAt(ball.getX(), ball.getY() + 2*BALL_RADIUS) != null) 
			return getElementAt(ball.getX(), ball.getY() + 2*BALL_RADIUS);
		else if (getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS) != null) 
			return getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS);
		else return null;
	}
	
	

	//	private GObject getCollidingObject(){
		//		if (getElementAt(ball.getX(),ball.getY()) != null){
	//			return getElementAt(ball.getX(),ball.getY());
	//		} else if (getElementAt(ball.getX() + 2*BALL_RADIUS,ball.getY()) != null){
	//			return getElementAt(ball.getX()+ 2*BALL_RADIUS,ball.getY());
	//		}else if (getElementAt(ball.getX(),ball.getY()+ 2*BALL_RADIUS) != null){
	//			return getElementAt(ball.getX(),ball.getY()+ 2*BALL_RADIUS);
	//		}else if (getElementAt(ball.getX()+2*BALL_RADIUS,ball.getY()+2*BALL_RADIUS) != null){
	//			return getElementAt(ball.getX()+2*BALL_RADIUS,ball.getY()+ 2*BALL_RADIUS);
	//		}else{
	//			return null;
	//		}
	//	}
	
	private void printResult(){   
       
         
        // condition to win
        if (countBrick == NBRICK_ROWS * NBRICKS_PER_ROW) {
        	// creates winner GLabel
            result = new GLabel ("You Win!!"); 
            
        }
        // condition to lose
        else if (turn == NTURNS ){
        //  creates loser GLabel
            result = new GLabel ("You lose T_T");
            
        }
        result.setFont("Times New Roman-40");
        result.setColor(Color.BLUE);
        add (result, getWidth()/2 - result.getWidth()/2,getHeight()/2 );
    }
	
	private void playSound(){
		try {
			//System.out.println("1");
			URL url = new URL("file:/E:/Studying/UMD_Summer2014/CS106a/Assignment3/bounce.au");
			//System.out.println("2");
			AudioClip clip = Applet.newAudioClip(url);
			//System.out.println("3");
			clip.play();
			//System.out.println("4");
			//Thread.sleep(1000);
			//System.out.println("end");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GObject collider;
	private int countBrick = 0;
	private GLabel result;
	private int turn;
	private GLabel point;
	private GLabel greeting;
}