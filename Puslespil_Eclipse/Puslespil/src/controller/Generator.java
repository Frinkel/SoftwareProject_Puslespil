package controller;

//CARLO
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.Piece;

public class Generator {
	private double boardSize = 600;
	private int pieceAmount = 6;
	//private Piece[][] pieceStorage;
	private Object[] pieceStorage;
	private int leapX;
	private int leapY;
	private int divideX;
	private int divideY;
	private int distortionPoints;

	
	public Generator(double boardSize, int pieceAmount, int distortionPoints) {
		this.boardSize = boardSize;
		this.pieceAmount = pieceAmount;
		this.distortionPoints = distortionPoints;
		
		// Find greatest factor 
		for (int i = 1; i * i <= pieceAmount; i++) {
            if (pieceAmount % i == 0) { 
            	divideY = i;
            	divideX = pieceAmount/i;
            }
		}
		this.pieceStorage = new Object[pieceAmount];
		
	}
	
	public Generator() { 
		
		// Find greatest factor
		for (int i = 1; i * i <= pieceAmount; i++) {
            if (pieceAmount % i == 0) { 
            	divideY = i;
            	divideX = pieceAmount/i;
            }
		}
		this.pieceStorage = new Object[pieceAmount];

				
	}
	
	public Object[] generate() {

		// Find distance between centers of pieces
		float leapX = (float) (boardSize/divideX);
		float leapY = (float) (boardSize/divideY);
		// Define size of each piece
		float pieceSizeX = leapX;
		float pieceSizeY = leapY;

		for(int i = 0; i < divideY;i++) {
			for(int j = 0; j < divideX;j++) {
				pieceStorage[j+(i*divideX)] = generatePiece((pieceSizeX/2) + leapX*j, (pieceSizeY/2) + leapY*i,leapX,leapY, distortionPoints, i, j , pieceStorage);
			}
		}
		return pieceStorage;
		
	}
	
	public String getPieceStorage() {
		return Arrays.deepToString(pieceStorage);
	}
	
	public double getBoardSize() {
		return boardSize;
	}
	
	public int getPieceAmount() {
		return pieceAmount;
	}
	// Generate a single piece
	public Point2D.Float[] generatePiece(float centerX, float centerY, float sizeX, float sizeY, int distortionPoints, int relativePositionY, int relativePositionX, Object[] pieceStorage){
		int points = getNumberOfPoints(relativePositionX, relativePositionY, distortionPoints);
		Point2D.Float[] formarray = new Point2D.Float[points+1];
		//add center of the piece to the point list
		int index = 0;
		formarray[index] = new Point2D.Float(centerX, centerY);
		index ++;
		//add corner coordinate
		formarray[index] = new Point2D.Float(-sizeX/2,-sizeY/2);
		index ++;
		//distort top , if distortion needed eg. we are not oon the border of the puzzle
		if(relativePositionY != 0) {
			Point2D.Float[] previousPiece = (Point2D.Float[]) pieceStorage[relativePositionX+((relativePositionY-1)*divideX)];
			int n = findPointNumberY(relativePositionX,(relativePositionY-1),distortionPoints);
			//findNumber // skal karl lave
			for(int i = distortionPoints-1; i >= 0; i--) {
				Point2D.Float point = previousPiece[n+i];
				float x = (float) point.getX();
				float y = (float) point.getY();
				formarray[index] = new Point2D.Float( x, (y - ((float) (boardSize/divideY))));
				index++;
			}
		}
		//add corner coordinate
		formarray[index] = new Point2D.Float(sizeX/2,-sizeY/2);
		index ++;
		//distort right side , if distortion needed eg. we are not oon the border of the puzzle
		if(relativePositionX != (divideX-1)) {
			Point2D.Float[] distortedList = distortRight(sizeX/2, -sizeY/2);
			for(int i = 0; i < distortedList.length; i++) {
				formarray[index] = distortedList[i];
				index++;
			}
		}
		//add corner coordinate
		formarray[index] = new Point2D.Float(sizeX/2,sizeY/2);
		index ++;
		
		//distort bottom , if distortion needed eg. we are not oon the border of the puzzle
		if(relativePositionY != (divideY-1)) {
			Point2D.Float[] distortedList = distortDown(sizeX/2, sizeY/2);
			for(int i = 0; i < distortedList.length; i++) {
				formarray[index] = distortedList[i];
				index++;
			}
		}
		//add corner coordinate
		formarray[index] = new Point2D.Float(-sizeX/2,sizeY/2);
		index ++;
		
		//distort left side , if distortion needed eg. we are not oon the border of the puzzle
		if(relativePositionX != 0) {
			Point2D.Float[] previousPiece = (Point2D.Float[]) pieceStorage[(relativePositionX-1)+(relativePositionY*divideX)];
			int n = findPointNumberX((relativePositionX-1),relativePositionY,distortionPoints);
			for(int i = distortionPoints-1; i >= 0; i--) {
				Point2D.Float point = previousPiece[n+i];
				float x = (float) point.getX();
				float y = (float) point.getY();

				formarray[index] = new Point2D.Float((x - ((float) (boardSize/divideX))), y);
				index++;
			}
		}
		
		return formarray;
	}
	
	

