package Generator;


import java.awt.geom.Point2D;

import java.awt.geom.Point2D.Float;
import java.util.Arrays;

public class Piece {
	private Point2D.Float center;
	private Float[] corners;
	private float sizeX;
	private float sizeY;
	
	public Piece(Point2D.Float center, Point2D.Float[] corners) {
		this.center = center;
		this.corners = corners;
	}
	
	public Piece(Point2D.Float center, float sizeX, float sizeY) {
		this.center = center;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	

	public void setCorners() {
		corners = new Point2D.Float[]{new Point2D.Float(-sizeX, -sizeY), new Point2D.Float(-sizeX, sizeY), new Point2D.Float(sizeX, -sizeY), new Point2D.Float(sizeX, sizeY)};
	}
	
	public Point2D.Float getCenter() {
		return center;
	}

	public String toString() {
		return ("x: " + center.x + ", " + "y: " + center.y + ", corners " + Arrays.toString(corners) + "\n");
		
	}
}
