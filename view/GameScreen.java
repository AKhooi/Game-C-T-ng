package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.GamePanel;

public class GameScreen {

	public GameScreen() {
		JFrame frame = new JFrame();
		JFrame frame2 = new JFrame();
		frame.setSize(780, 600);
		frame2.setSize(240, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		screen.setResizable(false);
		frame.setTitle("GAME CỜ TƯỚNG");

		// Set Layout
		frame.setLayout(new BorderLayout());
//		frame2.setLayout(new BorderLayout());

		// Add GamePanel
		GamePanel gp = new GamePanel();
		frame.add(gp, BorderLayout.CENTER);

//		//Add InfoPlayer
//		InfoPlayerPanel i4 = new InfoPlayerPanel();
//		frame2.add(i4, BorderLayout.CENTER);


//		frame.add(frame2, BorderLayout.EAST);

		frame.pack();

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		gp.launchGame();
	}

	public static void main(String[] args) {
		new GameScreen();
	}
}
