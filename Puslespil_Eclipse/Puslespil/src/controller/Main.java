package controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import model.Generator;
import model.ImageInitializer;
import model.Piece;
import model.PieceAndAngleDatatype;
import model.PieceCompare;
import model.PieceReader;
import model.PuzzleSolver;
import processing.core.PApplet;
import processing.core.PImage;
import view.View;

public class Main {
	private static int boardSize = 800;
	static Generator generator;
	static Object[] puzzleArray;;

	// Main method
	public static void main(String[] args) {
		// Alexander, Carl and mostly Joel
		// Create the view
		View view = new View();

		// Run the view
		PApplet.runSketch(new String[] { "--location=200,200", "" }, view);

		ArrayList<Piece> pieceList = view.getPieceList();
		boolean run = true;

		// Does everything in while loop while the view is running
		while (run) {
			Piece currentPiece = getCurrentPiece(view);

			if (view.mouseReleased) {
				pieceSnapping(true, view, generator, currentPiece);
				view.mouseReleased = false;
			}

			// Listen for the newPuzzle call from the view, stating a new puzzle should be
			// generated
			if (view.newPuzzle) {
				if (view.inputState && view.puzzlePath != "") { // READ IN A PUZZLE
					view.resetPieceList();
					readPuzzle(view, view.puzzlePath);
					view.newPuzzle = false;
					randomizePuzzle(view, pieceList, 100, view.initWidth - 100, true);

				} else if (!view.inputState) { // GENERATE RANDOM PUZZLE
					view.resetPieceList();
					generatePuzzle(view);
					view.newPuzzle = false;
					randomizePuzzle(view, pieceList, 100, view.initWidth - 100, true);
				}
			}

			// Listen for the solvePuzzle call from the view, stating that the puzzle should
			// be solved by our solving algorithm
			if (view.solvePuzzle && puzzleArray != null && !view.getPieceList().isEmpty()) {
				solvePuzzle(view, puzzleArray);
				view.solvePuzzle = false;
			}

			if (view.solveRotation && puzzleArray != null && !view.getPieceList().isEmpty()) {
				solveRotation(view, puzzleArray);
				view.solveRotation = false;
			}
		}
	}

	// Read a puzzle from a path, the puzzle MUST be a JSON file
	public static void readPuzzle(View view, String path) {

		// view.identicalPieces = false;
		PieceReader pieceReader = new PieceReader();
		puzzleArray = pieceReader.readPuzzle(path);

		PieceCompare pC = new PieceCompare();
		ArrayList<Object> equalList = pC.pieceComparator(puzzleArray);

		if (equalList != null && !equalList.isEmpty()) {

			view.setEqualList(equalList);
			view.identicalPieces = true;
		} else if (view.getEqualList() != null && !view.getEqualList().isEmpty()) {
			view.clearEqualList();
		}

		// Loop through puzzle array and create each Piece
		for (int i = 0; i < puzzleArray.length; i++) {
			Point2D.Float[] v = (Point2D.Float[]) puzzleArray[i];
			Point2D.Float center = new Point2D.Float(0.0f, 0.0f);

			Piece p = new Piece(view, center, v);
			view.addPieceToList(p);
		}
	}

	public static void generatePuzzle(View view) {
		// view.identicalPieces = false;

		int pieceAmount, distortionPoints;

		// Get data from the sliders in the menubar
		if (view.menubar != null) {
			pieceAmount = view.menubar.sliderPieceAmount.getValue();
			distortionPoints = view.menubar.sliderDistortionPoints.getValue();
		} else {
			pieceAmount = 1;
			distortionPoints = 1;
		}

		generator = new Generator(boardSize, pieceAmount, distortionPoints);

		// Get the images to be mapped onto the pieces
		String[] paths = {"assets\\images\\rap.jpg", "assets\\images\\fox.jpg",
				"assets\\images\\dog1.jpg", "assets\\images\\dog2.jpg" };
		ImageInitializer imi = new ImageInitializer(view, boardSize, pieceAmount, generator.getColumns(),
				generator.getRows());
		ArrayList<PImage> sprites = imi.imageSplitter(
				imi.imageLoader(paths).get((int) (Math.round(Math.random() * (paths.length - 1)))), boardSize);

		puzzleArray = generator.generate();
		PieceCompare pC = new PieceCompare();
		pC.pieceComparator(puzzleArray);

		// Loop through puzzle array and create each Piece
		for (int i = 0; i < puzzleArray.length; i++) {
			Point2D.Float[] v = (Point2D.Float[]) puzzleArray[i];
			Point2D.Float center = new Point2D.Float(0.0f, 0.0f);

			Piece p = new Piece(view, center, v, sprites.get(i));
			view.addPieceToList(p);
		}
	}

