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
	
	float angle = 0;
	
	// Zoom and pan
	float view_scale;
	float viewX, viewY;
	
	// identical use to setup in Processing IDE except for size()
	public void setup() {
		
		// Zoom and Pan init values
		view_scale = 1f;
		viewX = 0;
		viewY = 0;
		
		//smooth();
	}
	
	// method used only for setting the size of the window
	public void settings() {
		// Create the view object
		size(displayHeight*3>>2, displayHeight*3>>2);
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
			boolean overOne = false;
			Piece tempPiece = null;
			for(Piece piece : pieceList) {
				
				if(piece.isMouseOver() && currentPiece == null) {
					tempPiece = piece;
					//tempPiece.getShape().setFill(color(255,0,0));
				} else {
					piece.getShape().setFill(color(0,0,0));
				}
				
				piece.display();
			}
			
			if(tempPiece != null) {
				tempPiece.getShape().setFill(color(255,0,0));
			}
		}
	}
	
	// Mouse press event
	public void mousePressed() {
	for(Piece piece : pieceList){
		if(piece.isMouseOver()) {
			//System.out.println("piece clicked");
			currentPiece = piece;
			angle = currentPiece.getAngle();
		}
	  }
	}
	
	public void mouseReleased() {
		currentPiece = null;
	}
	
	// Panning feature
	public void mouseDragged(MouseEvent event) {
		
		if(currentPiece != null) {
			currentPiece.movePiece();
		}
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
		// TEST
		
		if(currentPiece != null) {
			angle += event.getCount()*45;
			currentPiece.rotatePiece(angle);
		}
		// Restrain zoom
		//view_scale = constrain(view_scale += event.getCount()*-0.1,  0.2f, 1.5f);
	}
	
	
	
	public void addPieceToList(Piece piece) {
		pieceList.add(piece);
	}
}
