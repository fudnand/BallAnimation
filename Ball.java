import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

public class Ball {
	private int x, y, size, trajectory;
	public final int moveBY = 5;
	private boolean hasCollided;
	String side;
	
	public Ball(String side, int width, int height){
		y = (int) (Math.random() * height);
		size = 25;
		hasCollided = false;	
		this.side = side;
		
		if(side.equalsIgnoreCase("left")){
			trajectory = 1;
			x= 1;
		}
		else{
			trajectory = -1;
			x= width;
		}
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public void setPosition(int xPos, int yPos){
		x = xPos;
		y = yPos;
	}
		
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(x, y, size, size);
		
	}  
	
	public void Moveforward(){
		x += moveBY * trajectory;
	}
		
	public void setHasCollided(Boolean hasCollided){
		this.hasCollided = hasCollided;
	}	

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Ball checkBall){
		if(x != checkBall.getX()) return false;
		if(y != checkBall.getY()) return false;
		return true;		
	}
	
	public boolean collision(int x, int y){
		if(Math.abs(getX() - x)< size && Math.abs(getY() - y) < size)
			return true;
		return false;
	}
	
	public boolean collisionWithEdge(int width){
		if(x+1> width || x-1< 0)
			return true;
		return false;
	}
}