	public static void solvePuzzle(View view, Object[] pieces) {
		PuzzleSolver puzzleSolver = new PuzzleSolver();
		PieceAndAngleDatatype[] pieceAndRotationalAngle = puzzleSolver.solvePuzzle(pieces); 
		// Loop through all pieces and calculate their solved rotation and position, and
		// move the pieces
		for (int i = 0; i < pieces.length; i++) {
			float angle = puzzleSolver.getAngleGivenIndex(i, pieceAndRotationalAngle);
			Point2D.Float center = puzzleSolver.getCenterGivenIndex(i, pieceAndRotationalAngle);
			view.getPieceList().get(i).rotatePiece(PApplet.degrees(angle));
			view.getPieceList().get(i).movePiece(center);
		}
	}

	public static void solveRotation(View view, Object[] pieces) {
		PuzzleSolver puzzleSolver = new PuzzleSolver();
		PieceAndAngleDatatype[] pieceAndRotationalAngle = puzzleSolver.solvePuzzle(pieces); 

		// Loop through all pieces and calculate their solved rotation and position, and
		// move the pieces
		for (int i = 0; i < pieces.length; i++) {
			float angle = puzzleSolver.getAngleGivenIndex(i, pieceAndRotationalAngle);
			view.getPieceList().get(i).rotatePiece(PApplet.degrees(angle));
		}
	}

	private static Piece getCurrentPiece(View view) {
		ArrayList<Piece> pieceList = view.getPieceList();

		try {
			for (Piece piece : pieceList) {
				if (piece.isCurrentPiece) {
					return piece;
				}
			}
		} catch (Exception e) {

		}

		return null;
	}

