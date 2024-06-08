package model;

import controller.GamePanel;

public class Pawn extends Piece {

	public Pawn(int color, int col, int row) {
		super(color, col, row);
		type = Type.PAWN;
		if (color == GamePanel.RED) {
			image = getImage("/piece/r_PAWN");
		} else {
			image = getImage("/piece/b_PAWN");
		}
		
		bl = new IsWithinBoard();
		
	}

	public boolean canMove(int targetCol, int targetRow) {
		if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			// Giá trị di chuyển khác nhau tùy theo màu sắc
			int moveValue;
			if (color == GamePanel.RED) {
				moveValue = -1;
			} else {
				moveValue = 1;
			}

			// Di chuyển 1 ô
			if (targetCol == preCol && targetRow == preRow + moveValue) {
				if (isValidSquare(targetCol, targetRow)) {
					return true;
				}
			}
			//Kể từ nước thứ 2 trở đi
			if (countMoved >= 2) {
				if (targetCol == preCol - 1 && targetRow == preRow || targetCol == preCol + 1 && targetRow == preRow) {
					if (isValidSquare(targetCol, targetRow)) {
						return true;
					}
				}
				if (targetCol == preCol && targetRow == preRow + moveValue) {
					if (isValidSquare(targetCol, targetRow)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
