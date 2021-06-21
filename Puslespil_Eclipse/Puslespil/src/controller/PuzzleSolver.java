package controller;

//why dis chit not vork?
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Arrays;
import java.util.ArrayList;
import controller.PieceCompare;

import model.Piece;
import processing.core.PApplet;

//hello
public class PuzzleSolver {
	public PuzzleSolver() {

	}

	public PieceAndAngleDatatype[] puzzleSolvers(Object[] pieceList) {

		PieceCompare pC = new PieceCompare();
		Object[] AncleLengthPieceList = new Object[pieceList.length];

		for (int i = 0; i < pieceList.length; i++) {

			Point2D.Float[] piece = (Point2D.Float[]) pieceList[i];
			AngleLength[] angleLength = pC.createAncleLengthArray(piece);
			AncleLengthPieceList[i] = angleLength;
//			System.out.println("aL " + i + "      " + Arrays.toString(angleLength));
		}
		Object[] groups = groupPieces(pieceList);

		Object[] adjacencyAndMatchingArray = produceAngleLengthPairs(AncleLengthPieceList,
				(ArrayList<Integer>) groups[0], (ArrayList<Integer>) groups[1], (ArrayList<Integer>) groups[2],
				(int) groups[3], (int) groups[4], (int) groups[5]);
		Object[] adjacencyArray = (Object[]) adjacencyAndMatchingArray[0];
		Object[] matchingArray = (Object[]) adjacencyAndMatchingArray[1];
		

		int startingIndex = 0;
		if((int) groups[3] == 0) {
			if((int) groups[4] == 0) {
				startingIndex = ((ArrayList<Integer>) groups[2]).get(0);
			}else {
				startingIndex = ((ArrayList<Integer>) groups[1]).get(0);
			}
		}else {
			startingIndex = ((ArrayList<Integer>) groups[0]).get(0);
		}
		
		PieceAndAngleDatatype[] pieceAndRotationalAngle = rotatePiecesOnPath(startingIndex, pieceList.length, matchingArray, adjacencyArray, pieceList, pC);
		return pieceAndRotationalAngle;
		
	}

	private PieceAndAngleDatatype[] rotatePiecesOnPath(int startingIndex, int length, Object[] matchingArray, Object[] adjacencyArray, Object[] pieceList, PieceCompare pC) {
		// TODO Auto-generated method stub
		int[][] adjacencyArray2= new int[adjacencyArray.length][];
		for(int i = 0; i < adjacencyArray.length; i++) {
			ArrayList<Integer> temp = (ArrayList<Integer>) adjacencyArray[i];
			int[] temp2 = new int[temp.size()];
			for(int j = 0; j < temp.size(); j++) {
				temp2[j] = temp.get(j);
			}
			adjacencyArray2[i] = temp2;
		}
		
		PieceAndAngleDatatype[] pieceAndRotationalAngle = new PieceAndAngleDatatype[length];
		pieceAndRotationalAngle[0] = new PieceAndAngleDatatype(startingIndex, 0.0f, new Point2D.Float(500.0f,500.0f));
		
		for(int i = 0; i < length-1; i++) {
			int[] nextPiece = findPieceThatNeedAngleAndCenter(adjacencyArray2,pieceAndRotationalAngle);
			pieceAndRotationalAngle[i+1] = rotatePiecesAndFindCenter(nextPiece[0], nextPiece[1], matchingArray, pieceList, pieceAndRotationalAngle, pC);
			
		}
		return pieceAndRotationalAngle;
	}
	
	
	private int[] findPieceThatNeedAngleAndCenter(int[][] adjacencyArray2,
			PieceAndAngleDatatype[] pieceAndRotationalAngle) {
		
		for(int i = 0; i < pieceAndRotationalAngle.length; i++) {
			int[] current = adjacencyArray2[i];			
			if(getIndexInAngleCenterArray(pieceAndRotationalAngle, current[0]) != -1) {
				for(int j = 1; j < current.length; j++) {
					if(getIndexInAngleCenterArray(pieceAndRotationalAngle, current[j]) == -1) {
						return new int[] {current[0], current[j]};
					}
				}
			}
		}		
		return new int[] {0, 0};
	}

