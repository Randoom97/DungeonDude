package Objects;

import java.awt.Rectangle;

public class Item {
	private int xPos, yPos;
	public int type = 0, POTION = 0, GEM = 1;
	
	public boolean exists = false;
	
	public void spawn(int x, int y){
		xPos = x;
		yPos = y;
		exists = true;
	}
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public Rectangle getBounds(){
		if(exists){
			return new Rectangle(xPos,yPos,32,32);
		}else{
			return new Rectangle(0,0,0,0);
		}
	}
}
