package model;

public class AngleLength {
	// Carl
	private float angle;
	private float length;
	
	public AngleLength(float angle, float length) {
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
