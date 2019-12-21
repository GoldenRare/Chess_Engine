package pieces;

import java.util.List;

import javax.swing.JButton;

public abstract class Pieces {

	protected boolean isWhite; 
	
	public Pieces(boolean isWhite) {
		
		this.isWhite = isWhite;
		
	}
	
	public boolean getColour() {
		
		return this.isWhite;
		
	}
	
	public abstract void printPiece(JButton b);
	public Pieces[][] makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, Pieces[][] oldBoard, List<JButton> b){
		
		Pieces[][] newBoard = oldBoard;
		newBoard[toIndexI][toIndexJ] = newBoard[lastIndexI][lastIndexJ];
		printPiece(b.get(toIndexI * 8 + toIndexJ));
		newBoard[lastIndexI][lastIndexJ] = null;
		b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
		
		//row * 8 + col
		return newBoard;
		
	}
	
	
	
	
	
}
