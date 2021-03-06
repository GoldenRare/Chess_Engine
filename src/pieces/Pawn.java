package pieces;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;
import board.Square;
import board_indexing.Ranks;
import utility.Position;

public class Pawn extends Pieces {
	
	public Pawn(boolean isWhite, int i, int j) {
		
		super(isWhite, i, j);
		super.pieceType = "PAWN";
		super.pieceValue = 100;
		super.hashIndex = (isWhite) ? 0 : 6;
		
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
	
	// MUST ADD BOOLEAN WASGAMESTATESET
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

		if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return oldBoard;
		
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
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
					
					if ((jRight <= 7) && (oldBoard.getBoard()[i][jRight] != null) && (oldBoard.getBoard()[i][jRight].pieceType().equals("PAWN")) && (!oldBoard.getBoard()[i][jRight].isWhite)) {
						
						oldBoard.setEnPassantSquare(i + 1, toIndexJ);
						wasEnPassantSet = true;
						
					} else if ((jLeft >= 0) && (oldBoard.getBoard()[i][jLeft] != null) && (oldBoard.getBoard()[i][jLeft].pieceType().equals("PAWN")) && (!oldBoard.getBoard()[i][jLeft].isWhite)) {
						
						oldBoard.setEnPassantSquare(i + 1, toIndexJ);
						wasEnPassantSet = true;
						
					}
				}
				
