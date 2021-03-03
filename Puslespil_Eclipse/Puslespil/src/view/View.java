package view;

import java.util.ArrayList;

import model.Piece;
import processing.core.PApplet;

public class View extends PApplet {
	
	public View() {
		System.out.println("hi");
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
		// Draw the pieces
		if(pieceList.size() != 0) {
			for(Piece piece : pieceList) {
				piece.display();
			}
		}
	}
	
	public void addPieceToList(Piece piece) {
		pieceList.add(piece);
	}
}
