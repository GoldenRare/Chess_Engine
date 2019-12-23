package pieces;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board_indexing.Ranks;

public class Pawn extends Pieces {

	public double value = 10; //Unsure
	
	public Pawn(boolean isWhite) {
		
		super(isWhite);
		
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
	
	public Pieces[][] makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, Pieces[][] oldBoard, List<JButton> b) {
		// Current implementation will make pawn go straight through opposing pawn
		// Account for captures, they can also occur on home rank
		if ((oldBoard[toIndexI][toIndexJ] != null) && (oldBoard[lastIndexI][lastIndexJ].getColour() == oldBoard[toIndexI][toIndexJ].getColour())) {
			
			return oldBoard;
			
		}

		if (Arrays.asList(oldBoard[Ranks.RANK2.ordinal()]).contains(oldBoard[lastIndexI][lastIndexJ]) && oldBoard[lastIndexI][lastIndexJ].isWhite) {
			if (((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) || ((toIndexI == lastIndexI - 2) && (toIndexJ == lastIndexJ))) {
				if ((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) {
					if (oldBoard[toIndexI][toIndexJ] != null) {
						
						return oldBoard;
						
					}
				} else if ((toIndexI == lastIndexI - 2) && (toIndexJ == lastIndexJ)) {
					if ((oldBoard[toIndexI][toIndexJ] != null) || (oldBoard[toIndexI + 1][toIndexJ] != null)) {
						
						return oldBoard;
						
					}
				}
				
				Pieces[][] newBoard = oldBoard;
				newBoard[toIndexI][toIndexJ] = newBoard[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				return newBoard;
			}	
		} else if (oldBoard[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI - 1) && (toIndexJ == lastIndexJ)) {
				
				Pieces[][] newBoard = oldBoard;
				newBoard[toIndexI][toIndexJ] = newBoard[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				return newBoard;
				
			}	
		} else if (Arrays.asList(oldBoard[Ranks.RANK7.ordinal()]).contains(oldBoard[lastIndexI][lastIndexJ]) && !oldBoard[lastIndexI][lastIndexJ].isWhite) {
			if (((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) || ((toIndexI == lastIndexI + 2) && (toIndexJ == lastIndexJ))) {
				if ((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) {
					if (oldBoard[toIndexI][toIndexJ] != null) {
						
						return oldBoard;
						
					}
				} else if ((toIndexI == lastIndexI + 2) && (toIndexJ == lastIndexJ)) {
					if ((oldBoard[toIndexI][toIndexJ] != null) || (oldBoard[toIndexI - 1][toIndexJ] != null)) {
						
						return oldBoard;
						
					}
				}
				
				Pieces[][] newBoard = oldBoard;
				newBoard[toIndexI][toIndexJ] = newBoard[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				return newBoard;
			}	
		} else if (!oldBoard[lastIndexI][lastIndexJ].isWhite) {
			
			if ((toIndexI == lastIndexI + 1) && (toIndexJ == lastIndexJ)) {
				
				Pieces[][] newBoard = oldBoard;
				newBoard[toIndexI][toIndexJ] = newBoard[lastIndexI][lastIndexJ];
				printPiece(b.get(toIndexI * 8 + toIndexJ));
				newBoard[lastIndexI][lastIndexJ] = null;
				b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
				return newBoard;
				
			}
		}
		//row * 8 + col
		return oldBoard;
		
	}
}
