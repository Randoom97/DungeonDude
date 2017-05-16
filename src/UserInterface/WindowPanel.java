package UserInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Classes.Main;
import Engine.TickThread;
import Objects.Boss;
import Objects.Person;

public class WindowPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	int W = 1194;
	int H = 822;
	public int xOffset;
	public int yOffset;

	Image img;
	Graphics doubleB;

	public static BufferedImage RogueGreen[] = new BufferedImage[5];
	public static BufferedImage RogueRed[] = new BufferedImage[5];
	public static BufferedImage RogueBlue[] = new BufferedImage[5];
	public static BufferedImage Rogue[] = new BufferedImage[5];
	public static BufferedImage RogueSheetRed, RogueSheetBlue, RogueSheetGreen, RogueSheet;

	public static BufferedImage Floor[][] = new BufferedImage[5][11];
	public static BufferedImage Wall[][] = new BufferedImage[5][11];
	public static BufferedImage Special[][] = new BufferedImage[2][11];

	public static BufferedImage Minotaur[] = new BufferedImage[8];
	public static BufferedImage Skeleton[] = new BufferedImage[8];
	public static BufferedImage DragonMatriarch[] = new BufferedImage[8];
	public static BufferedImage Dragon[] = new BufferedImage[8];
	public static BufferedImage Dyclopse[] = new BufferedImage[8];
	public static BufferedImage Cyclops[] = new BufferedImage[8];
	public static BufferedImage Verwirren[] = new BufferedImage[8];
	public static BufferedImage Morphling[] = new BufferedImage[8];
	public static BufferedImage DungeonDude;

	BufferedImage Title, Background;
	BufferedImage StartButton, ExitButton, LoadButton, Button;

	public static BufferedImage Fireball[] = new BufferedImage[4];
	public static BufferedImage Iceball[] = new BufferedImage[4];
	public static BufferedImage OPball[] = new BufferedImage[4];
	public static BufferedImage Corrosionball[] = new BufferedImage[4];
	public static BufferedImage blobBall;
	public static BufferedImage potion, gem, Ice, PoisonBubble, graveStone;

	BufferedImage Hud, fog;

	// Overall rendering
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, W, H);

		W = getWidth();
		H = getHeight();
		xOffset = Person.getXPos() - W / 2;
		yOffset = Person.getYPos() - H / 2;

		if (WindowFrame.state == WindowFrame.TITLE) {
			startScreen(g);
		}
		if (WindowFrame.state == WindowFrame.INFO) {
			infoScreen(g);
		}
		if (WindowFrame.state == WindowFrame.STORY) {
			Story(g);
		}
		if (WindowFrame.state == WindowFrame.CHARACTER_SELECTION) {
			charSelect(g);
		}
		if (WindowFrame.state == WindowFrame.GAME) {
			gameScreen(g);
			// debug(g);
			if (Main.frame.win) {
				ending(g);
			}
		}
	}

	// Start screen
	private void startScreen(Graphics g) {
		g.drawImage(Background, 0, 0, W, H, this);
		g.drawImage(Title, W / 2 - Title.getWidth() / 2, 100, this);

		// StartButton
		g.drawImage(StartButton, W / 2 - 562 / 2, H / 2, this);
		if (Main.frame.sButton.getModel().isRollover()) {
			g.setColor(Color.yellow);
			g.drawRect(W / 2 - 562 / 2, H / 2, 562, 75);
		}

		// Load button
		g.drawImage(LoadButton, W / 2 - 562 / 2, H / 2 + 100, this);
		if (Main.frame.lButton.getModel().isRollover()) {
			g.setColor(Color.yellow);
			g.drawRect(W / 2 - 562 / 2, H / 2 + 100, 562, 75);
		}

		// ExitButton
		g.drawImage(ExitButton, W / 2 - 562 / 2, H / 2 + 200, this);
		if (Main.frame.eButton.getModel().isRollover()) {
			g.setColor(Color.yellow);
			g.drawRect(W / 2 - 562 / 2, H / 2 + 200, 562, 75);
		}
		Main.frame.sButton.setLocation(W / 2 - 562 / 2, H / 2);
		Main.frame.lButton.setLocation(W / 2 - 562 / 2, H / 2 + 100);
		Main.frame.eButton.setLocation(W / 2 - 562 / 2, H / 2 + 200);
	}

	// Story screen
	private void Story(Graphics g) {
		g.drawImage(Background, 0, 0, W, H, this);
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.PLAIN, 20));
		switch (WindowFrame.level) {
		case 1:
			g.drawString("As all before you, you now walk into the dragon's lair, hoping to slay the dragon and recieve", 150, 200);
			g.drawString("it's riches. When you walk in, the wall closes in behind you! You are now trapped! You inspect", 150, 220);
			g.drawString("the wall to see if you can open it. That doesn't work. However, you do find an inscription on", 150, 240);
			g.drawString("the wall. It reads 'In order to leave this place you must obtain all five of the dimensional", 150, 260);
			g.drawString("crystals. Each Boss holds one crystal.' It looks like you got more than you bargened for.", 150, 280);
			break;
		case 2:
			g.drawString("The dragon slumps to the floor. Taking it's last breath, the whole thing disintigrates before", 150, 200);
			g.drawString("you! Where the mass of reptilian flesh was, there is now a shimering crystal. You belive you", 150, 220);
			g.drawString("have found the first dimensional crystal! As you pick up the crystal, you notice the room", 150, 240);
			g.drawString("around you changing. You can only assume that the crystal is taking you to the second crystal's", 150, 260);
			g.drawString("location. One down, four to go, you tell yourself.", 150, 280);
			break;
		case 3:
			g.drawString("It smells of burnt hair. The Minotaur disintigrates just as the dragon did. He too drops a", 150, 200);
			g.drawString("dimensional Crystal! Eager to find your way out of this dungeon of nightmares, you pick up the", 150, 220);
			g.drawString("crystal. This new place is filled with mist. The mist gives you a feeling of unease. You can", 150, 240);
			g.drawString("tell the worst is yet to come. Before you, there are three pasageways. You realize you're in a", 150, 260);
			g.drawString("maze. You grumble under your breath, 'Why did it have to be a maze.'", 150, 280);
			break;
		case 4:
			g.drawString("You brush off some slime as the octopus-like creature slumps to the maze floor. It drops the", 150, 200);
			g.drawString("third dimensional crystal. You're glad that the crystal takes you to the next room. You are", 150, 220);
			g.drawString("unsure you would be able to find your way in the maze again. As you arrive in the next room", 150, 240);
			g.drawString("you can feel the ground shaking and you hear a faint rumble in the distance. You hope you", 150, 260);
			g.drawString("don't have to find out the source of the disturbance.", 150, 280);
			break;
		case 5:
			g.drawString("The monstrous mass of muscle falls to the floor. You wonder which head was in controll. You", 150, 200);
			g.drawString("push the irrelevant thought aside. You now have four of the five dimensional crystals. In", 150, 220);
			g.drawString("this new room there is annother inscription on the wall. This one reads, 'The hardest chalenge", 150, 240);
			g.drawString("lies before you. You must defeat the final monster, known as Dungeon Dude and use the crystals", 150, 260);
			g.drawString("that you have gained to banish him to annother dimension. 'Shouldn't be too hard to", 150, 280);
			g.drawString("do, you tell yourself, as you stare off into the expansive room.", 150, 300);
			break;
		case 6:
			g.drawString("As you grab the fifth dimensional crystal Dungeon Dude begins to fade out of this dimension.", 150, 200);
			g.drawString("You have done it. Yet, you begin to wonder why the fifth crystal didn't take you out of the", 150, 220);
			g.drawString("dungeon. You notice writing appearing on the wall nearest to you. It reads, 'You are now the", 150, 240);
			g.drawString("new Dungeon Dude. Please enjoy your stay.' You have been tricked! This place never held", 150, 260);
			g.drawString("promises of riches. It only held your doom. You are stuck waiting until the next adventurer", 150, 280);
			g.drawString("appears to take your place.", 150, 300);
		}

		g.drawImage(StartButton, W / 2 - 562 / 2, H - (H / 4), this);
		if (Main.frame.cButton.getModel().isRollover()) {
			g.setColor(Color.yellow);
			g.drawRect(W / 2 - 562 / 2, H - (H / 4), 562, 75);
		}
		Main.frame.cButton.setLocation(W / 2 - 562 / 2, H - (H / 4));
	}

	// Info screen
	private void infoScreen(Graphics g) {
		g.drawImage(Background, 0, 0, W, H, this);
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.PLAIN, 20));
		g.drawString("Welcome to Dungeon Dude, in this game you will be playing as one of four similar adventurers as you", 150, 200);
		g.drawString("tackle the challenge of five immersive levels filled with stunning 8-bit graphics, and intense boss battles.", 150, 225);
		g.drawString("Controls are WASD for movement, Left-click to fire, F to heal.", 150, 300);
		g.drawString("1 for Ice ball, which freezes enemies for 3 seconds.", 150, 325);
		g.drawString("2 for Poison shot, which Poisons enemies for 3 seconds.", 150, 350);
		g.drawString("3 for Overpowered mode, which doubles your damage for 10 seconds.", 150, 375);

		g.drawImage(Button, W / 2 - 562 / 2, H - (H / 4), this);
		if (Main.frame.mButton.getModel().isRollover()) {
			g.setColor(Color.yellow);
			g.drawRect(W / 2 - 562 / 2, H - (H / 4), 562, 75);
		}
		Main.frame.mButton.setLocation(W / 2 - 562 / 2, H - (H / 4));
	}

	// Character selection
	private void charSelect(Graphics g) {
		g.setColor(Color.darkGray);
		g.drawImage(Background, 0, 0, W, H, this);
		for (int i = 0; i < Main.frame.classes.length; i++) {
			Main.frame.classes[i].setLocation(i * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40));
			g.fillRect(i * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40), W / 4 - 40, W / 4 - 40);
			if (Main.frame.classes[i].getModel().isRollover()) {
				g.setColor(Color.yellow);
				g.drawRect(i * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40), W / 4 - 40, W / 4 - 40);
				g.setColor(Color.darkGray);
			}
		}
		g.drawImage(RogueRed[0], 0 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40), W / 4 - 40, W / 4 - 40, this);
		g.drawImage(RogueGreen[0], 1 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40), W / 4 - 40, W / 4 - 40, this);
		g.drawImage(RogueBlue[0], 2 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40), W / 4 - 40, W / 4 - 40, this);
		g.drawImage(Rogue[0], 3 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40), W / 4 - 40, W / 4 - 40, this);
		// Strings for stats
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.PLAIN, 20));
		// Red/Mage
		g.drawString("Health: 1000", 0 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 300);
		g.drawString("Speed: 4", 0 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 320);
		g.drawString("Damage: 60", 0 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 340);
		// Green/Rogue
		g.drawString("Health: 600", 1 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 300);
		g.drawString("Speed: 5", 1 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 320);
		g.drawString("Damage: 120", 1 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 340);
		// Blue/Warrior
		g.drawString("Health: 1850", 2 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 300);
		g.drawString("Speed: 3", 2 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 320);
		g.drawString("Damage: 100", 2 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 340);
		// Yellow/Ranger
		g.drawString("Health: 550", 3 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 300);
		g.drawString("Speed: 5", 3 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 320);
		g.drawString("Damage: 120", 3 * (W / 4 - 20) + 50, H / 2 - (W / 4 - 40) + 340);
	}

	// Game screen
	private void gameScreen(Graphics g) {
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				g.drawImage(WindowFrame.map[x][y].getImg(), x * 64 - xOffset, y * 64 - yOffset, 64, 64, this);
			}
		}
		// person
		if (Person.health < 1) {
			g.drawImage(graveStone, W / 2 - 48 / 2, H / 2 - 48 / 2, 48, 48, this);
		} else {
			// Character animation
			if (Person.moving) {
				g.drawImage(Person.img[TickThread.animation / 6 % 5], W / 2 - 48 / 2, H / 2 - 48 / 2, 48, 48, this);
			} else {
				g.drawImage(Person.img[0], W / 2 - 48 / 2, H / 2 - 48 / 2, 48, 48, this);
			}
		}
		if (Person.reversed) {
			g.drawImage(blobBall, W / 2 - 48 / 2, H / 2 - 48 / 2, this);
		}
		// items
		for (int i = 0; i < WindowFrame.items.length; i++) {
			if (WindowFrame.items[i].exists && WindowFrame.items[i].type == 0) {
				g.drawImage(potion, WindowFrame.items[i].getX() - xOffset, WindowFrame.items[i].getY() - yOffset, this);
			}
			if (WindowFrame.items[i].exists && WindowFrame.items[i].type == 1) {
				g.drawImage(gem, WindowFrame.items[i].getX() - xOffset, WindowFrame.items[i].getY() - yOffset, this);
			}
		}
		// enemies
		if (WindowFrame.enemies[49] != null) {
			for (int i = 0; i < 50; i++) {
				if (WindowFrame.enemies[i].health > 0) {
					g.drawImage(WindowFrame.enemies[i].getImg(), WindowFrame.enemies[i].getX() - xOffset, WindowFrame.enemies[i].getY() - yOffset, this);
					g.setColor(new Color(255 - (WindowFrame.enemies[i].health * 255 / WindowFrame.enemies[i].maxHealth), WindowFrame.enemies[i].health * 255 / WindowFrame.enemies[i].maxHealth, 0));
					g.fillRect(WindowFrame.enemies[i].getX() - xOffset, WindowFrame.enemies[i].getY() - yOffset - 4, WindowFrame.enemies[i].health * 48 / WindowFrame.enemies[i].maxHealth, 6);
					g.drawString(WindowFrame.enemies[i].health + "/" + WindowFrame.enemies[i].maxHealth, WindowFrame.enemies[i].getX() - xOffset, WindowFrame.enemies[i].getY() - 10 - yOffset);
					if (WindowFrame.enemies[i].frozen) {
						g.drawImage(Ice, WindowFrame.enemies[i].getX() - xOffset, WindowFrame.enemies[i].getY() - yOffset, this);
					}
					if (WindowFrame.enemies[i].poisoned) {
						g.drawImage(PoisonBubble, WindowFrame.enemies[i].getX() - xOffset, WindowFrame.enemies[i].getY() - yOffset, this);
					}
				}
			}
		}
		// boss
		if (Boss.health > 0) {
			g.drawImage(Boss.getImg(), Boss.getX() - xOffset, Boss.getY() - yOffset, 96, 96, this);
			g.setColor(new Color(255 - (Boss.health * 255 / Boss.maxHealth), Boss.health * 255 / Boss.maxHealth, 0));
			g.fillRect(Boss.getX() - xOffset, Boss.getY() - yOffset - 6, Boss.health * 96 / Boss.maxHealth, 6);
			g.drawString(Boss.health + "/" + Boss.maxHealth, Boss.getX() - xOffset, Boss.getY() - 10 - yOffset);
			if (Boss.frozen) {
				g.drawImage(Ice, Boss.getX() - xOffset, Boss.getY() - yOffset, 96, 96, this);
			}
			if (Boss.poisoned) {
				g.drawImage(PoisonBubble, Boss.getX() - xOffset, Boss.getY() - yOffset, 96, 96, this);
			}
		}

		for (int i = 0; i < Person.projectiles.length; i++) {
			if (Person.projectiles[i].exists) {
				g.drawImage(Person.projectiles[i].img[TickThread.animation / 3 % 4], Person.projectiles[i].getX() - xOffset - 48, Person.projectiles[i].getY() - yOffset - 48, this);
			}
		}
		if (Boss.projectiles[0] != null) {
			for (int i = 0; i < Boss.projectiles.length; i++) {
				if (Boss.projectiles[i].exists) {
					g.drawImage(Boss.projectiles[i].img[0], Boss.projectiles[i].getX() - xOffset - 48, Boss.projectiles[i].getY() - yOffset - 48, this);
				}
			}
		}
		if (WindowFrame.level == 3) {
			g.drawImage(fog, 0, 0, this);
		}
		Hud(g);
	}

	// Hud
	private void Hud(Graphics g) {
		int HudOffset = H - H / 8;
		g.drawImage(Hud, 0, HudOffset, this);
		g.drawImage(Person.img[0], 60, 15 + HudOffset, this);
		g.drawImage(potion, 110, 15 + HudOffset, this);
		g.setColor(Color.black);
		g.drawString("x" + Person.hPots, 140, 40 + HudOffset);
		g.drawString("Health: " + Person.health + "/" + Person.maxHealth, 60, 75 + HudOffset);
		g.fillRect(60, 80 + HudOffset, 102, 20);
		g.setColor(new Color(255 - (Person.health * 255 / Person.maxHealth), (Person.health * 255 / Person.maxHealth), 0));
		g.fillRect(61, 81 + HudOffset, Person.health * 100 / Person.maxHealth, 18);
		for (int i = 0; i < 3; i++) {
			g.setColor(Color.black);
			g.fillRect(200 + i * 64, 50 + HudOffset, 48, 48);
			g.fillOval(190 + i * 64, 40 + HudOffset, 20, 20);
			g.setColor(Color.white);
			g.drawString("" + (i + 1), 195 + i * 64, 55 + HudOffset);
		}
		g.drawImage(Iceball[0], 200, 50 + HudOffset, this);
		g.drawImage(Corrosionball[0], 264, 50 + HudOffset, this);
		g.drawImage(OPball[0], 328, 50 + HudOffset, this);
		g.setColor(new Color(100, 100, 100, 200));

		for (int i = 0; i < 3; i++) {
			g.fillRect(201 + i * 64, 51 + HudOffset, 46, Person.ct[i] * 46 / Person.gcd);
		}
	}

	// Debug
	private void debug(Graphics g) {
		g.setColor(Color.RED);

		// Character
		g.drawRect((int) Person.getBounds().getX() - xOffset, (int) Person.getBounds().getY() - yOffset, 48, 48);

		// Items
		for (int i = 0; i < WindowFrame.items.length; i++) {
			g.drawRect((int) WindowFrame.items[i].getBounds().getX() - xOffset, (int) WindowFrame.items[i].getBounds().getY() - yOffset, 32, 32);
		}

		// Enemies
		for (int i = 0; i < WindowFrame.enemies.length; i++) {
			g.drawRect((int) WindowFrame.enemies[i].getBounds().getX() - xOffset, (int) WindowFrame.enemies[i].getBounds().getY() - yOffset, 48, 48);
		}
		g.drawRect((int) Boss.getBounds().getX() - xOffset, (int) Boss.getBounds().getY() - yOffset, 96, 96);
		// Projectiles
		for (int i = 0; i < Person.projectiles.length; i++) {
			g.drawRect((int) Person.projectiles[i].getBounds().getX() - xOffset, (int) Person.projectiles[i].getBounds().getY() - yOffset, 48, 48);
		}
		for (int i = 0; i < Boss.projectiles.length; i++) {
			g.drawRect((int) Boss.projectiles[i].getBounds().getX() - xOffset, (int) Boss.projectiles[i].getBounds().getY() - yOffset, 48, 48);
		}

		// Tiles
		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < 100; x++) {
				g.drawRect((int) WindowFrame.map[x][y].getBounds().getX() - xOffset, (int) WindowFrame.map[x][y].getBounds().getY() - yOffset, 64, 64);
			}
		}

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 11; y++) {
				g.drawImage(Wall[x][y], x * 64, y * 64, this);
			}
		}
	}

	private void ending(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("SansSerif", Font.PLAIN, 75));
		Main.frame.mButton.setLocation(W / 2 - 562 / 2, H / 2 + 100);
		g.drawImage(Button, W / 2 - 562 / 2, H / 2 + 100, this);
		if (Main.frame.mButton.getModel().isRollover()) {
			g.setColor(Color.yellow);
			g.drawRect(W / 2 - 562 / 2, H / 2 + 100, 562, 75);
		}
		if (Person.health > 0) {
			g.drawString("You Win!", W / 2 - 150, H / 2 - 100);
		} else {
			g.drawString("You Lose!", W / 2 - 175, H / 2 - 100);
		}
	}

	// Double buffering
	public void update(Graphics g) {
		System.out.println("Updated!");
		if (img == null) {
			img = createImage(this.getSize().width, this.getSize().height);
			doubleB = img.getGraphics();
		}

		doubleB.setColor(getBackground());
		doubleB.fillRect(0, 0, this.getSize().width, this.getSize().height);

		doubleB.setColor(getForeground());
		paint(doubleB);

		g.drawImage(img, 0, 0, this);
	}

	public void gatherResources() {
		try {
			DungeonDude = TCI(ImageIO.read(this.getClass().getResourceAsStream("/Resources/DungeonDude.png")));
			Title = TCI(ImageIO.read(this.getClass().getResourceAsStream("/Resources/Title Screen.png")));
			Background = TCI(ImageIO.read(this.getClass().getResourceAsStream("/Resources/Dungeon Door.png")));
			BufferedImage ButtonSheet = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Buttons/ButtonSheet.png")));
			StartButton = ButtonSheet.getSubimage(0, 0, 562, 75);
			LoadButton = ButtonSheet.getSubimage(0, 75, 562, 75);
			ExitButton = ButtonSheet.getSubimage(0, 150, 562, 75);
			Button = ButtonSheet.getSubimage(0, 225, 562, 75);

			// Tiles
			BufferedImage TileSheet = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Tiles/TileSheet.png")));
			for (int y = 0; y < 11; y++) {
				for (int x = 0; x < 5; x++) {
					Floor[x][y] = TileSheet.getSubimage(x * 128, y * 64, 64, 64);
					Wall[x][y] = TileSheet.getSubimage(64 + x * 128, y * 64, 64, 64);
				}
				for (int x = 0; x < 2; x++) {
					Special[x][y] = TileSheet.getSubimage(640 + x * 64, y * 64, 64, 64);
				}
			}

			// characters
			RogueSheetRed = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Characters/RogueSpriteSheetRed.png")));
			RogueSheetGreen = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Characters/RogueSpriteSheetGreen.png")));
			RogueSheetBlue = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Characters/RogueSpriteSheetBlue.png")));
			RogueSheet = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Characters/RogueSpriteSheet.png")));
			for (int i = 0; i < 5; i++) {
				RogueRed[i] = RogueSheetRed.getSubimage(0, i * 48, 48, 48);
				RogueGreen[i] = RogueSheetGreen.getSubimage(0, i * 48, 48, 48);
				RogueBlue[i] = RogueSheetBlue.getSubimage(0, i * 48, 48, 48);
				Rogue[i] = RogueSheet.getSubimage(0, i * 48, 48, 48);
			}

			// weapons/items
			BufferedImage weaponSheet = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/WeaponsItems/WeaponSheet.png")));
			for (int i = 0; i < 4; i++) {
				Fireball[i] = weaponSheet.getSubimage(0, i * 48, 48, 48);
				Iceball[i] = weaponSheet.getSubimage(48, i * 48, 48, 48);
				OPball[i] = weaponSheet.getSubimage(144, i * 48, 48, 48);
				Corrosionball[i] = weaponSheet.getSubimage(96, i * 48, 48, 48);
			}
			blobBall = weaponSheet.getSubimage(0, 192, 48, 48);
			potion = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/WeaponsItems/Potion.png")));
			gem = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/WeaponsItems/lvlUp.png")));

			// enemies
			BufferedImage EnemySpriteSheet = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Enemies/EnemySpriteSheet.png")));
			for (int i = 0; i < 8; i++) {
				Morphling[i] = EnemySpriteSheet.getSubimage(0, i * 48, 48, 48);
				Skeleton[i] = EnemySpriteSheet.getSubimage(48, i * 48, 48, 48);
				Dragon[i] = EnemySpriteSheet.getSubimage(96, i * 48, 48, 48);
				Cyclops[i] = EnemySpriteSheet.getSubimage(144, i * 48, 48, 48);
			}

			// bosses
			BufferedImage BossSpriteSheet = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Enemies/BossSpriteSheet.png")));
			for (int i = 0; i < 8; i++) {
				DragonMatriarch[i] = BossSpriteSheet.getSubimage(96, i * 96, 96, 96);
				Verwirren[i] = BossSpriteSheet.getSubimage(0, i * 96, 96, 96);
				Dyclopse[i] = BossSpriteSheet.getSubimage(192, i * 96, 96, 96);
				Minotaur[i] = BossSpriteSheet.getSubimage(288, i*96, 96, 96);
			}

			PoisonBubble = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/PoisonBubble.png")));
			graveStone = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Tombstone.png")));
			Ice = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Ice.png")));
			Hud = TCI(ImageIO.read(getClass().getResourceAsStream("/Resources/Scroll.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Generation of the fog
		fog = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
		Graphics f = fog.getGraphics();
		f.setColor(new Color(100, 100, 100, 100));
		f.fillRect(0, 0, W / 2 - 147, H);
		f.fillRect(W / 2 + 148, 0, W / 2, H);
		f.fillRect(W / 2 - 147, 0, 295, H / 2 - 147);
		f.fillRect(W / 2 - 147, H / 2 + 148, 295, H / 2 - 148);
		for (int i = 0; i < 100; i++) {
			f.setColor(new Color(100, 100, 100, i));
			f.drawRect(W / 2 - 48 - i, H / 2 - 48 - i, 96 + i * 2, 96 + i * 2);

		}
		fog = TCI(fog);
	}

	// Changes the images to the best compatible to the system
	public BufferedImage TCI(BufferedImage image) {
		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		if (image.getColorModel().equals(gfxConfig.getColorModel())) {
			return image;
		}

		BufferedImage newImage = gfxConfig.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics2D g2d = (Graphics2D) newImage.getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return newImage;
	}
}
