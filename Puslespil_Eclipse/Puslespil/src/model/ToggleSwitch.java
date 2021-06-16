package model;

import view.View;

public class ToggleSwitch {
	View view;
	
	String text1 = "";
	String text2 = "";
	
	int x, y, width, height;
	
	boolean state = false;
	
	public ToggleSwitch(View _view, String t1, String t2, int _x, int _y, int _width, int _height, boolean _state) {
		this.view = _view;
		this.x = _x;
		this.y = _y;
		this.width = _width;
		this.height = _height;
		this.state = _state;
		
		this.text1 = t1;
		this.text2 = t2;
	}
	
	public void draw() {
		view.strokeWeight(0);
		
		if(!state) {
			view.fill(255);
			view.rect(x, y, width/2, height);
			view.fill(150);
			view.rect(x + width/2, y, width/2, height);
		} else if(state) {
			view.fill(150);
			view.rect(x, y, width/2, height);
			view.fill(255);
			view.rect(x + width/2, y, width/2, height);
		}
		
		view.fill(0);
		view.text(text1, x+5, y+20);
		view.text(text2, x + width/2+5, y+20);
	}
	
	public void toggle() {
		if(mouseIsOver(x, y, width/2, height) && state) {
			//System.out.println(state);
			state = !state;
			view.resetPieceList();
		} else if(mouseIsOver(x+width/2, y, width/2, height) && !state) {
			//System.out.println(state);
			state = !state;
			view.resetPieceList();
		}
	}
	
	
	// Check if the mouse is over the text input field
	public boolean mouseIsOver(int _x, int _y, int _width, int _height) {
		if(view.mouseX > _x && view.mouseX < (_x + _width) && view.mouseY > _y && view.mouseY < (_y + _height)) {
			return true;
		}
		return false;
	}
	
	public boolean getState() {
		return state;
	}
}
