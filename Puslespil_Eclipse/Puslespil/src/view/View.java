package view;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import controller.Main;
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
		boolean piecelocked = false;
		if(!pieceList.isEmpty()) {
			for(int i = pieceList.size()-1; i >= 0; i--) {
				if(pieceList.get(i).isMouseOver() && !piecelocked && currentPiece == null) {
					pieceList.get(i).col = color(255,0,0);
					piecelocked = true;
				} else {
					pieceList.get(i).col = 0;
				}
			}
			if(currentPiece != null) {
				currentPiece.col = color(255,0,0);
			}
			for(Piece piece : pieceList) {
				piece.display();
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
			currentPiece.movePiece(new Point2D.Float(mouseX, mouseY));
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
	
	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}
	
	public Piece getCurrentPiece() {
		return currentPiece;
	}
}
