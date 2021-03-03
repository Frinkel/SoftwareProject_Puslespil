
ArrayList<Piece> pieceList;

boolean pieceLocked = false;
Piece helperP;
float xOffset;
float yOffset;

int c;

void setup(){
  size(800,800);
  background(255);
  c = 500/2;
  
  pieceList = new ArrayList<Piece>();
  
  //p1.setVertex(1, 10, 10);
  
  
  // Create a piece
  // Vertices for the piece (x,y) pairs
  float[] v1 = {-100, -100, 100, -100, 100, 100, -100, 100, -50, 0, -100, -100};
  Piece p1 = new Piece(this, 250, 250, v1);
  
  float[] v2 = {-100, -100, 100, -100, 150, 0, 100, 100, -100, 100, -100, -100};
  Piece p2 = new Piece(this, 500, 500, v2);
  
  float[] v3 = {0, 0, 100, 0, 100, 100, 0, 100, 0,0};
  Piece p3 = new Piece(this, 250, 500, v3);
  
  pieceList.add(p1);
  //pieceList.add(p2);
  //pieceList.add(p3);
  
}

void draw(){
  background(255);
  stroke(0);
  //ellipse(mouseX, mouseY, 100, 100);
  
  stroke(255,0,0);
  fill(0);

  for(Piece p : pieceList){
    p.display();
  }
}

// Might wanna create class bound onClick, onDragged and onReleased functions!
void mousePressed() {
  for(Piece p : pieceList){
    if(p.overPiece && helperP == null){
      //println("over");
      
      helperP = p;
      
      helperP.pieceLocked = true;
      xOffset = mouseX-p.center_x;
      yOffset = mouseY-p.center_y;
      helperP.shape.setFill(color(255,0,0));
      //helperP.shape.rotate(0.1);
    }
  }
}

void mouseDragged() {
  //println("drag");
  if(helperP != null) {
    helperP.center_x = mouseX - xOffset;
    helperP.center_y = mouseY - yOffset;
    helperP.init();
    helperP.shape.setFill(color(255,0,0));
  }
}

void mouseReleased() {
  if(helperP != null) {
    helperP.pieceLocked = false;
    helperP = null;
  }
}

void mouseWheel(MouseEvent event) {
  // Restrain zoom
  //println(event.getCount());
  if(helperP != null) {
    //helperP.rotate(event.getCount());
  }
}