	// Checks if puzzle is completed DOES NOT WORK AS INTENDED (BUGGED ON SOME
	// ANGLES)
	private static void completionCheck(View view, Generator generator) {
		/*
		 * For each piece, find all neighbor pieces, and check if they are colliding, if
		 * all pieces are colliding with their corrosponding neighbor pieces, the puzzle
		 * must be solved. *ERROR?* If you disable snapping and stack every piece, then
		 * the puzzle is per definition solved? *SOLVE?* check if an edge point, outside
		 * the puzzle, is inside the corrosponding neighbor puzzle?
		 */

		boolean isComplete = true;

		// Find neighbor pieces
		ArrayList<Piece> pieceList = view.getPieceList();

		for (Piece currentPiece : pieceList) {
			double boardsize = generator.getBoardSize();
			int columns = generator.getColumns();
			int rows = generator.getRows();
			double x_spacing = boardsize / columns;
			double y_spacing = boardsize / rows;
			int width = generator.getPieceBaseWidth();
			int height = generator.getPieceBaseHeight();

			int currentIndex = pieceList.indexOf(currentPiece);
			float angle = currentPiece.getAngle();
			int top = currentIndex - (columns);
			int bottom = currentIndex + (columns);
			int right = currentIndex + 1;
			int left = currentIndex - 1;
			// int offset = 100;

			Piece topNeighbor = null;
			Piece bottomNeighbor = null;
			Piece rightNeighbor = null;
			Piece leftNeighbor = null;

			if (top >= 0 && top < pieceList.size()) {
				topNeighbor = pieceList.get(top);
			}
			if (bottom >= 0 && bottom < pieceList.size()) {
				bottomNeighbor = pieceList.get(bottom);
			}
			if (right >= 0 && right < pieceList.size() && (right) % columns != 0) {
				rightNeighbor = pieceList.get(right);
			}
			if (left >= 0 && left < pieceList.size() && (left + 1) % columns != 0) {
				leftNeighbor = pieceList.get(left);
			}

			if (topNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle())))
					&& topNeighbor.contains(
							currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 90)) * -width),
							currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 90)) * height)) {
				isComplete &= true;
			} else if (topNeighbor != null) {
				isComplete &= false;
			}

			if (bottomNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(bottomNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(bottomNeighbor.getAngle())))
					&& bottomNeighbor.contains(
							currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 270)) * -width),
							currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 270)) * height)) {
				isComplete &= true;
			} else if (bottomNeighbor != null) {
				isComplete &= false;
			}

			if (rightNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle())))
					&& rightNeighbor.contains(
							currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 180)) * -width),
							currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 180)) * height)) {
				isComplete &= true;
			} else if (rightNeighbor != null) {
				isComplete &= false;
			}

			if (leftNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(leftNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(leftNeighbor.getAngle())))
					&& leftNeighbor.contains(currentPiece.getOrigin().x - (PApplet.cos(PApplet.radians(angle)) * width),
							currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle)) * height)) {
				isComplete &= true;
			} else if (leftNeighbor != null) {
				isComplete &= false;
			}

		}

		view.puzzleComplete = isComplete;

	}

	private static void completionCheckV2(View view, Generator generator) {

		boolean isComplete = true;

		for (Piece currentPiece : view.getPieceList()) {

			// Find neighbor pieces
			ArrayList<Piece> pieceList = view.getPieceList();
			int columns = generator.getColumns();
			int width = generator.getPieceBaseWidth();
			int height = generator.getPieceBaseHeight();
			int currentIndex = pieceList.indexOf(currentPiece);
			float angle = currentPiece.getAngle();
			int top = currentIndex - (columns);
			int bottom = currentIndex + (columns);
			int right = currentIndex + 1;
			int left = currentIndex - 1;

			Piece topNeighbor = null;
			Piece bottomNeighbor = null;
			Piece rightNeighbor = null;
			Piece leftNeighbor = null;

			float topDist = 0;
			float botDist = 0;
			float rightDist = 0;
			float leftDist = 0;

			if (top >= 0 && top < pieceList.size()) {
				topNeighbor = pieceList.get(top);
			}
			if (bottom >= 0 && bottom < pieceList.size()) {
				bottomNeighbor = pieceList.get(bottom);
			}
			if (right >= 0 && right < pieceList.size() && (right) % columns != 0) {
				rightNeighbor = pieceList.get(right);
			}
			if (left >= 0 && left < pieceList.size() && (left + 1) % columns != 0) {
				leftNeighbor = pieceList.get(left);
			}

			if (topNeighbor != null) {
				topDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						topNeighbor.getOrigin().x, topNeighbor.getOrigin().y) - height);
			}
			if (bottomNeighbor != null) {
				botDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						bottomNeighbor.getOrigin().x, bottomNeighbor.getOrigin().y) - height);
			}
			if (rightNeighbor != null) {
				rightDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						rightNeighbor.getOrigin().x, rightNeighbor.getOrigin().y) - width);
			}
			if (leftNeighbor != null) {
				leftDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						leftNeighbor.getOrigin().x, leftNeighbor.getOrigin().y) - width);
			}

			int distThreshold = 20;

			if (topNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle())))
					&& topDist < distThreshold && topDist >= -5
					&& ((Math.round(PApplet.cos(PApplet.radians(angle + 90)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle + 90)) * topNeighbor.getOrigin().x))
							- height / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle + 90)) * currentPiece.getOrigin().y)
									- Math.round(PApplet.sin(PApplet.radians(angle + 90)) * topNeighbor.getOrigin().y))
									- height / 2) >= 0) {

				isComplete &= true;

			} else if (topNeighbor != null) {
				isComplete &= false;
			}

			if (bottomNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(bottomNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(bottomNeighbor.getAngle())))
					&& botDist < distThreshold && botDist >= -5
					&& ((Math.round(PApplet.cos(PApplet.radians(angle + 90)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle + 90)) * bottomNeighbor.getOrigin().x))
							- height / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle + 90)) * currentPiece.getOrigin().y) - Math
									.round(PApplet.sin(PApplet.radians(angle + 90)) * bottomNeighbor.getOrigin().y))
									- height / 2) <= 0) {

				isComplete &= true;

			} else if (bottomNeighbor != null) {
				isComplete &= false;
			}

			if (rightNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle())))
					&& rightDist < distThreshold && rightDist >= -5
					&& ((Math.round(PApplet.cos(PApplet.radians(angle)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle)) * rightNeighbor.getOrigin().x))
							+ width / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle)) * currentPiece.getOrigin().y)
									- Math.round(PApplet.sin(PApplet.radians(angle)) * rightNeighbor.getOrigin().y))
									+ width / 2) <= 0) {

				isComplete &= true;

			} else if (rightNeighbor != null) {
				isComplete &= false;
			}

			if (leftNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(leftNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(leftNeighbor.getAngle())))
					&& leftDist < distThreshold && leftDist >= -5
					&& ((Math.round(PApplet.cos(PApplet.radians(angle)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle)) * leftNeighbor.getOrigin().x)) + width / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle)) * currentPiece.getOrigin().y)
									- Math.round(PApplet.sin(PApplet.radians(angle)) * leftNeighbor.getOrigin().y))
									+ width / 2) >= 0) {

				isComplete &= true;

			} else if (leftNeighbor != null) {
				isComplete &= false;
			}
		}
		view.puzzleComplete = isComplete;

	}

	// Regulates piece snapping
	private static void pieceSnapping(boolean active, View view, Generator generator, Piece currentPiece) {
		if (currentPiece != null && active) {
			// Find neighbor pieces
			ArrayList<Piece> pieceList = view.getPieceList();
			int columns = generator.getColumns();
			int width = generator.getPieceBaseWidth();
			int height = generator.getPieceBaseHeight();
			int currentIndex = pieceList.indexOf(currentPiece);
			float angle = currentPiece.getAngle();
			int top = currentIndex - (columns);
			int bottom = currentIndex + (columns);
			int right = currentIndex + 1;
			int left = currentIndex - 1;

			Piece topNeighbor = null;
			Piece bottomNeighbor = null;
			Piece rightNeighbor = null;
			Piece leftNeighbor = null;

			float topDist = 0;
			float botDist = 0;
			float rightDist = 0;
			float leftDist = 0;

			if (top >= 0 && top < pieceList.size()) {
				topNeighbor = pieceList.get(top);
			}
			if (bottom >= 0 && bottom < pieceList.size()) {
				bottomNeighbor = pieceList.get(bottom);
			}
			if (right >= 0 && right < pieceList.size() && (right) % columns != 0) {
				rightNeighbor = pieceList.get(right);
			}
			if (left >= 0 && left < pieceList.size() && (left + 1) % columns != 0) {
				leftNeighbor = pieceList.get(left);
			}

			if (topNeighbor != null) {
				topDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						topNeighbor.getOrigin().x, topNeighbor.getOrigin().y) - height);
			}
			if (bottomNeighbor != null) {
				botDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						bottomNeighbor.getOrigin().x, bottomNeighbor.getOrigin().y) - height);
			}
			if (rightNeighbor != null) {
				rightDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						rightNeighbor.getOrigin().x, rightNeighbor.getOrigin().y) - width);
			}
			if (leftNeighbor != null) {
				leftDist = (PApplet.dist(currentPiece.getOrigin().x, currentPiece.getOrigin().y,
						leftNeighbor.getOrigin().x, leftNeighbor.getOrigin().y) - width);
			}

			int distThreshold = 20;

			// New snapping method
			if (topNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle())))
					&& topDist < distThreshold && topDist >= 0
					&& ((Math.round(PApplet.cos(PApplet.radians(angle + 90)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle + 90)) * topNeighbor.getOrigin().x))
							- height / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle + 90)) * currentPiece.getOrigin().y)
									- Math.round(PApplet.sin(PApplet.radians(angle + 90)) * topNeighbor.getOrigin().y))
									- height / 2) >= 0) {

				Point2D.Float snap = new Point2D.Float(
						topNeighbor.getOrigin().x - (height) * PApplet.cos(PApplet.radians(angle - 90)),
						topNeighbor.getOrigin().y - (height) * PApplet.sin(PApplet.radians(angle - 90)));
				currentPiece.movePiece(snap);
				completionCheckV2(view, generator);

			} else if (bottomNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(bottomNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(bottomNeighbor.getAngle())))
					&& botDist < distThreshold && botDist >= 0
					&& ((Math.round(PApplet.cos(PApplet.radians(angle + 90)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle + 90)) * bottomNeighbor.getOrigin().x))
							- height / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle + 90)) * currentPiece.getOrigin().y) - Math
									.round(PApplet.sin(PApplet.radians(angle + 90)) * bottomNeighbor.getOrigin().y))
									- height / 2) <= 0) {

				Point2D.Float snap = new Point2D.Float(
						bottomNeighbor.getOrigin().x - (height) * PApplet.cos(PApplet.radians(angle + 90)),
						bottomNeighbor.getOrigin().y - (height) * PApplet.sin(PApplet.radians(angle + 90)));
				currentPiece.movePiece(snap);
				completionCheckV2(view, generator);

			} else if (rightNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle())))
					&& rightDist < distThreshold && rightDist >= 0
					&& ((Math.round(PApplet.cos(PApplet.radians(angle)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle)) * rightNeighbor.getOrigin().x))
							+ width / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle)) * currentPiece.getOrigin().y)
									- Math.round(PApplet.sin(PApplet.radians(angle)) * rightNeighbor.getOrigin().y))
									+ width / 2) <= 0) {

				Point2D.Float snap = new Point2D.Float(
						rightNeighbor.getOrigin().x - (width) * PApplet.cos(PApplet.radians(angle)),
						rightNeighbor.getOrigin().y - (width) * PApplet.sin(PApplet.radians(angle)));
				currentPiece.movePiece(snap);
				completionCheckV2(view, generator);

			} else if (leftNeighbor != null
					&& Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.sin(PApplet.radians(leftNeighbor.getAngle())))
					&& Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math
							.round(PApplet.cos(PApplet.radians(leftNeighbor.getAngle())))
					&& leftDist < distThreshold && leftDist >= 0
					&& ((Math.round(PApplet.cos(PApplet.radians(angle)) * currentPiece.getOrigin().x)
							- Math.round(PApplet.cos(PApplet.radians(angle)) * leftNeighbor.getOrigin().x)) + width / 2)
							+ ((Math.round(PApplet.sin(PApplet.radians(angle)) * currentPiece.getOrigin().y)
									- Math.round(PApplet.sin(PApplet.radians(angle)) * leftNeighbor.getOrigin().y))
									+ width / 2) >= 0) {

				Point2D.Float snap = new Point2D.Float(
						leftNeighbor.getOrigin().x - (width) * PApplet.cos(PApplet.radians(angle + 180)),
						leftNeighbor.getOrigin().y - (width) * PApplet.sin(PApplet.radians(angle + 180)));
				currentPiece.movePiece(snap);
				completionCheckV2(view, generator);

			}

			// OLD METHOD
			// Control snapping
