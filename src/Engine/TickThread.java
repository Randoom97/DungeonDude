package Engine;

import java.awt.Rectangle;

import Objects.Boss;
import Objects.Person;
import UserInterface.WindowFrame;
import Classes.Main;

public class TickThread implements Runnable {
	long time = 0;
	long tickTime = 16666666;
	int update = 0;

	long timing = 0;

	public static int animation = 0;

	public void run() {
		long lastTime = System.nanoTime();
		double delta = 0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				//Animation timer for animated sprites
				if (animation >= 59) {
					animation = 0;
				} else {
					animation++;
				}
				//Data that needs to be updated
				if (WindowFrame.state == WindowFrame.GAME) {
					Person.update();
					collision();

					// Projectile updates
					for (int i = 0; i < Person.projectiles.length; i++) {
						Person.projectiles[i].update();
					}
					for (int i = 0; i < Boss.projectiles.length; i++) {
						Boss.projectiles[i].update();
					}

					// Enemy updates
					for (int i = 0; i < WindowFrame.enemies.length; i++) {
						WindowFrame.enemies[i].update();
					}
					WindowFrame.boss.update();
				}
				updates++;
				delta--;
			}
			WindowFrame.pane.repaint();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				Main.frame.setTitle(WindowFrame.title + " " + updates + ":tps " + frames + ":fps");
				updates = 0;
				frames = 0;
			}
		}
	}

	private void collision() {
		enemyCollision();
		projectileCollision();
		bossCollision();
		personCollision();
	}

	private void personCollision() {
		Rectangle person = Person.getBounds();
		for (int i = 0; i < Boss.projectiles.length; i++) {
			if (Boss.projectiles[i].getBounds().intersects(person)) {
				if (Boss.type == 3 || (Boss.type == 5 && Boss.health < 66666 && Boss.health > 33333)) {
					Person.reversed = true;
				}
				Boss.projectiles[i].exists = false;
				Person.hit(Boss.baseDamage);
			}
		}
		if (Boss.getBounds().intersects(person)) {
			Person.hit(Boss.baseDamage);
		}
		for (int i = 0; i < WindowFrame.enemies.length; i++) {
			if (WindowFrame.enemies[i].getBounds().intersects(person)) {
				Person.hit(WindowFrame.enemies[i].baseDamage);
			}
		}
		for (int i = 0; i < WindowFrame.items.length; i++) {
			if (WindowFrame.items[i].getBounds().intersects(person)) {
				if (WindowFrame.items[i].type == 0) {
					Person.hPots++;
				}
				if (WindowFrame.items[i].type == 1) {
					WindowFrame.level++;
					Main.frame.setStory();
				}
				WindowFrame.items[i].exists = false;
			}
		}
	}

	private void projectileCollision() {
		// Character projectiles
		for (int i = 0; i < Person.projectiles.length; i++) {
			Rectangle projectile = Person.projectiles[i].getBounds();
			int xPos = Person.projectiles[i].getX() / 64;
			int yPos = Person.projectiles[i].getY() / 64;
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					Rectangle Tile = WindowFrame.map[Math.abs(xPos + x)][Math.abs(yPos + y)].getBounds();
					if (projectile.intersects(Tile)) {
						Person.projectiles[i].exists = false;
					}
				}
			}
		}
		// Boss projectiles
		for (int i = 0; i < Boss.projectiles.length; i++) {
			Rectangle projectile = Boss.projectiles[i].getBounds();
			int xPos = Boss.projectiles[i].getX() / 64;
			int yPos = Boss.projectiles[i].getY() / 64;
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					Rectangle Tile = WindowFrame.map[Math.abs(xPos + x)][Math.abs(yPos + y)].getBounds();
					if (projectile.intersects(Tile)) {
						Boss.projectiles[i].exists = false;
					}
				}
			}
		}

	}

	private void bossCollision() {
		Rectangle boss = Boss.getBounds();
		for (int j = 0; j < Person.projectiles.length; j++) {
			Rectangle projectile = Person.projectiles[j].getBounds();
			if (projectile.intersects(boss)) {
				if (Person.skills[2]) {
					Boss.hit(Person.baseDamage * 2);
				} else {
					Boss.hit(Person.baseDamage);
				}
				Person.projectiles[j].exists = false;
				if (Person.projectiles[j].frost) {
					Boss.frozen = true;
					Person.projectiles[j].frost = false;
				}
				if (Person.projectiles[j].poison) {
					Boss.poisoned = true;
					Person.projectiles[j].poison = false;
				}
			}
		}
	}

	private void enemyCollision() {
		for (int i = 0; i < WindowFrame.enemies.length; i++) {
			Rectangle enemy = WindowFrame.enemies[i].getBounds();
			for (int j = 0; j < Person.projectiles.length; j++) {
				Rectangle projectile = Person.projectiles[j].getBounds();
				if (projectile.intersects(enemy)) {
					if (Person.skills[2]) {
						WindowFrame.enemies[i].hit(Person.baseDamage * 2);
					} else {
						WindowFrame.enemies[i].hit(Person.baseDamage);
					}
					Person.projectiles[j].exists = false;
					if (Person.projectiles[j].frost) {
						WindowFrame.enemies[i].frozen = true;
						Person.projectiles[j].frost = false;
					}
					if (Person.projectiles[j].poison) {
						WindowFrame.enemies[i].poisoned = true;
						Person.projectiles[j].poison = false;
					}
				}
			}
		}
	}

}