	private PieceAndAngleDatatype rotatePiecesAndFindCenter(int piece1, int piece2, Object[] matchingArray, Object[] pieceList,PieceAndAngleDatatype[] pieceAndRotationalAngle,PieceCompare pC) {
		// TODO Auto-generated method stub
		PieceAndAngleDatatype pieceRotationAndCenter = new PieceAndAngleDatatype(0, 0, null);
		//pieceAndRotationalAngle[0] = new PieceAndAngleDatatype(finalPath[0], 0.0f, new Point2D.Float(500.0f,500.0f));
		
		//for(int i = 0; i < finalPath.length-1; i++) {
			int i = getIndexInAngleCenterArray(pieceAndRotationalAngle, piece1);
			int[] pair = getPair(piece1, piece2, matchingArray);
			Point2D.Float[][] threeMatchingPoints = getPointsOfTwoSuitablePieces((int[]) pair, pieceList);
			float angle = findRotationOfMatchingPair(threeMatchingPoints, pC);
			if(pair[0] == piece1) {
				Point2D.Float center = getPosOfCenter(threeMatchingPoints,angle+(pieceAndRotationalAngle[i].getPieceAngle()), (pieceAndRotationalAngle[i].getPieceAngle()), (pieceAndRotationalAngle[i].getCenter()),1);
				pieceRotationAndCenter = new PieceAndAngleDatatype(piece2, angle+(pieceAndRotationalAngle[i].getPieceAngle()),center);
			}else {
				Point2D.Float center = getPosOfCenter(threeMatchingPoints,((-angle)+(pieceAndRotationalAngle[i].getPieceAngle())), (pieceAndRotationalAngle[i].getPieceAngle()), (pieceAndRotationalAngle[i].getCenter()),-1);
				pieceRotationAndCenter = new PieceAndAngleDatatype(piece2, -angle+(pieceAndRotationalAngle[i].getPieceAngle()),center);
		//	}
			
			//System.out.println("angle : " + angle);
//			System.out.println(Arrays.toString(pair));
			
			
		}
		return pieceRotationAndCenter;
	}
	
	private int getIndexInAngleCenterArray(PieceAndAngleDatatype[] pieceAndRotationalAngle, int piece) {
		for(int i = 0; i < pieceAndRotationalAngle.length;i++ ){
			if(pieceAndRotationalAngle[i] == null) {
				return -1;
			}else if(pieceAndRotationalAngle[i].getPieceIndex() == piece) {
				return i;
			}
		}
		return -1;
		
	}
	

	private Point2D.Float getPosOfCenter(Point2D.Float[][] threeMatchingPoints, float angle, float prevAngle, Point2D.Float prevCenter,int direction) {
		//System.out.println();
		Point2D.Float[] pointsToRotate1 = new Point2D.Float[] {(threeMatchingPoints[1][0]),(threeMatchingPoints[1][1]),(threeMatchingPoints[1][2])};
		Point2D.Float[] rotatedPoints1 = rotatePoints(pointsToRotate1,angle);
		
		Point2D.Float[] pointsToRotate2 = new Point2D.Float[] {(threeMatchingPoints[0][0]),(threeMatchingPoints[0][1]),(threeMatchingPoints[0][2])};
		Point2D.Float[] rotatedPoints2 = rotatePoints(pointsToRotate2,prevAngle);
		

		
		if(direction < 0 ) {
			rotatedPoints1 = rotatePoints(pointsToRotate2,angle);
			rotatedPoints2 = rotatePoints(pointsToRotate1,prevAngle);
		}
		Point2D.Float point1 = rotatedPoints2[1];
		Point2D.Float point2 = rotatedPoints1[1];
		Point2D.Float vectorBA = new Point2D.Float((float) (point1.getX()-point2.getX()),(float) (point1.getY()-point2.getY()));
		
		
		float newCenterX = (float) (prevCenter.getX() + vectorBA.getX());
		float newCenterY = (float) (prevCenter.getY() + vectorBA.getY());
		
//		float newCenterX = (float) (prevCenter.getX() + (vectorBA.getX()*direction));
//		float newCenterY = (float) (prevCenter.getY() + (vectorBA.getY()*direction));
//		
		Point2D.Float newCenter = new Point2D.Float(newCenterX,newCenterY);
//		System.out.println("angel " + angle + "new Center: " + newCenter + "  Vector   " + vectorBA);
		return newCenter;
	}

