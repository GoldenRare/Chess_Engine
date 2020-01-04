package pieces;

import java.util.List;

import javax.swing.JButton;

import board.GameBoard;

public abstract class Pieces {

	protected boolean isWhite; 
	
	public Pieces(boolean isWhite) {
		
		this.isWhite = isWhite;
		
	}
	
	public boolean getColour() {
		
		return this.isWhite;
		
	}
	
	public abstract void printPiece(JButton b);
	public abstract GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b);
	
	
	
	
	
}
