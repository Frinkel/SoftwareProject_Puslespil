package Generator;


import java.awt.geom.Point2D;
import java.util.Arrays;

public class Generator {
	private double boardSize = 600;
	private int pieceAmount = 6;
	private Piece[][] pieceStorage;
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
		
		this.pieceStorage = new Piece[divideY][divideX];
	}
	
	public Generator() { 
		
		// Find greatest factor
		for (int i = 1; i * i <= pieceAmount; i++) {
            if (pieceAmount % i == 0) { 
            	divideY = i;
            	divideX = pieceAmount/i;
            }
		}
		
		this.pieceStorage = new Piece[divideY][divideX];
		
	}
	
	public void placeCenters() {

		// Find distance between centers of pieces
		float leapX = (float) (boardSize/divideX);
		float leapY = (float) (boardSize/divideY);
		// Define size of each piece
		float pieceSizeX = leapX;
		float pieceSizeY = leapY;
		
		
		
		for(int i = 0; i < divideY;i++) {
			for(int j = 0; j < divideX;j++) {
				pieceStorage[i][j] = new Piece(new Point2D.Float((float) ((pieceSizeX/2) + leapX*j), (float) ((pieceSizeY/2) + leapY*i)), (float) pieceSizeX/2, (float) pieceSizeY/2);
				pieceStorage[i][j].setCorners();
			}
		}
		
		
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

}
