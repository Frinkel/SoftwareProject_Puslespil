package controller;


import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Arrays;
import java.util.ArrayList;

import model.Piece;

public class PieceCompare {
	// Alexander and Carl
	public PieceCompare() {

	}

	public ArrayList<Object> pieceComparator(Object[] pieceList) {
		float[] circumferences = new float[pieceList.length];
		ArrayList<Integer> piecesSuspectedOfDuplication = new ArrayList<Integer>();
		boolean containBool = false;
		
		
		for (int i = 0; i < (pieceList.length); i++) {
			pieceList[i] = removePointOnLine((Point2D.Float[]) pieceList[i]);
			Point2D.Float[] piece = (Point2D.Float[]) pieceList[i];
			containBool = (containBool || contains(circumferences, getCircumference(piece)));
			if (contains(circumferences, getCircumference(piece))) {
				piecesSuspectedOfDuplication.add(i);
			}
			circumferences[i] = getCircumference(piece);
			// System.out.println(Arrays.toString(circumferences));
		}
		if (containBool) {
			ArrayList<Object> equalList = piecesWithEqualCircumFerences(circumferences, piecesSuspectedOfDuplication);
			equalList = checkEqualNumberOfCorners(equalList,pieceList);
			equalList = checkForEqualPieces(equalList, pieceList);
			return equalList;
				
			
		}
		return null;

	}
	
	private ArrayList<Object> checkForEqualPieces(ArrayList<Object> equalList, Object[] pieceList) {
		for(int i = 0; i < equalList.size();) {
			int[] pairToCheck = (int[]) equalList.get(i);
			Point2D.Float[] piece1 = (Point2D.Float[]) pieceList[pairToCheck[0]];
			Point2D.Float[] piece2 = (Point2D.Float[]) pieceList[pairToCheck[1]];
			AngleLength[] angleLength1 = createAncleLengthArray(piece1);
			AngleLength[] angleLength2 = createAncleLengthArray(piece2);
			
			
			if(checkAngleLengthPieces(angleLength1, angleLength2)) {
				i++;
			}else {
				equalList.remove(i);
			}
		}
		
		return equalList;
	}

	
	private boolean checkAngleLengthPieces(AngleLength[] angleLength1, AngleLength[] angleLength2) {
		int length = angleLength1.length;
		for(int j = 0; j < length; j++) {

			for(int i = 0; i < length; i++) {

				
				double angle1 = (double) Math.round(angleLength1[i].getAngle() * 1000d);
				double angle2 = (double) Math.round(angleLength2[i].getAngle() * 1000d);
				
				if(angle1 == angle2) {
					double length1 = (double) Math.round(angleLength1[i].getLength() * 1000d);
					double length2 = (double) Math.round(angleLength2[i].getLength() * 1000d);

					
					if(length1 == length2) {
						if(i == length - 1) {
							return true;
						}
					}else {
						i = length+1;
					}
				}else {
					i = length+1;
				}
			}
			AngleLength[] tempArray = angleLength2.clone();
			for(int k = 0; k < length; k++) {
				angleLength2[k] = tempArray[((length-1)+k)%length];
			}
			
		}
		return false;
	}

	public AngleLength[] createAncleLengthArray(Point2D.Float[] piece) {
		int listLength = piece.length;
		AngleLength[] angleLength = new AngleLength[listLength-1];
		for (int i = 0; i < (listLength - 1); i++) {
			angleLength[i] = new AngleLength(getAngleFromThreePoints(piece[i], piece[i+1], piece[(i+2)%(listLength-1)]), distance(piece[i], piece[i+1]));
		}
		return angleLength;
	}

	private ArrayList<Object> checkEqualNumberOfCorners(ArrayList<Object> equalList, Object[] pieceList) {
		for(int i = 0; i < equalList.size();) {
			int[] pairToCheck = (int[]) equalList.get(i);
			Point2D.Float[] piece1 = (Point2D.Float[]) pieceList[pairToCheck[0]];
			Point2D.Float[] piece2 = (Point2D.Float[]) pieceList[pairToCheck[1]];
			if(piece1.length == piece2.length) {
				i++;
			}else {
				equalList.remove(i);
			}
		}
		
		return equalList;
	}

	public Object removePointOnLine(Point2D.Float[] piece) {
		for(int i = 0;i<piece.length-1;i++) {
			if(threePointOnLine(piece[i],piece[((i+1)%(piece.length))],piece[((i+2)%(piece.length))])) {
				Point2D.Float[] newPiece = new Point2D.Float[piece.length-1];
				int index = 0;
				for(int j = 0;j<newPiece.length;) {
					if((j+index)!=(((i+1)%(piece.length)))) {
						newPiece[j] = piece[j+index];
						j++;
					}else {
						index++;
					}
				}
				return newPiece;
			}	
		}
		return piece;
	}

