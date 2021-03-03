package model;

import processing.core.PApplet;
import processing.core.PShape;

public class Piece {
	
	PApplet pA;
	
	float center_x, center_y, xOffset, yOffset;
	
	boolean overPiece, pieceLocked = false;
	
	float[] vertices;
	
	PShape shape;
	
	public Piece(PApplet _pA, float _center_x, float _center_y, float[] _vertices){
		// Get a reference to the main PApplet class
		this.pA = _pA;
		
		this.center_x = _center_x;
		this.center_y = _center_y;
		this.vertices = _vertices;
		
		init();
	}
	
	private void init() {
		shape = pA.createShape();
		//shape.setStroke(pA.color(0,0,0));
	    //shape.setFill(0);
	    
	    shape.beginShape();
	    	shape.stroke(0);
	    	shape.fill(0);
	    	shape.strokeWeight(0);
	        for (int i = 0; i < vertices.length; i+=2) {
	          shape.vertex(center_x + vertices[i], center_y + vertices[i+1]);
	        }
	    shape.endShape();
	}
	
	public void display() {
		
		
	    pA.shape(shape);
	    pA.ellipse(center_x,center_y,10,10);
	}
}
