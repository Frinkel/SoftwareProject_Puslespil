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
			AncleLength[] angleLength = pC.createAncleLengthArray(piece);
			AncleLengthPieceList[i] = angleLength;
//			System.out.println("aL " + i + "      " + Arrays.toString(angleLength));
		}
		Object[] groups = groupPieces(pieceList);

		Object[] adjacencyAndMatchingArray = produceAngleLengthPairs(AncleLengthPieceList,
				(ArrayList<Integer>) groups[0], (ArrayList<Integer>) groups[1], (ArrayList<Integer>) groups[2],
				(int) groups[3], (int) groups[4], (int) groups[5]);
		Object[] adjacencyArray = (Object[]) adjacencyAndMatchingArray[0];
		Object[] matchingArray = (Object[]) adjacencyAndMatchingArray[1];
		
		
		
		int[] finalPath = calculatePath(pieceList.length, adjacencyArray, matchingArray, (ArrayList<Integer>) groups[0], (ArrayList<Integer>) groups[1], (ArrayList<Integer>) groups[2],
				(int) groups[3], (int) groups[4], (int) groups[5]);
		
//		System.out.println(Arrays.toString(finalPath));
		PieceAndAngleDatatype[] pieceAndRotationalAngle = rotatePiecesOnPath(finalPath, matchingArray, pieceList, pC);
//		System.out.println(Arrays.toString(pieceAndRotationalAngle));
		
//		Point2D.Float[][] threeMatchingPoints = getPointsOfTwoSuitablePieces((int[]) matchingArray[0], pieceList);
//		System.out.println(Arrays.deepToString(threeMatchingPoints));

//		System.out.println(pC.getAngleFromThreePoints(threeMatchingPoints[0][0], threeMatchingPoints[0][1],
//				threeMatchingPoints[0][2]));

//		float rotationalAngle = findRotationOfMatchingPair(threeMatchingPoints, pC);
//		System.out.println("rotationalAngle : " + rotationalAngle);
//		System.out.println(pC.getAngleFromThreePoints(threeMatchingPoints[0][0], threeMatchingPoints[0][1], threeMatchingPoints[0][2]));

//		System.out.println("equals? ");

//		System.out.println(pC.getAngleFromThreePoints(threeMatchingPoints[1][0], threeMatchingPoints[1][1],
//				threeMatchingPoints[1][2]));


