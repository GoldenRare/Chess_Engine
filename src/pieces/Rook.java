package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;
import utility.Position;

public class Rook extends Pieces {
	
	public double value = 50; //Unsure
	
	public Rook(boolean isWhite, int i, int j) {
		
		super(isWhite, i, j);
		super.pieceType = "ROOK";
		super.pieceValue = 500;
		super.hashIndex = (isWhite) ? 3 : 9;
		
	}
	
	public void printPiece(JButton b) {
		
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_rook.png");
			Image scaledImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_rook.png");
			Image scaledImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		}
	}
	
	public GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b) {
		
		if (oldBoard.isWhiteToMove() != oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour()) {
			
			return oldBoard;
			
		}

		if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) && (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
			
			return oldBoard;
			
		}
		
		if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return oldBoard;
		
		if (((lastIndexI == toIndexI) && (toIndexJ >= 0 && toIndexJ <= 8)) || ((lastIndexJ == toIndexJ) && (toIndexI >= 0 && toIndexI <= 8))) {
			if ((lastIndexJ == toIndexJ) && (toIndexI >= 0 && toIndexI <= 8)) {
				if (lastIndexI < toIndexI) {
					int i = lastIndexI + 1;
					while (i < toIndexI) {
						
						if(oldBoard.getBoard()[i][toIndexJ] != null) {
							
							return oldBoard;
							
						}
						
						i++;
						
					}
				} else {
					int i = lastIndexI - 1;
					while (i > toIndexI) {
						
						if(oldBoard.getBoard()[i][toIndexJ] != null) {
							
							return oldBoard;
							
						}
						
						i--;
						
					}
				}
			} else {
				if (lastIndexJ < toIndexJ) {
					int i = lastIndexJ + 1;
					while (i < toIndexJ) {
						
						if(oldBoard.getBoard()[toIndexI][i] != null) {
							
							return oldBoard;
							
						}
						
						i++;
						
					}
				} else {
					int i = lastIndexJ - 1;
					while (i > toIndexJ) {
						
						if(oldBoard.getBoard()[toIndexI][i] != null) {
							
							return oldBoard;
							
						}
						
						i--;
						
					}
				}
			}
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
				if ((lastIndexI == 7) && (lastIndexJ == 7)) {
					
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1110);
					
				} else if ((lastIndexI == 7) && (lastIndexJ == 0)) {
					
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1101);
					
				}
				
			} else if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
				if ((lastIndexI == 0) && (lastIndexJ == 7)) {
					
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1011);
					
				} else if ((lastIndexI == 0) && (lastIndexJ == 0)) {
					
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b0111);
					
				}
			}
			
			GameBoard newBoard = oldBoard;
			removePiece(newBoard, toIndexI, toIndexJ);
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
			
		}
		
		//row * 8 + col
		return oldBoard;
		
	}
	
	public boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove) {
		
		if (isPieceColourNotTheSideToMove(lastIndexI, lastIndexJ, oldBoard)) return false;
		if (isToAndFromSquareTheSameColourPiece(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;
		if ((!checkPseudoMove) && (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard))) return false;
		
		if (((lastIndexI == toIndexI) && (toIndexJ >= 0 && toIndexJ <= 7)) || ((lastIndexJ == toIndexJ) && (toIndexI >= 0 && toIndexI <= 7))) {
			if ((lastIndexJ == toIndexJ) && (toIndexI >= 0 && toIndexI <= 7)) {
				if (lastIndexI < toIndexI) {
					int i = lastIndexI + 1;
					while (i < toIndexI) {
						
						if(oldBoard.getBoard()[i][toIndexJ] != null) {
							
							return false;
							
						}
						
						i++;
						
					}
				} else {
					int i = lastIndexI - 1;
					while (i > toIndexI) {
						
						if(oldBoard.getBoard()[i][toIndexJ] != null) {
							
							return false;
							
						}
						
						i--;
						
					}
				}
			} else {
				if (lastIndexJ < toIndexJ) {
					int i = lastIndexJ + 1;
					while (i < toIndexJ) {
						
						if(oldBoard.getBoard()[toIndexI][i] != null) {
							
							return false;
							
						}
						
						i++;
						
					}
				} else {
					int i = lastIndexJ - 1;
					while (i > toIndexJ) {
						
						if(oldBoard.getBoard()[toIndexI][i] != null) {
							
							return false;
							
						}
						
						i--;
						
					}
				}
			}
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
				if ((lastIndexI == 7) && (lastIndexJ == 7)) {
					
					updateWhiteKingKingsideHash(oldBoard);
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1110);
					
				} else if ((lastIndexI == 7) && (lastIndexJ == 0)) {
					
					updateWhiteKingQueensideHash(oldBoard);
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1101);
					
				}
				
			} else if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
				if ((lastIndexI == 0) && (lastIndexJ == 7)) {
					
					updateBlackKingKingsideHash(oldBoard);
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1011);
					
				} else if ((lastIndexI == 0) && (lastIndexJ == 0)) {
					
					updateBlackKingQueensideHash(oldBoard);
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b0111);
					
				}
			}
			
			updatePositionHash(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
			updateGameBoard(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
			this.square.setI(toIndexI);
			this.square.setJ(toIndexJ);
			
			return true;
			
		}
		return false;
		
	}
	
	private void updateWhiteKingKingsideHash(GameBoard board) {
		
		if ((board.getCastlingRights() & 0b0001) == 0b0001) {
			
			long newPositionHash = board.getPositionHash();
			newPositionHash ^= board.getZobristCastlingRights()[3];
			board.setPositionHash(newPositionHash);
			
		}
	}
	
	private void updateWhiteKingQueensideHash(GameBoard board) {
		
		if ((board.getCastlingRights() & 0b0010) == 0b0010) {
			
			long newPositionHash = board.getPositionHash();
			newPositionHash ^= board.getZobristCastlingRights()[2];
			board.setPositionHash(newPositionHash);
			
		}
	}
	
	private void updateBlackKingKingsideHash(GameBoard board) {
		
		if ((board.getCastlingRights() & 0b0100) == 0b0100) {
			
			long newPositionHash = board.getPositionHash();
			newPositionHash ^= board.getZobristCastlingRights()[1];
			board.setPositionHash(newPositionHash);
			
		}
	}
	
	private void updateBlackKingQueensideHash(GameBoard board) {
		
		if ((board.getCastlingRights() & 0b1000) == 0b1000) {
			
			long newPositionHash = board.getPositionHash();
			newPositionHash ^= board.getZobristCastlingRights()[0];
			board.setPositionHash(newPositionHash);
			
		}
	}
}
