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
	public boolean isCurrentPiece = false;
	float shapeWidth = 0;
	float shapeHeight = 0;
	
	
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
		shape = pA.createShape(PShape.PATH);
	    shape.beginShape();
	    	shape.fill(0);
	    	shape.stroke(0);
	        for (int i = 0; i < vertices.length; i++) {
	          shape.vertex(origin.x + vertices[i].x, origin.y + vertices[i].y);
	        }
	    shape.endShape();
	    
	    shapeWidth = (shape.getWidth() - origin.x);
	    shapeHeight = (shape.getHeight() - origin.y);
	}
	
	public void display() {
		shape.setFill(col);
	    pA.shape(shape);
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
	    pA.stroke(0,0,0);
	    	    
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
	
	
	
	public boolean isMouseOver() {
		if (shape.contains(pA.mouseX, pA.mouseY)) {
			
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
			
			//System.out.println(shape.getVertex(i));
			
			//for(int d = 0; i < shape.getVertexCount(); i++) {
				//System.out.print(shape.getVertex(d).x);
			//}
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
