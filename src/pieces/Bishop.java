package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;
import utility.Position;

public class Bishop extends Pieces {

	public double value = 10; //Unsure
	
	public Bishop(boolean isWhite, int i, int j) {
		
		super(isWhite, i, j);
		super.pieceType = "BISHOP";
		super.pieceValue = 300;
		super.hashIndex = (isWhite) ? 2 : 8;
		
	}
	
	public void printPiece(JButton b) {
		
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_bishop.png");
			Image scaledImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_bishop.png");
			Image scaledImg = img.getImage().getScaledInstance(85, 85, Image.SCALE_DEFAULT);
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
		// MAKE SURE TO TAKE IN TO ACCOUNT SAME SQUARE MOVES
		// Technically not Magnitude 
		int magnitudeOfI = (toIndexI - lastIndexI) * (toIndexI - lastIndexI);
		int magnitudeOfJ = (toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ);
		
		if (magnitudeOfI == magnitudeOfJ) {
			
			if ((lastIndexI > toIndexI) && (lastIndexJ > toIndexJ)) { // 5 5 -> 1 1 (top left)
				
				int checkSquares = lastIndexI - toIndexI - 1;
				int x = lastIndexI - 1;
				int y = lastIndexJ - 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return oldBoard;
						
					}
					x--;
					y--;
				}
			} else if ((lastIndexI < toIndexI) && (lastIndexJ < toIndexJ)) { // 1 1 -> 5 5 (bottom right)
				
				int checkSquares = toIndexI - lastIndexI - 1;
				int x = lastIndexI + 1;
				int y = lastIndexJ + 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return oldBoard;
						
					}
					x++;
					y++;
				}
			} else if ((lastIndexI > toIndexI) && (lastIndexJ < toIndexJ)) { // 6 1 -> 1 6 (top right)
				
				int checkSquares = lastIndexI - toIndexI - 1;
				int x = lastIndexI - 1;
				int y = lastIndexJ + 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return oldBoard;
						
					}
					x--;
					y++;
				}
				
			} else {
				
				int checkSquares = toIndexI - lastIndexI - 1;
				int x = lastIndexI + 1;
				int y = lastIndexJ - 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return oldBoard;
						
					}
					x++;
					y--;
				}
			}
			
			if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return oldBoard;
			
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
		
		//row * 8 + col
		return oldBoard;
		
	}
	
	public boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove) {
		
		if (isPieceColourNotTheSideToMove(lastIndexI, lastIndexJ, oldBoard)) return false;
		if (isToAndFromSquareTheSameColourPiece(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;
		if ((!checkPseudoMove) && (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard))) return false;

		int magnitudeOfI = (toIndexI - lastIndexI) * (toIndexI - lastIndexI);
		int magnitudeOfJ = (toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ);
		
		if (magnitudeOfI == magnitudeOfJ) {
			
			if ((lastIndexI > toIndexI) && (lastIndexJ > toIndexJ)) { // 5 5 -> 1 1 (top left)
				
				int checkSquares = lastIndexI - toIndexI - 1;
				int x = lastIndexI - 1;
				int y = lastIndexJ - 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return false;
						
					}
					x--;
					y--;
				}
			} else if ((lastIndexI < toIndexI) && (lastIndexJ < toIndexJ)) { // 1 1 -> 5 5 (bottom right)
				
				int checkSquares = toIndexI - lastIndexI - 1;
				int x = lastIndexI + 1;
				int y = lastIndexJ + 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return false;
						
					}
					x++;
					y++;
				}
			} else if ((lastIndexI > toIndexI) && (lastIndexJ < toIndexJ)) { // 6 1 -> 1 6 (top right)
				
				int checkSquares = lastIndexI - toIndexI - 1;
				int x = lastIndexI - 1;
				int y = lastIndexJ + 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return false;
						
					}
					x--;
					y++;
				}
				
			} else {
				
				int checkSquares = toIndexI - lastIndexI - 1;
				int x = lastIndexI + 1;
				int y = lastIndexJ - 1;
				for (int i = 0; i < checkSquares; i++) {
					
					if(oldBoard.getBoard()[x][y] != null) {
						
						return false;
						
					}
					x++;
					y--;
				}
			}
			
			//if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			updatePositionHash(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
			updateGameBoard(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
			this.square.setI(toIndexI);
			this.square.setJ(toIndexJ);
			
			//row * 8 + col
			return true;
			
		}
		
		//row * 8 + col
		return false;
		
	}
}
