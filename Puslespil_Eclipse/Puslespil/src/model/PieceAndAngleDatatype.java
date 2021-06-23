package model;

import java.awt.geom.Point2D;

public class PieceAndAngleDatatype {
	// Carl
	private int pieceIndex;
	private float pieceAngle;
	private Point2D.Float center;
	
	public PieceAndAngleDatatype(int pieceIndex, float pieceAngle, Point2D.Float center) {
		this.pieceIndex = pieceIndex;
		this.pieceAngle = pieceAngle;
		this.center = center;
	}
	
	public int getPieceIndex() {
		return pieceIndex;
	}
	
	public float getPieceAngle() {
		return pieceAngle;
	}
	
	public Point2D.Float getCenter() {
		return center;
	}
	
	
	public String toString() {
		return (pieceIndex + ", " + pieceAngle + ", " + center);
	}

}
