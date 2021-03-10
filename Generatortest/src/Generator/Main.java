package Generator;

import java.awt.geom.Point2D;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		Generator generator = new Generator(600, 9);
		generator.placeCenters();
		
		System.out.println("size: " + generator.getBoardSize()); 
		System.out.println("amount of pieces: " + generator.getPieceAmount());
		System.out.println(generator.getPieceStorage());
		
		
		
		
	}

}
