package controller;

import model.Piece;
import processing.core.PApplet;
import view.View;

public class Main{
	// The argument passed to main must match the class name
	public static void main(String[] args) {
		//View view = new View();
		// Set the main
		//PApplet.main("view.View");
		
		View view = new View();
		PApplet.runSketch(new String[]{"--location=200,200", ""}, view);
		
		float[] v1 = {-100, -100, 100, -100, 100, 100, -100, 100, -50, 0, -100, -100};
		Piece p1 = new Piece(view, 400, 400, v1);
		view.addPieceToList(p1);
		
		
	}
	
}