	private int[] getPair(int integer, int integer2, Object[] matchingArray) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < matchingArray.length; i++) {
			
			if(matchingArray[i] == (null)) {
				return new int[] {-1};
			}
			int[] pairToCheck = (int[]) matchingArray[i];
			if ((pairToCheck[0] == integer2 && pairToCheck[1] == integer) || (pairToCheck[1] == integer2 && pairToCheck[0] == integer)) {
				return pairToCheck;
			}
		}
		return new int[] {-1};
		
	}

	private int[] calculatePath(int length, Object[] adjacencyArray, Object[] matchingArray, ArrayList<Integer> gr1,
			ArrayList<Integer> gr2, ArrayList<Integer> gr3, int gr1Length, int gr2Length, int gr3Length) {
		//ArrayList<Integer> alreadyCovered = new ArrayList<Integer>();
		int startingIndex = 0;
		if(gr1Length == 0) {
			if(gr2Length == 0) {
				startingIndex = gr3.get(0);
			}else {
				startingIndex = gr2.get(0);
			}
		}else {
			startingIndex = gr1.get(0);
		}
		
		int[][] adjacencyArray2= new int[adjacencyArray.length][];
		for(int i = 0; i < adjacencyArray.length; i++) {
			ArrayList<Integer> temp = (ArrayList<Integer>) adjacencyArray[i];
			int[] temp2 = new int[temp.size()];
			for(int j = 0; j < temp.size(); j++) {
				temp2[j] = temp.get(j);
			}
			adjacencyArray2[i] = temp2;
		}
		int[] alreadyCovered = {startingIndex};
		int[] finalPath = getPath(startingIndex, adjacencyArray2, alreadyCovered, length);
//		System.out.println(Arrays.toString(finalPath));
		if(finalPath == null) {
			finalPath = new int[]{0};
		}
		return finalPath;
		//return new int[] {0, 13, 10, 19, 43, 58, 73, 17, 54, 45, 59, 79, 32, 8, 20, 23, 9, 41, 60, 76, 36, 50, 62, 51, 65, 47, 12, 6, 37, 11, 42, 24, 5, 18, 56, 14, 15, 1, 21, 55, 64, 7, 66, 25, 75, 28, 68, 46, 31, 33, 71, 77, 35, 30, 70, 52, 49, 63, 29, 40, 34, 16, 38, 72, 61, 44, 2, 22, 4, 26, 53, 27, 74, 78, 69, 3, 39, 57, 48, 67};

	}


	
	private int[] getPath(int startingIndex, int[][] adjacencyArray, int[] alreadyCovered,
			int length) {
		int[] adjacencyPair = null;
		int [] toReturn = null;

		for (int i = 0; i < adjacencyArray.length; i++) {
			int[] pair = adjacencyArray[i];
			if (pair[0] == startingIndex) {
				adjacencyPair = pair;
			}
		}
		int tempL = alreadyCovered.length;
		int[] temp = new int[tempL + 1];
		for(int j = 0; j < tempL; j++) {
			temp[j] = alreadyCovered[j];
		}
		for(int i = 1; i < adjacencyPair.length; i++) {
			if (alreadyCovered.length < length) {


				
				if (!arrContains(alreadyCovered,adjacencyPair[i])) {
					temp[tempL] = adjacencyPair[i];
					int[] temp2 = getPath(adjacencyPair[i], adjacencyArray, temp, length);
					if(temp2 != null) {
						toReturn = temp2;
						if(toReturn.length >= length) {
							return toReturn;
						}
					}
				}

			}else {
//				System.out.println(alreadyCovered.length + "        " + Arrays.toString(alreadyCovered));
				return alreadyCovered;
			}
		}
		return toReturn;		
	}
	private boolean arrContains(int[] arr, int a) {
		  for(int n : arr) {
			  if(n == a){
				  return true;
			  }  
		  }
		  return false;
	}

	private float findRotationOfMatchingPair(Point2D.Float[][] threeMatchingPoints, PieceCompare pC) {
		Point2D.Float vector0 = new Point2D.Float((float) (((threeMatchingPoints[0][0])).getX()-(threeMatchingPoints[0][2]).getX()),(float) ((threeMatchingPoints[0][0]).getY()-(threeMatchingPoints[0][2]).getY()));
		Point2D.Float vector1 = new Point2D.Float((float) ((threeMatchingPoints[1][2]).getX()-(threeMatchingPoints[1][0]).getX()),(float) ((threeMatchingPoints[1][2]).getY()-(threeMatchingPoints[1][0]).getY()));
//		System.out.println("vector0 " + vector0);
//		System.out.println("vector1 " + vector1);
		float length0 = (float) Math.sqrt(Math.pow(vector0.getX(), 2) + Math.pow(vector0.getY(), 2));
		float length1 = (float) Math.sqrt(Math.pow(vector1.getX(), 2) + Math.pow(vector1.getY(), 2));
		float dotProduct = (float) ((vector0.getX() * vector1.getX()) + (vector0.getY() * vector1.getY()));
		
		float angle = (float) (Math.acos((dotProduct)/(length0 * length1)));
		if(((dotProduct)/(length0 * length1)) > 1 ) {
			angle = 0;
		}

		Point2D.Float[] pointsToRotate = new Point2D.Float[] {(threeMatchingPoints[1][0]),(threeMatchingPoints[1][1]),(threeMatchingPoints[1][2])};
		Point2D.Float[] rotatedPoints = rotatePoints(pointsToRotate,angle);
		
		float d1 = ((float) (((double) Math.round((pC.distance((threeMatchingPoints[0][2]), rotatedPoints[0])) * 100d)) / 100));
		float d2 = ((float) (((double) Math.round((pC.distance((threeMatchingPoints[0][1]), rotatedPoints[1])) * 100d)) / 100));
		float d3 = ((float) (((double) Math.round((pC.distance((threeMatchingPoints[0][0]), rotatedPoints[2])) * 100d)) / 100));
//		System.out.println("d1 + d2 + d3 " + d1 + " " + d2 + " " + d3);
//		System.out.println(Arrays.toString(rotatedPoints));
//		System.out.println(angle);
		if(d1 == d2 && d2 == d3 && d1 == d3) {
			return angle;
		}else {
			return (float) (2*Math.PI-angle);
		}
	
	}
	
	private Point2D.Float[] rotatePoints(Point2D.Float[] pointsToRotate, float angle ){
		Point2D.Float[] rotatedPoints = new Point2D.Float[3];
		
		for(int i = 0; i < 3; i++) {
			float rotatedX = (float) (PApplet.cos(angle) * (pointsToRotate[i].getX()) - (PApplet.sin(angle) * (pointsToRotate[i].getY())));
			float rotatedY = (float) (PApplet.sin(angle) * (pointsToRotate[i].getX()) + (PApplet.cos(angle) * (pointsToRotate[i].getY())));
			rotatedPoints[i] = new Point2D.Float(rotatedX, rotatedY);
		}
		return rotatedPoints;
	}
	

	public Object[] groupPieces(Object[] pieceList) {
		ArrayList<Integer> gr1 = new ArrayList<Integer>();
		int gr1Length = 0;
		ArrayList<Integer> gr2 = new ArrayList<Integer>();
		int gr2Length = 0;
		ArrayList<Integer> gr3 = new ArrayList<Integer>();
		int gr3Length = 0;

		Point2D.Float[] piece1 = (Point2D.Float[]) pieceList[0];
		gr1Length = piece1.length;
		gr1.add(0);
// make sure to group correctly
		for (int i = 1; i < pieceList.length; i++) {
			Point2D.Float[] piece = (Point2D.Float[]) pieceList[i];
			int l = piece.length;
			if (l == gr1Length) {
				gr1.add(i);
			} else if (l == gr2Length) {
				gr2.add(i);
			} else if (l == gr3Length) {
				gr3.add(i);
			} else if (0 == gr2Length) {
				gr2Length = l;
				gr2.add(i);
			} else if (0 == gr3Length) {
				gr3Length = l;
				gr3.add(i);
			}
		}

		if (gr1Length > gr2Length) {
			ArrayList<Integer> temp = gr1;
			int tempLength = gr1Length;
			gr1 = gr2;
			gr1Length = gr2Length;
			gr2 = temp;
			gr2Length = tempLength;
		}
		if (gr2Length > gr3Length) {
			if (gr1Length > gr3Length) {
				ArrayList<Integer> temp1 = gr1;
				int tempLength1 = gr1Length;
				ArrayList<Integer> temp2 = gr2;
				int tempLength2 = gr2Length;
				gr1 = gr3;
				gr1Length = gr3Length;
				gr2 = temp1;
				gr2Length = tempLength1;
				gr3 = temp2;
				gr3Length = tempLength2;
			} else {
				ArrayList<Integer> temp = gr2;
				int tempLength = gr2Length;
				gr2 = gr3;
				gr2Length = gr3Length;
				gr3 = temp;
				gr3Length = tempLength;
			}

		}

//		System.out.println("gr1 L :" + gr1Length + " members" + gr1.toString());
//		System.out.println("gr2 L :" + gr2Length + " members" + gr2.toString());
//		System.out.println("gr3 L :" + gr3Length + " members" + gr3.toString());

		return new Object[] { gr1, gr2, gr3, gr1Length, gr2Length, gr3Length };
	}

	private Object[] produceAngleLengthPairs(Object[] AncleLengthPieceList, ArrayList<Integer> gr1,
		ArrayList<Integer> gr2, ArrayList<Integer> gr3, int gr1Length, int gr2Length, int gr3Length) {

		Object[] adjacencyArray = new Object[gr1.size() + gr2.size() + gr3.size()];
		Object[] matchingArray = new Object[2 * gr1.size() + (3*gr2.size()) + (4 * gr3.size())];
		int k = 0;
		for (int i = 0; i < gr1.size(); i++) {
			ArrayList<Integer> toAdd = new ArrayList<Integer>();
			toAdd.add(gr1.get(i));
			if (gr2.isEmpty()) {
//				System.out.println(" is in first case");
				for (int j = 0; j < gr1.size(); j++) {
					if (i != j) {
						if (!containsPair(matchingArray, gr1.get(i), gr1.get(j))) {
							int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr1.get(i)],
									(AngleLength[]) AncleLengthPieceList[gr1.get(j)], gr1.get(i), gr1.get(j));
							int neighbourInt = toAddMatching[1];
							if (neighbourInt != -1) {
								toAdd.add(neighbourInt);
								matchingArray[k] = toAddMatching;
								k++;
							} else {

							}
						}else {
							toAdd.add(gr1.get(j));
						}

					}
				}
//				System.out.println(toAdd.toString());

				adjacencyArray[i] = toAdd;
			} else if (gr3.isEmpty()) {
				for (int j = 0; j < gr1.size(); j++) {
					if (i != j) {
						if (!containsPair(matchingArray, gr1.get(i), gr1.get(j))) {
							int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr1.get(i)],
									(AngleLength[]) AncleLengthPieceList[gr1.get(j)], gr1.get(i), gr1.get(j));
							int neighbourInt = toAddMatching[1];
							if (neighbourInt != -1) {
								toAdd.add(neighbourInt);
								matchingArray[k] = toAddMatching;
								k++;
							}
						}else {
							toAdd.add(gr1.get(j));
						}
					}
				}

				for (int j = 0; j < gr2.size(); j++) {
					if (!containsPair(matchingArray, gr1.get(i), gr2.get(j))) {
						int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr1.get(i)],
								(AngleLength[]) AncleLengthPieceList[gr2.get(j)], gr1.get(i), gr2.get(j));
						int neighbourInt = toAddMatching[1];
						if (neighbourInt != -1) {
							toAdd.add(neighbourInt);
							matchingArray[k] = toAddMatching;
							k++;
						}
					}else {
						toAdd.add(gr2.get(j));
					}
				}