	private Point2D.Float[] distortRight(float posX, float posY) {
		Point2D.Float[] distortedList = new Point2D.Float[distortionPoints];
		float randomY = posY;
		float randomX = 0;
		for(int i = 0; i < distortionPoints; i++) {
			randomY = chooseRandom(-posY, randomY);
			if(i == (distortionPoints-1)) {
				float d = -posY-randomY;
				System.out.println("d " + d);
				float smallest = getSmallest(d, ((float) (boardSize/divideX)/2));
				randomX = chooseRandom(smallest, -smallest);
				randomX += ((float) (boardSize/divideX)/2);
			}else {
				
				float d = randomY - posY;			
				float smallest = getSmallest(d, ((float) (boardSize/divideX)/2));
				randomX = chooseRandom(smallest, -smallest);
				randomX += ((float) (boardSize/divideX)/2);
				
			}
			distortedList[i] = new Point2D.Float(randomX, randomY);
			
		}
		
		return distortedList;
	}
	
	private Point2D.Float[] distortDown(float posX, float posY) {
		Point2D.Float[] distortedList = new Point2D.Float[distortionPoints];
		float randomX = posX;
		float randomY = 0;
	
		for(int i = 0; i < distortionPoints; i++) {
			randomX = chooseRandom(randomX, -posX);
			if(i == (distortionPoints-1)) {
				float d = randomX - posX;
				float smallest = getSmallest(d, (float) ((boardSize/divideY)/2));
				randomY = chooseRandom(smallest, -smallest );
				randomY += ((float) (boardSize/divideY)/2);				
			}else {
				float d = posX - randomX;
				float smallest = getSmallest(d, (float) ((boardSize/divideY)/2));
				randomY = chooseRandom(smallest, -smallest );
				randomY += ((float) (boardSize/divideY)/2);

			}
			distortedList[i] = new Point2D.Float(randomX, randomY);
			
		}
		
		return distortedList;
	}

	private float getSmallest(float d, float f) {
		if(Math.abs(d) > Math.abs(f)) {
			return f;
		}else {
			return d;
		}
	}

	private int getNumberOfPoints(int relativePositionX, int relativePositionY, int distortionPoints) {
		int numberOfPoints = 4;
		if(relativePositionX != 0) {
			numberOfPoints += distortionPoints;
		}
		if(relativePositionY != 0) {
			numberOfPoints += distortionPoints;
			
		}
		if(relativePositionX != (divideX-1)) {
			
			numberOfPoints += distortionPoints;
		}
		if(relativePositionY != (divideY-1)) {
			numberOfPoints += distortionPoints;
			
		}
		System.out.println(numberOfPoints);
		return numberOfPoints;
	}
	
	private int findPointNumberY(int x,int y, int distortionPoints) {
		int n = 4;
		if(y != 0) {n += distortionPoints;}
		if(x != (divideX-1)) { n += distortionPoints; }
		return n;
	}
	private int findPointNumberX(int x,int y, int distortionPoints) {
		int n = 3;
		if(y != 0) {n += distortionPoints;}
		return n;
	}
	
	
	private float chooseRandom(float upper, float lower) {
		return (float) (Math.random() * (upper - lower)) + lower;
	}
	
	public int getColumns() {
		return divideX;
	}
	

}
