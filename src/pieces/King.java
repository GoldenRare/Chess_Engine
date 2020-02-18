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
		
		// Consider adding new function for this
		// Consider bitboards
		// If castle is correct, castle does not turn off until next king move
		// Can currently castle through check
		
		if (oldBoard.isWhiteToMove() != oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour()) {
			
			return oldBoard;
			
		}
		
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
			
			if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
				
				oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1100);
				
			} else {
				
				oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b0011);
				
			}
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
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
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
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				//row * 8 + col
				return newBoard;
			}
		}
		
		//row * 8 + col
		return oldBoard;
		
	}
	
	public Square getSquare() {
		
		return this.square;
		
	}
}
