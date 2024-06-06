package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class StartingScreen extends JFrame {

	JButton withMachine,twoPlayer, guide;
	
	public StartingScreen() {
		JFrame startFrame = new JFrame();
		startFrame.setTitle("GAME CỜ TƯỚNG");
		
		withMachine = new JButton();
		twoPlayer = new JButton("2 Người Chơi");
		guide = new JButton("Hướng Dẫn");
		
		startFrame.add(withMachine);
		startFrame.add(twoPlayer);
		startFrame.add(guide);
		
		startFrame.setLayout(null);
		
		withMachine.setBounds(100, 200, 200, 50);
		twoPlayer.setBounds(100, 300, 200, 50);
		guide.setBounds(100, 400, 200, 50);
		
		init();
		startFrame.setSize(400, 600);
		startFrame.setResizable(false);
		startFrame.setVisible(true);
		startFrame.setLocationRelativeTo(null);
		startFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void init() {
		twoPlayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GameScreen();
			}
		});
	}
	public static void main(String[] args) {
		new StartingScreen();
	}
}
