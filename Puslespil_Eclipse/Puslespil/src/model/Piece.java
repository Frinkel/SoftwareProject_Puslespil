package model;

import processing.core.PApplet;

public class Piece {
	
	PApplet p5;
	
	float center_x, center_y, r, r1;
	public Piece(PApplet _p5, float cx, float cy, float r, float r1){
		this.center_x = cx;
		this.center_y = cy;
		this.r = r;
		this.r1 = r1;
		
		this.p5 = _p5;
	}
	
	public void display() {
		
		p5.ellipse(center_x, center_y, r, r1);
	}
}
