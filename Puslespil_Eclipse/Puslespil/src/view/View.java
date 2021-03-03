package view;

import java.util.ArrayList;

import model.Piece;
import processing.core.PApplet;

public class View extends PApplet {
	
	public View() {
		System.out.println("View running..");
	}
	
	// Variables
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	
	// identical use to setup in Processing IDE except for size()
	public void setup() {
		
	}
	
	// method used only for setting the size of the window
	public void settings() {
		// Create the view object
		size(800, 800);
	}
	
	// identical use to draw in Processing IDE
	public void draw(){
		background(255);
		stroke(0);
		
		//stroke(255,0,0);
		//fill(0);
		
		// Draw the pieces
		
			for(Piece piece : pieceList) {
				piece.display();
			}
		
	}
	
	public void addPieceToList(Piece piece) {
		pieceList.add(piece);
	}
}