//				System.out.println(toAdd.toString());

				adjacencyArray[i] = toAdd;
			} else {
//				System.out.println("is in last case");
				for (int j = 0; j < gr2.size(); j++) {
					if (!containsPair(matchingArray, gr1.get(i), gr2.get(j))) {
						int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr1.get(i)],
								(AngleLength[]) AncleLengthPieceList[gr2.get(j)], gr1.get(i), gr2.get(j));
						int neighbourInt = toAddMatching[1];
						if (neighbourInt != -1) {
							toAdd.add(neighbourInt);
							matchingArray[k] = toAddMatching;
							k++;
						}
					}else {
						toAdd.add(gr2.get(j));
					}

				}
//				System.out.println(toAdd.toString());

				adjacencyArray[i] = toAdd;
			}

		}
		for (int i = 0; i < gr2.size(); i++) {
			ArrayList<Integer> toAdd = new ArrayList<Integer>();
			toAdd.add(gr2.get(i));
			if (gr3.isEmpty()) {
				for (int j = 0; j < gr1.size(); j++) {
					if (!containsPair(matchingArray, gr2.get(i), gr1.get(j))) {
						int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr2.get(i)],
								(AngleLength[]) AncleLengthPieceList[gr1.get(j)], gr2.get(i), gr1.get(j));
						int neighbourInt = toAddMatching[1];
	
						if (neighbourInt != -1) {
							toAdd.add(neighbourInt);
							matchingArray[k] = toAddMatching;
							k++;
						}
					}else {
						toAdd.add(gr1.get(j));
					}
				}
				for (int j = 0; j < gr2.size(); j++) {
					if (i != j) {
						if (!containsPair(matchingArray, gr2.get(i), gr2.get(j))) {
							int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr2.get(i)],
									(AngleLength[]) AncleLengthPieceList[gr2.get(j)], gr2.get(i), gr2.get(j));
							int neighbourInt = toAddMatching[1];
	
							if (neighbourInt != -1) {
								toAdd.add(neighbourInt);
								matchingArray[k] = toAddMatching;
								k++;
							}
						}else {
							toAdd.add(gr2.get(j));
						}
					}
				}
