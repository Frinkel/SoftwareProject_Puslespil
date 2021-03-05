package controller;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

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
		
		Point2D.Float[] v2 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(150, 0), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-100, -100)};
		Piece p2 = new Piece(view, new Point2D.Float(400.0f, 400.0f), v2);
		view.addPieceToList(p2);
		
		//float[] v2 = {-100, -100, 100, -100, 150, 0, 100, 100, -100, 100, -100, -100};
		//Piece p2 = new Piece(view, 100, 100, v2);
		//view.addPieceToList(p2);
		
		/*
		
		Point2D.Float[] v2 = (Point2D.Float[]) O2[53];
		Piece p2 = new Piece(view, new Point2D.Float(600.0f, 600.0f), v2);
		view.addPieceToList(p2);
		
		Point2D.Float[] v3 = (Point2D.Float[]) O2[54];
		Piece p3 = new Piece(view, new Point2D.Float(200.0f, 200.0f), v3);
		view.addPieceToList(p3);
		*/
		/*
		PieceReader pR = new PieceReader();
		Object[] O2 = pR.pieceReader();
		int j = -1;
		int a = 0;
		for(int i = 0; i < 121; i++) {
			Point2D.Float[] v = (Point2D.Float[]) O2[i];
			a++;
			if(i % 11 == 0) {j++; a=0;}
			
			Piece p = new Piece(view, new Point2D.Float(0.0f+100*a, 0.0f+100*j), v);
			view.addPieceToList(p);
		}
		*/
	}
	
}
