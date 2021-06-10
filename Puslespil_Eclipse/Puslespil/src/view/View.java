package view;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import controller.Main;
import model.Button;
import model.ImageInitializer;
import model.Menubar;
import model.Piece;
import model.Slider;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PShape;
import processing.core.PShapeSVG.Font;
import processing.core.PVector;
import processing.event.MouseEvent;

public class View extends PApplet {
	
	public View() {
		System.out.println("View running..");
	}
	
	// Variables
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	public boolean mouseReleased = false;
	ArrayList<PImage> sprites = new ArrayList<PImage>();
	public int initWidth = width;
	public int initHeight = height;
	
	boolean debugView = false;
	
	Piece currentPiece;
	
	float angle = 0;
	
	public boolean puzzleComplete = false;
	
	// Zoom and pan
	float view_scale;
	float viewX, viewY;
	
	PImage img;
	PShape myShape;
	
	// Menu variables
	boolean showMenu = false;
	Button menu_button;
	public Menubar menubar;
	public boolean newPuzzle = true;
	int menubarXPos = width;
	PFont completionFont;
	PFont menuFont;
	
	
	
	
	
	
	// identical use to setup in Processing IDE except for size()
	public void setup() {
		
		// Zoom and Pan init values
		view_scale = 1f;
		viewX = 0;
		viewY = 0;
		
		menu_button = new Button(this, "Menu", width-120, 20, 100, 30);
		menubar = new Menubar(this, width-150, 0, 150, height/2);
		
		
		this.initWidth = width;
		
		
		//FONT
		completionFont = createFont("Arial", 26);
		menuFont = createFont("Arial", 14);
		
		
	}
	
	// method used only for setting the size of the window
	public void settings() {
		// Create the view object
		size(displayHeight*3>>2, displayHeight*3>>2, P3D);
	}
	
	// identical use to draw in Processing IDE
	public void draw(){
		// For zoom and panning
		//translate(viewX, viewY);
		//scale(view_scale);
		
		// Change background color to white
		background(100);
		
		// Draw the pieces
		boolean piecelocked = false;
		if(!pieceList.isEmpty() && showMenu == false) {
//			for(int i = pieceList.size()-1; i >= 0; i--) {
//				if(pieceList.get(i).isMouseOver() && !piecelocked && currentPiece == null) {
//					pieceList.get(i).col = color(255,0,0);
//					piecelocked = true;
//				} else {
//					pieceList.get(i).col = 0;
//				}
//			}
//			if(currentPiece != null) {
//				currentPiece.col = color(255,0,0);
//			}
			try {
			for(Piece piece : pieceList) {
//				if(pieceList.isEmpty() || piece == null) {
//					break;
//				}
				piece.display();
			}
			} catch(Exception e) {
				System.out.println("***********************************BREAK********************************************");
				newPuzzle = true;
				puzzleComplete = false;
			}
		}
		
		
		
		// Draw the menu bar
		menubar();
		
		// Draw the completion view
		if(puzzleComplete) {completionView();}
		
		if(debugView) {debugDisplay();}
		
	}
	

	
	// Visuals 
	public void menubar() {
		textAlign(LEFT);
		textFont(menuFont, 14);
		menubar.display();
		
		if(menubarXPos != width) {
			menubar.updatePos(width - 150, 0);
			menubarXPos = width;
		}
	}
	
	public void menuButtonClicked(int _x, int _y) {
		switch(menubar.MouseOverButton(_x, _y)) {
		case(0):
			newPuzzle = true;
			puzzleComplete = false;
		break;
		case(1):
			System.out.println("1");
		break;
		case(2):
			System.out.println("2");
		break;
		}
	}
	
	public void completionView() {
		textAlign(CENTER);
		textFont(completionFont, 26);
		fill(color(220,0,0));
		text("Congratulations, you completed the puzzle!", menubarXPos/2, 100);
	}
	
	public void debugDisplay() {
		for(Piece piece : pieceList) {
			piece.debugDisplay();
		}
	}

	// Mouse events
	public void mousePressed() {
		
		boolean piecelocked = false;
		if(!pieceList.isEmpty() && showMenu == false) {
			for(int i = pieceList.size()-1; i >= 0; i--) {
				if(pieceList.get(i).isMouseOver() && !piecelocked && currentPiece == null) {
					piecelocked = true;
					currentPiece = pieceList.get(i);
					currentPiece.isCurrentPiece = true;
					angle = currentPiece.getAngle();
				}
			}
		}
		
		menuButtonClicked(mouseX, mouseY);
		
//		if(menu_button.MouseIsOver()) {
//			System.out.println("click");
//			showMenu = !showMenu;
//		}
		
		/*
		for(Piece piece : pieceList){
			
			if(piece.isMouseOver()) {
				//System.out.println("piece clicked");
				currentPiece = piece;
				currentPiece.isCurrentPiece = true;
				angle = currentPiece.getAngle();
			}
		}
		*/
	}
	
	public void mouseReleased() {
		if(currentPiece != null) {
			mouseReleased = true;
			currentPiece.isCurrentPiece = false;
			currentPiece = null;
			mouseReleased = false;
			angle = 0;
		}
		
	}
	
	public void mouseDragged(MouseEvent event) {
		
		if(currentPiece != null) {
			currentPiece.movePiece(new Point2D.Float(mouseX, mouseY));
		}
		
		menubar.sliderDragged();
	}
	
	public void mouseWheel(MouseEvent event) {
		// Rotate piece
		if(currentPiece != null) {
			angle += event.getCount()*45;
			currentPiece.rotatePiece(angle);
		}
	}
	
	// Keyboard events
	public void keyPressed() {
		if(this.key == 'd') {
			debugView = !debugView;
		}
		
		if(this.key == 'r') {
			if(currentPiece != null) {
				angle += 45;
				currentPiece.rotatePiece(angle);
			}
		}
	}
	
	// Getters and setters
	public void setImageList(ArrayList<PImage> _sprites) {
		this.sprites = _sprites;
	}
	
	public void addPieceToList(Piece piece) {
		pieceList.add(piece);
	}
	
	public void resetPieceList() {
		pieceList.clear();
	}
	
	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}
	
	public Piece getCurrentPiece() {
		return currentPiece;
	}
}
