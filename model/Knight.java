package model;

import controller.GamePanel;

public class Knight extends Piece {

	public Knight(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		type = Type.KNIGHT;
		if (color == GamePanel.RED) {
			image = getImage("/piece/r_KNIGHT");
		} else {
			image = getImage("/piece/b_KNIGHT");
		}
	}

	public boolean canMove(int targetCol, int targetRow) {
		if (isWithinBoard(targetCol, targetRow)) {
			// Kiểm tra các nước đi của quân mã
			int[][] moves = { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 } };

			// Vị trí cản đường
			int[][] blockers = { { 1, 0 }, { 1, 0 }, { -1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 0, 1 }, { 0, -1 } };

			for (int i = 0; i < moves.length; i++) {
				int newCol = preCol + moves[i][0];
				int newRow = preRow + moves[i][1];
				int blockCol = preCol + blockers[i][0];
				int blockRow = preRow + blockers[i][1];

				if (targetCol == newCol && targetRow == newRow) {
					// Kiểm tra vị trí cản đường
					if (getHittingP(blockCol, blockRow) == null) {
						if (isValidSquare(targetCol, targetRow)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
