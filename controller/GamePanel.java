package controller;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Bishop;
import model.Cannon;
import model.Elephant;
import model.King;
import model.Knight;
import model.Mouse;
import model.Pawn;
import model.Piece;
import model.Rook;
import model.Type;
import view.Board;
import view.GameFrame;
import view.Observer;
import view.StartingView;

public class GamePanel extends JPanel {
	public static final int WIDTH = 780;
	public static final int HEIGHT = 600;
	Board board = new Board();
	Mouse mouse = new Mouse();
	GameFrame gs;
	GameState state;

	// Button
	JButton surrender = new JButton("Đầu hàng");
	JButton quit = new JButton("Thoát");

	// PIECES
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	protected Piece activeP, checkingP;

	private List<Observer> observers = new ArrayList<>();

	// COLOR
	public static final int RED = 0;
	public static final int BLACK = 1;
	public int currentColor = RED;

	// BOOLEANS
	boolean canMove;
	boolean validSquare;
	boolean gameover;

	public GamePanel(GameFrame gs) {
		this.gs = gs;
		this.state = new WaitingState();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		setLayout(null);
		add(surrender);
		add(quit);
		surrender.setBounds(560, 550, 90, 30);
		quit.setBounds(670, 550, 90, 30);

		init();

		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		setPieces();
		copyPieces(pieces, simPieces);

	}