//				System.out.println(toAdd.toString());

				adjacencyArray[i + gr1.size()] = toAdd;
			} else {
				for (int j = 0; j < gr1.size(); j++) {
					if (!containsPair(matchingArray, gr2.get(i), gr1.get(j))) {
						int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr2.get(i)],
								(AngleLength[]) AncleLengthPieceList[gr1.get(j)], gr2.get(i), gr1.get(j));
						int neighbourInt = toAddMatching[1];
	
						if (neighbourInt != -1) {
							toAdd.add(neighbourInt);
							matchingArray[k] = toAddMatching;
							k++;
						}
					}else {
						toAdd.add(gr1.get(j));
					}
				}
				for (int j = 0; j < gr2.size(); j++) {
					if (i != j) {
						if (!containsPair(matchingArray, gr2.get(i), gr2.get(j))) {
							int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr2.get(i)],
									(AngleLength[]) AncleLengthPieceList[gr2.get(j)], gr2.get(i), gr2.get(j));
							int neighbourInt = toAddMatching[1];
	
							if (neighbourInt != -1) {
								toAdd.add(neighbourInt);
								matchingArray[k] = toAddMatching;
								k++;
							}
						}else {
							toAdd.add(gr2.get(j));
						}
					}
				}

				for (int j = 0; j < gr3.size(); j++) {
					if (!containsPair(matchingArray, gr2.get(i), gr3.get(j))) {
						int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr2.get(i)],
								(AngleLength[]) AncleLengthPieceList[gr3.get(j)], gr2.get(i), gr3.get(j));
						int neighbourInt = toAddMatching[1];
	
						if (neighbourInt != -1) {
							toAdd.add(neighbourInt);
							matchingArray[k] = toAddMatching;
							k++;
						}
					}else {
						toAdd.add(gr3.get(j));
					}
				}
