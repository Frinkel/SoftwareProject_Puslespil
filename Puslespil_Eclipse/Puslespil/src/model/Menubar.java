package model;

import java.util.ArrayList;

import processing.core.PApplet;

public class Menubar {
	PApplet view;
	int x, y, width, height;
	Button generate_button;
	
	// Sliders
	ArrayList<Slider> sliderList = new ArrayList<>();
	public Slider sliderPieceAmount;
	public Slider sliderDistortionPoints;
	
	
	int minDistortionPoints = 1;
	int minPieceAmount = 1;
	int maxDistortionPoints = 20;
	int maxPieceAmount = 100;
	
	public Menubar(PApplet _view, int _x, int _y, int _width, int _height) {
		this.view = _view;
		this.x = _x;
		this.y = _y;
		this.width = _width;
		this.height = _height;
		generate_button = new Button(view, "Generate new puzzle", width-120, 20, 100, 30);
		
		// Sliders
		sliderPieceAmount = new Slider(view, "Piece Amount", x + 15, 220, minPieceAmount, maxPieceAmount, minPieceAmount);
		sliderDistortionPoints = new Slider(view, "Distortion Points", x + 15, 300, minDistortionPoints, maxDistortionPoints, minDistortionPoints);
		sliderList.add(sliderPieceAmount);
		sliderList.add(sliderDistortionPoints);
		
	}
	
	public void display() {
		
		view.fill(0);
		view.rect(x, y, width, height, 0, 0, 20, 20);
		
		// On hover
		if(MouseIsOver(x, 40)) {
			view.fill(100);
			view.rect(x, y + 35, width, 40);
		} else if (MouseIsOver(x, 60)) {
			view.fill(100);
			view.rect(x, y + 75, width, 40);
		} else if (MouseIsOver(x, 100)) {
			view.fill(100);
			view.rect(x, y + 115, width, 40);
		}
		
		view.fill(255);
		view.text("Menubar", x + 50, y + 20);
		view.stroke(255);
		view.line(x + 10, y + 30, x + width-10, y+30);
		view.text("Generate new puzzle", x + 10, y + 60);
		view.text("Solve puzzle", x + 35, y + 100);
		view.text("Solve next piece", x + 25, y + 140);
		view.line(x + 10, y + 160, x + width-10, y+160);
		
		for(Slider slider : sliderList) {
			slider.drawSlider();
		}
		
		
	}
	
	public boolean MouseIsOver(int _x, int _y) {
		if(view.mouseX > _x && view.mouseX < (_x + width) && view.mouseY > _y && view.mouseY < (_y + 40)) {
			return true;
		}
		return false;
	}
	
	public int MouseOverButton(int _x, int _y) {
		if(MouseIsOver(x, 40)) {
			return 0;
		} else if (MouseIsOver(x, 60)) {
			return 1;
		} else if (MouseIsOver(x, 100)) {
			return 2;
		}
		
		return -1;
	}
	
	public void updatePos(int _x, int _y) {
		this.x = _x;
		this.y = _y;
		
		int distortionPoints = sliderDistortionPoints.getValue();
		int pieceAmount = sliderPieceAmount.getValue();
		
		sliderList.remove(sliderPieceAmount);
		sliderList.remove(sliderDistortionPoints);
		
		
		sliderPieceAmount = new Slider(view, "Piece Amount", x + 15, 220, minPieceAmount, maxPieceAmount, pieceAmount);
		sliderDistortionPoints = new Slider(view, "Distortion Points", x + 15, 300, minDistortionPoints, maxDistortionPoints, distortionPoints);
		
		sliderList.add(sliderPieceAmount);
		sliderList.add(sliderDistortionPoints);
	}
	
	public void sliderDragged() {
		for(Slider slider : sliderList) {
			slider.onMouseDragged();
		}
	}
}
