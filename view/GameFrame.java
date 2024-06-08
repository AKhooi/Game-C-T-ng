package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.GamePanel;

import java.awt.BorderLayout;

public class GameFrame extends JFrame implements Runnable, Observer {
	final int FPS = 60;
	Thread gameThread;
	GamePanel gp;

	public GameFrame() {
		// Thiết lập JFrame
		setSize(780, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("GAME CỜ TƯỚNG");
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		// Tạo GamePanel với tham chiếu đến GameScreen
		gp = new GamePanel(this);
		gp.addObserver(this); // Thêm GameScreen làm observer của GamePanel
		add(gp, BorderLayout.CENTER);

		pack();

		setVisible(true);

		// Bắt đầu game
		launchGame();
	}

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
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

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (gp.isGameOver()) {
			if (gp.getCurrentColor() == 0) {
				JOptionPane.showMessageDialog(this, "ĐỘI ĐỎ ĐÃ CHIẾN THẮNG!", "CHÚC MỪNG",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "ĐỘI ĐEN ĐÃ CHIẾN THẮNG!", "CHÚC MỪNG",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