//				System.out.println(toAdd.toString());

				adjacencyArray[i + gr1.size()] = toAdd;

			}

		}
		for (int i = 0; i < gr3.size(); i++) {
			ArrayList<Integer> toAdd = new ArrayList<Integer>();
			toAdd.add(gr3.get(i));
			for (int j = 0; j < gr2.size(); j++) {
				if (!containsPair(matchingArray, gr3.get(i), gr2.get(j))) {
					int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr3.get(i)],
							(AngleLength[]) AncleLengthPieceList[gr2.get(j)], gr3.get(i), gr2.get(j));
					int neighbourInt = toAddMatching[1];
	
					if (neighbourInt != -1) {
						toAdd.add(neighbourInt);
						matchingArray[k] = toAddMatching;
						k++;
					}
				}else {
					toAdd.add(gr2.get(j));
				}
			}
			for (int j = 0; j < gr3.size(); j++) {
				if (i != j) {
					if (!containsPair(matchingArray, gr3.get(i), gr3.get(j))) {
						int[] toAddMatching = checkTwoPieces((AngleLength[]) AncleLengthPieceList[gr3.get(i)],
								(AngleLength[]) AncleLengthPieceList[gr3.get(j)], gr3.get(i), gr3.get(j));
						int neighbourInt = toAddMatching[1];
	
						if (neighbourInt != -1) {
							toAdd.add(neighbourInt);
							matchingArray[k] = toAddMatching;
							k++;
						}
					}else {
						toAdd.add(gr3.get(j));
					}
				}
			}
			//System.out.println(toAdd.toString());

			adjacencyArray[i + gr1.size() + gr2.size()] = toAdd;

		}
