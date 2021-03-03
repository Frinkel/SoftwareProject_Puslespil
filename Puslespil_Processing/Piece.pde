class Piece {
  // Some variables
  float center_x;
  float center_y;
  float[] vertices;
  PShape shape;
  
  int rotationDirection = 0;
  
  boolean overPiece = false;
  boolean pieceLocked = false;
  
  float xOffset;
  float yOffset;
  
  PApplet context;
  
  // A constructor
  Piece(PApplet P5, int cx, int cy, float[] v){
    this.center_x = cx;
    this.center_y = cy;
    this.vertices = v;
    
    this.context = P5;
    
    init();
  }
  
  
  
  void init(){
    
    shape = createShape();
    shape.setStroke(color(0,0,0));
    shape.setFill(0);
    
    shape.beginShape();
    shape.strokeWeight(0);
        for (int i = 0; i < vertices.length; i+=2) {
          shape.vertex(center_x + vertices[i], center_y + vertices[i+1]);
          //println("x: " + vertices[i] + ", y: " + vertices[i+1]);
        }
    shape.endShape();
  }
  
  void updateVertices(){
    int count = 0;
    for(int i = 0; i < shape.getVertexCount(); i++){
      println(shape.getVertex(i));
      //PVector vec = shape.getVertex(i);
      shape.setVertex(i, center_x + vertices[count], center_y + vertices[count+1]);
      count += 2;
    }
  }
  
  void rotate(int dir) {
    // xangle = (c.x + cos(body.getAngle()) * move_len) - c.x; 
    // yangle = (c.y - sin(body.getAngle()) * move_len) - c.y;
    //shape = createShape();
    //shape.setStroke(color(0,0,0));
    //shape.setFill(0);
    
    // In degrees
    rotationDirection += 90 * dir;
    println(radians((center_x + cos(rotationDirection) * vertices[0])));
    
    
    shape = createShape();
    shape.beginShape();
    //shape.strokeWeight(0);
    for (int i = 0; i < vertices.length; i+=2) {
      shape.vertex(center_x + cos(radians(rotationDirection)) * vertices[i], 
                   center_y - sin(radians(rotationDirection)) * vertices[i+1]);
    } 
    shape.endShape();
  }
  
  PShape getShape() {
    return shape;
  }

  // Display the shape!
  void display() {
    // Test if the cursor is over the box 
    //println((shape.getWidth()-center_x) + " " + (shape.getHeight()-center_y));
    
    if (mouseX > center_x-(shape.getWidth()-center_x) && mouseX < center_x+(shape.getWidth()-center_x) && 
        mouseY > center_y-(shape.getHeight()-center_y) && mouseY < center_y+(shape.getHeight()-center_y)) {
      overPiece = true;  
      if(!pieceLocked) { 
         //stroke(255); 
         shape.setFill(153);
         //fill(color(255,255,255));
         //println("inside");
         xOffset = mouseX-center_x;
         yOffset = mouseY-center_y;
         fill(color(255,255,255));
      } 
    } else {
      overPiece = false;
      //println("not inside");
      fill(color(255,0,0));
      shape.setFill(0);
    }
    
    shape(shape);
    ellipse(center_x,center_y,10,10);
  }
  
  
}
