package controller;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class PieceReader {
	double multiplier = 100;
	
	public PieceReader() {

	}

	public Object[] pieceReader() {
		JSONParser parser = new JSONParser();
		Object obj = null;
		JSONObject jsonObject = null;
		//Puzzle-1r-3c-2988-sol      Puzzle-1r-4c-7284-rot        Puzzle-2r-2c-5863-rot		Puzzle-3r-3c-2445-rot          Puzzle-4r-6c-3898-rot       Puzzle-8r-10c-1628-sol          Puzzle-5r-8c-4228-rot
		try {
			obj = parser.parse(new FileReader("assets\\Puzzle-8r-10c-1628-sol.json"));
			jsonObject = (JSONObject) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		//find the amount of pieces
		long numberOfPieces = (Long) jsonObject.get("no. of pieces");
		System.out.println("no of pieces:" + numberOfPieces);
		
		
		//generate the individual pieces
		Object[] pieces = new Object[(int) numberOfPieces];
		JSONArray piecesList = (JSONArray) jsonObject.get("pieces");
		readPieces(piecesList,pieces,(int) numberOfPieces);
		for (int i = 0; i < pieces.length; i++) {
			Point2D.Float[] pieceCorners = (Point2D.Float[]) pieces[i];	
		}
		
		return pieces;
	}
	
	public Object[] readPuzzle(String path) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		JSONObject jsonObject = null;
		//Puzzle-1r-3c-2988-sol      Puzzle-1r-4c-7284-rot        Puzzle-2r-2c-5863-rot		Puzzle-3r-3c-2445-rot          Puzzle-4r-6c-3898-rot       Puzzle-8r-10c-1628-sol          Puzzle-5r-8c-4228-rot
		try {
			obj = parser.parse(new FileReader(path));
			jsonObject = (JSONObject) obj;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		//find the amount of pieces
		long numberOfPieces = (Long) jsonObject.get("no. of pieces");
		System.out.println("no of pieces:" + numberOfPieces);
		
		
		//generate the individual pieces
		Object[] pieces = new Object[(int) numberOfPieces];
		JSONArray piecesList = (JSONArray) jsonObject.get("pieces");
		readPieces(piecesList,pieces,(int) numberOfPieces);
		for (int i = 0; i < pieces.length; i++) {
			Point2D.Float[] pieceCorners = (Point2D.Float[]) pieces[i];	
		}
		
		return pieces;
	}

	public void readPieces(JSONArray piecesList,Object[] pieces, int nop) {
		for(int i= 0;i<piecesList.size();i++) {
			JSONObject piece = (JSONObject) piecesList.get(i);
			JSONArray pieceCord = (JSONArray) piece.get("corners");
			Point2D.Float[] pieceCorners = new Point2D.Float[pieceCord.size()+1];
			double sumX = 0;
			double sumY = 0;
			
			for (int j = 0; j < pieceCord.size(); j++) {
				JSONObject corner = (JSONObject) pieceCord.get(j);
				JSONObject corner1 = (JSONObject) corner.get("coord");
				double cornerX = (double) corner1.get("x") * multiplier;
				sumX += cornerX;
				double cornerY = (double) corner1.get("y") * multiplier;
				sumY += cornerY;
				Point2D.Float point = new Point2D.Float((float) cornerX,(float) cornerY);
				pieceCorners[j] = point;
			}
			JSONObject corner = (JSONObject) pieceCord.get(0);
			JSONObject corner1 = (JSONObject) corner.get("coord");
			double cornerX = (double) corner1.get("x") * multiplier;
			double cornerY = (double) corner1.get("y") * multiplier;
			Point2D.Float point = new Point2D.Float((float) cornerX,(float) cornerY);
			pieceCorners[pieceCord.size()] = point;
			
			double avgX = (sumX/pieceCord.size());
			double avgY = (sumY/pieceCord.size());
			
			for(int k = 0; k < pieceCorners.length;k++) {
				pieceCorners[k].x -= avgX;
				pieceCorners[k].y -= avgY;
				
				
				
			}
			
			pieces[i] = pieceCorners;
		}
	}

}
