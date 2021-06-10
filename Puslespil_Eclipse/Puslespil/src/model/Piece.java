package model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PShape;
import processing.core.PVector;

public class Piece {
	
	PApplet pA;
	public int col = 0;
	float xOffset, yOffset;
	float angle = 0;
	public boolean isCurrentPiece = false;
	float shapeWidth = 0;
	float shapeHeight = 0;
	PImage texture = null;
	
	
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
	
	public Piece(PApplet _pA, Point2D.Float _origin, Point2D.Float[] _vertices, PImage img){
		// Get a reference to the main PApplet class
		this.pA = _pA;
		
		this.vertices = _vertices;
		this.origin = _origin;
		
		this.texture = img;
		
		init();
	}
	
	private void init() {
		shape = pA.createShape();
	    shape.beginShape();
	    	shape.noStroke();
	    	shape.textureMode(PConstants.NORMAL);
	    	shape.texture(texture);
	        for (int i = 0; i < vertices.length-1; i++) {
	        	shape.vertex(origin.x + vertices[i].x, origin.y + vertices[i].y, Math.signum(vertices[i].x + Math.abs(vertices[i].x)), Math.signum(vertices[i].y + Math.abs(vertices[i].y)));
	        }
	    shape.endShape(PApplet.CLOSE);
	    //shape.setTexture(texture);
	    //shapeWidth = (shape.getWidth() - origin.x);
	    //shapeHeight = (shape.getHeight() - origin.y);
	}
	
	public void display() {
		//shape.setFill(col);
		
	    pA.shape(shape);
//	    pA.ellipse(origin.x, origin.y,10,10);
//	    // TESTING
//	    pA.stroke(255,0,0);
//	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle)) * -200);
//	    pA.stroke(0,255,0);
//	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle+90)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle+90)) * -200);
//	    pA.stroke(0,0,255);
//	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle-90)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle-90)) * -200);
//	    pA.stroke(255,255,0);
//	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle+180)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle+180)) * -200);
//	    pA.stroke(0,0,0);
	    	    
	    // GREEN = LEFT
	    /*
	    pA.stroke(0,255,0);
	    pA.line(origin.x - (50 * PApplet.cos(PApplet.radians(angle))), origin.y - (50 * PApplet.sin(PApplet.radians(angle))), (origin.x - (50 * PApplet.cos(PApplet.radians(angle)))) + (PApplet.cos(PApplet.radians(-angle+90)) * -200) , (origin.y-(50 * PApplet.sin(PApplet.radians(angle)))) - PApplet.sin(PApplet.radians(-angle+90)) * -200);
	    pA.line(origin.x + (50 * PApplet.cos(PApplet.radians(angle))), origin.y + (50 * PApplet.sin(PApplet.radians(angle))), (origin.x + (50 * PApplet.cos(PApplet.radians(angle)))) + (PApplet.cos(PApplet.radians(-angle+90)) * -200) , (origin.y+(50 * PApplet.sin(PApplet.radians(angle)))) - PApplet.sin(PApplet.radians(-angle+90)) * -200);
	    //System.out.println(shapeWidth);
	    //pA.line(origin.x - ((shapeWidth - 50) * PApplet.cos(PApplet.radians(-angle))), origin.y + (50 * PApplet.sin(PApplet.radians(angle))), (origin.x - ((shapeWidth - 50) * PApplet.cos(PApplet.radians(angle)))) + (PApplet.cos(PApplet.radians(-angle)) * -100) , (origin.y+(50 * PApplet.sin(PApplet.radians(angle)))) - PApplet.sin(PApplet.radians(-angle)) * -100);
	    
	    
	    
	    pA.stroke(255,0,0);
	    float xmult = (float) (PApplet.sin(PApplet.radians(-angle)));
	    float ymult = (float) (PApplet.cos(PApplet.radians(-angle)));
	    //System.out.println("a" + -angle +" x:"+xmult + " y:"+ymult);
	    pA.line(origin.x - (50 * xmult), origin.y - (50 * ymult), (origin.x - 50 * xmult) + (PApplet.cos(PApplet.radians(-angle)) * -200) , (origin.y - 50 * ymult) - PApplet.sin(PApplet.radians(-angle)) * -200);
	    pA.line(origin.x + (50 * xmult), origin.y + (50 * ymult), (origin.x + 50 * xmult) + (PApplet.cos(PApplet.radians(-angle)) * -200) , (origin.y + 50 * ymult) - PApplet.sin(PApplet.radians(-angle)) * -200);
	    
	    //pA.line(origin.x - ((shapeWidth-50) * xmult), origin.y - (50 * ymult), (origin.x - (shapeWidth-50) * xmult) + (PApplet.cos(PApplet.radians(-angle)) * -200) , (origin.y - 50 * ymult) - PApplet.sin(PApplet.radians(-angle)) * -200);
	    */
	    
	}
	
	public void debugDisplay() {
		pA.fill(255);
		pA.stroke(0,0,0);
		pA.ellipse(origin.x, origin.y,10,10);
	    // TESTING
	    pA.stroke(255,0,0);
	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle)) * -200);
	    pA.stroke(0,255,0);
	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle+90)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle+90)) * -200);
	    pA.stroke(0,0,255);
	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle-90)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle-90)) * -200);
	    pA.stroke(255,255,0);
	    pA.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle+180)) * -200) , origin.y - PApplet.sin(PApplet.radians(-angle+180)) * -200);
	    
	    for(int i = 0; i < shape.getVertexCount(); i++) {
			float rotatedX = PApplet.cos(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x) - PApplet.sin(PApplet.radians(angle)) * ((origin.y + vertices[i].y)-origin.y) + origin.x;;
			float rotatedY = PApplet.sin(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x) + PApplet.cos(PApplet.radians(angle)) * ((origin.y + vertices[i].y)-origin.y) + origin.y;
			pA.ellipse(rotatedX, rotatedY, 10, 10);
		}
	}

	// FROM: https://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
	public boolean contains(float x, float y) {
		Point2D test = new Point2D.Float(x,y);
		int i;
		int j;
		boolean result = false;
		for (i = 0, j = shape.getVertexCount() - 1; i < shape.getVertexCount(); j = i++) {
		  if ((shape.getVertex(i).y > test.getY()) != (shape.getVertex(j).y > test.getY()) &&
		      (test.getX() < (shape.getVertex(j).x - shape.getVertex(i).x) * (test.getY() - shape.getVertex(i).y) / (shape.getVertex(j).y-shape.getVertex(i).y) + shape.getVertex(i).x)) {
		    result = !result;
		  }
		    
		}
		return result;
	}

	
	public boolean isMouseOver() {
		if (this.contains(pA.mouseX, pA.mouseY)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void movePiece(Point2D.Float _move) {
		for(int i = 0; i < shape.getVertexCount(); i++) {
			origin = _move;
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
		}
	}
	
	
	// GETTERS AND SETTERS
	public PShape getShape() {
		return this.shape;
	}
	
	public float getAngle() {
		return this.angle;
	}
	
	public Point2D.Float getOrigin() {
		return this.origin;
	}
	
	public Point2D.Float[] getCurrentVertices() {
		Point2D.Float[] verticesList = new Point2D.Float[vertices.length];
		
		for(int i = 0; i < verticesList.length; i++) {
			verticesList[i] = new Point2D.Float(shape.getVertex(i).x, shape.getVertex(i).y);
		}
		
		return verticesList;
	}
	
	public float getShapeWidth() {
		return shapeWidth;
	}
	
	public float getShapeHeight() {
		return shapeHeight;
	}
}
