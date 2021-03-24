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
	private float distortionLevel;
	
	public Generator(double boardSize, int pieceAmount, int distortionPoints, float distortionLevel) {
		this.boardSize = boardSize;
		this.pieceAmount = pieceAmount;
		this.distortionPoints = distortionPoints;
		this.distortionLevel = distortionLevel;
		
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
				
				//tilføj center
				
				//loop til hjørner
				
//				pieceStorage[i][j] = new Piece(new Point2D.Float((float) ((pieceSizeX/2) + leapX*j), (float) ((pieceSizeY/2) + leapY*i)), (float) pieceSizeX/2, (float) pieceSizeY/2);
//				pieceStorage[i][j].setCorners();
				
				//Corner/side pieces have less points because they cant distort that side.
				int points = 4 + (2* distortionPoints);
				boolean distortRight = false;
				boolean distortBottom = false;
				if(i != divideY-1) {
					
					distortBottom = true;
				}
				if(j != divideX-1) {
					
					distortRight = true;
				}
				System.out.println("points:" + points);
				pieceStorage[j+(i*divideX)] = generatePiece(points, (pieceSizeX/2) + leapX*j, (pieceSizeY/2) + leapY*i,leapX,leapY, distortionPoints, distortionLevel, distortRight, distortBottom);
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
	
	public Point2D.Float[] generatePiece(int points, float centerX, float centerY, float sizeX, float sizeY, int distortionPoints, float distortionLevel, boolean distortRight, boolean distortBottom){
		Point2D.Float[] formarray = new Point2D.Float[points+1];
		//add center of the piece to the point list
		
		formarray[0] = new Point2D.Float(centerX, centerY);
//		System.out.print(centerX + "  " + centerY + "   ");
		
		formarray[1] = new Point2D.Float(-sizeX/2,-sizeY/2);
//		System.out.println(-sizeX/2 +"     "  + -sizeY/2);
		formarray[2] = new Point2D.Float(-sizeX/2,sizeY/2);
		formarray[3] = new Point2D.Float(sizeX/2,sizeY/2);
		formarray[4] = new Point2D.Float(sizeX/2,-sizeY/2);
		
		
		if(distortRight) {
			Point2D.Float[] formarrayaddRight = distortRight(sizeX, sizeY, distortionPoints, distortionLevel);
			for(int k = 5; k < formarray.length; k++) {
				formarray[k] = formarrayaddRight[k-k];
			}
		}
		if(distortBottom) {
			Point2D.Float[] formarrayaddBottom = distortBottom(sizeX, sizeY, distortionPoints, distortionLevel);
			for(int k = 5+distortionPoints; k < formarray.length; k++) {
				formarray[k] = formarrayaddBottom[k-k];
			}
		}
		
		
		return formarray;
	}
	
	public Point2D.Float[] distortRight(float sizeX, float sizeY, int distortionPoints, float distortionLevel){
		
		Point2D.Float[] formarrayRight = new Point2D.Float[distortionPoints];
		
		
		float[] tempArrayY = new float[distortionPoints];
		float[] tempArrayX = new float[distortionPoints];
		
		for(int i = 0; i < distortionPoints; i++) {
			tempArrayY[i] = chooseRandom(sizeY);
		}
		Arrays.sort(tempArrayY);
		System.out.println(tempArrayY.toString());
		for(int i = 0; i < distortionPoints; i++) {
			float randomRange = (float) (Math.random() * distortionLevel - distortionLevel);
			float randDistort = ((sizeX/2) + randomRange);
			tempArrayX[i] = randDistort;
		}
		for(int i = 0; i < formarrayRight.length; i++) {
			formarrayRight[i] = new Point2D.Float(tempArrayX[i], tempArrayY[i]);
		}
	

		
		return formarrayRight;
	}
	
public Point2D.Float[] distortBottom(float sizeX, float sizeY, int distortionPoints, float distortionLevel){
		
		Point2D.Float[] formarrayaddBottom = new Point2D.Float[distortionPoints];
		
		
		float[] tempArrayY = new float[distortionPoints];
		float[] tempArrayX = new float[distortionPoints];
		
		for(int i = 0; i < distortionPoints; i++) {
			tempArrayX[i] = chooseRandom(sizeX);
		}
		Arrays.sort(tempArrayX);
		System.out.println(tempArrayX.toString());
		for(int i = 0; i < distortionPoints; i++) {
			float randomRange = (float) (Math.random() * distortionLevel - distortionLevel);
			float randDistort =  ((sizeY/2) + randomRange);
			tempArrayY[i] = randDistort;
		}
		for(int i = 0; i < formarrayaddBottom.length; i++) {
			formarrayaddBottom[i] = new Point2D.Float(tempArrayX[i], tempArrayY[i]);
		}
			
		

		
		return formarrayaddBottom;
	}

	private float chooseRandom(float sizeSide) {
		return (float) (sizeSide * Math.random());
		
	}
	
	public int getColumns() {
		return divideX;
	}
	

}
