package model;

import controller.GamePanel;

public class Elephant extends Piece{
	public Elephant(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		type = Type.ELEPHANT;
		if(color == GamePanel.RED) {
			image = getImage("/piece/r_ELEPHANT");
		}else {
			image = getImage("/piece/b_ELEPHANT");
		}
	}
	@Override
	public boolean isWithinBoard(int targetCol, int targetRow) {
		if (targetCol >= 0 && targetCol <= 8 && targetRow >= 0 && targetRow <= 4 || 
				targetCol >= 0 && targetCol <= 8 && targetRow >= 5 && targetRow <= 9) {
			return true;
		}
		return false;
	}
	public boolean canMove(int targetCol, int targetRow) {
		if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			if(Math.abs(targetCol - preCol) == 2 && Math.abs(targetRow - preRow) == 2) {
				if(isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow) == false) {
					return true;
				}
			}
		}
		return false;
	}
}
