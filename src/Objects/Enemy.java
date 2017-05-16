package Objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Engine.TickThread;
import UserInterface.WindowFrame;
import UserInterface.WindowPanel;

public class Enemy {
	int xPos, yPos;
	public int xUpdate, yUpdate;
	public int img = 0;
	public int direction = 0, LEFT = 0, RIGHT = 1;
	
	public int hitTimer = 0, frostTimer = 0, poisonTimer = 0;

	public int moveSpeed, baseDamage, health, maxHealth;

	public boolean seen = false, dead = false, frozen = false, poisoned = false;

	public Enemy(int type) {
		switch (type) {
		case 1:
			setDragon();
			break;
		case 2:
			setSkeleton();
			break;
		case 3:
			setMorphlings();
			break;
		case 4:
			setCyclops();
			break;
		default:
			System.out.println("Unknown enemy type!");
			break;
		}
	}

	public void spawn(int x, int y) {
		setPos(x, y);
		health = maxHealth;
		dead = false;
	}

	public void setSkeleton() {
		moveSpeed = 2;
		baseDamage = 30;
		maxHealth = 600;
		img = 1;
	}

	public void setDragon() {
		maxHealth = 200;
		moveSpeed = 3;
		baseDamage = 4;
		img = 2;
	}

	public void setMorphlings() {
		maxHealth = Person.maxHealth;
		moveSpeed = Person.moveSpeed - 2;
		baseDamage = Person.baseDamage / 4;
		img = 3;
	}

	public void setCyclops() {
		maxHealth = 4000;
		moveSpeed = 1;
		baseDamage = 300;
		img = 4;
	}

	public BufferedImage getImg() {
		switch (img) {
		case 1:
			return WindowPanel.Skeleton[TickThread.animation/5%4 + (direction*4)];
		case 2:
			return WindowPanel.Dragon[TickThread.animation/5%4 + (direction*4)];
		case 3:
			return WindowPanel.Morphling[TickThread.animation/5%4 + (direction*4)];
		case 4:
			return WindowPanel.Cyclops[TickThread.animation/10%4 + (direction*4)];
		default:
			System.out.println("Unknown enemy image!");
			return null;
		}
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}

	public void hit(int damage) {
		health -= damage;
	}

	public void update() {
		if (health < 1) {
			// Health pot spawning
			if (!dead) {
				Random random = new Random();
				int r = random.nextInt((100) + 1);
				if (r < 15) {
					boolean spawned = false;
					int i = 0;
					while (spawned == false) {
						if (i >= WindowFrame.items.length) {
							break;
						}
						if (WindowFrame.items[i].exists) {
							i++;
						} else {
							WindowFrame.items[i].spawn(xPos, yPos);
							spawned = true;
						}
					}
				}
				dead = true;
			}
			return;
		}
		if(poisoned){
			poisonTimer++;
			health-=4;
			if(poisonTimer > 180){
				poisoned = false;
				poisonTimer = 0;
			}
		}
		if(frozen){
			frostTimer++;
			if(frostTimer > 180){
				frostTimer = 0;
				frozen = false;
			}
			return;
		}
		if (Math.abs(xPos - Person.getXPos()) < 650 && Math.abs(yPos - Person.getYPos()) < 475) {
			seen = true;
		}
		if (seen) {
			//Movement
			if (Math.abs(xPos + 24 - Person.getXPos()) > 10) {
				if (xPos + 24 - Person.getXPos() > 0) {
					move(-moveSpeed, 0);
					direction = LEFT;
				} else {
					move(moveSpeed, 0);
					direction = RIGHT;
				}
			}
			if (Math.abs(yPos + 24 - Person.getYPos()) > 10) {
				if (yPos + 24 - Person.getYPos() > 0) {
					move(0, -moveSpeed);
				} else {
					move(0, moveSpeed);
				}
			}
		}
	}

	private void move(int xa, int ya) {
		boolean xmoving = true;
		boolean ymoving = true;
		for (int i = 0; i < 4; i++) {
			int xt = ((xPos + xa + 24) + i % 2 * 48 - 24) / 64;
			int y = ((yPos + 24) + i / 2 * 48 - 24) / 64;
			if (WindowFrame.map[xt][y].tile == Tile.WALL) {
				xmoving = false;
			}
			int x = ((xPos + 24) + i % 2 * 48 - 24) / 64;
			int yt = ((yPos + ya + 24) + i / 2 * 48 - 24) / 64;
			if (WindowFrame.map[x][yt].tile == Tile.WALL) {
				ymoving = false;
			}
		}
		if (xmoving) {
			setPos(xPos + xa, yPos);
		}
		if (ymoving) {
			setPos(xPos, yPos + ya);
		}
	}

	public Rectangle getBounds() {
		if (health > 0) {
			return new Rectangle(this.xPos, this.yPos, 48, 48);
		} else {
			return new Rectangle(0, 0, 0, 0);
		}
	}
}
