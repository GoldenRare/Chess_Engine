package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;

public class Rook extends Pieces {
	
	public double value = 50; //Unsure
	
	public Rook(boolean isWhite) {
		
		super(isWhite);
		
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
			
			if (oldBoard.getBoard()[lastIndexI][lastIndexJ].isWhite) {
				if ((lastIndexI == 7) && (lastIndexJ == 7)) {
					
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1110);
					
				} else if ((lastIndexI == 7) && (lastIndexJ == 0)) {
					
					oldBoard.setCastlingRights(oldBoard.getCastlingRights() & 0b1101);
					
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
