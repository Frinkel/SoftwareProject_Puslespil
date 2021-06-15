package controller;

public class PieceAndAngleDatatype {
	private int pieceIndex;
	private float pieceAngle;
	
	public PieceAndAngleDatatype(int pieceIndex, float pieceAngle) {
		this.pieceIndex = pieceIndex;
		this.pieceAngle = pieceAngle;
	}
	
	public int getPieceIndex() {
		return pieceIndex;
	}
	
	public float getPieceAngle() {
		return pieceAngle;
	}
	
	public String toString() {
		return (pieceIndex + ", " + pieceAngle);
	}

}
