package controller;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;

import model.ImageInitializer;
import model.Piece;
import processing.core.PApplet;
import processing.core.PImage;
import view.View;

public class Main {
	// The argument passed to main must match the class name
	private static int pieceAmount = 9;
	private static int boardSize = 800;
	private static int distortionPoints = 4;
	
	
	static Generator generator;
	
	public static void main(String[] args) {
		
		View view = new View();
		
		
		
		PApplet.runSketch(new String[]{"--location=200,200", ""}, view);
		
		//boardSize = (int) (view.width/1.25);
//		System.out.println("w " + view.width/2);
		
		
		
		//float[] v2 = {-100, -100, 100, -100, 150, 0, 100, 100, -100, 100, -100, -100};
		//Piece p2 = new Piece(view, 100, 100, v2);
		//view.addPieceToList(p2);
		
		/*
		
		Point2D.Float[] v2 = (Point2D.Float[]) O2[53];
		Piece p2 = new Piece(view, new Point2D.Float(600.0f, 600.0f), v2);
		view.addPieceToList(p2);
		
		Point2D.Float[] v3 = (Point2D.Float[]) O2[54];
		Piece p3 = new Piece(view, new Point2D.Float(200.0f, 200.0f), v3);
		view.addPieceToList(p3);
		*/
		
		/*
		PieceReader pR = new PieceReader();
		Object[] O2 = pR.pieceReader();
		int j = -1;
		int a = 0;
		for(int i = 0; i < 121; i++) {
			Point2D.Float[] v = (Point2D.Float[]) O2[i];
			a++;
			if(i % 11 == 0) {j++; a=0;}
			
			Piece p = new Piece(view, new Point2D.Float(0.0f+100*a, 0.0f+100*j), v);
			view.addPieceToList(p);
		}
		*/
		
		
		// Create the generator
//		Generator g = new Generator(boardSize, pieceAmount, distortionPoints);
//		
//		// Get the images to be mapped onto the pieces
//		String[] paths = {"assets\\\\images\\\\puppie2.jpg", "assets\\\\images\\\\puppie1.jpg", "assets\\\\images\\\\reyna.jpg"};
//		ImageInitializer imi = new ImageInitializer(view, boardSize, pieceAmount, g.getColumns(), g.getRows());
//		ArrayList<PImage> sprites = imi.imageSplitter(imi.imageLoader(paths).get(2), boardSize);
//		//view.setImageList(sprites);
//		
//		
//		Object[] O3 = g.generate();
//		PieceCompare pC = new PieceCompare();
//		pC.pieceComparator(O3);
//		
//		
//		for(int i = 0; i < pieceAmount; i++) {
//			Point2D.Float[] v = (Point2D.Float[]) O3[i];
//			Point2D.Float center = v[0];
//			Point2D.Float[] v1 = new Point2D.Float[v.length-1];
//			for(int j = 1; j<v.length;j++) {
//				v1[j-1]=v[j];
//			}
//			
//			Piece p = new Piece(view, center, v1, sprites.get(i));
//			view.addPieceToList(p);
//		}
		
		// COMMENT THIS OUT IF YOU WANNA TEST WITHOUT GENERATED
		//generatePuzzle(view);
	
		
		ArrayList<Piece> pieceList = view.getPieceList();
		//randomizePuzzle(view, pieceList, 100, view.width-100, true);
		
		//completionCheck(view, g);
		
		/*
		Point2D.Float[] v1 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-50, 0), new Point2D.Float(-100, -100)};
		Piece p1 = new Piece(view, new Point2D.Float(400.0f, 400.0f), v1);
		//p1.rotatePiece(90);
		view.addPieceToList(p1);
		/*
		Point2D.Float[] v2 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-50, 0), new Point2D.Float(-100, -100)};
		Piece p2 = new Piece(view, new Point2D.Float(500.0f, 500.0f), v1);
		view.addPieceToList(p2);
		
		/*
		Point2D.Float[] v3 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-150, 0), new Point2D.Float(-100, -100)};
		Piece p3 = new Piece(view, new Point2D.Float(700.0f, 700.0f), v3);
		view.addPieceToList(p3);
		
		Point2D.Float[] v4 = {new Point2D.Float(-100, -100), new Point2D.Float(100, -100), new Point2D.Float(100, 100), new Point2D.Float(-100, 100), new Point2D.Float(-150, 0), new Point2D.Float(-100, -100)};
		Piece p4 = new Piece(view, new Point2D.Float(700.0f, 700.0f), v4);
		view.addPieceToList(p4);
		*/
		
		
		//System.out.println(identicalIdentifier(p1,p3));
		
		
		
		
		/*
		PieceReader pR = new PieceReader();
		Object[] O2 = pR.pieceReader();
		pC.pieceComparator(O2);
		
		
		int j = -1;
		int a = 0;
		for(int i = 0; i < 8; i++) {
			Point2D.Float[] v = (Point2D.Float[]) O2[i];
			a++;
			if(i % 11 == 0) {j++; a=0;}
			
			Piece p = new Piece(view, new Point2D.Float(0.0f+100*a, 0.0f+100*j), v);
			view.addPieceToList(p);
		}
		*/
		
		//new Thread(new puzzleButler(view)).start();
		
		// Continue running below code
		//Piece currentPiece = view.getCurrentPiece();
		boolean run = true;
		while(run) {
			Piece currentPiece = getCurrentPiece(view);
			
			if(view.mouseReleased) {
				//System.out.println("realease");
				pieceSnapping(true, view, generator, currentPiece);
			}
			
			if(view.newPuzzle) {
				view.resetPieceList();
				generatePuzzle(view);
				view.newPuzzle = false;
				randomizePuzzle(view, pieceList, 100, view.initWidth-100, true);
			}
		}
	}
	
