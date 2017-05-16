package Objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Engine.TickThread;
import UserInterface.WindowFrame;
import UserInterface.WindowPanel;

public class Boss {
	private static int xPos;
	private static int yPos;
	public double xUpdate, yUpdate;

	public static int cooldown = 1, hitTimer = 0, frostTimer = 0, poisonTimer = 0;
	public static int health, maxHealth, moveSpeed, baseDamage, gcd;
	public static boolean skill, seen = false, dead = false, frozen = false, poisoned = false;
	public static int direction, RIGHT = 1, LEFT = 0;

	public static Projectile[] projectiles = new Projectile[30];

	public static int type, DRAGON_MATRIARCH = 1, MINOTAUR = 2, VERWIRREN = 3, DYCLOPSE = 4, DUNGEON_DUDE = 5;

	public Boss(int type) {
		Boss.type = type;
		switch (type) {
		case 1:
			setDragonMatriarch();
			break;
		case 2:
			setMinotaur();
			break;
		case 3:
			setVerwirren();
			break;
		case 4:
			setDyclops();
			break;
		case 5:
			setDungeonDude();
			break;
		default:
			System.out.println("Unkown boss type!");
			break;
		}

		for (int i = 0; i < projectiles.length; i++) {
			projectiles[i] = new Projectile();
		}
	}

	public void update() {
		if (health < 1) {
			if (!dead) {
				boolean spawned = false;
				int i = 0;
				while (spawned == false) {
					if (i >= WindowFrame.items.length) {
						break;
					}
					if (WindowFrame.items[i].exists) {
						i++;
					} else {
						WindowFrame.items[i].type = 1;
						WindowFrame.items[i].spawn(xPos, yPos);
						spawned = true;
					}
				}
				dead = true;
			}
			return;
		}
		if (poisoned) {
			poisonTimer++;
			health -= 4;
			if (poisonTimer > 180) {
				poisoned = false;
				poisonTimer = 0;
			}
		}
		if (frozen) {
			frostTimer++;
			if (frostTimer > 180) {
				frozen = false;
				frostTimer = 0;
			}
			return;
		}
		if (Math.abs(xPos - Person.getXPos()) < 650 && Math.abs(yPos - Person.getYPos()) < 475) {
			seen = true;
		}
		if (seen) {
			// Special skills for bosses
			if (skill()) {
				return;
			} else {
				double angle = Math.atan2(yPos - Person.getYPos(), xPos - Person.getXPos());
				xUpdate = -(5 * Math.cos(angle));
				yUpdate = -(5 * Math.sin(angle));
			}

			if (Math.abs(xPos + 48 - Person.getXPos()) > 10) {
				if (xPos + 48 - Person.getXPos() > 0) {
					move(-moveSpeed, 0);
					direction = RIGHT;
				} else {
					move(moveSpeed, 0);
					direction = LEFT;
				}
			}
			if (Math.abs(yPos + 48 - Person.getYPos()) > 10) {
				if (yPos + 48 - Person.getYPos() > 0) {
					move(0, -moveSpeed);
				} else {
					move(0, moveSpeed);
				}
			}
		}
	}

	private boolean move(int xa, int ya) {
		boolean xmoving = true;
		boolean ymoving = true;
		for (int i = 0; i < 9; i++) {
			int xt = ((xPos + xa + 48) + i % 3 * 48 - 48) / 64;
			int y = ((yPos + 48) + i / 3 * 48 - 48) / 64;
			if (WindowFrame.map[xt][y].tile == Tile.WALL) {
				xmoving = false;
				return false;
			}
			int x = ((xPos + 48) + i % 3 * 48 - 48) / 64;
			int yt = ((yPos + ya + 48) + i / 3 * 48 - 48) / 64;
			if (WindowFrame.map[x][yt].tile == Tile.WALL) {
				ymoving = false;
				return false;
			}
		}
		if (xmoving) {
			setPos(xPos + xa, yPos);
		}
		if (ymoving) {
			setPos(xPos, yPos + ya);
		}
		return true;
	}

	public static void hit(int damage) {
		health -= damage;
	}

	public void spawn(int x, int y) {
		cooldown = 1;
		skill = false;
		seen = false;
		dead = false;
		setPos(x, y);
		health = maxHealth;
	}

	public void setMinotaur() {
		maxHealth = 35000;
		moveSpeed = 1;
		baseDamage = 250;
		gcd = 900;
	}

	public void setDragonMatriarch() {
		maxHealth = 30000;
		moveSpeed = 0;
		baseDamage = 60;
		gcd = 180;
	}

	public void setVerwirren() {
		maxHealth = 35000;
		moveSpeed = 2;
		baseDamage = 5;
		gcd = 180;
	}

	public void setDyclops() {
		maxHealth = 35000;
		moveSpeed = 1;
		baseDamage = 300;
		gcd = 300;
	}

	public void setDungeonDude() {
		health = 100000;
		maxHealth = 100000;
		moveSpeed = 2;
		baseDamage = 100;
		gcd = 60;
	}