	public boolean contains(float[] circumferences, float circumference) {
		for (float x : circumferences) {
			if (x == circumference) {
				return true;
			}
		}
		return false;
	}

	public float getCircumference(Point2D.Float[] points) {
		float circumference = 0.0f;
		for (int i = 0; i < (points.length - 1); i++) {
			circumference += distance(points[i], points[i + 1]);
		}
		// System.out.println("circumference = " + circumference);
		return circumference;

	}

	public float distance(Point2D.Float p1, Point2D.Float p2) {
		float toReturn = Math.abs((float) Math.sqrt((Math.pow((p2.getX() - p1.getX()), 2)) + (Math.pow((p2.getY() - p1.getY()), 2))));
		
		
		return (float) (((double) Math.round(toReturn * 100d))/100);
	}

	public boolean threePointOnLine(Point2D.Float point1, Point2D.Float point2, Point2D.Float point3) {
		double n = 0.0;
		double m = 0.0;
		m = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
		n = (point3.getY() - point2.getY()) / (point3.getX() - point2.getX());
		if (((double) Math.round(m * 10000d)) == ((double) Math.round(n * 10000d))) {
			return true;
		} else {
			return false;
		}

	}

	public ArrayList<Object> piecesWithEqualCircumFerences(float[] circumferences,
		ArrayList<Integer> piecesSuspectedOfDuplication) {
		ArrayList<Object> equalList = new ArrayList<Object>();
		for (int i = 0; i < circumferences.length; i++) {
			for (int j = 0; j < circumferences.length; j++) {
				if ((j != i) && (((double) Math.round(circumferences[j] * 1000d)
						/ 1000d) == ((double) Math.round(circumferences[i] * 1000d) / 1000d))) {
					int[] toAdd = { i, j };
					if (!(containsDuplicateValues(equalList, toAdd))) {
						equalList.add(toAdd);
					}
				}
			}
		}
		return equalList;

	}

	public boolean containsDuplicateValues(ArrayList<Object> equalList, int[] toAdd) {
		for (int i = 0; i < equalList.size(); i++) {
			int j = ((int[]) equalList.get(i))[0];
			int k = ((int[]) equalList.get(i))[1];
			if ((j == toAdd[0] && k == toAdd[1]) || (j == toAdd[1] && k == toAdd[0])) {
				return true;
			}
		}
		return false;
	}
	
	public float getAngleFromThreePoints(Point2D.Float point1, Point2D.Float point2, Point2D.Float point3) {
		Point2D.Float vectorBA = new Point2D.Float((float) (point1.getX()-point2.getX()),(float) (point1.getY()-point2.getY()));
		Point2D.Float vectorBC = new Point2D.Float((float) (point3.getX()-point2.getX()),(float) (point3.getY()-point2.getY()));
		
		
		
		float lengthAB = (float) Math.sqrt(Math.pow(vectorBA.getX(), 2) + Math.pow(vectorBA.getY(), 2));
		float lengthBC = (float) Math.sqrt(Math.pow(vectorBC.getX(), 2) + Math.pow(vectorBC.getY(), 2));
		float dotProduct = (float) ((vectorBA.getX() * vectorBC.getX()) + (vectorBA.getY() * vectorBC.getY()));
		
		float distanceFromLine = distanceToCenter(point1,point3);
		float x = (float) ((point2.getX() - point1.getX()) + point3.getX());
		float y = (float) ((point2.getY() - point1.getY()) + point3.getY());
		Point2D.Float pointFrom1And2 = new Point2D.Float(x,y);
		float distanceFromLinePoint = distanceToCenter(point2,pointFrom1And2);
		float angle = (float) (Math.acos((dotProduct)/(lengthAB * lengthBC)));
		if(dotProduct == 0) {
			angle = (float) (Math.PI/2);
		}
		
		
		if(distanceFromLinePoint < distanceFromLine) {
			angle = (float) ((2*Math.PI)-(angle));
		}
		
		return angle;
		
	}

	

	private float distanceToCenter(Float point1, Float point3) {
		float x0 = 0.0f;
		float x1 = (float) point1.getX();
		float x2 = (float) point3.getX();
		float y0 = 0.0f;
		float y1 = (float) point1.getY();
		float y2 = (float) point3.getY();
		
		float distance = (float) (Math.abs(((x2-x1)*(y1-y0))-((x1-x0)*(y2-y1)))/(Math.sqrt((Math.pow((x2-x1), 2))+(Math.pow((y2-y1), 2)))));
		
		return distance;
				
		
		
		
	}
	
}
