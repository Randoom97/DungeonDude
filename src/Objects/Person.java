package Objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import UserInterface.WindowFrame;
import UserInterface.WindowPanel;
import Classes.Input;
import Classes.Main;

public class Person {
	static int xPos = 0;

	static int yPos = 0;

	public static BufferedImage img[] = new BufferedImage[5];

	public static int type = 0;

	public static int moveSpeed;

	public static int maxHealth;

	public static int baseDamage;

	public static int health;

	public static int gcd;
	public static int cooldownTimer = 0;
	public static int hPots = 0;

	public static boolean reversed;

	public static boolean moving;
	private static int rTime = 0;

	public static boolean[] skills = new boolean[3];
	public static int[] ct = new int[3];

	public static Projectile[] projectiles = new Projectile[100];

	public Person() {
		for (int i = 0; i < projectiles.length; i++) {
			projectiles[i] = new Projectile();
		}
	}

	public static void update() {
		if (health == 0 && !Main.frame.win) {
			Main.frame.win = true;
			Main.frame.add(Main.frame.mButton);
			Input.up = false;
			Input.left = false;
			Input.down = false;
			Input.right = false;
		}
		//moving for animation
		if(Input.up || Input.left|| Input.down|| Input.right){
			moving = true;
		}else{
			moving = false;
		}
		// skill cooldowns
		if (skills[2]) {
			if (cooldownTimer > 600) {
				skills[2] = false;
				cooldownTimer = 0;
			} else {
				cooldownTimer++;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (skills[i]) {
				ct[i] = gcd;
			} else if (ct[i] > 0) {
				ct[i]--;
			}
		}
		// Image switching
		if (Input.left) {
			switch (type) {
			case 0:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheetRed.getSubimage(48, i*48, 48, 48);
				}
				break;
			case 1:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheetGreen.getSubimage(48, i*48, 48, 48);
				}
				break;
			case 2:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheetBlue.getSubimage(48, i*48, 48, 48);
				}
				break;
			case 3:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheet.getSubimage(48, i*48, 48, 48);
				}
			}
		}
		if (Input.right) {
			switch (type) {
			case 0:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheetRed.getSubimage(0, i*48, 48, 48);
				}
				break;
			case 1:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheetGreen.getSubimage(0, i*48, 48, 48);
				}
				break;
			case 2:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheetBlue.getSubimage(0, i*48, 48, 48);
				}
				break;
			case 3:
				for(int i = 0; i< 5; i++){
					img[i] = WindowPanel.RogueSheet.getSubimage(0, i*48, 48, 48);
				}
			}
		}
		move();
	}

	private static void move() {
		// movement
		if (rTime == 300 && reversed == true) {
			reversed = false;
			rTime = 0;
		} else if (reversed == true) {
			rTime++;
		}
		int speed = 0;
		if (reversed) {
			speed = -moveSpeed;
		} else {
			speed = moveSpeed;
		}
		int xa = 0;
		int ya = 0;
		if (Input.up) {
			ya = -speed;
		}
		if (Input.left) {
			xa = -speed;
		}
		if (Input.down) {
			ya = speed;
		}
		if (Input.right) {
			xa = speed;
		}
		// Collision
		boolean xmoving = true;
		boolean ymoving = true;
		for (int i = 0; i < 4; i++) {
			int xt = (int) ((xPos + xa) + i % 2 * 48 - 24) / 64;
			int y = (int) ((yPos) + i / 2 * 48 - 24) / 64;
			if (WindowFrame.map[xt][y].tile == Tile.WALL) {
				xmoving = false;
			}
			int x = (int) ((xPos) + i % 2 * 48 - 24) / 64;
			int yt = (int) ((yPos + ya) + i / 2 * 48 - 24) / 64;
			if (WindowFrame.map[x][yt].tile == Tile.WALL) {
				ymoving = false;
			}
		}
		if (xmoving) {
			setLocation(xPos + xa, yPos);
		}
		if (ymoving) {
			setLocation(xPos, yPos + ya);
		}
	}

	public static void heal() {
		if (hPots < 1) {
			return;
		} else {
			hPots--;
			if (health + 100 > maxHealth) {
				health = maxHealth;
			} else {
				health += 100;
			}
		}
	}

	public static void hit(int damage) {
		if (health - damage < 1) {
			health = 0;
		} else {
			health -= damage;
		}
	}

	public static void attack(int x, int y) {
		boolean shot = false;
		int i = 0;
		while (shot == false) {
			if (i >= projectiles.length) {
				break;
			}
			if (projectiles[i].exists) {
				i++;
			} else {
				// Skill setting
				if (skills[0]) {
					System.arraycopy(WindowPanel.Iceball, 0, projectiles[i].img, 0, 4);
					projectiles[i].frost = true;
					skills[0] = false;
				} else if (skills[1]) {
					System.arraycopy(WindowPanel.Corrosionball, 0, projectiles[i].img, 0, 4);
					projectiles[i].poison = true;
					skills[1] = false;
				} else if (skills[2]) {
					System.arraycopy(WindowPanel.OPball, 0, projectiles[i].img, 0, 4);
				} else {
					System.arraycopy(WindowPanel.Fireball, 0, projectiles[i].img, 0, 4);
				}

				double angle = Math.atan2(y - Main.frame.getHeight() / 2, x - Main.frame.getWidth() / 2);
				projectiles[i].fire(angle, 6, (int) xPos, (int) yPos);
				shot = true;
			}
		}
	}

	public static void setMage() {
		moveSpeed = 4;
		maxHealth = 1000;
		health = 1000;
		baseDamage = 60;
		gcd = 300;
		System.arraycopy(WindowPanel.RogueRed, 0, img, 0, 5);
		type = 0;
	}

	public static void setRanger() {
		moveSpeed = 5;
		maxHealth = 550;
		health = 550;
		baseDamage = 120;
		gcd = 300;
		System.arraycopy(WindowPanel.Rogue,0,img,0,5);
		type = 3;
	}

	public static void setRogue() {
		moveSpeed = 5;
		maxHealth = 600;
		health = 600;
		baseDamage = 120;
		gcd = 300;
		// gcd = 1440;
		System.arraycopy(WindowPanel.RogueGreen, 0, img, 0, 5);
		type = 1;
	}

	public static void setWarrior() {
		moveSpeed = 3;
		maxHealth = 1850;
		health = 1850;
		baseDamage = 100;
		gcd = 300;
		System.arraycopy(WindowPanel.RogueBlue, 0, img, 0, 5);
		type = 2;
	}

	public static int getYPos() {
		return yPos;
	}

	public static int getXPos() {
		return xPos;
	}

	public static void setLocation(int xPos, int yPos) {
		Person.xPos = xPos;
		Person.yPos = yPos;
	}

	public static Rectangle getBounds() {
		return new Rectangle((int) (xPos - 24), (int) (yPos - 24), 48, 48);
	}
}
