package controller;

import model.Piece;
import processing.core.PApplet;

public class Main extends PApplet{
	Piece p;
	
	 // The argument passed to main must match the class name
   public static void main(String[] args) {
       PApplet.main("controller.Main");
   }
   
   // method used only for setting the size of the window
   public void settings(){
   	size(400,400);
   }

   // identical use to setup in Processing IDE except for size()
   public void setup(){
       p = new Piece(this, 200, 200, 100, 100);
   }

   // identical use to draw in Processing IDE
   public void draw(){
   	p.display();
   }
}
