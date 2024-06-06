package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import controller.GamePanel;

public class GameScreen extends JFrame implements Runnable {
	final int FPS = 60;
	Thread gameThread;
	GamePanel gp = new GamePanel();

	public GameScreen() {
		JFrame frame = new JFrame();
		frame.setSize(780, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("GAME CỜ TƯỚNG");

		// Set Layout
		frame.setLayout(new BorderLayout());

		// Add GamePanel
		frame.add(gp, BorderLayout.CENTER);

		frame.pack();

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		launchGame();
	}

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// GAME LOOP
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				gp.update();
				delta--;
			}
		}
	}


}
