package utility;

import board.GameBoard;
import board.Square;
import pieces.Pieces;

public class Position {

	private boolean isWhiteToMove;
	private int castlingRights; //Order of the bits: blackKingQueenside, blackKingKingside, whiteKingQueenside, whiteKingKingside
	private int enPassantI;
	private int enPassantJ;
	private int lastIndexI;
	private int lastIndexJ;
	private int toIndexI;
	private int toIndexJ;
	private Pieces capturedPiece = null;
	private Pieces promotedPawn = null;
	private boolean castlingKingSide;
	private boolean castlingQueenSide;
	private boolean isEnPassantMove;
	
	public Position(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, boolean castlingKingSide, boolean castlingQueenSide, boolean isEnPassantMove) {
		
		stateBeforeMakeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, castlingKingSide, castlingQueenSide, isEnPassantMove);
		
	}
	
	// King square not updating may still need to be checked
	public static boolean isMyKingInCheck(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board) {
		
		boolean result = true;
		
		if (board.getBoard()[lastIndexI][lastIndexJ].makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, true)) {
			if (board.getBoard()[toIndexI][toIndexJ].getColour() == true) {
				
				result = Square.isSquareAttacked(board, board.getKingPieces()[0].getSquare(), true);
				
			} else {
				
				result = Square.isSquareAttacked(board, board.getKingPieces()[1].getSquare(), false);
				
			}
			board.undoMove();
		}

		return result;
		
	}
	
	public static boolean isMyKingInCheck(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, char promotionPiece) {
		
		boolean result = true;
		
		if (board.getBoard()[lastIndexI][lastIndexJ].makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, true, promotionPiece)) {
			if (board.getBoard()[toIndexI][toIndexJ].getColour() == true) {
				
				result = Square.isSquareAttacked(board, board.getKingPieces()[0].getSquare(), true);
				
			} else {
				
				result = Square.isSquareAttacked(board, board.getKingPieces()[1].getSquare(), false);
				
			}
			board.undoMove();
		}

		return result;
		
	}
	
	private void stateBeforeMakeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard board, boolean castlingKingSide, boolean castlingQueenSide, boolean isEnPassantMove) {
		
		this.isWhiteToMove = board.isWhiteToMove();
		this.castlingRights = board.getCastlingRights();
		this.enPassantI = board.getEnPassantSquare().getI();
		this.enPassantJ = board.getEnPassantSquare().getJ();
		this.lastIndexI = lastIndexI;
		this.lastIndexJ = lastIndexJ;
		this.toIndexI = toIndexI;
		this.toIndexJ = toIndexJ;
		this.castlingKingSide = castlingKingSide;
		this.castlingQueenSide = castlingQueenSide;
		this.isEnPassantMove = isEnPassantMove;
		
		if (isEnPassantMove) {
			
			this.capturedPiece = board.getBoard()[lastIndexI][toIndexJ];
			
		} else {
			
			this.capturedPiece = board.getBoard()[toIndexI][toIndexJ];
			
		}
		
		this.promotedPawn = board.getBoard()[lastIndexI][lastIndexJ];
		
	}
	
	public boolean isWhiteToMove() {
		
		return this.isWhiteToMove;
		
	}
	
	public int castlingRights() {
		
		return this.castlingRights;
		
	}
	
	public int enPassantI() {
		
		return this.enPassantI;
		
	}
	
	public int enPassantJ() {
		
		return this.enPassantJ;
		
	}
	
	public int lastIndexI() {
		
		return this.lastIndexI;
		
	}
	
	public int lastIndexJ() {
		
		return this.lastIndexJ;
		
	}
	
	public int toIndexI() {
		
		return this.toIndexI;
		
	}
	
	public int toIndexJ() {
		
		return this.toIndexJ;
		
	}
	
	public Pieces capturedPiece() {
		
		return this.capturedPiece;
		
	}
	
	public boolean castlingKingSide() {
		
		return this.castlingKingSide;
		
	}
	
	public boolean castlingQueenSide() {
		
		return this.castlingQueenSide;
		
	}
	
	public boolean isEnPassantMove() {
		
		return this.isEnPassantMove;
		
	}
	
	public Pieces promotedPawn() {
		
		return this.promotedPawn;
		
	}
}
