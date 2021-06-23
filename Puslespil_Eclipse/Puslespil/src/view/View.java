package view;

import java.awt.geom.Point2D;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.Menubar;
import model.Piece;
import model.TextInputField;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.MouseEvent;

public class View extends PApplet {
	// Joel
	public View() {
	}

	// View variables
	public int initWidth = width;
	public int initHeight = height;
	public boolean mouseReleased = false;
	public boolean puzzleComplete = false;

	// Piece variables
	boolean debugView = false;
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	float angle = 0;
	Piece currentPiece;
	ArrayList<Point2D.Float> offsetList = new ArrayList<>();
	Point2D.Float currentPieceOffset;

	// Menu variables
	public Menubar menubar;
	public boolean newPuzzle = true;
	public boolean solvePuzzle = false;
	public boolean solveRotation = false;
	public String puzzlePath = "";
	private String textInput = "";
	boolean showMenu = false;
	boolean ctrlPressed = false;
	boolean shiftPressed = false;
	public TextInputField pathInput;
	int menubarXPos = width;
	int menubarHeight = height / 2;
	PFont completionFont;
	ArrayList<Object> equalList = null;
	public boolean identicalPieces = false;
	public ArrayList<Integer> colorList = new ArrayList<>();

	// Controls if we're writing in the input field
	public boolean writing = false;

	// Controls whether to generate a puzzle or read a JSON
	public boolean inputState = false;

	// Setup runs once, when the view is created
	public void setup() {
		// Menu setup
		menubarHeight = height / 2;
		menubar = new Menubar(this, width - 150, 0, 150, menubarHeight);
		pathInput = new TextInputField(this, width / 2, height);
		this.initWidth = width;

		// Font setup
		completionFont = createFont("Arial", 26);
	}

	// The method is used only for setting the size of the window
	public void settings() {
		// Set the veiw port size
		size(displayHeight * 3 >> 2, displayHeight * 3 >> 2, P3D);
	}

	// Draw is run every frame of the program
	public void draw() {

		// Reapply background color (To erase what was previously drawn)
		background(200);

		// Draw the Pieces
		if (!pieceList.isEmpty()) {
			try {
				for (Piece piece : pieceList) {
					piece.display();
				}
			} catch (Exception e) {
				newPuzzle = true;
				puzzleComplete = false;
			}
		}

		// Draw Menubar
		menubar();

		// Draw the completion view
		if (puzzleComplete) {
			completionView();
		}

		// Draw the debug display
		if (debugView) {
			debugDisplay();
		}

		// Draw the input field
		if (inputState) {
			pathInput.draw();
		}

		identicalPieces();

	}

	// Visuals
	public void menubar() {
		// Draw the menubar
		menubar.display();

		// Update the position if the view is resized
		if (menubarXPos != width) {
			menubar.updatePos(width - 150, 0);
			menubarXPos = width;
			pathInput.updatePos(width, menubarHeight);
		}
	}

	public void completionView() {
		// Draw the completion text
		textAlign(CENTER);
		textFont(completionFont, 26);
		fill(color(220, 0, 0));
		text("Congratulations, you completed the puzzle!", menubarXPos / 2, 100);
	}

	public void debugDisplay() {
		try {
			for (Piece piece : pieceList) {
				piece.debugDisplay();
			}
		} catch (Exception e) {
		}
	}

	public void identicalPieces() {

		if (identicalPieces) {

			colorList.clear();
			int col = color(random(0, 255), random(0, 255), random(0, 255));
			for (int i = 0; i < equalList.size(); i++) {
				colorList.add(col - 1000000 * i);

			}

			for (int i = 0; i < equalList.size(); i++) {
				int[] intList = ((int[]) equalList.get(i));
				pieceList.get(intList[0]).getShape().setFill(colorList.get(i));
				pieceList.get(intList[1]).getShape().setFill(colorList.get(i));
			}

			identicalPieces = false;
		}

		if (equalList != null && !equalList.isEmpty()) {

			textAlign(CENTER);
			textFont(completionFont, 26);
			fill(color(220, 0, 0));
			text("THERE'S IDENTICAL PIECES!", menubarXPos / 2, 100);
			textFont(completionFont, 16);
			text("Puzzle is not solveable, since there's multiple Pieces which are identical.", menubarXPos / 2, 120);
		}
	}

