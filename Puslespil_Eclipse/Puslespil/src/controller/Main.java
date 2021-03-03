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
		
		
		Piece p = new Piece(view, 200, 200, 100, 100);
		Piece p1 = new Piece(view, 100, 100, 100, 100);
		Piece p2 = new Piece(view, 100, 300, 100, 100);
		view.addPieceToList(p);
		view.addPieceToList(p1);
		view.addPieceToList(p2);
		
		System.out.println();
		
		//view.addPieceToList(p);
		
	}
	
}
