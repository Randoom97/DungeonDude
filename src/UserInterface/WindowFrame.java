package UserInterface;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import Classes.Input;
import Objects.Boss;
import Objects.Enemy;
import Objects.Item;
import Objects.Person;
import Objects.Tile;

public class WindowFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public static String title = "Dungeon Dude β";

	public static Input input = new Input();

	public static Person person = new Person();

	public static Tile[][] map = new Tile[100][100];
	public static Enemy[] enemies = new Enemy[50];
	public static Item[] items = new Item[50];
	public static Boss boss;

	public static WindowPanel pane = new WindowPanel();
	public JButton sButton = new JButton(), eButton = new JButton(), lButton = new JButton(), mButton = new JButton(), cButton = new JButton();
	public JButton[] classes = new JButton[4];

	public static int state;
	public static int TITLE = 0, GAME = 1, CHARACTER_SELECTION = 2, INFO = 3, STORY = 4;

	public static int level = 1;
	public boolean win = false;

	public void setTitle(){
		removeObjects();
		add(sButton);
		add(lButton);
		add(eButton);
		add(pane);
		state = TITLE;
	}

	public void setInfo() {
		removeObjects();
		add(mButton);
		add(pane);
		state = INFO;
	}

	public void setStory() {
		removeObjects();
		add(cButton);
		add(pane);
		state = STORY;
	}

	public void setGame() {
		removeObjects();
		loadMap(level);
		add(pane);
		state = GAME;
	}

	public void setCharSelect() {
		removeObjects();
		for (int i = 0; i < classes.length; i++) {
			add(classes[i]);
		}
		add(pane);
		state = CHARACTER_SELECTION;

	}

	public void removeObjects() {
		remove(sButton);
		remove(lButton);
		remove(eButton);
		remove(mButton);
		remove(cButton);
		for (int i = 0; i < classes.length; i++) {
			remove(classes[i]);
		}
		remove(pane);
	}

	// Initializes the window
	public void startWindow() {
		pane.gatherResources();
		this.addKeyListener(input);
		this.addMouseListener(input);
		setSize(1200, 850);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		sizeButtons();
		addParts();
		setIconImage(WindowPanel.DungeonDude);
		setTitle();
	}

	// adds main screen buttons
	private void addParts() {
		sButton.setFocusable(false);
		sButton.setSize(562, 75);
		sButton.setBorderPainted(false);
		sButton.setContentAreaFilled(false);
		sButton.addActionListener(input);
		add(sButton);
		eButton.setFocusable(false);
		eButton.setSize(562, 75);
		eButton.setBorderPainted(false);
		eButton.setContentAreaFilled(false);
		eButton.addActionListener(input);
		add(eButton);
		lButton.setFocusable(false);
		lButton.setSize(562, 75);
		lButton.setBorderPainted(false);
		lButton.setContentAreaFilled(false);
		lButton.addActionListener(input);
		add(lButton);
		mButton.setFocusable(false);
		mButton.setSize(562, 75);
		mButton.setBorderPainted(false);
		mButton.setContentAreaFilled(false);
		mButton.addActionListener(input);
		cButton.setFocusable(false);
		cButton.setSize(562, 75);
		cButton.setBorderPainted(false);
		cButton.setContentAreaFilled(false);
		cButton.addActionListener(input);
		cButton.setFocusable(false);
		add(pane);
	}

	// Adds character selection buttons
	public void sizeButtons() {
		for (int i = 0; i < classes.length; i++) {
			classes[i] = new JButton();
			classes[i].setFocusable(false);
			classes[i].setBorderPainted(false);
			classes[i].setContentAreaFilled(false);
			classes[i].addActionListener(input);
			classes[i].setSize(getWidth() / 4 - 40, getWidth() / 4 - 40);
			classes[i].setLocation(i * (getWidth() / 4 - 20) + 50, getHeight() / 2 - (getWidth() / 4 - 40));
			add(classes[i]);
		}
	}

	public void loadMap(int i) {
		try {
			if (i == 6) {
				win = true;
				add(mButton);
				return;
			}
			// BufferedImage Map = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			BufferedImage Map = ImageIO.read(getClass().getResourceAsStream("/Resources/Maps/Map" + i + ".png"));
			boss = new Boss(i);
			for (int y = 0; y < 100; y++) {
				for (int x = 0; x < 100; x++) {
					// int terms of 0x11223344 11 = alpha, 22 = red, 33 = green, 44 = blue
					// alpha should always be 256 or 0xff
					switch (Map.getRGB(x, y)) {
					case 0xff404040:
						map[x][y] = new Tile(x, y, 0);
						break;
					case 0xff000000:
						map[x][y] = new Tile(x, y, 1);
						break;
					case 0xffff0000:
						map[x][y] = new Tile(x, y, 2);
						break;
					case 0xffb200ff:
						map[x][y] = new Tile(x, y, 0);
						// Boss spawn here
						boss.spawn(x * 64, y * 64);
						break;
					case 0xff00ff00:
						map[x][y] = new Tile(x, y, 0);
						Person.setLocation(x * 64 + 24, y * 64 + 24);
						break;
					default:
						System.out.println("Unknown tile type!");
						break;
					}
				}
			}
			// Loads dragon whelps for Dungeon dude
			boolean spawnEnemies = true;
			if (i == 5) {
				i = 1;
				spawnEnemies = false;
			}
			for (int j = 0; j < enemies.length; j++) {
				enemies[j] = new Enemy(i);
				items[j] = new Item();
			}
			if (spawnEnemies) {
				Random r = new Random();
				int spawned = 0;
				while (spawned < 50) {
					int x = r.nextInt(100);
					int y = r.nextInt(100);
					if (map[x][y].tile == Tile.FLOOR) {
						enemies[spawned++].spawn(x * 64, y * 64);
					}
				}
			}
			connectTextures();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectTextures() {
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {
				boolean wall[] = new boolean[4];
				int btile = map[x][y].tile;
				if (y > 0) {
					if (map[x][y - 1].tile == btile) {
						wall[0] = true;
					}
				}
				if(x < 99){
					if(map[x+1][y].tile == btile){
						wall[1] = true;
					}
				}
				if(y < 99){
					if(map[x][y+1].tile == btile){
						wall[2] = true;
					}
				}
				if(x > 0){
					if(map[x-1][y].tile == btile){
						wall[3] = true;
					}
				}
				//Tells the tile which type it is
				int type = 0;
				if(wall[0] && !wall[1] && wall[2] && !wall[3]){
					type = 1;
				}
				if(!wall[0] && wall[1] && !wall[2] && wall[3]){
					type = 2;
				}
				if(!wall[0] && !wall[1] && wall[2] && wall[3]){
					type = 3;
				}
				if(wall[0] && !wall[1] && !wall[2] && wall[3]){
					type = 4;
				}
				if(wall[0] && wall[1] && !wall[2] && !wall[3]){
					type = 5;
				}
				if(!wall[0] && wall[1] && wall[2] && !wall[3]){
					type = 6;
				}
				if(!wall[0] && wall[1] && wall[2] && wall[3]){
					type = 7;
				}
				if(wall[0] && !wall[1] && wall[2] && wall[3]){
					type = 8;
				}
				if(wall[0] && wall[1] && !wall[2] && wall[3]){
					type = 9;
				}
				if(wall[0] && wall[1] && wall[2] && !wall[3]){
					type = 10;
				}
				map[x][y].type = type;
			}
		}
	}

	public void convert(int i) {
		try {
			FileReader in = new FileReader(("src/Resources/Maps/Map" + i + ".txt"));
			BufferedReader br = new BufferedReader(in);
			String line;
			BufferedImage Map = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			int j = 0;
			while ((line = br.readLine()) != null) {
				for (int k = 0; k < 100; k++) {
					int rgb = 0;
					switch (Integer.parseInt(line.substring(k, k + 1))) {
					case 0:
						rgb = 0xff404040;
						break;
					case 1:
						rgb = 0xff000000;
						break;
					case 2:
						rgb = 0xffff0000;
						break;
					}
					Map.setRGB(k, j, rgb);
				}
				j++;
			}
			ImageIO.write(Map, "png", new File("src/Resources/Maps/Map" + i + ".png"));
			br.close();
			in.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}