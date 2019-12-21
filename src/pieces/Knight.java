package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Knight extends Pieces {

	public double value = 10; //Unsure
	
	public Knight(boolean isWhite) {
		
		super(isWhite);
		
	}
	
	public void printPiece(JButton b) {
		
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_knight.png");
			Image scaledImg = img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_knight.png");
			Image scaledImg = img.getImage().getScaledInstance(105, 105, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
		}
		
		
	}
	
	public Pieces[][] makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, Pieces[][] oldBoard, List<JButton> b) {
		// Consider adding new function for this
		// Consider bitboards
		// DOES MOVE LEAVE KING IN CHECK
		if ((oldBoard[toIndexI][toIndexJ] != null) && (oldBoard[lastIndexI][lastIndexJ].getColour() == oldBoard[toIndexI][toIndexJ].getColour())) {
			
			return oldBoard;
			
		}
		// Technically not Magnitude
		int magnitudeOfMove = ((toIndexI - lastIndexI) * (toIndexI - lastIndexI)) + ((toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ));
		int knightMagnitude = 5;
		
		if (magnitudeOfMove == knightMagnitude) {
			
			Pieces[][] newBoard = oldBoard;
			newBoard[toIndexI][toIndexJ] = newBoard[lastIndexI][lastIndexJ];
			printPiece(b.get(toIndexI * 8 + toIndexJ));
			newBoard[lastIndexI][lastIndexJ] = null;
			b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
			return newBoard;
			
		}
		
		//row * 8 + col
		return oldBoard;
		
	}
}
