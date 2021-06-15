package view;


import java.awt.geom.Point2D;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.Button;
import model.Menubar;
import model.Piece;
import model.TextInputField;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
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
	
	
	// Menu variables
	boolean showMenu = false;
	Button menu_button;
	public Menubar menubar;
	public boolean newPuzzle = true;
	public boolean solvePuzzle = false;
	int menubarXPos = width;
	int menubarHeight = height/2;
	PFont completionFont;
	PFont menuFont;
	public TextInputField pathInput;
	boolean ctrlPressed = false;
	public String puzzlePath = "";
	
	// Controls if we're writing in the input field
	public boolean writing = false;
	
	// Controls whether to generate a puzzle or read a JSON
	public boolean inputState = false;
	
	
	// identical use to setup in Processing IDE except for size()
	public void setup() {
		
		menubarHeight = height/2;
		
		menu_button = new Button(this, "Menu", width-120, 20, 100, 30);
		menubar = new Menubar(this, width-150, 0, 150, menubarHeight);
		pathInput = new TextInputField(this, width/2, height);
		System.out.println(menubarHeight);
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

		// Change background color to white
		background(200);
		
		// Draw the pieces
		if(!pieceList.isEmpty()) {
			try {
				for(Piece piece : pieceList) {
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
		
		if(inputState) { // If state is read JSON file
			pathInput.draw();
		}
		
	}
	

	
	// Visuals 
	public void menubar() {
		textAlign(LEFT);
		textFont(menuFont, 14);
		menubar.display();
		
		if(menubarXPos != width) {
			menubar.updatePos(width - 150, 0);
			menubarXPos = width;
			pathInput.updatePos(width, menubarHeight);
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
			solvePuzzle = true;
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
		
		pathInput.selectInputField();
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
		
		// Toggle debug view
		if(this.key == 'd' && !writing) {
			debugView = !debugView;
		}
		
		// Rotate currentPiece by key
		if(this.key == 'r') {
			if(currentPiece != null) {
				angle += 45;
				currentPiece.rotatePiece(angle);
			}
		}
		
		
		
		// --------- TextField Control ---------
		// Paste into input field
		if(key == CODED) {
			if(keyCode == CONTROL) {
				ctrlPressed = true;
			}
		} else {
			if(ctrlPressed && keyCode == 86) {
				System.out.println("ctrl + v");
				pathInput.pasteContents();
				ctrlPressed = false;
			}
		}
		pathInput.addChar(key);
		
		// Confirm that the path has to be read
		if(key == ENTER && pathInput.selected() && inputState) {
			//System.out.println("return");
			System.out.println(pathInput.getValue());
			System.out.println(checkPath(pathInput.getValue()));
			if(checkPath(pathInput.getValue())) {
				puzzlePath = pathInput.getValue();
			}
			pathInput.toggleSelected();
			
			newPuzzle = true;
		}
		
	}
	
	public void keyReleased() {
		
		// Reset control key
		if (key == CODED) {
			if (keyCode == CONTROL) {
				ctrlPressed = false;
			}
		}
	} 
	
	public boolean checkPath(String string) {
		return isFileJSON(string) && doesPathExist(string);
	}
	
	public boolean isFileJSON(String string) {
		if(string.length() > 4) {
			return (string.substring(string.length()-4, string.length()).toLowerCase().equals("json"));
		}
		
		return false;
	}
	
	public boolean doesPathExist(String string) {
		Path p = Paths.get(string);
		return (Files.exists(p));
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