	public static void generatePuzzle(View view) {
		int pieceAmount, distortionPoints;
		if(view.menubar != null) {
			pieceAmount = view.menubar.sliderPieceAmount.getValue();
			distortionPoints = view.menubar.sliderDistortionPoints.getValue();
		} else {
			pieceAmount = 2;
			distortionPoints = 2;
		}
		
		generator = new Generator(boardSize, pieceAmount, distortionPoints);
		
		// Get the images to be mapped onto the pieces
		String[] paths = {"assets\\\\images\\\\puppie2.jpg", "assets\\\\images\\\\puppie1.jpg", "assets\\\\images\\\\reyna.jpg", "assets\\\\\\\\images\\\\\\\\carl.jpg"};
		ImageInitializer imi = new ImageInitializer(view, boardSize, pieceAmount, generator.getColumns(), generator.getRows());
		ArrayList<PImage> sprites = imi.imageSplitter(imi.imageLoader(paths).get((int) (Math.round(Math.random() * (paths.length-1)))), boardSize);
		//view.setImageList(sprites);
		System.out.println(Math.round(Math.random() * paths.length) - 1);
		
		PieceReader pR = new PieceReader();
//		Object[] O3 = pR.pieceReader();
		Object[] O3 = generator.generate();
		PieceCompare pC = new PieceCompare();
		pC.pieceComparator(O3);
		PuzzleSolver pS = new PuzzleSolver();
//		pS.puzzleSolver(O3);
		
		for(int i = 0; i < O3.length; i++) {
			Point2D.Float[] v = (Point2D.Float[]) O3[i];
			Point2D.Float center = new Point2D.Float(0.0f,0.0f);
			
			
			Piece p = new Piece(view, center, v, sprites.get(i));
			view.addPieceToList(p);
		}
	}
	
	// Gets the current piece
	private static Piece getCurrentPiece(View view) {
		ArrayList<Piece> pieceList = view.getPieceList();
		for(Piece piece : pieceList) {
			if(piece.isCurrentPiece) {
				return piece;
			}
		}
		return null;
	}
	
