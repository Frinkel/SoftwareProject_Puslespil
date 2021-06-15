package model;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PShape;
import view.View;

public class Slider {
	
	View view;
	
	PShape rect;
	PShape clickable;
	
	int x;
	int y;
	int originX;
	int originY;
	int clickableX;
	int clickableY;
	int sides = 20;
	int sliderWidth = 100;
	
	int value;
	int min = 0;
	int max = 5;
	
	String name;
	
	PFont font;
	
	boolean isInside = false;
	
	
	public Slider(View _pA, String _name, int _x, int _y, int _min, int _max, int initValue) {
		this.view = _pA;
		this.x = _x;
		this.y = _y;
		this.originX = _x;
		this.originY = _y;
		this.value = initValue;
		this.min = _min;
		this.max = _max;
		this.name = _name;
		
		rect = view.createShape(PConstants.RECT, x, y-5, sliderWidth, 10);
		
		int offSet = (int) ((float)(sliderWidth*(value/(float)max)-sides/2));
		clickableX = x + offSet + sides/2;
		clickableY = y - 10;
		
		clickable = view.createShape(PConstants.RECT, clickableX, clickableY, 20, 20);
		clickable.setFill(view.color(255, 255, 255));
		clickable.setStroke(false);
		rect.setStroke(false);
		rect.setFill(view.color(255,255,255));
		
		font = view.createFont("Arial",14,true);
	}
	
	boolean mouseOver() {
		int mx = view.mouseX, my = view.mouseY;
		if (mx > clickableX-sides & mx < clickableX+sides & my > clickableY & my < clickableY+sides)  isInside = true;
		else                                        											isInside = false;
		
		return isInside;
	}
	
	public void drawSlider() {
		view.fill(255);
		view.textFont(font,14);
		view.textAlign(PConstants.LEFT);
		view.text(min, x - 10, clickableY + 16);
		view.text(max, x + sliderWidth + 3, clickableY + 16);
		
		// DRAW
		view.shape(rect);
		view.shape(clickable);
		
		//pA.rect(10, 10, 100, 100);
		
		view.fill(255);
		view.textAlign(PConstants.CENTER);
		view.text(value, clickableX - sides/4, clickableY - 5);
		view.text(name, x+(int)(sliderWidth/2), y-30);
		
		
	}
	
	private void updateBox(int x, int y, int _offSet) {
		clickable = view.createShape(PConstants.RECT, clickableX-sides/2, clickableY, 20, 20);
	}
	

	public void onMouseDragged() {
		if(mouseOver() && !view.inputState) {
			clickableX = PApplet.constrain(view.mouseX, originX, originX + sliderWidth);
			view.fill(255);
			updateBox(clickableX, clickableY, -sides/2);
			
			value = (int)Math.round(((float)(clickableX - originX)/sliderWidth)*(max - min) + min);

		}
	}
	
	public int getValue() {
		return value;
	}
}