	private void init() {
		surrender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameover = true;
				surrender.setEnabled(false);
				changePlayer();
				notifyObservers();
			}
		});
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gs.dispose();
				new StartingView();
			}
		});
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void play() {
		state.handle();
	}

	public void update() {
		if (gameover == false) {
			/// MOUSE BUTTON CLICK ///
			if (mouse.clicked) {
				mouse.clicked = false; // Reset the clicked flag
				if (activeP == null) {
					// If the activeP is null, check if you can pick up a piece
					for (Piece piece : simPieces) {
						// If the mouse is on an ally piece, pick it up as the activeP
						if (piece.color == currentColor && piece.col == mouse.x / Board.SQUARE_SIZE
								&& piece.row == mouse.y / Board.SQUARE_SIZE) {
							activeP = piece;
						}
					}
				} else {
					// If the player is holding a piece, simulate the move
					if (canMove && validSquare) {
						// MOVE COFIRMED
						// Update the piece list in case a piece has been captured and removed during
						// the simulation
						copyPieces(simPieces, pieces);
						// Move the piece to the new position if the move is valid
						activeP.updatePosition();
						if (isKingInCheck() && isCheckmate()) {
							gameover = true;
						} else {
							changePlayer();
						}
					} else {
						// Reset the piece position if the move is invalid
						activeP.resetPosition();
					}
					activeP = null;
				}
			}
			if (activeP != null) {
				simulate();
			}
//			notifyObservers();
		}
		repaint();
	}

	private void simulate() {
		canMove = false;
		validSquare = false;
		// Reset the piece list in every loop
		// This is bassicaly for restoring the removed piece during the simulation
		copyPieces(pieces, simPieces);
		// If a piece is being held, update its position
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		// Check if the piece is hovering over a reachable square
		if (activeP.canMove(activeP.col, activeP.row)) {
			canMove = true;
			// If hitting a piece, remove it from the list
			if (activeP.hittingP != null) {
				simPieces.remove(activeP.hittingP.getIndex());
			}
			if (isIllegal(activeP) == false && opponentCanCaptureKing() == false) {
				validSquare = true;
			}
		}
	}

	private boolean isIllegal(Piece king) {
		if (king.type == Type.KING) {
			for (Piece piece : simPieces) {
				if (piece != king && piece.color != king.color && piece.canMove(king.col, king.row)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isKingInCheck() {
		Piece king = getKing(true);
		if (activeP.canMove(king.col, king.row)) {
			checkingP = activeP;
			return true;
		} else {
			checkingP = null;
		}
		return false;
	}

	public boolean opponentCanCaptureKing() {
		Piece king = getKing(false);
		for (Piece piece : simPieces) {
			if (piece.color != king.color && piece.canMove(king.col, king.row)) {
				return true;
			}
		}
		return false;
	}

	private Piece getKing(boolean opponent) {

		Piece king = null;
		for (Piece piece : simPieces) {
			if (opponent) {
				if (piece.type == Type.KING && piece.color != currentColor) {
					king = piece;
				}
			} else {
				if (piece.type == Type.KING && piece.color == currentColor) {
					king = piece;
				}
			}
		}
		return king;
	}

	private boolean isCheckmate() {
		Piece king = getKing(true);
		if (kingCanMove(king)) {
			return false;
		} else {

			// Nhưng nếu vẫn còn cách
			// Kiểm tra xem có thể chặn đợt tấn công bằng quân của bạn không?

			// Kiểm tra vị trí của quân khác và quân tướng
			int colDiff = Math.abs(checkingP.col - king.col);
			int rowDiff = Math.abs(checkingP.row - king.row);

			// Kiểm tra đợt tấn công từ hướng trên hoặc dưới
			if (colDiff == 0) {
				// Kiểm tra xem có quân nào chiếu bên trên không
				if (checkingP.row < king.row) {
					for (int row = checkingP.row; row < king.row; row++) {
						for (Piece piece : simPieces) {
							if (piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row)) {
								return false;
							}
						}
					}
				}
				// Kiểm tra xem có quân nào chiếu bên dưới không
				if (checkingP.row > king.row) {
					for (int row = checkingP.row; row > king.row; row--) {
						for (Piece piece : simPieces) {
							if (piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row)) {
								return false;
							}
						}
					}
				}
			} else if (rowDiff == 0) {
				// Kiểm tra đợt tấn công từ hướng trái hoặc phải
				if (checkingP.col < king.col) {
					// Kiểm tra xem có quân nào chiếu bên trái không
					for (int col = checkingP.col; col < king.col; col++) {
						for (Piece piece : simPieces) {
							if (piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row)) {
								return false;
							}
						}
					}
				}
				if (checkingP.col > king.col) {
					// Kiểm tra xem có quân nào chiếu bên phải không
					for (int col = checkingP.col; col > king.col; col--) {
						for (Piece piece : simPieces) {
							if (piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row)) {
								return false;
							}
						}
					}
				}
			} else {
			}
		}
		return true;
	}

	private boolean kingCanMove(Piece king) {
		// Mô phỏng các ô mà quân tướng có thể di chuyển
		if (isValidMove(king, 0, -1)) {
			return true;
		}
		if (isValidMove(king, 0, 1)) {
			return true;
		}
		if (isValidMove(king, 1, 0)) {
			return true;
		}
		if (isValidMove(king, -1, 0)) {
			return true;
		}

		return false;
	}

	private boolean isValidMove(Piece king, int colPlus, int rowPlus) {
		boolean isValidMove = false;
		// Cập nhật vị trí quân tướng ở lần thứ 2
		king.col += colPlus;
		king.row += rowPlus;

		if (king.canMove(king.col, king.row)) {
			if (king.hittingP != null) {
				simPieces.remove(king.hittingP.getIndex());
			}
			if (isIllegal(king) == false) {
				isValidMove = true;
			}
		}
		// Trả lại vị trí của quân tướng và xếp lại quân cờ
		king.resetPosition();
		copyPieces(pieces, simPieces);

		return isValidMove;
	}

	public void setPieces() {
		// Red team
		pieces.add(new Pawn(RED, 0, 6));
		pieces.add(new Pawn(RED, 2, 6));
		pieces.add(new Pawn(RED, 4, 6));
		pieces.add(new Pawn(RED, 6, 6));
		pieces.add(new Pawn(RED, 8, 6));
		pieces.add(new Cannon(RED, 1, 7));
		pieces.add(new Cannon(RED, 7, 7));
		pieces.add(new Rook(RED, 0, 9));
		pieces.add(new Rook(RED, 8, 9));
		pieces.add(new Knight(RED, 1, 9));
		pieces.add(new Knight(RED, 7, 9));
		pieces.add(new Elephant(RED, 2, 9));
		pieces.add(new Elephant(RED, 6, 9));
		pieces.add(new Bishop(RED, 3, 9));
		pieces.add(new Bishop(RED, 5, 9));
		pieces.add(new King(RED, 4, 9));

		// Black team
		pieces.add(new Pawn(BLACK, 0, 3));
		pieces.add(new Pawn(BLACK, 2, 3));
		pieces.add(new Pawn(BLACK, 4, 3));
		pieces.add(new Pawn(BLACK, 6, 3));
		pieces.add(new Pawn(BLACK, 8, 3));
		pieces.add(new Cannon(BLACK, 1, 2));
		pieces.add(new Cannon(BLACK, 7, 2));
		pieces.add(new Rook(BLACK, 0, 0));
		pieces.add(new Rook(BLACK, 8, 0));
		pieces.add(new Knight(BLACK, 1, 0));
		pieces.add(new Knight(BLACK, 7, 0));
		pieces.add(new Elephant(BLACK, 2, 0));
		pieces.add(new Elephant(BLACK, 6, 0));
		pieces.add(new Bishop(BLACK, 3, 0));
		pieces.add(new Bishop(BLACK, 5, 0));
		pieces.add(new King(BLACK, 4, 0));

	}

	public void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		target.clear();
		for (int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
	}

	public void changePlayer() {
		if (currentColor == RED) {
			currentColor = BLACK;
		} else {
			currentColor = RED;
		}
		activeP = null;
	}

	public boolean isGameOver() {
		return gameover;
	}

	public int getCurrentColor() {
		return currentColor;
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// BOARD
		board.draw(g2);

		// PIECES
		for (Piece p : simPieces) {
			p.draw(g2);
		}

		if (activeP != null) {
			if (canMove) {
				if (isIllegal(activeP) || opponentCanCaptureKing()) {
					g2.setColor(Color.gray);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE,
							Board.SQUARE_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				} else {
					g2.setColor(Color.green);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE,
							Board.SQUARE_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
			}
			// Draw the active piece in the end so it won't be hidden by the board or the
			// colored square
			activeP.draw(g2);
		}
		// STATUS MESSAGE
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Time New Roman", Font.PLAIN, 20));
		g2.setColor(Color.white);
		if (gameover == false) {
			if (currentColor == RED) {
				play();
				g2.drawString("Đến lượt quân đỏ", 580, 400);
				if (checkingP != null && checkingP.color == BLACK) {
					setState(new KingInCheckState());
					play(); // In ra trạng thái "Tướng đang bị chiếu!"
					g2.setColor(Color.red);
					g2.drawString("Tướng đang bị chiếu!", 580, 450);
				}
			} else {
				play();
				g2.drawString("Đến lượt quân đen", 580, 200);
				if (checkingP != null && checkingP.color == RED) {
					setState(new KingInCheckState());
					play(); // In ra trạng thái "Tướng đang bị chiếu!"
					g2.setColor(Color.red);
					g2.drawString("Tướng đang bị chiếu!", 580, 250);
				}
			}
		} else
			setState(new EndGameState());
			play(); // In ra trạng thái "Trò chơi đã kết thúc."
//			String s = "";
//			if (currentColor == RED) {
//				s = "Đội đỏ đã chiến thắng";
//			} else {
//				s = "Đội đen đã chiến thắng";
//			}
//			g2.setFont(new Font("UTM HelvetIns", Font.PLAIN, 40));
//			g2.setColor(Color.green);
//			g2.drawString(s, 90, 320);

	}
}
