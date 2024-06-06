package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.text.TabableView;

import controller.GamePanel;
import view.Board;

public abstract class Piece {

	public Type type;
	public BufferedImage image;
	public int x, y;
	public int col, row, preCol, preRow;
	public int color;
	public Piece hittingP;
	public int countMoved = 0;
	

	public Piece(int color, int col, int row) {
		this.color = color;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		preCol = col;
		preRow = row;
	}

	// Gán hình cho quân cờ
	public BufferedImage getImage(String imagePath) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public int getX(int col) {
		return col * Board.SQUARE_SIZE;
	}

	public int getY(int row) {

		return row * Board.SQUARE_SIZE;
	}

	public int getCol(int x) {
		return (x + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
	}

	public int getRow(int y) {
		return (y + Board.HALF_SQUARE_SIZE) / Board.SQUARE_SIZE;
	}

	public int getIndex() {
		for (int index = 0; index < GamePanel.simPieces.size(); index++) {
			if (GamePanel.simPieces.get(index) == this) {
				return index;
			}
		}
		return 0;
	}

	public void updatePosition() {
		x = getX(col);
		y = getY(row);
		preCol = getCol(x);
		preRow = getRow(y);
		countMoved++;
	}

	public void resetPosition() {
		col = preCol;
		row = preRow;
		x = getX(col);
		y = getY(row);
	}

	public boolean canMove(int targetCol, int targetRow) {
		return false;
	}

	public boolean isWithinBoard(int targetCol, int targetRow) {
		if (targetCol >= 0 && targetCol <= 8 && targetRow >= 0 && targetRow <= 9) {
			return true;
		}
		return false;
	}
	//Kiểm tra ô đích có giống ô đang đứng không
	public boolean isSameSquare(int targetCol, int targetRow) {
		if (targetCol == preCol && targetRow == preRow) {
			return true;
		}
		return false;
	}
	//Kiểm tra có quân cờ nào đang ở vị trí đích không
	public Piece getHittingP(int targetCol, int targetRow) {
		for (Piece piece : GamePanel.simPieces) {
			if (piece.col == targetCol && piece.row == targetRow && piece != this) {
				return piece;
			}
		}
		return null;
	}
	//Kiểm tra có bắt được quân cờ ở vị trí đích không
	public boolean isValidSquare(int targetCol, int targetRow) {
		hittingP = getHittingP(targetCol, targetRow);
		if (hittingP == null) { // Ô này trống
			return true;
		} else { // Ô này đã có quân cờ
			if (hittingP.color != this.color) {// Nếu màu khác nhau thì sẽ bị bắt
				return true;
			} else {
				hittingP = null;
			}
		}
		return false;
	}
	
	//Kiểm tra quân cờ có trên đường thẳng không
	public boolean pieceIsOnStraightLine(int targetCol, int targetRow) {
		// Khi quân cờ di chuyển qua trái
		for (int c = preCol - 1; c > targetCol; c--) {
			for (Piece piece : GamePanel.simPieces) {
				if (piece.col == c && piece.row == targetRow) {
					hittingP = piece;
					return true;
				}

			}
		}
		// Khi quân cờ di chuyển qua phải
		for (int c = preCol + 1; c < targetCol; c++) {
			for (Piece piece : GamePanel.simPieces) {
				if (piece.col == c && piece.row == targetRow) {
					hittingP = piece;
					return true;
				}

			}
		}
		// Khi quân cờ di chuyển lên trên
		for (int r = preRow - 1; r > targetRow; r--) {
			for (Piece piece : GamePanel.simPieces) {
				if (piece.col == targetCol && piece.row == r) {
					hittingP = piece;
					return true;
				}
			}
		}
		// Khi quân cờ di chuyển xuống dưới
		for (int r = preRow + 1; r < targetRow; r++) {
			for (Piece piece : GamePanel.simPieces) {
				if (piece.col == targetCol && piece.row == r) {
					hittingP = piece;
					return true;
				}

			}
		}
		return false;
	}
	
	//Kiểm tra quân cờ có trên đường chéo không
	public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {
		if (targetRow < preRow) {
			// Lên trên qua trái
			for (int c = preCol - 1; c > targetCol; c--) {
				int diff = Math.abs(c - preCol);
				for (Piece piece : GamePanel.simPieces) {
					if (piece.col == c && piece.row == preRow - diff) {
						hittingP = piece;
						return true;
					}
				}
			}
			// Lên trên qua phải
			for (int c = preCol + 1; c < targetCol; c++) {
				int diff = Math.abs(c - preCol);
				for (Piece piece : GamePanel.simPieces) {
					if (piece.col == c && piece.row == preRow - diff) {
						hittingP = piece;
						return true;
					}
				}
			}
		}

		if (targetRow > preRow) {
			// Xuống dưới bên trái
			for (int c = preCol - 1; c > targetCol; c--) {
				int diff = Math.abs(c - preCol);
				for (Piece piece : GamePanel.simPieces) {
					if (piece.col == c && piece.row == preRow + diff) {
						hittingP = piece;
						return true;
					}
				}
			}
			// Xuống dưới bên phải
			for (int c = preCol + 1; c < targetCol; c++) {
				int diff = Math.abs(c - preCol);
				for (Piece piece : GamePanel.simPieces) {
					if (piece.col == c && piece.row == preRow + diff) {
						hittingP = piece;
						return true;
					}
				}
			}
		}
		return false;
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
	}
}
