package model;

import controller.GamePanel;

public class Bishop extends Piece {

	public Bishop(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		type = Type.BISHOP;
		if (color == GamePanel.RED) {
			image = getImage("/piece/r_BISHOP");
		} else {
			image = getImage("/piece/b_BISHOP");
		}
		
		bl = new IsWithinPalace();
		
	}

//	public boolean isWithinBoard(int targetCol, int targetRow) {
//		if (targetCol >= 3 && targetCol <= 5 && targetRow >= 7 && targetRow <= 9
//				|| targetCol >= 3 && targetCol <= 5 && targetRow >= 0 && targetRow <= 2) {
//			return true;
//		}
//		return false;
//	}

	public boolean canMove(int targetCol, int targetRow) {
		if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			if (Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
				if (isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow) == false) {
					return true;
				}
			}
		}
		return false;
	}
}
