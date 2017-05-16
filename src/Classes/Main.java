package Classes;

import Engine.RenderThread;
import Engine.TickThread;
import UserInterface.WindowFrame;

public class Main {

	public static TickThread tickThread = new TickThread();
	public static RenderThread renderThread = new RenderThread();
	public static WindowFrame frame = new WindowFrame();

	// TODO Initializes and loads anything we need
	public static void main(String[] args) {
//		frame.convert(5);
		frame.startWindow();
		new Thread(tickThread).start();
//		new Thread(renderThread).start();
	}
}
