package controller;

import java.awt.geom.Point2D;

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
		
		
		Point2D.Float[] v1 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-50, 0), new Point2D.Float(-100, -100)};
		Piece p1 = new Piece(view, new Point2D.Float(400.0f, 400.0f), v1);
		view.addPieceToList(p1);
		
		//float[] v2 = {-100, -100, 100, -100, 150, 0, 100, 100, -100, 100, -100, -100};
		//Piece p2 = new Piece(view, 100, 100, v2);
		//view.addPieceToList(p2);
		
		PieceReader pR = new PieceReader();
		pR.pieceReader();
		
		
	}
	
}
