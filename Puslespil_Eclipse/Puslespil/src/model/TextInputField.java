package model;



import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import view.View;

public class TextInputField {
	String text = "Enter Path to Puzzle here!";
	
	View view;
	
	int width = 200;
	int height = 20;
	int x, boxX;
	int y, boxY;
	
	PFont font;
	
	boolean inputFieldSelected = false;
	
	public TextInputField(View pA, int _x, int _y) {
		this.view = pA;
		this.x = _x - width/2;
		this.y = _y - height;
		this.boxX = _x - width/2;
		this.boxY = _y - height;
		
		font = view.createFont("Arial", 14);
	}
	
	public void draw() {
		//System.out.println(text);
		
		if(inputFieldSelected) {
			view.fill(255);
		} else {
			view.fill(150);
		}
		view.rect(x, y, width, height);
		view.fill(0);
		view.textAlign(PConstants.LEFT);
		view.textFont(font,14);
		view.text(text, x, y + 15);
		
		
		
		//System.out.println(Toolkit.getDefaultToolkit().getSystemClipboard().toString());
	}
	
	public void addChar(char key) {
		
		if(inputFieldSelected) {
			// Add a key to the text
			if(key >= 32 && key <= 125) {
				text = text + key;
			}
			
			// Delete the last char from the string
			if(key == 8 && text != "") {
				text = text.substring(0, text.length()-1);
			}
			
			// Update the width
			if(view.textWidth(text) >= 200) {
				width = (int) view.textWidth(text);
			} else {
				width = 200;
			}
		}
	}
	
	// Control pasting of content into the text
	public void pasteContents() {
		if(inputFieldSelected) {
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			String paste = "";
	        try {
				paste = c.getContents(null).getTransferData(DataFlavor.stringFlavor).toString();
				text = paste;
	        } catch (UnsupportedFlavorException | IOException e) {}
	        
	        System.out.println(paste);
		}
	}
	
	// Check if the mouse is over the text input field
	public boolean mouseIsOver() {
		if(view.mouseX > x && view.mouseX < (x + width) && view.mouseY > y && view.mouseY < (y + height)) {
			return true;
		}
		return false;
	}
	
	public void selectInputField() {
		if(mouseIsOver()) {
			inputFieldSelected = true;
			view.writing = true;
			if(text.equals("Enter Path to Puzzle here!")) {
				text = "";
			}
		} else {
			inputFieldSelected = false;
			view.writing = false;
			if(text.equals("")) {
				text = "Enter Path to Puzzle here!";
			}
		}
	}
	
	public void updatePos(int _width, int _height) {
		if(_width == view.displayWidth) {
			this.x = _width - width;
			this.y = _height + height;
		} else {
			this.x = this.boxX;
			this.y = this.boxY;
		}
	}
	
	public String getValue() {
			return text;
	}
	
	public void setValue(String s) {
		text = s;
	}
	
	public boolean selected() {
		return inputFieldSelected;
	}
	
	public void toggleSelected() {
		inputFieldSelected = !inputFieldSelected;
		if(text.equals("")) {
			text = "Enter Path to Puzzle here!";
		}
	}
}

