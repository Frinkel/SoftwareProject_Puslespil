package model;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import view.View;

public class Menubar {
	// Joel
	View view;
	int x, y, width, height;

	// Sliders
	ArrayList<Slider> sliderList = new ArrayList<>();
	public Slider sliderPieceAmount;
	public Slider sliderDistortionPoints;

	// Togglers
	public ToggleSwitch firstToggle;

	PFont menuFont;

	int minDistortionPoints = 1;
	int minPieceAmount = 1;
	int maxDistortionPoints = 20;
	int maxPieceAmount = 100;

	public Menubar(View _view, int _x, int _y, int _width, int _height) {
		this.view = _view;
		this.x = _x;
		this.y = _y;
		this.width = _width;
		this.height = _height;

		// Sliders
		sliderPieceAmount = new Slider(view, "Piece Amount", x + 15, 220, minPieceAmount, maxPieceAmount,
				minPieceAmount);
		sliderDistortionPoints = new Slider(view, "Distortion Points", x + 15, 300, minDistortionPoints,
				maxDistortionPoints, minDistortionPoints);
		sliderList.add(sliderPieceAmount);
		sliderList.add(sliderDistortionPoints);

		menuFont = view.createFont("Arial", 14);

		firstToggle = new ToggleSwitch((View) view, "Generate", "Read", x, 350, width, 30, false);

	}

	public void display() {

		view.fill(0);
		view.rect(x, y, width, height, 0, 0, 20, 20);
		view.textAlign(PApplet.LEFT);
		view.textFont(menuFont, 14);

		// On hover
		if (MouseIsOverButton(x, 35) && !view.inputState) {
			view.fill(100);
			view.rect(x, y + 35, width, 40);
		} else if (MouseIsOverButton(x, 75)) {
			view.fill(100);
			view.rect(x, y + 75, width, 40);
		} else if (MouseIsOverButton(x, 115)) {
			view.fill(100);
			view.rect(x, y + 115, width, 40);
		}

		view.strokeWeight(2);
		view.fill(255);
		view.text("Menubar", x + 50, y + 20);
		view.stroke(255);
		view.line(x + 10, y + 30, x + width - 10, y + 30);

		if (view.inputState) {
			view.fill(100);
			view.text("Generate new puzzle", x + 10, y + 60);
		} else {
			view.text("Generate new puzzle", x + 10, y + 60);
		}

		view.fill(255);
		view.text("Solve puzzle", x + 35, y + 100);
		view.text("Solve rotation", x + 30, y + 140);
		view.line(x + 10, y + 160, x + width - 10, y + 160);

		// Draw the puzzle input toggle
		firstToggle.draw();

		if (!view.inputState) {
			for (Slider slider : sliderList) {
				slider.drawSlider();
			}
		}
		

	}

	public boolean MouseIsOverButton(int _x, int _y) {
		if (view.mouseX > _x && view.mouseX < (_x + width) && view.mouseY > _y && view.mouseY < (_y + 40)) {
			return true;
		}
		return false;
	}
	
	public boolean MouseIsOverMenu() {
		if (view.mouseX > x && view.mouseX < (x + width) && view.mouseY > y && view.mouseY < (y + height)) {
			return true;
		}
		return false;
	}

	public int MouseOverButton(int _x, int _y) {

		if (MouseIsOverButton(x, 35) && !view.inputState) {
			return 0;
		} else if (MouseIsOverButton(x, 75)) {
			return 1;
		} else if (MouseIsOverButton(x, 115)) {
			return 2;
		}

		// Control the toggle click
		firstToggle.toggle();
		view.inputState = firstToggle.getState();

		return -1;
	}

	public void updatePos(int _x, int _y) {
		this.x = _x;
		this.y = _y;

		int distortionPoints = sliderDistortionPoints.getValue();
		int pieceAmount = sliderPieceAmount.getValue();
		boolean toggleState = firstToggle.getState();

		sliderList.remove(sliderPieceAmount);
		sliderList.remove(sliderDistortionPoints);

		sliderPieceAmount = new Slider(view, "Piece Amount", x + 15, 220, minPieceAmount, maxPieceAmount, pieceAmount);
		sliderDistortionPoints = new Slider(view, "Distortion Points", x + 15, 300, minDistortionPoints,
				maxDistortionPoints, distortionPoints);
		firstToggle = new ToggleSwitch((View) view, "Generate", "Read", x, 350, width, 30, toggleState);

		sliderList.add(sliderPieceAmount);
		sliderList.add(sliderDistortionPoints);
	}

	public void sliderDragged() {
		if (view.getCurrentPiece() == null) {
			for (Slider slider : sliderList) {
				slider.onMouseDragged();
			}
		}
	}
}
