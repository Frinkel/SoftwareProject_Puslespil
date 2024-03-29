package model;

//CARLO
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class Generator {
	// Alexander and Carl
	private double boardSize = 600;
	private int pieceAmount = 6;
	// private Piece[][] pieceStorage;
	private Object[] pieceStorage;
	private int divideX;
	private int divideY;
	private int distortionPoints;
	private ArrayList<Double> lengthCheck;

	public Generator(double boardSize, int pieceAmount, int distortionPoints) {
		this.boardSize = boardSize;
		this.pieceAmount = pieceAmount;
		this.distortionPoints = distortionPoints;
		this.lengthCheck = new ArrayList<Double>();

		// Find greatest factor
		for (int i = 1; i * i <= pieceAmount; i++) {
			if (pieceAmount % i == 0) {
				divideY = i;
				divideX = pieceAmount / i;
			}
		}
		this.pieceStorage = new Object[pieceAmount];

	}

	public Object[] generate() {

		// Find distance between centers of pieces
		float pieceLength = (float) (boardSize / divideX);
		float pieceHeight = (float) (boardSize / divideY);
		// Define size of each piece
		float pieceSizeX = pieceLength;
		float pieceSizeY = pieceHeight;

		for (int i = 0; i < divideY; i++) {
			for (int j = 0; j < divideX; j++) {
				pieceStorage[j + (i * divideX)] = generatePiece((pieceSizeX / 2) + pieceLength * j,
						(pieceSizeY / 2) + pieceHeight * i, pieceLength, pieceHeight, distortionPoints, i, j,
						pieceStorage);
			}
		}
		return pieceStorage;

	}

	// Generate a single piece
	public Point2D.Float[] generatePiece(float centerX, float centerY, float sizeX, float sizeY, int distortionPoints,
			int relativePositionY, int relativePositionX, Object[] pieceStorage) {
		int points = getNumberOfPoints(relativePositionX, relativePositionY, distortionPoints);
		Point2D.Float[] formarray = new Point2D.Float[points + 1];
		// add center of the piece to the point list
		int index = 0;
		// add corner coordinate
		formarray[index] = new Point2D.Float(-sizeX / 2, -sizeY / 2);
		index++;
		// distort top , if distortion needed eg. we are not on the border of the puzzle
		if (relativePositionY != 0) {
			Point2D.Float[] previousPiece = (Point2D.Float[]) pieceStorage[relativePositionX
					+ ((relativePositionY - 1) * divideX)];
			int n = findPointNumberY(relativePositionX, (relativePositionY - 1), distortionPoints);

			for (int i = distortionPoints - 1; i >= 0; i--) {
				Point2D.Float point = previousPiece[n + i];
				float x = (float) point.getX();
				float y = (float) point.getY();
				formarray[index] = new Point2D.Float(x, (y - ((float) (boardSize / divideY))));
				index++;
			}
		}
		// add corner coordinate
		formarray[index] = new Point2D.Float(sizeX / 2, -sizeY / 2);
		index++;
		// distort right side , if distortion needed eg. we are not oon the border of
		// the puzzle
		if (relativePositionX != (divideX - 1)) {
			Point2D.Float[] distortedList = distortRight(sizeX / 2, -sizeY / 2);
			for (int i = 0; i < distortedList.length; i++) {
				formarray[index] = distortedList[i];
				index++;
			}
		}
		// add corner coordinate
		formarray[index] = new Point2D.Float(sizeX / 2, sizeY / 2);
		index++;

		// distort bottom , if distortion needed eg. we are not oon the border of the
		// puzzle
		if (relativePositionY != (divideY - 1)) {
			Point2D.Float[] distortedList = distortDown(sizeX / 2, sizeY / 2);
			for (int i = 0; i < distortedList.length; i++) {
				formarray[index] = distortedList[i];
				index++;
			}
		}
		// add corner coordinate
		formarray[index] = new Point2D.Float(-sizeX / 2, sizeY / 2);
		index++;

		// distort left side , if distortion needed eg. we are not on the border of the
		// puzzle
		if (relativePositionX != 0) {
			Point2D.Float[] previousPiece = (Point2D.Float[]) pieceStorage[(relativePositionX - 1)
					+ (relativePositionY * divideX)];
			int n = findPointNumberX((relativePositionX - 1), relativePositionY, distortionPoints);
			for (int i = distortionPoints - 1; i >= 0; i--) {
				Point2D.Float point = previousPiece[n + i];
				float x = (float) point.getX();
				float y = (float) point.getY();

				formarray[index] = new Point2D.Float((x - ((float) (boardSize / divideX))), y);
				index++;
			}
		}
		formarray[index] = new Point2D.Float(-sizeX / 2, -sizeY / 2);
		return formarray;
	}

	private Point2D.Float[] distortRight(float posX, float posY) {
		Point2D.Float[] distortedList = new Point2D.Float[distortionPoints];
		float randomY = posY;
		float randomX = 0;

		float[] randomPoints = chooseRandomPoints(posY, -posY, distortionPoints);

		for (int i = 0; i < distortionPoints; i++) {
			randomY = randomPoints[i];

			if (i == 0) {
				do {
					float d1 = -posY - randomY;
					float d2 = randomY - posY;

					float smallest1 = getSmallest(d1, d2);
					float smallest = getSmallest(smallest1, ((float) (boardSize / divideX) / 2));
					randomX = chooseRandom(smallest, -smallest);
					randomX += ((float) (boardSize / divideX) / 2);

				} while (lengthCheck.contains((double) randomX));
				distortedList[i] = new Point2D.Float(randomX, randomY);
				lengthCheck.add((double) randomX);
			} else {
				float d1 = -posY - randomY;
				float d2 = randomY - posY;

				float smallest1 = getSmallest(d1, d2);
				float smallest = getSmallest(smallest1, ((float) (boardSize / divideX) / 2));
				randomX = chooseRandom(smallest, -smallest);
				randomX += ((float) (boardSize / divideX) / 2);
				distortedList[i] = new Point2D.Float(randomX, randomY);
			}

		}

		return distortedList;
	}

	private Point2D.Float[] distortDown(float posX, float posY) {
		Point2D.Float[] distortedList = new Point2D.Float[distortionPoints];
		float randomX = posX;
		float randomY = 0;

		float[] randomPoints = chooseRandomPoints(posX, -posX, distortionPoints);
		randomPoints = Reverse(randomPoints);
		for (int i = 0; i < distortionPoints; i++) {
			randomX = randomPoints[i];

			if (divideX == divideY) {
				if (i == 0) {
					do {
						float d1 = randomX - posX;
						float d2 = -posX - randomX;
						float smallest1 = getSmallest(d1, d2);
						float smallest = getSmallest(smallest1, ((float) (boardSize / divideY) / 2));
						randomY = chooseRandom(smallest, -smallest);
						randomY += ((float) (boardSize / divideY) / 2);
						distortedList[i] = new Point2D.Float(randomX, randomY);

					} while (lengthCheck.contains((double) randomY));
					distortedList[i] = new Point2D.Float(randomX, randomY);
					lengthCheck.add((double) randomY);
				} else {
					float d1 = randomX - posX;
					float d2 = -posX - randomX;
					float smallest1 = getSmallest(d1, d2);
					float smallest = getSmallest(smallest1, ((float) (boardSize / divideY) / 2));
					randomY = chooseRandom(smallest, -smallest);
					randomY += ((float) (boardSize / divideY) / 2);
					distortedList[i] = new Point2D.Float(randomX, randomY);
				}
			} else {
				float d1 = randomX - posX;
				float d2 = -posX - randomX;
				float smallest1 = getSmallest(d1, d2);
				float smallest = getSmallest(smallest1, ((float) (boardSize / divideY) / 2));
				randomY = chooseRandom(smallest, -smallest);
				randomY += ((float) (boardSize / divideY) / 2);
				distortedList[i] = new Point2D.Float(randomX, randomY);
			}

		}

		return distortedList;
	}

	private int findPointNumberY(int x, int y, int distortionPoints) {
		int n = 4;
		if (y != 0) {
			n += distortionPoints;
		}
		if (x != (divideX - 1)) {
			n += distortionPoints;
		}
		return n - 1;
	}

	private int findPointNumberX(int x, int y, int distortionPoints) {
		int n = 3;
		if (y != 0) {
			n += distortionPoints;
		}
		return n - 1;
	}

	private float[] chooseRandomPoints(float upper, float lower, int distortionPoints) {
		float[] randomPoints = new float[distortionPoints];

		for (int i = 0; i < distortionPoints; i++) {
			randomPoints[i] = (float) (Math.random() * (upper - lower)) + lower;
		}
		Arrays.sort(randomPoints);
		return randomPoints;
	}

	private float[] Reverse(float[] a) {
		// https://www.tutorialkart.com/java/java-array-reverse/
		float[] result = new float[a.length];
		for (int i = 0; i < a.length; i++) {
			result[a.length - 1 - i] = a[i];
		}
		return result;
	}

	private float chooseRandom(float upper, float lower) {
		return (float) (Math.random() * (upper - lower)) + lower;
	}

	private float getSmallest(float d, float f) {
		if (Math.abs(d) > Math.abs(f)) {
			return f;
		} else {
			return d;
		}
	}

//	private boolean contains(ArrayList<Float> lengthCheck, float length) {
//		for(int i = 0; i < lengthCheck.size(); i++) {
//			if(lengthCheck.get(i).equals(length)) {
//				return true;
//			}
//		}
//		return false;
//		
//	}

	// GETTERS AND SETTERS
	public int getColumns() {
		return divideX;
	}

	public int getRows() {
		return divideY;
	}

	public double getBoardSize() {
		return boardSize;
	}

	public int getPieceBaseWidth() {
		return (int) (boardSize / divideX);
	}

	public int getPieceBaseHeight() {
		return (int) (boardSize / divideY);
	}

	public String getPieceStorage() {
		return Arrays.deepToString(pieceStorage);
	}

	private int getNumberOfPoints(int relativePositionX, int relativePositionY, int distortionPoints) {
		int numberOfPoints = 4;
		if (relativePositionX != 0) {
			numberOfPoints += distortionPoints;
		}
		if (relativePositionY != 0) {
			numberOfPoints += distortionPoints;

		}
		if (relativePositionX != (divideX - 1)) {

			numberOfPoints += distortionPoints;
		}
		if (relativePositionY != (divideY - 1)) {
			numberOfPoints += distortionPoints;

		}
		return numberOfPoints;
	}

	public int getPieceAmount() {
		return pieceAmount;
	}

}