	// Checks if puzzle is completed DOES NOT WORK AS INTENDED
	private static void completionCheck(View view, Generator generator) {
		/*
		 * For each piece, find all neighbor pieces, and check if they are colliding, if all pieces are colliding with their corrosponding neighbor pieces, the puzzle must be solved.
		 * *ERROR?* If you disable snapping and stack every piece, then the puzzle is per definition solved?
		 * *SOLVE?* check if an edge point, outside the puzzle, is inside the corrosponding neighbor puzzle?
		 */
		
		boolean isComplete = true;
		
		// Find neighbor pieces
		ArrayList<Piece> pieceList = view.getPieceList();
		
		for(Piece currentPiece : pieceList) {
			double boardsize = generator.getBoardSize();
			int columns   = generator.getColumns();
			int rows	  = generator.getRows();
			double x_spacing = boardsize/columns;
			double y_spacing = boardsize/rows;
			int width 	= generator.getPieceBaseWidth();
			int height 	= generator.getPieceBaseHeight();
			
			int currentIndex = pieceList.indexOf(currentPiece);
			float angle =  currentPiece.getAngle();
			int top 	=  currentIndex - (columns);
			int bottom 	=  currentIndex + (columns);
			int right 	=  currentIndex       	+ 1;
			int left 	=  currentIndex	     	- 1;
			//int offset 	=  100;
			
			
			Piece topNeighbor 		= null;
			Piece bottomNeighbor 	= null;
			Piece rightNeighbor 	= null;
			Piece leftNeighbor		= null;
			
			if(top >= 0 && top < pieceList.size()) 								{ topNeighbor    = pieceList.get(top);} 
			if(bottom >= 0 && bottom < pieceList.size()) 						{ bottomNeighbor = pieceList.get(bottom);}
			if(right >= 0 && right < pieceList.size() && (right)%columns != 0) 	{ rightNeighbor  = pieceList.get(right);}
			if(left >= 0 && left < pieceList.size() && (left+1)%columns != 0) 	{ leftNeighbor	 = pieceList.get(left);}
			

			
			if(topNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle()))) &&
					topNeighbor.contains(
					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 90)) * - width), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 90)) * height)) {
				isComplete &= true;
			} else if(topNeighbor != null) {
				isComplete &= false;
			}
			
			if(bottomNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(bottomNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(bottomNeighbor.getAngle()))) &&
					bottomNeighbor.contains(
					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 270)) * - width), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 270)) * height)) {
				isComplete &= true;
			} else if(bottomNeighbor != null) {
				isComplete &= false;
			}
			
			
			if(rightNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle()))) &&
					rightNeighbor.contains(
					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 180)) * - width), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 180)) * height)) {
				isComplete &= true;
			} else if(rightNeighbor != null) {
				isComplete &= false;
			}
			
			if(leftNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(leftNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(leftNeighbor.getAngle()))) &&
					leftNeighbor.contains(
					currentPiece.getOrigin().x - (PApplet.cos(PApplet.radians(angle)) * width), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle)) * height)) {
				isComplete &= true;
			} else if(leftNeighbor != null) {
				isComplete &= false;
			}
			//System.out.println("comp: " + isComplete);
