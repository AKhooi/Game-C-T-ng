package model;

import controller.GamePanel;

public class Cannon extends Piece {
	public Cannon(int color, int col, int row) {
		super(color, col, row);
		// TODO Auto-generated constructor stub
		type = Type.CANNON;
		if (color == GamePanel.RED) {
			image = getImage("/piece/r_CANNON");
		} else {
			image = getImage("/piece/b_CANNON");
		}
		
		bl = new IsWithinBoard();
		
	}

	public boolean canMove(int targetCol, int targetRow) {
		if (isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			if (targetCol != preCol && targetRow != preRow) {
				// Pháo chỉ có thể di chuyển theo hàng ngang hoặc dọc
				return false;
			}

			int directionCol = Integer.compare(targetCol, preCol);
			int directionRow = Integer.compare(targetRow, preRow);
			int col = preCol + directionCol;
			int row = preRow + directionRow;
			boolean hasBarrier = false;

			while (col != targetCol || row != targetRow) {
				if (getHittingP(col, row) != null) {
					if (hasBarrier) {
						// Có nhiều hơn một quân cản
						return false;
					}
					hasBarrier = true;
				}
				col += directionCol;
				row += directionRow;
			}

			Piece targetPiece = getHittingP(targetCol, targetRow);

			if (targetPiece == null) {
				// Không có quân cờ tại ô đích, di chuyển bình thường
				if(!hasBarrier && isValidSquare(targetCol, targetRow))
				return true;
			} else {
				// Có quân cờ tại ô đích, cần phải có một quân cản giữa đường
				if(hasBarrier && isValidSquare(targetCol, targetRow))
				return true;

			}
		}
		return false;
	}
}
