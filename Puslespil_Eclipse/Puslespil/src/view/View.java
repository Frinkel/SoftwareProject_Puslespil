package view;

import model.Piece;
import processing.core.PApplet;

public class View {
	// Variables
	PApplet Main;
	
	// Constructor
	public View(PApplet _Main) {
		
	}
	
	// Functions
	public void initView(int screenWidth, int screenHeight) {
		Main.size(screenWidth, screenHeight);
	}
}
