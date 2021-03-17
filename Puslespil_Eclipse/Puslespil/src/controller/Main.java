package controller;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import model.Piece;
import processing.core.PApplet;
import view.View;

public class Main{
	// The argument passed to main must match the class name
	private static int pieceAmount = 7;
	private static int boardSize = 600;
	
	public static void main(String[] args) {
		//View view = new View();
		// Set the main
		//PApplet.main("view.View");s
		
		View view = new View();
		PApplet.runSketch(new String[]{"--location=200,200", ""}, view);
		
		
		Point2D.Float[] v1 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-50, 0), new Point2D.Float(-100, -100)};
		Piece p1 = new Piece(view, new Point2D.Float(400.0f, 400.0f), v1);
		p1.rotatePiece(90);
		view.addPieceToList(p1);
		
		Point2D.Float[] v3 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-50, 0), new Point2D.Float(-100, -100)};
		Piece p3 = new Piece(view, new Point2D.Float(400.0f, 400.0f), v3);
		view.addPieceToList(p3);
		
		
		
		
		for(int i = 1; i <= 3; i ++) {
			boolean notidentical = true;
			p3.rotatePiece(i*45);			
			for(Point2D.Float p : p1.getCurrentVertices()) {
				System.out.print(p.toString());
			}
			System.out.println();
			for(Point2D.Float p : p3.getCurrentVertices()) {
				System.out.print(p.toString());
			}
			
			for(int j = 0; j < p3.getShape().getVertexCount(); j++) {
				if(p3.getCurrentVertices()[j] == p1.getCurrentVertices()[j]) {
					notidentical = false;
				}
			}
			
			System.out.println(notidentical);
			
		}
		
		
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
		Generator g = new Generator(boardSize,pieceAmount);
		
		Object[] O3 = g.generate();
		for(int i = 0; i < pieceAmount; i++) {
			Point2D.Float[] v = (Point2D.Float[]) O3[i];
			Point2D.Float center = v[0];
			Point2D.Float[] v1 = new Point2D.Float[v.length-1];
			for(int j = 1; j<v.length;j++) {
				v1[j-1]=v[j];
			}
			//Point2D.Float[] v1 = (Point2D.Float[]) O3[i];
			
			
			
			Piece p = new Piece(view, center, v1);
			view.addPieceToList(p);
			
		}
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
