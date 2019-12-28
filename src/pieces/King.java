package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;

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
	
	public GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b) {
		
		// Consider adding new function for this
		//Consider bitboards
		if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) && (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
					
			return oldBoard;
					
		}
		
		// MAKE SURE TO TAKE IN TO ACCOUNT SAME SQUARE MOVES
		// Technically not Magnitude 
		int magnitudeOfI = (toIndexI - lastIndexI) * (toIndexI - lastIndexI);
		int magnitudeOfJ = (toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ);
		int magnitudeOfMove = magnitudeOfI + magnitudeOfJ;
		
		if ((magnitudeOfMove == 1) || (magnitudeOfMove == 2)) {
			
			//Disable all of that colours castling rights
			GameBoard newBoard = oldBoard;
			newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
			printPiece(b.get(toIndexI * 8 + toIndexJ));
			newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
			b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
			
			//row * 8 + col
			return newBoard;
			
		} // else if (checkCastlingRights)
		
		//row * 8 + col
		return oldBoard;
		
	}
}