//			if(topNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle()))) &&
//						Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle()))) &&
//						topNeighbor.contains(
//						currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 90)) * - multiplierY), 
//						currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 90)) * multiplierY)) {
//				Point2D.Float snap = new Point2D.Float(topNeighbor.getOrigin().x + (height)*PApplet.cos(PApplet.radians(angle + 90)), topNeighbor.getOrigin().y + (height)*PApplet.sin(PApplet.radians(angle + 90)));
//				currentPiece.movePiece(snap);
//				completionCheck(view, generator); // Check if puzzle is completed
//				
//			} else if(bottomNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(bottomNeighbor.getAngle()))) &&
//					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(bottomNeighbor.getAngle()))) &&
//					bottomNeighbor.contains(
//					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 270)) * - multiplierY), 
//					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 270)) * multiplierY)) {
//				Point2D.Float snap = new Point2D.Float(bottomNeighbor.getOrigin().x + (height)*PApplet.cos(PApplet.radians(angle + 270)), bottomNeighbor.getOrigin().y + (height)*PApplet.sin(PApplet.radians(angle + 270)));
//				currentPiece.movePiece(snap);
//				completionCheck(view, generator); // Check if puzzle is completed
//				
//			} else if(rightNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle()))) &&
//					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle()))) &&
//					rightNeighbor.contains(
//					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 180)) * - multiplierX), 
//					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 180)) * multiplierX)) {
//				Point2D.Float snap = new Point2D.Float(rightNeighbor.getOrigin().x - (width)*PApplet.cos(PApplet.radians(angle)), rightNeighbor.getOrigin().y - (width)*PApplet.sin(PApplet.radians(angle)));
//				currentPiece.movePiece(snap);
//				completionCheck(view, generator); // Check if puzzle is completed
//				
//			} else if(leftNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(leftNeighbor.getAngle()))) &&
//					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(leftNeighbor.getAngle()))) &&
//					leftNeighbor.contains(
//					currentPiece.getOrigin().x - (PApplet.cos(PApplet.radians(angle)) * multiplierX), 
//					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle)) * multiplierX)) {
//				Point2D.Float snap = new Point2D.Float(leftNeighbor.getOrigin().x + (width)*PApplet.cos(PApplet.radians(angle)), leftNeighbor.getOrigin().y + (width)*PApplet.sin(PApplet.radians(angle)));
//				currentPiece.movePiece(snap);
//				completionCheck(view, generator); // Check if puzzle is completed
//			}
		}
	}

	// Randomizes puzzle position within a certain area
	private static void randomizePuzzle(View view, ArrayList<Piece> pieceList, float min, float max,
			boolean randomRotation) {
		for (Piece piece : pieceList) {
			Point2D.Float randomPos = new Point2D.Float((float) (Math.random() * (max - min) + min),
					(float) (Math.random() * (max - min) + min));
			piece.movePiece(randomPos);
			if (randomRotation) {
				float randomAngle = (float) (Math.floor((Math.random() * (8))) * 45);
				piece.rotatePiece(randomAngle);
			}
		}
	}

	// Checks whether two pieces are identical (BRUTE FORCE)
	public static boolean identicalIdentifier(Piece p1, Piece p2) {
		float p1StartAngle = p1.getAngle();
		Point2D.Float p2StartOrigin = p2.getOrigin();

		// Move piece 2 to be on top of piece 1
		p2.movePiece(p1.getOrigin());

		boolean identical = false;
		for (int i = 0; i <= 8; i++) {
			boolean identical2 = true;
			p1.rotatePiece(i * 45);
			for (int j = 0; j < p1.getShape().getVertexCount(); j++) {
				if (p1.getCurrentVertices()[j].x == p2.getCurrentVertices()[j].x
						&& p1.getCurrentVertices()[j].y == p2.getCurrentVertices()[j].y) {
					identical2 &= true;
				} else {
					identical2 &= false;
				}
			}

			// If we have a case where all vertices match, then we can exit
			if (identical2) {
				p2.movePiece(p2StartOrigin);
				p1.rotatePiece(p1StartAngle);
				return true;
			}
			identical |= identical2;
		}

		// Move piece 2 back to it's original position
		p2.movePiece(p2StartOrigin);

		return identical;
	}
}
