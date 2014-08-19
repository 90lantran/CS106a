/*
 * File: NameSurferGraph.java
 * --------------------------*
 *  This class is responsible for updating the graph whenever the
 * list of entries changes or the window is resized.
 */

import acm.graphics.*;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {


	private ArrayList<NameSurferEntry> data = new ArrayList<NameSurferEntry>();
	private GPoint[] points =  new GPoint[11]; 
	private double spaces ;
	private Color[] colors = new Color[4];
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
		colors[0] = Color.BLACK;
		colors[1] = Color.RED;
		colors[2] = Color.BLUE;
		colors[3] = Color.MAGENTA;
	}


	private void drawBackground(){
		drawVertical();
		drawHorizontal();
		//drawDecades();
	}

	private void drawVertical(){

		spaces = (double)getWidth()/NDECADES;
		for (int i = 0; i < NDECADES  ; i ++){
			double x0 = (i+1)*spaces;
			double y0 = 0 ;
			double x1 = x0;
			double y1 = getHeight();
			GLine verticalLine = new GLine(x0,y0,x1,y1);
			add(verticalLine);

			GLabel decade = new GLabel(""+(START_DECADE + 10*i));
			add(decade,i*spaces,getHeight());
		}
	}

	private void drawHorizontal(){
		double x0 = 0;
		double y0 = GRAPH_MARGIN_SIZE;
		double x1 = getWidth();
		double y1 = y0;
		GLine firstVerticalLine = new GLine(x0,y0,x1,y1);

		double x2 = x0;
		double y2 = getHeight()-GRAPH_MARGIN_SIZE;
		double x3 = x1;
		double y3 = y2;
		GLine secondVerticalLine = new GLine(x2,y2,x3,y3);

		add(firstVerticalLine);
		add(secondVerticalLine);
	}

	private void drawData(){
		double spaces = (double)getWidth()/NDECADES;
		int colorIndex = 0;
		
		if (!data.isEmpty()){
			for(NameSurferEntry item:data){
				String name = item.getName();
				//int[] ranks = item.getNumData();
				
				for (int i = 0; i < 11; i++){
					int ranking = item.getRank(i);
					
					if (ranking != 0){
						double x = i*spaces;
						double y = GRAPH_MARGIN_SIZE+(getHeight()- 2*GRAPH_MARGIN_SIZE)*ranking/1000.0;
						GLabel label = new GLabel(name +" "+ ranking);
						label.setColor(colors[colorIndex]);
						add(label,x,y);

						GPoint point = new GPoint(x,y);
						//points =;
						points[i] = point;
						//connectPoints();
					}else{
						double x = i*spaces;
						double y = getHeight()- GRAPH_MARGIN_SIZE;

						GLabel label = new GLabel(name+"*");
						label.setColor(colors[colorIndex]);
						add(label,x,y);

						GPoint point = new GPoint(x,y);
						points[i] = point;
					}
					
					

				}
				connectPoints(colorIndex);
				colorIndex++;
				if (colorIndex > 3) colorIndex = colorIndex%4;
				
			}
		}

	}

	private void connectPoints(int index){
		for (int i=0; i < points.length-1;i++){

			GLine segment = new GLine(points[i].getX(),points[i].getY(),
					points[i+1].getX(),points[i+1].getY());
			segment.setColor(colors[index]);
			add(segment);
		}
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		// You fill this in //
		data.clear();
	}
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		//data = new ArrayList<NameSurferEntry>();
		data.add(entry);
		//System.out.println("finished adding ");
		update();
	}
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever 
	 * the size of the canvas changes.
	 */
	public void update() {
		// You fill this in //
		removeAll();
		drawBackground();
		drawData();

	}
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }


}
