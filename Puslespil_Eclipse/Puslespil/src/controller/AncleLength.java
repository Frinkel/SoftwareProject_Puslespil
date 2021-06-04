package controller;

public class AncleLength {
	private float angle;
	private float length;
	
	public AncleLength(float angle, float length) {
		this.angle = angle;
		this.length = length;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public float getLength() {
		return length;
	}
	
	public String toString() {
		return ("angle: " + getAngle() + " length: " + getLength());
	}

}
