package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class InfoPlayerPanel extends GamePanel {
	public static final int WIDTH = 240;
	public static final int HEIGHT = 600;
	public InfoPlayerPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// STATUS MESSAGE
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Time New Roman", Font.PLAIN, 20));
		g2.setColor(Color.white);

		if (currentColor == RED) {
			g2.drawString("Đến lượt quân đỏ", 100, 400);
			if (checkingP != null && checkingP.color == BLACK) {
				g2.setColor(Color.red);
				g2.drawString("Tướng đang bị chiếu!", 100, 450);
			}
		} else {
			g2.drawString("Đến lượt quân đen", 100, 200);
			if (checkingP != null && checkingP.color == RED) {
				g2.setColor(Color.red);
				g2.drawString("Tướng đang bị chiếu!", 100, 250);
			}
		}

	}
}
