package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class King extends Pieces {

	public double value = 10; //Unsure
	
	public King(boolean isWhite) {
		
		super(isWhite);
		
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
	
	public Pieces[][] makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, Pieces[][] oldBoard, List<JButton> b) {
		
		// Consider adding new function for this
		//Consider bitboards
		if ((oldBoard[toIndexI][toIndexJ] != null) && (oldBoard[lastIndexI][lastIndexJ].getColour() == oldBoard[toIndexI][toIndexJ].getColour())) {
					
			return oldBoard;
					
		}
		
		// MAKE SURE TO TAKE IN TO ACCOUNT SAME SQUARE MOVES
		// Technically not Magnitude 
		int magnitudeOfI = (toIndexI - lastIndexI) * (toIndexI - lastIndexI);
		int magnitudeOfJ = (toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ);
		int magnitudeOfMove = magnitudeOfI + magnitudeOfJ;
		
		if ((magnitudeOfMove == 1) || (magnitudeOfMove == 2)) {
			
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
