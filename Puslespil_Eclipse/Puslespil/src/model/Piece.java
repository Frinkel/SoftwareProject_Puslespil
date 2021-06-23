package model;

import java.awt.geom.Point2D;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import view.View;

public class Piece {
	// Joel
	// Get a reference to the PApplet object
	View view;

	// Piece variables
	float angle = 0;
	public boolean isCurrentPiece = false;
	float shapeWidth = 0;
	float shapeHeight = 0;
	PImage texture = null;

	// Piece creation variables
	Point2D.Float[] vertices;
	Point2D.Float origin;
	PShape shape;

	public Piece(View _view, Point2D.Float _origin, Point2D.Float[] _vertices) {
		this.view = _view;
		this.vertices = _vertices;
		this.origin = _origin;

		// Create the piece (without texture)
		init();
	}

	public Piece(View _view, Point2D.Float _origin, Point2D.Float[] _vertices, PImage img) {
		this.view = _view;
		this.vertices = _vertices;
		this.origin = _origin;
		this.texture = img;

		// Create the piece (with texture)
		initWithTexture();
	}

	// Piece creation
	private void init() {
		shape = view.createShape();
		shape.beginShape();
		shape.fill(255);
		shape.stroke(0);
		shape.strokeWeight(2);
		for (int i = 0; i < vertices.length - 1; i++) {
			shape.vertex(origin.x + vertices[i].x, origin.y + vertices[i].y);
		}
		shape.endShape(PApplet.CLOSE);
	}

	private void initWithTexture() {
		shape = view.createShape();
		shape.beginShape();
		shape.noStroke();
		shape.textureMode(PConstants.NORMAL);
		shape.texture(texture);
		for (int i = 0; i < vertices.length - 1; i++) {
			shape.vertex(origin.x + vertices[i].x, origin.y + vertices[i].y,
					Math.signum(vertices[i].x + Math.abs(vertices[i].x)),
					Math.signum(vertices[i].y + Math.abs(vertices[i].y)));
		}
		shape.endShape(PApplet.CLOSE);
	}

	// Visuals
	public void display() {
		view.shape(shape);
	}

	public void debugDisplay() {
		// Configuration
		view.fill(255, 255, 255);
		view.strokeWeight(2);
		view.stroke(0, 0, 0);

		// Center
		view.ellipse(origin.x, origin.y, 10, 10);

		// Angle lines
		view.stroke(255, 0, 0);
		view.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle)) * -200),
				origin.y - PApplet.sin(PApplet.radians(-angle)) * -200);
		view.stroke(0, 255, 0);
		view.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle + 90)) * -200),
				origin.y - PApplet.sin(PApplet.radians(-angle + 90)) * -200);
		view.stroke(0, 0, 255);
		view.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle - 90)) * -200),
				origin.y - PApplet.sin(PApplet.radians(-angle - 90)) * -200);
		view.stroke(255, 255, 0);
		view.line(origin.x, origin.y, origin.x + (PApplet.cos(PApplet.radians(-angle + 180)) * -200),
				origin.y - PApplet.sin(PApplet.radians(-angle + 180)) * -200);
		view.stroke(255);

		// Vertices
		for (int i = 0; i < shape.getVertexCount(); i++) {
			float rotatedX = PApplet.cos(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x)
					- PApplet.sin(PApplet.radians(angle)) * ((origin.y + vertices[i].y) - origin.y) + origin.x;
			float rotatedY = PApplet.sin(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x)
					+ PApplet.cos(PApplet.radians(angle)) * ((origin.y + vertices[i].y) - origin.y) + origin.y;
			view.ellipse(rotatedX, rotatedY, 10, 10);
		}
	}

	// Derived from:
	// https://web.archive.org/web/20161108113341/https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	public boolean contains(float x, float y) {
		Point2D test = new Point2D.Float(x, y);
		int i;
		int j;
		boolean result = false;
		for (i = 0, j = shape.getVertexCount() - 1; i < shape.getVertexCount(); j = i++) {
			if ((shape.getVertex(i).y > test.getY()) != (shape.getVertex(j).y > test.getY())
					&& (test.getX() < (shape.getVertex(j).x - shape.getVertex(i).x)
							* (test.getY() - shape.getVertex(i).y) / (shape.getVertex(j).y - shape.getVertex(i).y)
							+ shape.getVertex(i).x)) {
				result = !result;
			}

		}
		return result;
	}

	public boolean isMouseOver() {
		if (this.contains(view.mouseX, view.mouseY)) {
			return true;
		} else {
			return false;
		}
	}

	public void movePiece(Point2D.Float _move) {
		for (int i = 0; i < shape.getVertexCount(); i++) {
			origin = _move;
			float rotatedX = PApplet.cos(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x)
					- PApplet.sin(PApplet.radians(angle)) * ((origin.y + vertices[i].y) - origin.y) + origin.x;
			;
			float rotatedY = PApplet.sin(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x)
					+ PApplet.cos(PApplet.radians(angle)) * ((origin.y + vertices[i].y) - origin.y) + origin.y;
			shape.setVertex(i, rotatedX, rotatedY);
		}
	}

	public void rotatePiece(float _angle) {
		this.angle = _angle;
		for (int i = 0; i < shape.getVertexCount(); i++) {
			float rotatedX = PApplet.cos(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x)
					- PApplet.sin(PApplet.radians(angle)) * ((origin.y + vertices[i].y) - origin.y) + origin.x;
			float rotatedY = PApplet.sin(PApplet.radians(angle)) * ((origin.x + vertices[i].x) - origin.x)
					+ PApplet.cos(PApplet.radians(angle)) * ((origin.y + vertices[i].y) - origin.y) + origin.y;
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

		for (int i = 0; i < verticesList.length; i++) {
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
