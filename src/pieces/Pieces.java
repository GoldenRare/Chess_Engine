package pieces;

import java.util.List;

import javax.swing.JButton;

import board.GameBoard;
import board.Square;

public abstract class Pieces {

	protected boolean isWhite;
	protected String pieceType;
	protected Square square;
	
	public Pieces(boolean isWhite) {
		
		this.isWhite = isWhite;
		
	}
	
	public Pieces(boolean isWhite, int i, int j) {
		
		this.isWhite = isWhite;
		this.square = new Square(i, j);
		
	}
	
	public boolean getColour() {
		
		return this.isWhite;
		
	}
	
	public String pieceType() {
		
		return this.pieceType;
		
	}
	
	public Square getSquare() {
		
		return this.square;
		
	}
	
	public abstract void printPiece(JButton b);
	public abstract GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b);
	
	
	
	
	
}
