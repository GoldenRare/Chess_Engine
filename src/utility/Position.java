package utility;

import board.GameBoard;
import board.Square;
import pieces.Pieces;

public class Position {

	// MESSES UP WITH KINGS BECAUSE GETSQUARE IS THE OLD GETSQUARE
	// POTENTIAL ERRORS WITH PAWN, ROOK
	public static boolean isMyKingInCheck(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board) {
		
		boolean result;
		Pieces pieceMoving = board.getBoard()[lastIndexI][lastIndexJ];
		Pieces pieceMaybeTaking = board.getBoard()[toIndexI][toIndexJ];
		
		board.getBoard()[toIndexI][toIndexJ] = pieceMoving;
		board.getBoard()[lastIndexI][lastIndexJ] = null;
		
		if (board.getBoard()[toIndexI][toIndexJ].getColour() == true) {
			
			result = Square.isSquareAttacked(board, board.getKingPieces()[0].getSquare(), true);
			
		} else {
			
			result = Square.isSquareAttacked(board, board.getKingPieces()[1].getSquare(), false);
			
		}
		
		board.getBoard()[toIndexI][toIndexJ] = pieceMaybeTaking;
		board.getBoard()[lastIndexI][lastIndexJ] = pieceMoving;
		
		return result;
		
	}
}