//		System.out.println(pC.getAngleFromThreePoints(threeMatchingPoints[1][0], threeMatchingPoints[1][1], threeMatchingPoints[1][2]));
		return pieceAndRotationalAngle;
		
	}

	private PieceAndAngleDatatype[] rotatePiecesOnPath(int[] finalPath, Object[] matchingArray, Object[] pieceList, PieceCompare pC) {
		// TODO Auto-generated method stub
		PieceAndAngleDatatype[] pieceAndRotationalAngle = new PieceAndAngleDatatype[finalPath.length];
		pieceAndRotationalAngle[0] = new PieceAndAngleDatatype(finalPath[0], 0.0f, new Point2D.Float(500.0f,500.0f));
		
		for(int i = 0; i < finalPath.length-1; i++) {
			
			int[] pair = getPair(finalPath[i], finalPath[i+1], matchingArray);
			Point2D.Float[][] threeMatchingPoints = getPointsOfTwoSuitablePieces((int[]) pair, pieceList);
			float angle = findRotationOfMatchingPair(threeMatchingPoints, pC);
			if(pair[0] == finalPath[i]) {
				Point2D.Float center = getPosOfCenter(threeMatchingPoints,angle+(pieceAndRotationalAngle[i].getPieceAngle()), (pieceAndRotationalAngle[i].getPieceAngle()), (pieceAndRotationalAngle[i].getCenter()),1);
				pieceAndRotationalAngle[i+1] = new PieceAndAngleDatatype(finalPath[i+1], angle+(pieceAndRotationalAngle[i].getPieceAngle()),center);
			}else {
				Point2D.Float center = getPosOfCenter(threeMatchingPoints,((-angle)+(pieceAndRotationalAngle[i].getPieceAngle())), (pieceAndRotationalAngle[i].getPieceAngle()), (pieceAndRotationalAngle[i].getCenter()),-1);
				pieceAndRotationalAngle[i+1] = new PieceAndAngleDatatype(finalPath[i+1], -angle+(pieceAndRotationalAngle[i].getPieceAngle()),center);
			}
			
			//System.out.println("angle : " + angle);
//			System.out.println(Arrays.toString(pair));
			
			
		}
		return pieceAndRotationalAngle;
	}

	private Point2D.Float getPosOfCenter(Point2D.Float[][] threeMatchingPoints, float angle, float prevAngle, Point2D.Float prevCenter,int direction) {
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
		ArrayList<Integer> alreadyCovered = new ArrayList<Integer>();
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
		
		alreadyCovered.add(startingIndex);
		int[] finalPath = getPath(startingIndex, adjacencyArray, alreadyCovered, length);
		return finalPath;
	}

	private int[] getPath(int startingIndex, Object[] adjacencyArray, ArrayList<Integer> alreadyCovered, int length) {
		ArrayList<Integer> adjacencyPair = null;
		
		for(int i = 0; i < adjacencyArray.length; i++) {
			ArrayList<Integer> pair = ((ArrayList<Integer>) adjacencyArray[i]);
			if(pair.get(0) == startingIndex) {
				adjacencyPair = pair;
//				System.out.println("startingIndex " + startingIndex);
				if(startingIndex == 74) {
//					System.out.println("WERE IN 74 " + adjacencyPair.toString());
				}
			}
		}
		for(int i = 1; i < adjacencyPair.size(); i++) {
			if(!alreadyCovered.contains(adjacencyPair.get(i))) {
				alreadyCovered.add(adjacencyPair.get(i));
//				System.out.println("adjacencyPair i : " + adjacencyPair.get(i));
				
				int[] array1 = getPath(adjacencyPair.get(i), adjacencyArray, alreadyCovered, length);
				if(array1.length == length) {
					return array1;
				}
			}
			
		}
		int[] newArray = new int[alreadyCovered.size()];
		for(int i = 0; i < alreadyCovered.size(); i++) {
			newArray[i] = alreadyCovered.get(i);
		}
		return newArray;
		
		
		
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

		System.out.println("gr1 L :" + gr1Length + " members" + gr1.toString());
		System.out.println("gr2 L :" + gr2Length + " members" + gr2.toString());
		System.out.println("gr3 L :" + gr3Length + " members" + gr3.toString());

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
							int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr1.get(i)],
									(AncleLength[]) AncleLengthPieceList[gr1.get(j)], gr1.get(i), gr1.get(j));
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
							int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr1.get(i)],
									(AncleLength[]) AncleLengthPieceList[gr1.get(j)], gr1.get(i), gr1.get(j));
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
						int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr1.get(i)],
								(AncleLength[]) AncleLengthPieceList[gr2.get(j)], gr1.get(i), gr2.get(j));
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
						int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr1.get(i)],
								(AncleLength[]) AncleLengthPieceList[gr2.get(j)], gr1.get(i), gr2.get(j));
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
						int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr2.get(i)],
								(AncleLength[]) AncleLengthPieceList[gr1.get(j)], gr2.get(i), gr1.get(j));
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
							int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr2.get(i)],
									(AncleLength[]) AncleLengthPieceList[gr2.get(j)], gr2.get(i), gr2.get(j));
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
						int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr2.get(i)],
								(AncleLength[]) AncleLengthPieceList[gr1.get(j)], gr2.get(i), gr1.get(j));
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
							int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr2.get(i)],
									(AncleLength[]) AncleLengthPieceList[gr2.get(j)], gr2.get(i), gr2.get(j));
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
						int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr2.get(i)],
								(AncleLength[]) AncleLengthPieceList[gr3.get(j)], gr2.get(i), gr3.get(j));
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
					int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr3.get(i)],
							(AncleLength[]) AncleLengthPieceList[gr2.get(j)], gr3.get(i), gr2.get(j));
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
						int[] toAddMatching = checkTwoPieces((AncleLength[]) AncleLengthPieceList[gr3.get(i)],
								(AncleLength[]) AncleLengthPieceList[gr3.get(j)], gr3.get(i), gr3.get(j));
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

	private int[] checkTwoPieces(AncleLength[] angleLength1, AncleLength[] angleLength2, int index0, int index1) {
		// TODO Auto-generated method stub
		ArrayList<Integer> adjacencyPieces = new ArrayList<Integer>();
		for (int i = 0; i < angleLength1.length; i++) {
			for (int j = 0; j < angleLength2.length; j++) {
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
			Point2D.Float[] piece = (Float[]) pieceList[matchingPoints[i]];
			for (int j = 0; j < 3; j++) {
				threeMatchingPoints[i][j] = piece[(matchingPoints[i + 2] + j) % (piece.length)];
			}
		}
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