				if (!wasEnPassantSet) {
					
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
					oldBoard.setEnPassantSquare(-1, -1);
					
				}
				
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				
				//row * 8 + col
				return newBoard;
			} else if ((toIndexI == lastIndexI - 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
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
			}	 
		} else if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) {
				if (oldBoard.getBoard()[toIndexI][toIndexJ] != null) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
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
				
			} else if ((toIndexI == lastIndexI - 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				
				Square enPassantSquare = oldBoard.getEnPassantSquare();
				if ((toIndexI == enPassantSquare.getI()) && (toIndexJ == enPassantSquare.getJ())) {
					
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, true));
					GameBoard newBoard = oldBoard;
					newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
					printPiece(b.get(toIndexI * 8 + toIndexJ));
					newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
					b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
					newBoard.getBoard()[enPassantSquare.getI() + 1][enPassantSquare.getJ()] = null;
					b.get((enPassantSquare.getI() + 1) * 8 + enPassantSquare.getJ()).setIcon(null);
					newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
					newBoard.setEnPassantSquare(-1, -1);
					this.square.setI(toIndexI);
					this.square.setJ(toIndexJ);
					
					//row * 8 + col
					return newBoard;
					
				}
				
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
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
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
					
					if ((jRight <= 7) && (oldBoard.getBoard()[i][jRight] != null) && (oldBoard.getBoard()[i][jRight].pieceType().equals("PAWN")) && (oldBoard.getBoard()[i][jRight].isWhite)) {
						
						oldBoard.setEnPassantSquare(i - 1, toIndexJ);
						wasEnPassantSet = true;
						
					} else if ((jLeft >= 0) && (oldBoard.getBoard()[i][jLeft] != null) && (oldBoard.getBoard()[i][jLeft].pieceType().equals("PAWN")) && (oldBoard.getBoard()[i][jLeft].isWhite)) {
						
						oldBoard.setEnPassantSquare(i - 1, toIndexJ);
						wasEnPassantSet = true;
						
					}
				}
				
				
				if (!wasEnPassantSet) {
					
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
					oldBoard.setEnPassantSquare(-1, -1);
					
				}
				
				GameBoard newBoard = oldBoard;
				newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
				this.square.setI(toIndexI);
				this.square.setJ(toIndexJ);
				
				
				//row * 8 + col
				return newBoard;
			} else if ((toIndexI == lastIndexI + 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
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
			}	
		} else if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) {
				if (oldBoard.getBoard()[toIndexI][toIndexJ] != null) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
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
				
			} else if ((toIndexI == lastIndexI + 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				
				Square enPassantSquare = oldBoard.getEnPassantSquare();
				if ((toIndexI == enPassantSquare.getI()) && (toIndexJ == enPassantSquare.getJ())) {
					
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, true));
					GameBoard newBoard = oldBoard;
					newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
					printPiece(b.get(toIndexI * 8 + toIndexJ));
					newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
					b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
					newBoard.getBoard()[enPassantSquare.getI() - 1][enPassantSquare.getJ()] = null;
					b.get((enPassantSquare.getI() - 1) * 8 + enPassantSquare.getJ()).setIcon(null);
					newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
					newBoard.setEnPassantSquare(-1, -1);
					this.square.setI(toIndexI);
					this.square.setJ(toIndexJ);
					
					//row * 8 + col
					return newBoard;
					
				}
				
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return oldBoard;
					
				}
				
				oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
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
			}
		}
		//row * 8 + col
		return oldBoard;
		
	}
	public boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove) {return false;}
	public boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove, char promotionPiece) {
		
		// 2) Implement enPassant hashing 
		Square setEnPassantSquare = new Square(-1, -1);
		
		if ((toIndexI < 0) || (toIndexI > 7) || (toIndexJ < 0) || (toIndexJ > 7)) return false;
		if (isPieceColourNotTheSideToMove(lastIndexI, lastIndexJ, oldBoard)) return false;
		if (isToAndFromSquareTheSameColourPiece(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;
		if ((!checkPseudoMove) && (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, promotionPiece))) return false;
		
		if (canPawnMoveUpTwo(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare)) return validMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare);
		
		if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) {
				if (canPawnNotMoveUpOne(toIndexI, toIndexJ, oldBoard)) return false;
				if (isItAPromotionMove(lastIndexI, lastIndexJ, toIndexI, oldBoard)) return validPromotionMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare, promotionPiece); 
				return validMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare);
				
			} else if ((toIndexI == lastIndexI - 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				
				Square enPassantSquare = oldBoard.getEnPassantSquare();
				if ((toIndexI == enPassantSquare.getI()) && (toIndexJ == enPassantSquare.getJ())) {
					
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, true));
					updatePositionHash(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
					GameBoard newBoard = oldBoard;
					removePiece(newBoard, lastIndexI, toIndexJ);
					newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
					newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
					newBoard.getBoard()[enPassantSquare.getI() + 1][enPassantSquare.getJ()] = null;
					newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
					newBoard.setEnPassantSquare(-1, -1);
					this.square.setI(toIndexI);
					this.square.setJ(toIndexJ);
					
					//row * 8 + col
					return true;
					
				}
				
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return false;
					
				}
				if (isItAPromotionMove(lastIndexI, lastIndexJ, toIndexI, oldBoard)) return validPromotionMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare, promotionPiece);
				return validMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare);
			}
		} 
 
	
		if (!oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) {
				if (canPawnNotMoveUpOne(toIndexI, toIndexJ, oldBoard)) return false;
				if (isItAPromotionMove(lastIndexI, lastIndexJ, toIndexI, oldBoard)) return validPromotionMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare, promotionPiece);
				return validMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare);
				
			} else if ((toIndexI == lastIndexI + 1) && ((toIndexJ == lastIndexJ + 1) || (toIndexJ == lastIndexJ - 1))) {
				
				Square enPassantSquare = oldBoard.getEnPassantSquare();
				if ((toIndexI == enPassantSquare.getI()) && (toIndexJ == enPassantSquare.getJ())) {
					
					oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, true));
					updatePositionHash(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
					GameBoard newBoard = oldBoard;
					removePiece(newBoard, lastIndexI, toIndexJ);
					newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
					newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
					newBoard.getBoard()[enPassantSquare.getI() - 1][enPassantSquare.getJ()] = null;
					newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
					newBoard.setEnPassantSquare(-1, -1);
					this.square.setI(toIndexI);
					this.square.setJ(toIndexJ);
					
					//row * 8 + col
					return true;
					
				}
				
				if ((oldBoard.getBoard()[toIndexI][toIndexJ] == null) || (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
					return false;
					
				}
				
				if (isItAPromotionMove(lastIndexI, lastIndexJ, toIndexI, oldBoard)) return validPromotionMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare, promotionPiece);
				return validMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, setEnPassantSquare);
			}
		}

		return false;
		
	}

	private boolean canPawnMoveUpTwo(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, Square setEnPassantSquare) {
		
		boolean result = false;
		
		if (Arrays.asList(board.getBoard()[Ranks.RANK2.ordinal()]).contains(board.getBoard()[lastIndexI][lastIndexJ]) && board.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			if ((toIndexI == lastIndexI - 2) && (toIndexJ == lastIndexJ)) {
					
				if (canPawnNotMoveUpTwo(toIndexI, toIndexJ, board)) return result;
				setPotentialEnPassantSquare(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, setEnPassantSquare);
				result = true;
				
			} 	
		} else if (Arrays.asList(board.getBoard()[Ranks.RANK7.ordinal()]).contains(board.getBoard()[lastIndexI][lastIndexJ]) && !board.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			if ((toIndexI == lastIndexI + 2) && (toIndexJ == lastIndexJ)) {
				
				if (canPawnNotMoveUpTwoBlack(toIndexI, toIndexJ, board)) return result;
				setPotentialEnPassantSquareBlack(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, setEnPassantSquare);
				result = true;
				
			}
		}
		
		return result;

	}

	private boolean validMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board,
			Square setEnPassantSquare) {
		board.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false, false, false));
		updatePositionHash(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board);
		updateBoard(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, setEnPassantSquare);
		this.square.setI(toIndexI);
		this.square.setJ(toIndexJ);
		return true;
	}
	
	private boolean validPromotionMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board,
			Square setEnPassantSquare, char promotionPiece) {
		board.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false, false, false));
		board.setPositionHash(board.getPositionHash() ^ board.getZobristBlackToMove());
		updatePromotionBoard(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, setEnPassantSquare, promotionPiece);
		return true;
	}

	private void updateBoard(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, Square setEnPassantSquare) {
		
		removePiece(board, toIndexI, toIndexJ);
		board.getBoard()[toIndexI][toIndexJ] = board.getBoard()[lastIndexI][lastIndexJ];
		board.getBoard()[lastIndexI][lastIndexJ] = null;
		board.setWhiteToMove(!board.isWhiteToMove());
		board.setEnPassantSquare(setEnPassantSquare.getI(), setEnPassantSquare.getJ());
		
	}
	
	private void updatePromotionBoard(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, Square setEnPassantSquare, char promotionPiece) {
		
		removePiece(board, toIndexI, toIndexJ);
		removePiece(board, lastIndexI, lastIndexJ);
		choosePromotionPiece(toIndexI, toIndexJ, board, promotionPiece);
		board.getBoard()[lastIndexI][lastIndexJ] = null;
		board.setWhiteToMove(!board.isWhiteToMove());
		board.setEnPassantSquare(setEnPassantSquare.getI(), setEnPassantSquare.getJ());
		
	}

	// Potential indexOutOfBounds
	private void setPotentialEnPassantSquare(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, Square setEnPassantSquare) {
		
		int i = toIndexI;
		int jRight = toIndexJ + 1;
		int jLeft = toIndexJ - 1;
		
		if ((jRight <= 7) && (board.getBoard()[i][jRight] != null) && (board.getBoard()[i][jRight].pieceType().equals("PAWN")) && (!board.getBoard()[i][jRight].isWhite)) {
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristEnPassant()[toIndexJ]);
			setEnPassantSquare.setI(i + 1);
			setEnPassantSquare.setJ(toIndexJ);
			
		} else if ((jLeft >= 0) && (board.getBoard()[i][jLeft] != null) && (board.getBoard()[i][jLeft].pieceType().equals("PAWN")) && (!board.getBoard()[i][jLeft].isWhite)) {
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristEnPassant()[toIndexJ]);
			setEnPassantSquare.setI(i + 1);
			setEnPassantSquare.setJ(toIndexJ);
			
		}
	}
	
	// Potential indexOutOfBounds
	private void setPotentialEnPassantSquareBlack(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, Square setEnPassantSquare) {
		
		int i = toIndexI;
		int jRight = toIndexJ + 1;
		int jLeft = toIndexJ - 1;
		
		if ((jRight <= 7) && (board.getBoard()[i][jRight] != null) && (board.getBoard()[i][jRight].pieceType().equals("PAWN")) && (board.getBoard()[i][jRight].isWhite)) {
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristEnPassant()[toIndexJ]);
			setEnPassantSquare.setI(i - 1);
			setEnPassantSquare.setJ(toIndexJ);
			
		} else if ((jLeft >= 0) && (board.getBoard()[i][jLeft] != null) && (board.getBoard()[i][jLeft].pieceType().equals("PAWN")) && (board.getBoard()[i][jLeft].isWhite)) {

			board.setPositionHash(board.getPositionHash() ^ board.getZobristEnPassant()[toIndexJ]);
			setEnPassantSquare.setI(i - 1);
			setEnPassantSquare.setJ(toIndexJ);
			
		}
	}
	
	
	private boolean canPawnNotMoveUpOne(int toIndexI, int toIndexJ, GameBoard board) {
		
		if (board.getBoard()[toIndexI][toIndexJ] != null) return true;
		return false;
				
	}
	
	private boolean canPawnNotMoveUpTwo(int toIndexI, int toIndexJ, GameBoard board) {
		
		if ((board.getBoard()[toIndexI][toIndexJ] != null) || (board.getBoard()[toIndexI + 1][toIndexJ] != null)) return true;
		return false;
			
	}
	
	private boolean canPawnNotMoveUpTwoBlack(int toIndexI, int toIndexJ, GameBoard board) {
		
		if ((board.getBoard()[toIndexI][toIndexJ] != null) || (board.getBoard()[toIndexI - 1][toIndexJ] != null)) return true;
		return false;
			
	}
	
	private boolean isItAPromotionMove(int lastIndexI, int lastIndexJ, int toIndexI, GameBoard board) {
		
		boolean result = false;
		if (board.getBoard()[lastIndexI][lastIndexJ].isWhite) {
			
			result = (toIndexI == 0) ? true : false;
			
		} else {
			
			result = (toIndexI == 7) ? true : false;
			
		}
		
		return result;
		
	}
	
	private void choosePromotionPiece(int toIndexI, int toIndexJ, GameBoard board, char c) {
		
		int promotionSquare = (toIndexI * 8) + toIndexJ;
		
		switch (c) {
		
		case 'r':
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][9]);
			board.getBoard()[toIndexI][toIndexJ] = new Rook(false, toIndexI, toIndexJ);
			board.getBlackPieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		case 'n':
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][7]);
			board.getBoard()[toIndexI][toIndexJ] = new Knight(false, toIndexI, toIndexJ);
			board.getBlackPieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		case 'b':
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][8]);
			board.getBoard()[toIndexI][toIndexJ] = new Bishop(false, toIndexI, toIndexJ);
			board.getBlackPieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		case 'q': 
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][10]);
			board.getBoard()[toIndexI][toIndexJ] = new Queen(false, toIndexI, toIndexJ);
			board.getBlackPieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;	
			
		case 'R':
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][3]);
			board.getBoard()[toIndexI][toIndexJ] = new Rook(true, toIndexI, toIndexJ);
			board.getWhitePieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		case 'N':
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][1]);
			board.getBoard()[toIndexI][toIndexJ] = new Knight(true, toIndexI, toIndexJ);
			board.getWhitePieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		case 'B':
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][2]);
			board.getBoard()[toIndexI][toIndexJ] = new Bishop(true, toIndexI, toIndexJ);
			board.getWhitePieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		case 'Q': 
			
			board.setPositionHash(board.getPositionHash() ^ board.getZobristBoardArray()[promotionSquare][4]);
			board.getBoard()[toIndexI][toIndexJ] = new Queen(true, toIndexI, toIndexJ);
			board.getWhitePieces().add(board.getBoard()[toIndexI][toIndexJ]);
			break;
			
		default:
			break;
		
		}
	}
}






































