package model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import processing.core.PApplet;
import processing.core.PShape;

public class Piece {
	
	PApplet pA;
	public int col = 0;
	float xOffset, yOffset;
	float angle = 0;
	
	public boolean overPiece, pieceLocked = false;
	
	// Vertices 
	Point2D.Float[] vertices;
	Point2D.Float origin;
	
	PShape shape;
	
	
	public Piece(PApplet _pA, Point2D.Float _origin, Point2D.Float[] _vertices){
		// Get a reference to the main PApplet class
		this.pA = _pA;
		
		this.vertices = _vertices;
		this.origin = _origin;
		
		init();
	}
	
	private void init() {
		// Create the polygon shape
		//pA.shapeMode(PShape.CENTER);
		shape = pA.createShape(PShape.PATH);
	    shape.beginShape();
	    	shape.stroke(0);
	    	//shape.fill(0);
	    	//shape.strokeWeight(0);
	        for (int i = 0; i < vertices.length; i++) {
	          shape.vertex(origin.x + vertices[i].x, origin.y + vertices[i].y);
	        }
	    shape.endShape();
	}
	
	public void display() {
		
		//if (shape.contains(pA.mouseX, pA.mouseY)) {
		//	System.out.println("over a Piece");
		//}
		
		//System.out.println(centerCoords.x + ", " + centerCoords.y);
		shape.setFill(col);
	    pA.shape(shape);
	    pA.ellipse(origin.x, origin.y,10,10);
	}
	
	
	
	public boolean isMouseOver() {
		if (shape.contains(pA.mouseX, pA.mouseY)) {
			
			return true;
		} else {
			return false;
		}
	}
	
	public void movePiece() {
		//System.out.println("piece moved");
		for(int i = 0; i < shape.getVertexCount(); i++) {
			origin = new Point2D.Float(pA.mouseX, pA.mouseY);
			float rotatedX = PApplet.cos(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x) - PApplet.sin(PApplet.radians(angle)) * ((origin.y + vertices[i].y)-origin.y) + origin.x;;
			float rotatedY = PApplet.sin(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x) + PApplet.cos(PApplet.radians(angle)) * ((origin.y + vertices[i].y)-origin.y) + origin.y;
			shape.setVertex(i, rotatedX, rotatedY);
		}
	}
	
	public void rotatePiece(float _angle) {
		this.angle = _angle;
		for(int i = 0; i < shape.getVertexCount(); i++) {
			float rotatedX = PApplet.cos(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x) - PApplet.sin(PApplet.radians(angle)) * ((origin.y + vertices[i].y)-origin.y) + origin.x;
			float rotatedY = PApplet.sin(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x) + PApplet.cos(PApplet.radians(angle)) * ((origin.y + vertices[i].y)-origin.y) + origin.y;
			shape.setVertex(i, rotatedX, rotatedY);
			//vertices[i] = new Point2D.Float(rotatedX, rotatedY);
		}
	}
	
	// GETTERS AND SETTERS
	public PShape getShape() {
		return this.shape;
	}
	
	public float getAngle() {
		return this.angle;
	}
	
}