	public boolean skill() {
		// System.out.println(cooldown);
		if (cooldown < 1) {
			skill = true;
			cooldown = gcd;
		}
		if (type == DRAGON_MATRIARCH && skill) {
			dragonSkill();
		}
		if (type == MINOTAUR && skill) {
			minotaurSkill();
			return true;
		}
		if (type == VERWIRREN && skill) {
			virwirrenSkill();
		}
		if (type == DYCLOPSE && skill) {
			dyclopseSkill();
			return true;
		}
		if (type == DUNGEON_DUDE && skill) {
			switch (health / 33333) {
			case 0:
				gcd = 300;
				dyclopseSkill();
				return true;
			case 1:
				gcd = 180;
				virwirrenSkill();
				break;
			case 2:
				gcd = 180;
				dragonSkill();
				break;
			case 3:
				gcd = 180;
				dragonSkill();
				break;
			default:
				System.out.println("Boss health is too high/low");
			}
		}
		cooldown--;
		return false;
	}

	public void dyclopseSkill() {
		if (cooldown % 20 == 0 && cooldown > gcd - 60) {
			for (int i = 0; i < 14; i++) {
				boolean shot = false;
				int j = 0;
				while (shot == false) {
					if (j >= projectiles.length) {
						break;
					}
					if (projectiles[j].exists) {
						j++;
					} else {
						for (int k = 0; k < 4; k++) {
							projectiles[j].img[k] = WindowPanel.Floor[3][0];
						}
						double angle = Math.atan2(yPos + 48 - Person.getYPos(), xPos + 48 - Person.getXPos());
						projectiles[j].TTL = 30;
						projectiles[j].fire(i * (Math.PI / 7) + angle, 6, xPos + 48, yPos + 48);
						shot = true;
					}
				}
			}
		}
		if (cooldown == gcd - 60) {
			skill = false;
		}
		cooldown--;
	}

	public void virwirrenSkill() {
		if (cooldown == gcd) {
			for (int i = 0; i < 8; i++) {
				boolean shot = false;
				int j = 0;
				while (shot == false) {
					if (j >= projectiles.length) {
						break;
					}
					if (projectiles[j].exists) {
						j++;
					} else {
						for (int k = 0; k < 4; k++) {
							projectiles[j].img[k] = WindowPanel.blobBall;
						}
						double angle = Math.atan2(yPos + 48 - Person.getYPos(), xPos + 48 - Person.getXPos());
						projectiles[j].fire(i * (Math.PI / 4) + angle, 6, xPos + 48, yPos + 48);
						shot = true;
					}
				}
			}
		}
	}

	// Dragon breath/ whelp spawn
	private void dragonSkill() {
		if (cooldown % 6 == 0 && cooldown < 45) {
			boolean shot = false;
			int i = 0;
			while (shot == false) {
				if (i >= projectiles.length) {
					break;
				}
				if (projectiles[i].exists) {
					i++;
				} else {
					System.arraycopy(WindowPanel.Fireball, 0, projectiles[i].img, 0, 4);
					double angle = Math.atan2(yPos + 48 - Person.getYPos(), xPos + 96 - Person.getXPos());
					projectiles[i].fire(Math.PI + angle, 10, xPos + 96, yPos + 48);
					shot = true;
				}
			}

		}
		if (cooldown == gcd) {
			boolean spawned = false;
			int i = 0;
			while (spawned == false) {
				if (i >= WindowFrame.enemies.length) {
					break;
				}
				if (WindowFrame.enemies[i].health > 0) {
					i++;
				} else {
					WindowFrame.enemies[i].spawn(xPos, yPos);
					spawned = true;
				}
			}
		}
	}

	// Minotaur charge
	private void minotaurSkill() {
		if (!move((int) xUpdate, 0)) {
			skill = false;
			cooldown = gcd;
		}
		if (!move(0, (int) yUpdate)) {
			skill = false;
			cooldown = gcd;
		}
		cooldown--;
	}

	public void setPos(int x, int y) {
		xPos = x;
		yPos = y;
	}

	public static int getX() {
		return xPos;
	}

	public static int getY() {
		return yPos;
	}

	public static BufferedImage getImg() {
		switch (type) {
		case 1:
			if (cooldown < 45) {
				return WindowPanel.DragonMatriarch[1 + (direction * 2)];
			} else {
				return WindowPanel.DragonMatriarch[0 + (direction * 2)];
			}
		case 2:
			if (skill) {
				return WindowPanel.Minotaur[4 + (direction * 2) + (TickThread.animation / 10 % 2)];
			} else {
				return WindowPanel.Minotaur[0 + (direction * 2) + (TickThread.animation / 10 % 2)];
			}
		case 3:
			return WindowPanel.Verwirren[TickThread.animation / 7 % 8];
		case 4:
			if (cooldown > gcd - 60) {
				return WindowPanel.Dyclopse[4 + (direction * 2) + (TickThread.animation / 10 % 2)];
			} else {
				return WindowPanel.Dyclopse[0 + (direction * 2) + (TickThread.animation / 10 % 2)];
			}
		case 5:
			return WindowPanel.DungeonDude;
		default:
			System.out.println("Unknown boss type!");
			return null;
		}
	}

	public static Rectangle getBounds() {
		if (health > 0) {
			return new Rectangle(xPos, yPos, 96, 96);
		} else {
			return new Rectangle(0, 0, 0, 0);
		}
	}
}
