package Objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import UserInterface.WindowFrame;
import UserInterface.WindowPanel;

public class Tile {
	int xPos, yPos;

	public int type;
	public int tile;
	public static int FLOOR = 0, WALL = 1, FLOOR_DAMAGE = 2;

	public Tile(int x, int y, int tile) {
		this.xPos = x;
		this.yPos = y;
		this.tile = tile;
	}

	// Rectangle for collision checking
	public Rectangle getBounds() {
		if (tile == 1) {
			return new Rectangle(xPos * 64, yPos * 64, 64, 64);
		} else {
			return new Rectangle(0, 0, 0, 0);
		}
	}

	public BufferedImage getImg() {
		int level = 0;
		if(WindowFrame.level == 6){
			level = 5;
		}else{
			level = WindowFrame.level;
		}
		switch (tile) {
		case 0:
			return WindowPanel.Floor[-1+level][type];
		case 1:
			return WindowPanel.Wall[-1+level][type];
		case 2:
			if(level == 5){
				return WindowPanel.Special[1][0];
			}
			return WindowPanel.Special[0][0];
		default:
			System.out.println("Unknown tile type!");
			return null;
		}
	}

}