//			// Collision check
//			if(topNeighbor != null) {
////				isComplete &= Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle()))) &&
////						Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle()))) &&
////						topNeighbor.contains(
////						currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 90)) * - width/2), 
////						currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 90)) * height/2);
//			}
//			if(rightNeighbor != null) {
//				isComplete &= Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle()))) &&
//						Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle()))) &&
//						rightNeighbor.contains(
//						currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 180)) * - (width/2 + 10)), 
//						currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 180)) * (height/2 + 10));
//			}
			
		}
		
		view.puzzleComplete = isComplete;
		System.out.println("Is puzzle completed?: " + isComplete);
	}
	
	
	/*
	 * DOES NOT WORK AS INTENDED;
	 * PICKUP PIECE NOT WORKING AS INTEDED, MIGHT BE THE NEW CONTAINS METHOD!
	 */
	// Regulates piece snapping
	private static void pieceSnapping(boolean active, View view, Generator generator, Piece currentPiece) {
		if(currentPiece != null && active) {
			// Find neighbor pieces
			ArrayList<Piece> pieceList = view.getPieceList();
			int columns = generator.getColumns();
			int width 	= generator.getPieceBaseWidth();
			int height 	= generator.getPieceBaseHeight();
			int currentIndex = pieceList.indexOf(currentPiece);
			float angle =  currentPiece.getAngle();
			int top 	=  currentIndex - (columns);
			int bottom 	=  currentIndex + (columns);
			int right 	=  currentIndex       	+ 1;
			int left 	=  currentIndex	     	- 1;
			
			int multiplierX 	= width*5/6;	// To get a better snapping feel
			int multiplierY 	= height*5/6;	// To get a better snapping feel
			
			Piece topNeighbor 		= null;
			Piece bottomNeighbor 	= null;
			Piece rightNeighbor 	= null;
			Piece leftNeighbor		= null;
			
			if(top >= 0 && top < pieceList.size()) 								{ topNeighbor    = pieceList.get(top);} 
			if(bottom >= 0 && bottom < pieceList.size()) 						{ bottomNeighbor = pieceList.get(bottom);}
			if(right >= 0 && right < pieceList.size() && (right)%columns != 0) 	{ rightNeighbor  = pieceList.get(right);}
			if(left >= 0 && left < pieceList.size() && (left+1)%columns != 0) 	{ leftNeighbor	 = pieceList.get(left);}
			
			// Control snapping
			if(topNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(topNeighbor.getAngle()))) &&
						Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(topNeighbor.getAngle()))) &&
						topNeighbor.contains(
						currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 90)) * - multiplierX), 
						currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 90)) * multiplierY)) {
				Point2D.Float snap = new Point2D.Float(topNeighbor.getOrigin().x + (height)*PApplet.cos(PApplet.radians(angle + 90)), topNeighbor.getOrigin().y + (height)*PApplet.sin(PApplet.radians(angle + 90)));
				currentPiece.movePiece(snap);
				completionCheck(view, generator); // Check if puzzle is completed
				
			} else if(bottomNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(bottomNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(bottomNeighbor.getAngle()))) &&
					bottomNeighbor.contains(
					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 270)) * - multiplierX), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 270)) * multiplierY)) {
				//System.out.println("bot collide");
				Point2D.Float snap = new Point2D.Float(bottomNeighbor.getOrigin().x + (height)*PApplet.cos(PApplet.radians(angle + 270)), bottomNeighbor.getOrigin().y + (height)*PApplet.sin(PApplet.radians(angle + 270)));
				currentPiece.movePiece(snap);
				completionCheck(view, generator); // Check if puzzle is completed
				
			} else if(rightNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(rightNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(rightNeighbor.getAngle()))) &&
					rightNeighbor.contains(
					currentPiece.getOrigin().x + (PApplet.cos(PApplet.radians(angle + 180)) * - multiplierX), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle + 180)) * multiplierY)) {
				//System.out.println("right collide");
				Point2D.Float snap = new Point2D.Float(rightNeighbor.getOrigin().x - (width)*PApplet.cos(PApplet.radians(angle)), rightNeighbor.getOrigin().y - (width)*PApplet.sin(PApplet.radians(angle)));
				currentPiece.movePiece(snap);
				completionCheck(view, generator); // Check if puzzle is completed
				
			} else if(leftNeighbor != null && Math.round(PApplet.sin(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.sin(PApplet.radians(leftNeighbor.getAngle()))) &&
					Math.round(PApplet.cos(PApplet.radians(currentPiece.getAngle()))) == Math.round(PApplet.cos(PApplet.radians(leftNeighbor.getAngle()))) &&
					leftNeighbor.contains(
					currentPiece.getOrigin().x - (PApplet.cos(PApplet.radians(angle)) * multiplierX), 
					currentPiece.getOrigin().y - PApplet.sin(PApplet.radians(angle)) * multiplierY)) {
				Point2D.Float snap = new Point2D.Float(leftNeighbor.getOrigin().x + (width)*PApplet.cos(PApplet.radians(angle)), leftNeighbor.getOrigin().y + (width)*PApplet.sin(PApplet.radians(angle)));
				currentPiece.movePiece(snap);
				completionCheck(view, generator); // Check if puzzle is completed
			}
		}
	}
	
	// Randomizes puzzle position within a certain area
	private static void randomizePuzzle(View view, ArrayList<Piece> pieceList, float min, float max, boolean randomRotation) {
		for(Piece piece : pieceList) {
			Point2D.Float randomPos = new Point2D.Float((float)(Math.random() * (max - min) + min), (float)(Math.random() * (max - min) + min));
			piece.movePiece(randomPos);
			if(randomRotation) {
				float randomAngle = (float) (Math.floor((Math.random() * (8)))*45);
				piece.rotatePiece(randomAngle);
			}
		}
	}
	
	// Checks whether two pieces are identical
	public static boolean identicalIdentifier(Piece p1, Piece p2) {
		float p1StartAngle = p1.getAngle();
		Point2D.Float p2StartOrigin = p2.getOrigin();
		
		// Move piece 2 to be on top of piece 1
		p2.movePiece(p1.getOrigin());
		
		boolean identical = false;
		for(int i = 0; i <= 8; i ++) {
			boolean identical2 = true;
			p1.rotatePiece(i*45);
			for(int j = 0; j < p1.getShape().getVertexCount(); j++) {
				if(p1.getCurrentVertices()[j].x == p2.getCurrentVertices()[j].x && p1.getCurrentVertices()[j].y == p2.getCurrentVertices()[j].y) {
					identical2 &= true;
				} else {
					identical2 &= false;
				}
			}
			
			// If we have a case where all vertices match, then we can exit
			if(identical2) { p2.movePiece(p2StartOrigin); p1.rotatePiece(p1StartAngle); return true; }
			identical |= identical2;
		}
		
		// Move piece 2 back to it's original position
		p2.movePiece(p2StartOrigin);
		
		return identical;
	}
}


