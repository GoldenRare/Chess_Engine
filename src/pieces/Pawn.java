package pieces;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;
import board.Square;
import board_indexing.Ranks;

public class Pawn extends Pieces {

	public double value = 10; //Unsure
	
	public Pawn(boolean isWhite) {
		
		super(isWhite);
		super.pieceType = "PAWN";
		
	}
	
	public void printPiece(JButton b) {
	
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_pawn.png");
			Image scaledImg = img.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_pawn.png");
			Image scaledImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		}
	}
	
	public GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b) {
		// Current implementation will make pawn go straight through opposing pawn
		// Account for captures, they can also occur on home rank
		// For en Passant make sure the check is for opposing pawns
		
		boolean wasEnPassantSet = false;
		
		if (oldBoard.isWhiteToMove() != oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour()) {
			
			return oldBoard;
			
		}
		
		if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) && (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
			
			return oldBoard;
			
		}

		if (Arrays.asList(oldBoard.getBoard()[Ranks.RANK2.ordinal()]).contains(oldBoard.getBoard()[lastIndexI][lastIndexJ]) && oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			if (((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) || ((toIndexI == lastIndexI - 2) && (toIndexJ == lastIndexJ))) {
				if ((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) {
					if (oldBoard.getBoard()[toIndexI][toIndexJ] != null) {
						
						return oldBoard;
						
					}
				} else if ((toIndexI == lastIndexI - 2) && (toIndexJ == lastIndexJ)) {
					if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) || (oldBoard.getBoard()[toIndexI + 1][toIndexJ] != null)) {
						
						return oldBoard;
						
					}
					
					int i = toIndexI;
					int jRight = toIndexJ + 1;
					int jLeft = toIndexJ - 1;
					
					if ((jRight <= 7) && (oldBoard.getBoard()[i][jRight] != null) && (oldBoard.getBoard()[i][jRight].pieceType().equals("PAWN")) && (!oldBoard.getBoard()[i][jRight].isWhite)) {
						
						oldBoard.setEnPassantSquare(i + 1, toIndexJ);
						wasEnPassantSet = true;
						
					} else if ((jLeft >= 0) && (oldBoard.getBoard()[i][jLeft] != null) && (oldBoard.getBoard()[i][jLeft].pieceType().equals("PAWN")) && (!oldBoard.getBoard()[i][jLeft].isWhite)) {
						
						oldBoard.setEnPassantSquare(i + 1, toIndexJ);
						wasEnPassantSet = true;
						
					}
				}
				
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				
				if (!wasEnPassantSet) {
					
					newBoard.setEnPassantSquare(-1, -1);
					
				}
				
				//row * 8 + col
				return newBoard;
			} else if ((toIndexI == lastIndexI - 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				
				//row * 8 + col
				return newBoard;
			}	 
		} else if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) {
				if (oldBoard.getBoard()[toIndexI][toIndexJ] != null) {
					
					return oldBoard;
					
				}
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				
				//row * 8 + col
				return newBoard;
				
			} else if ((toIndexI == lastIndexI - 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				
				Square enPassantSquare = oldBoard.getEnPassantSquare();
				if ((toIndexI == enPassantSquare.getI()) && (toIndexJ == enPassantSquare.getJ())) {
					
					GameBoard newBoard = oldBoard;
					newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
					printPiece(b.get(toIndexI * 8 + toIndexJ));
					newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
					b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
					newBoard.getBoard()[enPassantSquare.getI() + 1][enPassantSquare.getJ()] = null;
					b.get((enPassantSquare.getI() + 1) * 8 + enPassantSquare.getJ()).setIcon(null);
					newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
					newBoard.setEnPassantSquare(-1, -1);
					
					//row * 8 + col
					return newBoard;
					
				}
				
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				
				//row * 8 + col
				return newBoard;
			}
		} else if (Arrays.asList(oldBoard.getBoard()[Ranks.RANK7.ordinal()]).contains(oldBoard.getBoard()[lastIndexI][lastIndexJ]) && !oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			if (((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) || ((toIndexI == lastIndexI + 2) && (toIndexJ == lastIndexJ))) {
				if ((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) {
					if (oldBoard.getBoard()[toIndexI][toIndexJ] != null) {
						
						return oldBoard;
						
					}
				} else if ((toIndexI == lastIndexI + 2) && (toIndexJ == lastIndexJ)) {
					if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) || (oldBoard.getBoard()[toIndexI - 1][toIndexJ] != null)) {
						
						return oldBoard;
						
					}
					
					int i = toIndexI;
					int jRight = toIndexJ + 1;
					int jLeft = toIndexJ - 1;
					
					if ((jRight <= 7) && (oldBoard.getBoard()[i][jRight] != null) && (oldBoard.getBoard()[i][jRight].pieceType().equals("PAWN")) && (oldBoard.getBoard()[i][jRight].isWhite)) {
						
						oldBoard.setEnPassantSquare(i - 1, toIndexJ);
						wasEnPassantSet = true;
						
					} else if ((jLeft >= 0) && (oldBoard.getBoard()[i][jLeft] != null) && (oldBoard.getBoard()[i][jLeft].pieceType().equals("PAWN")) && (oldBoard.getBoard()[i][jLeft].isWhite)) {
						
						oldBoard.setEnPassantSquare(i - 1, toIndexJ);
						wasEnPassantSet = true;
						
					}
				}
				
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				
				if (!wasEnPassantSet) {
					
					newBoard.setEnPassantSquare(-1, -1);
					
				}
				
				//row * 8 + col
				return newBoard;
			} else if ((toIndexI == lastIndexI + 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				
				//row * 8 + col
				return newBoard;
			}	
		} else if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) {
				if (oldBoard.getBoard()[toIndexI][toIndexJ] != null) {
					
					return oldBoard;
					
				}
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				
				//row * 8 + col
				return newBoard;
				
			} else if ((toIndexI == lastIndexI + 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				
				Square enPassantSquare = oldBoard.getEnPassantSquare();
				if ((toIndexI == enPassantSquare.getI()) && (toIndexJ == enPassantSquare.getJ())) {
					
					GameBoard newBoard = oldBoard;
					newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
					printPiece(b.get(toIndexI * 8 + toIndexJ));
					newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
					b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
					newBoard.getBoard()[enPassantSquare.getI() - 1][enPassantSquare.getJ()] = null;
					b.get((enPassantSquare.getI() - 1) * 8 + enPassantSquare.getJ()).setIcon(null);
					newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
					newBoard.setEnPassantSquare(-1, -1);
					
					//row * 8 + col
					return newBoard;
					
				}
				
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				newBoard.setEnPassantSquare(-1, -1);
				
				//row * 8 + col
				return newBoard;
			}
		}
		//row * 8 + col
		return oldBoard;
		
	}
}
