package Engine;

import Classes.Main;
import UserInterface.WindowFrame;

public class RenderThread implements Runnable {
	private int frames = 0;
	private long time = System.currentTimeMillis();
	
	public void run() {
		while (true) {
			WindowFrame.pane.repaint();
			frames++;
			if(System.currentTimeMillis() - time > 1000){
				time = System.currentTimeMillis();
				Main.frame.setTitle(""+frames);
				frames = 0;
			}
		}
	}
}
