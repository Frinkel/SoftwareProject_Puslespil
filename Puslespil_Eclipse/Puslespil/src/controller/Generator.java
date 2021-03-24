package controller;

//CARLO
import java.awt.geom.Point2D;
import java.util.Arrays;

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
	
	public Generator(double boardSize, int pieceAmount) {
		this.boardSize = boardSize;
		this.pieceAmount = pieceAmount;
		
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

				pieceStorage[j+(i*divideX)] = generatePiece(4, (pieceSizeX/2) + leapX*j, (pieceSizeY/2) + leapY*i,leapX,leapY);
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
	
	public Point2D.Float[] generatePiece(int points, float centerX, float centerY, float sizeX, float sizeY){
		Point2D.Float[] formarray = new Point2D.Float[points+1];
		//add center of the piece to the point list
		
		formarray[0] = new Point2D.Float(centerX, centerY);
		System.out.print(centerX + "  " + centerY + "   ");
		
		formarray[1] = new Point2D.Float(-sizeX/2,-sizeY/2);
		System.out.println(-sizeX/2 +"     "  + -sizeY/2);
		formarray[2] = new Point2D.Float(-sizeX/2,sizeY/2);
		formarray[3] = new Point2D.Float(sizeX/2,sizeY/2);
		formarray[4] = new Point2D.Float(sizeX/2,-sizeY/2);
		
//		for(int k = 1; k < points+1; k++) {
//			formarray[k] = distort();
//		}
		return formarray;
	}
	
	public Point2D.Float distort(){
		
		
		return null;
	}
	
	public int getColumns() {
		return divideX;
	}
	

}
