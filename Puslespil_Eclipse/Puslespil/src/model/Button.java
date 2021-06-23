package model;

import processing.core.PApplet;
import processing.core.PConstants;

//public class Button {
//	PApplet pA;
//	int x, y;
//	int width = 100;
//	int height = 50;
//	
//	public Button(PApplet _pA, int _x, int _y) {
//		this.pA = _pA;
//		this.x = _x;
//		this.y = _y;
//		
//		initialize();
//	}
//	
//	private void initialize() {
//		
//	}
//	
//	public void display() {
//		pA.fill(0);
//		pA.rect(x, y, width, height, 50);
//	}
//}


// From: https://blog.startingelectronics.com/a-simple-button-for-processing-language-code/
public class Button {
	// Joel
	PApplet pA;
	String label;
	float x;    // top left corner x position
	float y;    // top left corner y position
	float w;    // width of button
	float h;    // height of button
	
	int backgroundColor = 0;
	int borderColor = 141;
	int textColor = 255;
	  
	public Button(PApplet _pA, String labelB, float xpos, float ypos, float widthB, float heightB) {
		this.pA = _pA;
		label = labelB;
		x = xpos;
		y = ypos;
		w = widthB;
		h = heightB;
	}
	
	public void update(int _backgroundColor, int _borderColor, int _textColor) {
		backgroundColor = _backgroundColor;
		borderColor = _borderColor;
		textColor = _textColor;
	}
	
	public void Draw() {
		pA.fill(backgroundColor);
		pA.stroke(borderColor);
		pA.rect(x, y, w, h, 10);
		pA.textAlign(PConstants.CENTER, PConstants.CENTER);
		pA.fill(textColor);
		pA.text(label, x + (w / 2), y + (h / 2));
	}
	
	public boolean MouseIsOver() {
		if(pA.mouseX > x && pA.mouseX < (x + w) && pA.mouseY > y && pA.mouseY < (y + h)) {
			return true;
		}
		return false;
	}
}
