package controller;

import java.awt.geom.Point2D;
import java.util.Arrays;   
import java.util.ArrayList;

import model.Piece;

//hello
public class PieceCompare {
	public PieceCompare() {
		
		
	}
	
	public void ultimusMaximusComparitus(Object[] PieceList) {
		float[] circumferences = new float[PieceList.length];
		ArrayList<Integer> piecesSuspectedOfDuplication = new ArrayList<Integer>();
		boolean containBool = false;
		for(int i = 0; i < (PieceList.length); i++) {
			Point2D.Float[] v = (Point2D.Float[]) PieceList[i];
			containBool = (containBool || contains(circumferences,getCircumference(v)));
			if( contains(circumferences,getCircumference(v))) {
				piecesSuspectedOfDuplication.add(i);
			}
			circumferences[i] = getCircumference(v);
			//System.out.println(Arrays.toString(circumferences));
		}
		if(containBool) {
			System.out.println("Alexanders bool er true");
			piecesWithEqualCircumFerences(circumferences,piecesSuspectedOfDuplication);
		}else {
			System.out.println("Alexanders bool er false");
			
		}
		
			
		
		
		
	}
	
	
	public boolean contains(float[] circumferences, float circumference) {
		for(float x : circumferences) {
			if(x == circumference ) {
				return true;
			}
		}
		return false;
	}
	
	public float getCircumference(Point2D.Float[] points) {
		float circumference = 0.0f;
		for(int i = 1; i < (points.length -1);i++) {
			circumference += distance(points[i],points[i+1]);
		}
		//System.out.println("circumference = " + circumference);
		return circumference;
		
	}

	public float distance (Point2D.Float p1,Point2D.Float p2) {
		return Math.abs((float) Math.sqrt( (Math.pow((p2.getX() - p1.getX()),2)) + (Math.pow((p2.getY() - p1.getY()),2))));
	}
	
	public boolean threePointOnLine(Point2D.Float point1,Point2D.Float point2,Point2D.Float point3) {
		double n = 0.0;
		double m = 0.0;
		m = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());  
	    n = (point3.getY() - point2.getY()) / (point3.getX() - point2.getX());
	    if( m == n){  
	        return true;  
	    }else {  
	    	return false; 
	    } 
	}
	
	
	public ArrayList<Object> piecesWithEqualCircumFerences(float[] circumferences, ArrayList<Integer> piecesSuspectedOfDuplication){
		ArrayList<Object> equalList = new ArrayList<Object>();
		for(int i : piecesSuspectedOfDuplication){
			for(int j = 0; j < circumferences.length;j++) {
				if((j!=i)&&(circumferences[j] == circumferences[i])) {
					int[] toAdd = {i,j};
					System.out.println(Arrays.toString(toAdd));
					equalList.add(toAdd);
				}
			}
		}
		return equalList;
			
	}
	
	
}

