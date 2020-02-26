package pieces;

import java.util.List;

import javax.swing.JButton;

import board.GameBoard;
import board.Square;

public abstract class Pieces {

	protected boolean isWhite;
	protected String pieceType;
	protected Square square;
	
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
	
	protected boolean isPieceColourNotTheSideToMove(int lastIndexI, int lastIndexJ, GameBoard board){
		
		if (board.isWhiteToMove() != board.getBoard()[lastIndexI][lastIndexJ].getColour()) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	protected boolean isToAndFromSquareTheSameColourPiece(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board) {
		
		if ((board.getBoard()[toIndexI][toIndexJ] != null) && (board.getBoard()[lastIndexI][lastIndexJ].getColour() == board.getBoard()[toIndexI][toIndexJ].getColour())) {
			
			return true;
					
		}
		
		return false;
	}
	
	protected void updateGameBoard(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board) {
		
		removePiece(board, toIndexI, toIndexJ);
		board.getBoard()[toIndexI][toIndexJ] = board.getBoard()[lastIndexI][lastIndexJ];
		board.getBoard()[lastIndexI][lastIndexJ] = null;
		board.setWhiteToMove(!board.isWhiteToMove());
		board.setEnPassantSquare(-1, -1);
		
	}
	
	public void removePiece(GameBoard newBoard, int toIndexI, int toIndexJ) {
		
		if ((newBoard.getBoard()[toIndexI][toIndexJ] != null) && (newBoard.getBoard()[toIndexI][toIndexJ].getColour() == true)) {
			
			newBoard.getWhitePieces().remove(newBoard.getBoard()[toIndexI][toIndexJ]);
			
		} else if ((newBoard.getBoard()[toIndexI][toIndexJ] != null) && (newBoard.getBoard()[toIndexI][toIndexJ].getColour() == false)) {
			
			newBoard.getBlackPieces().remove(newBoard.getBoard()[toIndexI][toIndexJ]);
		}
	}
	
	public abstract void printPiece(JButton b);
	public abstract GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b);
	public abstract boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove);
	
	
	
	
	
}
