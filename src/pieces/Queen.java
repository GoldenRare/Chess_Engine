package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;

public class Queen extends Pieces {

	public double value = 10; //Unsure
	
	public Queen(boolean isWhite) {
		
		super(isWhite);
		
	}
	
	public void printPiece(JButton b) {
		
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_queen.png");
			Image scaledImg = img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_queen.png");
			Image scaledImg = img.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
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
			GameBoard newBoard = oldBoard;
			newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
			printPiece(b.get(toIndexI * 8 + toIndexJ));
			newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
			b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
			newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
			
			//row * 8 + col
			return newBoard;
			
		} else if (magnitudeOfI == magnitudeOfJ) {
			
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
			
			GameBoard newBoard = oldBoard;
			newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
			printPiece(b.get(toIndexI * 8 + toIndexJ));
			newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
			b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
			newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
			
			//row * 8 + col
			return newBoard;
			
		}
		
		//row * 8 + col
		return oldBoard;
		
	}
}
