package Classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Objects.Person;
import UserInterface.WindowFrame;

public class Input implements KeyListener, MouseListener, ActionListener {

	public static boolean up = false, left = false, down = false, right = false;
	private String devmode = "";

	public void mouseClicked(MouseEvent mouse) {

	}

	public void mouseEntered(MouseEvent mouse) {

	}

	public void mouseExited(MouseEvent mouse) {

	}

	public void mousePressed(MouseEvent mouse) {

	}

	public void mouseReleased(MouseEvent mouse) {
		if (Person.health > 0) {
			Person.attack(mouse.getX(), mouse.getY());
		}
	}

	public void keyPressed(KeyEvent key) {
		if (Person.health > 0) {
			// Character movement
			if (key.getKeyCode() == KeyEvent.VK_W) {
				up = true;
			}
			if (key.getKeyCode() == KeyEvent.VK_A) {
				left = true;
			}
			if (key.getKeyCode() == KeyEvent.VK_S) {
				down = true;
			}
			if (key.getKeyCode() == KeyEvent.VK_D) {
				right = true;
			}

			// Character skills
			if (key.getKeyCode() == KeyEvent.VK_1 && Person.ct[0] < 1) {
				Person.skills[0] = true;
			}
			if (key.getKeyCode() == KeyEvent.VK_2 && Person.ct[1] < 1) {
				Person.skills[1] = true;
			}
			if (key.getKeyCode() == KeyEvent.VK_3 && Person.ct[2] < 1) {
				Person.skills[2] = true;
			}

			// Healing
			if (key.getKeyCode() == KeyEvent.VK_F) {
				Person.heal();
			}
		}

	}

	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode() == KeyEvent.VK_W) {
			up = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_A) {
			left = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_S) {
			down = false;
		}
		if (key.getKeyCode() == KeyEvent.VK_D) {
			right = false;
		}
		
		//dev mode
		if(key.getKeyCode() == KeyEvent.VK_UP){
			devmode += "U";
		}
		if(key.getKeyCode() == KeyEvent.VK_DOWN){
			devmode += "D";
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT){
			devmode += "L";
		}
		if(key.getKeyCode() == KeyEvent.VK_RIGHT){
			devmode += "R";
		}
		if(key.getKeyCode() == KeyEvent.VK_ENTER){
			if(devmode.equals("UUDDLRLR")){
				System.out.println("Dev mode activated!");
				Person.maxHealth = Integer.MAX_VALUE;
				Person.health = Integer.MAX_VALUE;
				Person.baseDamage = Integer.MAX_VALUE;
				devmode = "";
			}else{
				devmode = "";
			}
		}
	}

	public void keyTyped(KeyEvent key) {

	}

	public void actionPerformed(ActionEvent button) {
		// Start button pressed
		if (button.getSource() == Main.frame.sButton) {
			Main.frame.setCharSelect();
		}

		// Load button pressed
		if (button.getSource() == Main.frame.lButton) {
			Main.frame.setInfo();
		}

		// Exit button pressed
		if (button.getSource() == Main.frame.eButton) {
			System.exit(0);
		}

		// Class was chosen
		for (int i = 0; i < Main.frame.classes.length; i++) {
			// TODO set the character to what class they chose
			if (button.getSource() == Main.frame.classes[i]) {
				switch (i) {
				case 0:
					Person.setMage();
					break;
				case 1:
					Person.setRogue();
					break;
				case 2:
					Person.setWarrior();
					break;
				case 3:
					Person.setRanger();
					break;
				default:
					System.out.println("Unknown character type");
					break;
				}
				Main.frame.setStory();
			}
		}

		//Continue Button pressed
		if(button.getSource() == Main.frame.cButton){
			Main.frame.setGame();
		}
		
		// Main button pressed
		if (button.getSource() == Main.frame.mButton) {
			if (Person.health > 0) {
				WindowFrame.level = 1;
			}
			Main.frame.setTitle();
			Main.frame.win = false;
			System.out.println("Back to main");
		}
	}
}
