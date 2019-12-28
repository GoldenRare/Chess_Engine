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
	public GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b){
		
		GameBoard newBoard = oldBoard;
		newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
		printPiece(b.get(toIndexI * 8 + toIndexJ));
		newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
		b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
		
		//row * 8 + col
		return newBoard;
		
	}
	
	
	
	
	
}
