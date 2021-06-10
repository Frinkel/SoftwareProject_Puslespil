package model;

import java.util.Random;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PShape;

public class Slider {
	
	PApplet pA;
	
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
	
	
	public Slider(PApplet _pA, String _name, int _x, int _y, int _min, int _max, int initValue) {
		this.pA = _pA;
		this.x = _x;
		this.y = _y;
		this.originX = _x;
		this.originY = _y;
		this.value = initValue;
		this.min = _min;
		this.max = _max;
		this.name = _name;
		
		rect = pA.createShape(PConstants.RECT, x, y-5, sliderWidth, 10);
		
		int offSet = (int) ((float)(sliderWidth*(value/(float)max)-sides/2));
		clickableX = x + offSet + sides/2;
		clickableY = y - 10;
		
		clickable = pA.createShape(PConstants.RECT, clickableX, clickableY, 20, 20);
		clickable.setFill(pA.color(255, 255, 255));
		clickable.setStroke(false);
		rect.setStroke(false);
		
//		clickable = pA.createShape(PShape.PATH);
//		clickable.beginShape();
//		clickable.vertex(x + offSet, clickableY);
//		clickable.vertex((x+sides) + offSet, clickableY);
//		clickable.vertex((x+sides) + offSet, clickableY+sides);
//		clickable.vertex(x + offSet, clickableY+sides);
//		clickable.vertex(x + offSet, clickableY);
//		clickable.endShape(PConstants.CLOSE);
		//clickable.translate(-100 + (initValue/max), -5);
		
		// RECT
//		rect.setFill(255);
//		//rect.setStroke(0);
//		//rect.setStrokeWeight(2);
//		rect.setStroke(0);
//		rect.setStrokeWeight(10);
//		
//		// CLICKABLE
//		clickable.setFill(255);
//		clickable.setStroke(0);
//		clickable.setStrokeWeight(2);
		
		font = pA.createFont("Arial",16,true);
	}
	
	boolean mouseOver() {
		int mx = pA.mouseX, my = pA.mouseY;
		if (mx > clickableX-sides & mx < clickableX+sides & my > clickableY & my < clickableY+sides)  isInside = true;
		else                                        											isInside = false;
		
		return isInside;
	}
	
	public void drawSlider() {
		pA.fill(255);
		pA.textFont(font,16);
		pA.textAlign(PConstants.LEFT);
		pA.text(min, x - 10, clickableY + 16);
		pA.text(max, x + sliderWidth + 3, clickableY + 16);
		
		// DRAW
		pA.shape(rect);
		pA.shape(clickable);
		
		//pA.rect(10, 10, 100, 100);
		
		pA.text(value, clickableX - sides/4, clickableY - 5);
		
		pA.text(name, x+sliderWidth/2-pA.textWidth(name)/2, y-30);
		
		
	}
	
	private void updateBox(int x, int y, int _offSet) {
		
		clickable = pA.createShape(PConstants.RECT, clickableX-sides/2, clickableY, 20, 20);
		//x = PApplet.constrain(x, originX, originX + sliderWidth);
		
//		clickable = pA.createShape(PShape.PATH);
//		clickable.beginShape();
//		clickable.vertex(x + _offSet, y);
//		clickable.vertex((x+sides) + _offSet, y);
//		clickable.vertex((x+sides) + _offSet, y+sides);
//		clickable.vertex(x + _offSet, y+sides);
//		clickable.vertex(x + _offSet, y);
//		clickable.endShape();
//		clickable.setStrokeWeight(2);
	}
	

	public void onMouseDragged() {
		if(mouseOver()) {
			//clickable.setFill(0);
			//clickable.setStroke(0);
			//clickable.setStrokeWeight(2);
			//rect.setStrokeWeight(2);
			clickableX = PApplet.constrain(pA.mouseX, originX, originX + sliderWidth);
			updateBox(clickableX, clickableY, -sides/2);
			
			value = (int)Math.round(((float)(clickableX - originX)/sliderWidth)*(max - min) + min);
			
			
		} else {
			//clickable.setFill(255);
			//clickable.setStroke(0);
			//clickable.setStrokeWeight(2);
		}
	}

	public void onMouseReleased() {
		if(clickable.contains(pA.mouseX, pA.mouseY)) {
			//clickable.setFill(255);
			//clickable.setStroke(0);
			//clickable.setStrokeWeight(2);
		}
	}
	
	public int getValue() {
		return value;
	}
}
