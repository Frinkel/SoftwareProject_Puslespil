package model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import processing.core.PApplet;
import processing.core.PShape;

public class Piece {
	
	PApplet pA;
	
	float xOffset, yOffset;
	
	public boolean overPiece, pieceLocked = false;
	
	// Vertices 
	Point2D.Float[] vertices;
	Point2D.Float centerCoords;
	
	PShape shape;
	
	
	public Piece(PApplet _pA, Point2D.Float _centerCoords, Point2D.Float[] _vertices){
		// Get a reference to the main PApplet class
		this.pA = _pA;
		
		this.vertices = _vertices;
		this.centerCoords = _centerCoords;
		
		init();
	}
	
	private void init() {
		// Create the polygon shape
		shape = pA.createShape(PShape.PATH);
	    shape.beginShape();
	    	shape.stroke(0);
	    	shape.fill(0);
	    	shape.strokeWeight(0);
	        for (int i = 0; i < vertices.length; i++) {
	          shape.vertex(centerCoords.x + vertices[i].x, centerCoords.y + vertices[i].y);
	        }
	    shape.endShape();
	}
	
	public void display() {
		
		//if (shape.contains(pA.mouseX, pA.mouseY)) {
		//	System.out.println("over a Piece");
		//}
		
		//System.out.println(centerCoords.x + ", " + centerCoords.y);
	    pA.shape(shape);
	    pA.ellipse(centerCoords.x,centerCoords.y,10,10);
	}
	
	
	
	public boolean isMouseOver() {
		if (shape.contains(pA.mouseX, pA.mouseY)) {
			
			return true;
		} else {
			return false;
		}
	}
	
	// GETTERS AND SETTERS
	public PShape getShape() {
		return shape;
	}
	
	public void updateCenterCoords(Point2D.Float _updateCoords) {
		this.centerCoords = new Point2D.Float(centerCoords.x + _updateCoords.x, centerCoords.y + _updateCoords.y);
		System.out.println(centerCoords.x + ", " + centerCoords.y);
		//shape.translate(-_updateCoords.x, -_updateCoords.y);
	}
}