	// Mouse events
	public void mousePressed(MouseEvent event) {

		// Controls the pickup of a Piece
		boolean piecelocked = false;
		if (!pieceList.isEmpty() && showMenu == false) {
			for (int i = pieceList.size() - 1; i >= 0; i--) {
				if (pieceList.get(i).isMouseOver() && !piecelocked && currentPiece == null) {
					piecelocked = true;
					currentPiece = pieceList.get(i);
					currentPiece.isCurrentPiece = true;
					angle = currentPiece.getAngle();
					currentPieceOffset = new Point2D.Float((int) (mouseX - currentPiece.getOrigin().x),
							(mouseY - currentPiece.getOrigin().y));
				}
			}
		}

		// Checks whether a menubar button is clicked
		menuButtonClicked(mouseX, mouseY);

		// Check if the input field is selected
		pathInput.selectInputField();

		if (pathInput.selected()) {
			pathInput.setValue(textInput);
		} else if (!pathInput.selected() && !pathInput.getValue().equals("Enter Path to Puzzle here!")
				&& !pathInput.getValue().equals("Invalid Path!")) {
			textInput = pathInput.getValue();
		}

		// Generate an offsetList whenever the middle mousebutton is clicked
		if (event.getButton() == 3) {
			offsetList.clear();
			for (Piece piece : pieceList) {
				int offsetX = (int) (mouseX - piece.getOrigin().x);
				int offsetY = (int) (mouseY - piece.getOrigin().y);
				offsetList.add(new Point2D.Float(offsetX, offsetY));
			}
		}
	}

	public void mouseReleased() {
		// Reset the current picked up Piece
		if (currentPiece != null) {
			mouseReleased = true;
			currentPiece.isCurrentPiece = false;
			currentPiece = null;
			angle = 0;
		}

	}

	public void mouseDragged(MouseEvent event) {
		// Move the currently picked up Piece
		if (currentPiece != null && event.getButton() == LEFT) {
			currentPiece.movePiece(new Point2D.Float(mouseX - currentPieceOffset.x, mouseY - currentPieceOffset.y));
		}

		// Controls if the sliders is dragged
		menubar.sliderDragged();

		// Move all pieces together
		if (event.getButton() == 3) { // Middle mouse button
			for (Piece piece : pieceList) {
				piece.movePiece(new Point2D.Float(mouseX - offsetList.get(pieceList.indexOf(piece)).x,
						mouseY - offsetList.get(pieceList.indexOf(piece)).y));
			}
		}
	}

	public void mouseWheel(MouseEvent event) {
		// Rotate piece
		if (currentPiece != null && !shiftPressed) {
			angle += event.getCount() * 45;
			currentPiece.rotatePiece(angle);
		} else if (currentPiece != null && shiftPressed && inputState) {
			angle += event.getCount();
			currentPiece.rotatePiece(angle);
		}
	}

	// Keyboard events
	public void keyPressed() {

		// Toggle debug view
		if (this.key == 'd' && !writing) {
			debugView = !debugView;
		}

		// Rotate currentPiece by key
		if (this.key == 'r') {
			if (currentPiece != null) {
				angle += 45;
				currentPiece.rotatePiece(angle);
			}
		}

		// --------- TextField Control ---------
		// Paste into input field
		if (key == CODED) {
			if (keyCode == CONTROL) {
				ctrlPressed = true;
			} else if (keyCode == SHIFT) {
				shiftPressed = true;
			}
		} else {
			if (ctrlPressed && keyCode == 86) {
				pathInput.pasteContents();
				ctrlPressed = false;
			}
		}

		// Add the char to the input field
		pathInput.addChar(key);

		// Check if the patch exists and that the file type is correct
		if (key == ENTER && pathInput.selected() && inputState) {
			if (checkPath(pathInput.getValue())) {
				puzzlePath = pathInput.getValue();
				textInput = pathInput.getValue();

				// Tell Main to generate a new puzzle
				newPuzzle = true;
			} else {
				textInput = pathInput.getValue();
				pathInput.setValue("Invalid Path!");
			}
			pathInput.toggleSelected();
		}
	}

	public void keyReleased() {

		// Reset control key
		if (key == CODED) {
			if (keyCode == CONTROL) {
				ctrlPressed = false;
			} else if (keyCode == SHIFT) {
				shiftPressed = false;
			}
		}
	}

	public boolean checkPath(String string) {
		return isFileJSON(string) && doesPathExist(string);
	}

	public boolean isFileJSON(String string) {
		if (string.length() > 4) {
			return (string.substring(string.length() - 4, string.length()).toLowerCase().equals("json"));
		}

		return false;
	}

	public boolean doesPathExist(String string) {
		Path p = Paths.get(string);
		return (Files.exists(p));
	}

	// Misc
	public void menuButtonClicked(int _x, int _y) {
		switch (menubar.MouseOverButton(_x, _y)) {
		case (0): // Create a new puzzle
			newPuzzle = true;
			puzzleComplete = false;
			break;
		case (1): // Solve puzzle
			solvePuzzle = true;
			break;
		case (2):
			solveRotation = true;
			break;
		}
	}

	public void resetPieceList() {
		pieceList.clear();
	}

	// Getters and setters

	public void addPieceToList(Piece piece) {
		pieceList.add(piece);
	}

	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}

	public Piece getCurrentPiece() {
		return currentPiece;
	}

	public void setEqualList(ArrayList<Object> _equalList) {
		this.equalList = _equalList;
	}

	public void clearEqualList() {
		this.equalList.clear();
	}

	public ArrayList<Object> getEqualList() {
		return this.equalList;
	}

}
