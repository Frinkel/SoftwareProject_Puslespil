package controller;

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
		groupPieces(pieceList);
		
		
	}

	public void groupPieces(Object[] pieceList) {
		ArrayList<Integer> gr1 = new ArrayList<Integer>();
		int gr1Length = 0;
		ArrayList<Integer> gr2 = new ArrayList<Integer>();
		int gr2Length = 0;
		ArrayList<Integer> gr3 = new ArrayList<Integer>();
		int gr3Length = 0;

		Point2D.Float[] piece1 = (Point2D.Float[]) pieceList[0];
		gr1Length = piece1.length;
		gr1.add(0);

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
		
		System.out.println("gr1 L :" + gr1Length + " members" + gr1.toString());
		System.out.println("gr2 L :" + gr2Length + " members" + gr2.toString());
		System.out.println("gr3 L :" + gr3Length + " members" + gr3.toString());
		
		
	}

}