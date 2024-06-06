package view;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

	public class Board {
		final int MAX_COL = 9;
		final int MAX_ROW = 10;
		public static final int SQUARE_SIZE = 60;
		public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;
	
		public void draw(Graphics2D g2) {
			// Thiết lập màu nền
	        g2.setColor(new Color(250,206,156));
	        g2.fillRect(0, 0, MAX_COL * SQUARE_SIZE, MAX_ROW * SQUARE_SIZE);

	        // Thiết lập màu vẽ cho các đường
	        g2.setColor(Color.red);
	        
			for (int row = 0; row < MAX_ROW; row++) {
				for (int col = 0; col < MAX_COL; col++) {
					
					// Tọa độ giữa ô vuông
					int x = col * SQUARE_SIZE + HALF_SQUARE_SIZE;
					int y = row * SQUARE_SIZE + HALF_SQUARE_SIZE;
					// Vẽ giới hạn bên phải bàn cờ
					if (col == 8 && row != 10) {
						// Vẽ sọc dọc
						g2.drawLine(8 * SQUARE_SIZE + HALF_SQUARE_SIZE, HALF_SQUARE_SIZE,
								8 * SQUARE_SIZE + HALF_SQUARE_SIZE, 4 * SQUARE_SIZE + HALF_SQUARE_SIZE);
						g2.drawLine(8 * SQUARE_SIZE + HALF_SQUARE_SIZE, 5 * SQUARE_SIZE + HALF_SQUARE_SIZE,
								8 * SQUARE_SIZE + HALF_SQUARE_SIZE, 9 * SQUARE_SIZE + HALF_SQUARE_SIZE);
					}
					// Vẽ giới hạn bên dưới bàn cờ và giữa bàn cờ
					else if (row == 9 && col != 9 || row == 4 && col != 8) {
						// Vẽ sọc ngang
						g2.drawLine(x, y, x + SQUARE_SIZE, y);
	
					} else {
						// Vẽ sọc ngang
						g2.drawLine(x, y, x + SQUARE_SIZE, y);
						// Vẽ sọc dọc
						g2.drawLine(x, y, x, y + SQUARE_SIZE);
					}
	
					// Vẽ đường đi chéo của quân Sĩ
					g2.drawLine(3 * SQUARE_SIZE + HALF_SQUARE_SIZE, HALF_SQUARE_SIZE, 5 * SQUARE_SIZE + HALF_SQUARE_SIZE,
							2 * SQUARE_SIZE + HALF_SQUARE_SIZE);
					g2.drawLine(5 * SQUARE_SIZE + HALF_SQUARE_SIZE, HALF_SQUARE_SIZE, 3 * SQUARE_SIZE + HALF_SQUARE_SIZE,
							2 * SQUARE_SIZE + HALF_SQUARE_SIZE);
					g2.drawLine(3 * SQUARE_SIZE + HALF_SQUARE_SIZE, 7 * SQUARE_SIZE + HALF_SQUARE_SIZE,
							5 * SQUARE_SIZE + HALF_SQUARE_SIZE, 9 * SQUARE_SIZE + HALF_SQUARE_SIZE);
					g2.drawLine(5 * SQUARE_SIZE + HALF_SQUARE_SIZE, 7 * SQUARE_SIZE + HALF_SQUARE_SIZE,
							3 * SQUARE_SIZE + HALF_SQUARE_SIZE, 9 * SQUARE_SIZE + HALF_SQUARE_SIZE);
				
				}
			}
		}
	}