package controller;

//why dis chit not vork
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.Arrays;
import java.util.ArrayList;
import controller.PieceCompare;

import model.Piece;

//hello
public class PuzzleSolver {
	public PuzzleSolver() {

	}

	public void puzzleSolver(Object[] pieceList) {

		PieceCompare pC = new PieceCompare();
		Object[] AncleLengthPieceList = new Object[pieceList.length];

		for (int i = 0; i < pieceList.length; i++) {

			Point2D.Float[] piece = (Point2D.Float[]) pieceList[i];
			AncleLength[] angleLength = pC.createAncleLengthArray(piece);
			AncleLengthPieceList[i] = angleLength;
			System.out.println("aL " + i + "      " + Arrays.toString(angleLength));
		}
		Object[] groups = groupPieces(pieceList);

		Object[] adjacencyAndMatchingArray = produceAngleLengthPairs(AncleLengthPieceList,
				(ArrayList<Integer>) groups[0], (ArrayList<Integer>) groups[1], (ArrayList<Integer>) groups[2],
				(int) groups[3], (int) groups[4], (int) groups[5]);
		Object[] adjacencyArray = (Object[]) adjacencyAndMatchingArray[0];
		Object[] matchingArray = (Object[]) adjacencyAndMatchingArray[1];

		for (int i = 0; i < adjacencyArray.length; i++) {
			int[] alreadyCovered = new int[matchingArray.length];
			int[] adjacencyPair = (int[]) adjacencyArray[i];
			for(int j = 1; j < adjacencyPair.length;j++) {
				alreadyCovered[i] = adjacencyPair[0];
				
			}
			// code
		}

		Point2D.Float[][] threeMatchingPoints = getPointsOfTwoSuitablePieces((int[]) matchingArray[0], pieceList);
		System.out.println(Arrays.deepToString(threeMatchingPoints));

		System.out.println(pC.getAngleFromThreePoints(threeMatchingPoints[0][0], threeMatchingPoints[0][1],
				threeMatchingPoints[0][2]));
		System.out.println("equals? ");
		System.out.println(pC.getAngleFromThreePoints(threeMatchingPoints[1][0], threeMatchingPoints[1][1],
				threeMatchingPoints[1][2]));

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
		Object[] matchingArray = new Object[2 * gr1.size() + 3 * gr2.size() + 4 * gr3.size()];
		int k = 0;
		for (int i = 0; i < gr1.size(); i++) {
			ArrayList<Integer> toAdd = new ArrayList<Integer>();
			toAdd.add(gr1.get(i));
			if (gr2.isEmpty()) {
				System.out.println(" is in first case");
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
				System.out.println(toAdd.toString());

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
				System.out.println(toAdd.toString());

				adjacencyArray[i] = toAdd;
			} else {
				System.out.println("is in last case");
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
				System.out.println(toAdd.toString());

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
				System.out.println(toAdd.toString());

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
				System.out.println(toAdd.toString());

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
					}else {
						toAdd.add(gr2.get(j));
					}
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
			System.out.println(toAdd.toString());

			adjacencyArray[i + gr1.size() + gr2.size()] = toAdd;

		}
		System.out.println("adjacency : " + Arrays.toString(adjacencyArray));
		System.out.println("matchingarray : " + Arrays.deepToString(matchingArray));
		return new Object[] { adjacencyArray, matchingArray };

	}

	private boolean containsPair(Object[] matchingArray, int integer, int integer2) {
		
		for (int i = 0; i < matchingArray.length; i++) {
			
			if(matchingArray[i] == (null)) {
				System.out.println("hello");
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

}