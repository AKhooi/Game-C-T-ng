package model;

import controller.GamePanel;

public class Rook extends Piece {
	public Rook(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		type = Type.ROOK;
		if (color == GamePanel.RED) {
			image = getImage("/piece/r_ROOK");
		} else {
			image = getImage("/piece/b_ROOK");
		}
		
		bl = new IsWithinBoard();
		
	}

	public boolean canMove(int targetCol, int targetRow) {
		if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			// Quân xe có thể di chuyển miễn là cột hoặc hàng của nó giống nhau
			if (targetCol == preCol || targetRow == preRow) {
				if (isValidSquare(targetCol, targetRow) && pieceIsOnStraightLine(targetCol, targetRow) == false) {
					return true;
				}
			}
		}
		return false;
	}
}
