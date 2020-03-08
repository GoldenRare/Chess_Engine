package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;
import board.Square;
import utility.Position;

public class King extends Pieces {

	public double value = 10; //Unsure
	
	public King(boolean isWhite, int i, int j) {
		
		super(isWhite, i, j);
		super.pieceType = "KING";
		
	}
	
	public void printPiece(JButton b) {
		
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_king.png");
			Image scaledImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_king.png");
			Image scaledImg = img.getImage().getScaledInstance(95, 95, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		}	
	}
	
	public GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b) {
		
		if (oldBoard.isWhiteToMove() != oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour())
		
		if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) && (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
			return oldBoard;
					
		}
		
		int tempI = this.square.getI();
		int tempJ = this.square.getJ();
		this.square.setI(toIndexI);
		this.square.setJ(toIndexJ);
		
		if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) {
			
			this.square.setI(tempI);
			this.square.setJ(tempJ);
			return oldBoard;
			
		}
		
		this.square.setI(tempI);
		this.square.setJ(tempJ);
		
		// MAKE SURE TO TAKE IN TO ACCOUNT SAME SQUARE MOVES
		// Technically not Magnitude 
		int magnitudeOfI = (toIndexI - lastIndexI) * (toIndexI - lastIndexI);
		int magnitudeOfJ = (toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ);
		int magnitudeOfMove = magnitudeOfI + magnitudeOfJ;
		
		if ((magnitudeOfMove == 1) || (magnitudeOfMove == 2)) {
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			disableCastlingRights(lastIndexI, lastIndexJ, oldBoard);
			GameBoard newBoard = oldBoard;
			newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
			printPiece(b.get(toIndexI * 8 + toIndexJ));
			newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
			b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
			newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
			newBoard.setEnPassantSquare(-1, -1);
			this.square.setI(toIndexI);
			this.square.setJ(toIndexJ);
			
			//row * 8 + col
			return newBoard;
			
		} else if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			// Can disable castling rights after a completed castle
			if ((lastIndexI == 7) && (lastIndexJ == 4) && (toIndexI == 7) && (toIndexJ == 6) && ((oldBoard.getCastlingRights() & 0b0001) == 0b0001)) { // Attempting Kingside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[7][5] != null) || (oldBoard.getBoard()[7][6] != null)) {
					
					return oldBoard;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(7, 4), true) || Square.isSquareAttacked(oldBoard, new Square(7, 5), true)
						|| Square.isSquareAttacked(oldBoard, new Square(7, 6), true)) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, true, false, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[7][5] = newBoard.getBoard()[7][7];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[7][5].printPiece(b.get(7 * 8 + 5));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[7][7] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				b.get(7 * 8 + 7).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b1100);
				newBoard.getBoard()[7][5].getSquare().setI(7);
				newBoard.getBoard()[7][5].getSquare().setJ(5);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				System.out.println(newBoard.getBoard()[7][5].getSquare().getI() + " " + newBoard.getBoard()[7][5].getSquare().getJ());
				//row * 8 + col
				return newBoard;
			} else if ((lastIndexI == 7) && (lastIndexJ == 4) && (toIndexI == 7) && (toIndexJ == 2) && ((oldBoard.getCastlingRights() & 0b0010) == 0b0010)) { // Attempting Queenside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[7][3] != null) || (oldBoard.getBoard()[7][2] != null) || (oldBoard.getBoard()[7][1] != null)) {
					
					return oldBoard;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(7, 4), true) || Square.isSquareAttacked(oldBoard, new Square(7, 3), true)
						|| Square.isSquareAttacked(oldBoard, new Square(7, 2), true)) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, true, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[7][3] = newBoard.getBoard()[7][0];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[7][3].printPiece(b.get(7 * 8 + 3));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[7][0] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				b.get(7 * 8 + 0).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b1100);
				newBoard.getBoard()[7][3].getSquare().setI(7);
				newBoard.getBoard()[7][3].getSquare().setJ(3);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return newBoard;
			}
		} else if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			// Can disable castling rights after a completed castle
			if ((lastIndexI == 0) && (lastIndexJ == 4) && (toIndexI == 0) && (toIndexJ == 6) && ((oldBoard.getCastlingRights() & 0b0100) == 0b0100)) { // Attempting Kingside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[0][5] != null) || (oldBoard.getBoard()[0][6] != null)) {
					
					return oldBoard;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(0, 4), false) || Square.isSquareAttacked(oldBoard, new Square(0, 5), false)
						|| Square.isSquareAttacked(oldBoard, new Square(0, 6), false)) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, true, false, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[0][5] = newBoard.getBoard()[0][7];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[0][5].printPiece(b.get(0 * 8 + 5));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[0][7] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				b.get(0 * 8 + 7).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b0011);
				newBoard.getBoard()[0][5].getSquare().setI(0);
				newBoard.getBoard()[0][5].getSquare().setJ(5);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return newBoard;
			} else if ((lastIndexI == 0) && (lastIndexJ == 4) && (toIndexI == 0) && (toIndexJ == 2) && ((oldBoard.getCastlingRights() & 0b1000) == 0b1000)) { // Attempting Queenside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[0][3] != null) || (oldBoard.getBoard()[0][2] != null) || (oldBoard.getBoard()[0][1] != null)) {
					
					return oldBoard;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(0, 4), false) || Square.isSquareAttacked(oldBoard, new Square(0, 3), false)
						|| Square.isSquareAttacked(oldBoard, new Square(0, 2), false)) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, true, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[0][3] = newBoard.getBoard()[0][0];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[0][3].printPiece(b.get(0 * 8 + 3));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[0][0] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				b.get(0 * 8 + 0).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b0011);
				newBoard.getBoard()[0][3].getSquare().setI(0);
				newBoard.getBoard()[0][3].getSquare().setJ(3);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return newBoard;
			}
		}
		
		//row * 8 + col
		return oldBoard;
		
	}
	
	public boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove) {
		
		if (isPieceColourNotTheSideToMove(lastIndexI, lastIndexJ, oldBoard)) return false;
		if (isToAndFromSquareTheSameColourPiece(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;	
		if ((!checkPseudoMove) && (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard))) return false;
		
		int magnitudeOfI = (toIndexI - lastIndexI) * (toIndexI - lastIndexI);
		int magnitudeOfJ = (toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ);
		int magnitudeOfMove = magnitudeOfI + magnitudeOfJ;
		
		if ((magnitudeOfMove == 1) || (magnitudeOfMove == 2)) {
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			disableCastlingRights(lastIndexI, lastIndexJ, oldBoard);
			updateGameBoard(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
			this.square.setI(toIndexI);
			this.square.setJ(toIndexJ);
			return true;
			
		} else if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			// Can disable castling rights after a completed castle
			if ((lastIndexI == 7) && (lastIndexJ == 4) && (toIndexI == 7) && (toIndexJ == 6) && ((oldBoard.getCastlingRights() & 0b0001) == 0b0001)) { // Attempting Kingside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[7][5] != null) || (oldBoard.getBoard()[7][6] != null)) {
					
					return false;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(7, 4), true) || Square.isSquareAttacked(oldBoard, new Square(7, 5), true)
						|| Square.isSquareAttacked(oldBoard, new Square(7, 6), true)) {
					
					return false;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, true, false, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[7][5] = newBoard.getBoard()[7][7];
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[7][7] = null;
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b1100);
				newBoard.getBoard()[7][5].getSquare().setI(7);
				newBoard.getBoard()[7][5].getSquare().setJ(5);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return true;
			} else if ((lastIndexI == 7) && (lastIndexJ == 4) && (toIndexI == 7) && (toIndexJ == 2) && ((oldBoard.getCastlingRights() & 0b0010) == 0b0010)) { // Attempting Queenside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[7][3] != null) || (oldBoard.getBoard()[7][2] != null) || (oldBoard.getBoard()[7][1] != null)) {
					
					return false;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(7, 4), true) || Square.isSquareAttacked(oldBoard, new Square(7, 3), true)
						|| Square.isSquareAttacked(oldBoard, new Square(7, 2), true)) {
					
					return false;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, true, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[7][3] = newBoard.getBoard()[7][0];
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[7][0] = null;
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b1100);
				newBoard.getBoard()[7][3].getSquare().setI(7);
				newBoard.getBoard()[7][3].getSquare().setJ(3);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return true;
			}
		} else if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			// Can disable castling rights after a completed castle
			if ((lastIndexI == 0) && (lastIndexJ == 4) && (toIndexI == 0) && (toIndexJ == 6) && ((oldBoard.getCastlingRights() & 0b0100) == 0b0100)) { // Attempting Kingside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[0][5] != null) || (oldBoard.getBoard()[0][6] != null)) {
					
					return false;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(0, 4), false) || Square.isSquareAttacked(oldBoard, new Square(0, 5), false)
						|| Square.isSquareAttacked(oldBoard, new Square(0, 6), false)) {
					
					return false;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, true, false, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[0][5] = newBoard.getBoard()[0][7];
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[0][7] = null;
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b0011);
				newBoard.getBoard()[0][5].getSquare().setI(0);
				newBoard.getBoard()[0][5].getSquare().setJ(5);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return true;
			} else if ((lastIndexI == 0) && (lastIndexJ == 4) && (toIndexI == 0) && (toIndexJ == 2) && ((oldBoard.getCastlingRights() & 0b1000) == 0b1000)) { // Attempting Queenside castle
				
				// Need a rook check?
				if ((oldBoard.getBoard()[0][3] != null) || (oldBoard.getBoard()[0][2] != null) || (oldBoard.getBoard()[0][1] != null)) {
					
					return false;
					
				}
				
				if (Square.isSquareAttacked(oldBoard, new Square(0, 4), false) || Square.isSquareAttacked(oldBoard, new Square(0, 3), false)
						|| Square.isSquareAttacked(oldBoard, new Square(0, 2), false)) {
					
					return false;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, true, false));
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				newBoard.getBoard()[0][3] = newBoard.getBoard()[0][0];
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				newBoard.getBoard()[0][0] = null;
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				newBoard.setCastlingRights(newBoard.getCastlingRights() & 0b0011);
				newBoard.getBoard()[0][3].getSquare().setI(0);
				newBoard.getBoard()[0][3].getSquare().setJ(3);
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return true;
			}
		}
		
		//row * 8 + col
		return false;
		
	}

	private void disableCastlingRights(int lastIndexI, int lastIndexJ, GameBoard board) {
		if (board.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			board.setCastlingRights(board.getCastlingRights() & 0b1100);
			
		} else {
			
			board.setCastlingRights(board.getCastlingRights() & 0b0011);
			
		}
	}
	
	public Square getSquare() {
		
		return this.square;
		
	}
}
