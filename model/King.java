package model;

import controller.GamePanel;

public class King extends Piece {

	public King(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		type = Type.KING;
		if (color == GamePanel.RED) {
			image = getImage("/piece/r_KING");
		} else {
			image = getImage("/piece/b_KING");
		}
	}

	public boolean isWithinBoard(int targetCol, int targetRow) {
		if (targetCol >= 3 && targetCol <= 5 && targetRow >= 7 && targetRow <= 9 || 
				targetCol >= 3 && targetCol <= 5 && targetRow >= 0 && targetRow <= 2) {
			return true;
		}
		return false;
	}

	public boolean canMove(int targetCol, int targetRow) {
		if (isWithinBoard(targetCol, targetRow)) {
			if (Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1) {
				if (isValidSquare(targetCol, targetRow)) {
					return true;
				}
			}
		}
		return false;
	}
}
