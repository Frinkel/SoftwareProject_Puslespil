package view;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Piece;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class View extends PApplet {
	
	public View() {
		System.out.println("View running..");
	}
	
	// Variables
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	
	Piece currentPiece;
	
	// Zoom and pan
	float view_scale;
	float viewX, viewY;
	
	// identical use to setup in Processing IDE except for size()
	public void setup() {
		
		// Zoom and Pan init values
		view_scale = 1f;
		viewX = 0;
		viewY = 0;
	}
	
	// method used only for setting the size of the window
	public void settings() {
		// Create the view object
		size(800, 800);
	}
	
	// identical use to draw in Processing IDE
	public void draw(){
		// For zoom and panning
		//translate(viewX, viewY);
		//scale(view_scale);
		
		// Change background color to white
		background(255);
		
		// Draw the pieces
		if(!pieceList.isEmpty()) {
			for(Piece piece : pieceList) {
				piece.display();
				
				if(piece.isMouseOver()) {
					piece.getShape().setFill(color(255,0,0));;
				} else {
					piece.getShape().setFill(color(0,0,0));;
				}
			}
		}
	}
	
	// Mouse press event
	public void mousePressed() {
	for(Piece piece : pieceList){
		if(piece.isMouseOver()) {
			System.out.println("piece clicked");
		}
		
		/*
	    if(p.overPiece && helperP == null){
	      //println("over");
	      
	      helperP = p;
	      
	      helperP.pieceLocked = true;
	      xOffset = mouseX-p.center_x;
	      yOffset = mouseY-p.center_y;
	      helperP.shape.setFill(color(255,0,0));
	      //helperP.shape.rotate(0.1);
	    }
	    */
	  }
	}
	
	// Panning feature
	public void mouseDragged(MouseEvent event) {
		
		/*
		float _x = (pmouseX - mouseX);
		float _y = (pmouseY - mouseY);
		viewX -= _x;
		viewY -= _y;
		
		for(Piece piece : pieceList) {
			piece.updateCenterCoords(new Point2D.Float((float) _x, (float) _y));
		}
		*/
	}
	
	// Zoom feature
	public void mouseWheel(MouseEvent event) {
	  // Restrain zoom
	  //view_scale = constrain(view_scale += event.getCount()*-0.1,  0.2f, 1.5f);
	}
	
	
	
	public void addPieceToList(Piece piece) {
		pieceList.add(piece);
	}
	
	
}
