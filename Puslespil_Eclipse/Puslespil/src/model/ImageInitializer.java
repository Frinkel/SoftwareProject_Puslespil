package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageInitializer {
	int boardSize;
	int pieceAmount;
	int columns;
	int rows;
	int width, height;
	PApplet pA;
	ArrayList<PImage> originalImages = new ArrayList<PImage>();
	ArrayList<PImage> sprites = new ArrayList<PImage>();
	
	public ImageInitializer(PApplet _pA, int _boardSize, int _pieceAmount, int _columns, int _rows){
		// Get a reference to the main PApplet class
		this.pA = _pA;
		this.boardSize = _boardSize;
		this.pieceAmount = _pieceAmount;
		this.columns = _columns;
		this.rows = _rows;
		width = boardSize / columns;
		height = boardSize / rows;
	}
	
	public ArrayList<PImage> imageLoader(String[] paths) {
		// Get the images from the paths
		for(String s : paths) {
			PImage img = pA.loadImage(s);
			if(img.height != img.width) {
				PImage cutImg = img.get(img.height/2, 0, img.height, img.height);
				cutImg.resize(boardSize, boardSize);
				originalImages.add(cutImg);
			} else {
				img.resize(boardSize, boardSize);
				originalImages.add(img);
			}
			
			
		}
		return originalImages;
	}
	
	public ArrayList<PImage> imageSplitter(PImage img, int num) {
		
		for(int i = 0; i < rows; i++) {
			System.out.println("new");
			for(int j = 0; j < columns; j++) {
				System.out.print((j*height) +" " + (i*width) + ", ");
				PImage nimg = img.get(j*width, i*height, width, height);
				sprites.add(nimg);
			}
		}
		return sprites;
	}
}