//		System.out.println("adjacency : " + Arrays.toString(adjacencyArray));
//		System.out.println("size : " + adjacencyArray.length);
//		System.out.println("matchingarray : " + Arrays.deepToString(matchingArray));
		return new Object[] { adjacencyArray, matchingArray };

	}

	private boolean containsPair(Object[] matchingArray, int integer, int integer2) {
		
		for (int i = 0; i < matchingArray.length; i++) {
			
			if(matchingArray[i] == (null)) {
				return false;
			}
			int[] pairToCheck = (int[]) matchingArray[i];
			if ((pairToCheck[0] == integer2 && pairToCheck[1] == integer) || (pairToCheck[1] == integer2 && pairToCheck[0] == integer)) {
				return true;
			}
		}
		return false;
	}

	private int[] checkTwoPieces(AngleLength[] angleLength1, AngleLength[] angleLength2, int index0, int index1) {
		// TODO Auto-generated method stub
		ArrayList<Integer> adjacencyPieces = new ArrayList<Integer>();
		for (int i = 0; i < angleLength1.length; i++) {
			for (int j =  angleLength2.length-1; j >= 0; j--) {
				if (((float) (((double) Math.round((angleLength1[i].getAngle() + angleLength2[j].getAngle()) * 100d))
						/ 100) == ((float) (((double) Math.round((2 * Math.PI) * 100d)) / 100)))) {
					if ((angleLength1[i].getLength() == angleLength2[(j + 1) % angleLength2.length].getLength()
							|| angleLength1[i].getLength() == angleLength2[j].getLength())
							&& (angleLength1[(i + 1) % angleLength1.length]
									.getLength() == angleLength2[(j + 1) % angleLength2.length].getLength()
									|| angleLength1[(i + 1) % angleLength1.length].getLength() == angleLength2[j]
											.getLength())) {
						
							return new int[] { index0, index1, i, j };
						
						
					}
				}
			}
		}
		return new int[] { -1, -1, -1, -1 };
	}

	private Float[][] getPointsOfTwoSuitablePieces(int[] matchingPoints, Object[] pieceList) {
		Point2D.Float[][] threeMatchingPoints = new Point2D.Float[2][3];
		
		for (int i = 0; i < 2; i++) {
			//System.out.print(matchingPoints[i]);
			Point2D.Float[] piece = (Float[]) pieceList[matchingPoints[i]];
			for (int j = 0; j < 3; j++) {
				threeMatchingPoints[i][j] = piece[(matchingPoints[i + 2] + j) % (piece.length)];
			}
		}
		///System.out.println("");
		return threeMatchingPoints;
	}

	public float getAngleGivenIndex(int index, PieceAndAngleDatatype[] pieceAndRotationalAngle) {
		for(int i = 0; i < pieceAndRotationalAngle.length;i++) {
			if(pieceAndRotationalAngle[i].getPieceIndex() == index) {
				return pieceAndRotationalAngle[i].getPieceAngle();
			}
		}
		return 0.0f;
		
	}
	
	public Point2D.Float getCenterGivenIndex(int index, PieceAndAngleDatatype[] pieceAndRotationalAngle) {
		for(int i = 0; i < pieceAndRotationalAngle.length;i++) {
			if(pieceAndRotationalAngle[i].getPieceIndex() == index) {
				return pieceAndRotationalAngle[i].getCenter();
			}
		}
		return new Point2D.Float(0.0f,0.0f);
		
	}

}