package board;

import java.util.Arrays;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Pieces;
import pieces.Queen;
import pieces.Rook;

public class GameBoard {
	
	private Pieces[][] board = new Pieces[8][8]; 
	private boolean isWhiteToMove;
	private int castlingRights; //Order of the bits: blackKingQueenside, blackKingKingside, whiteKingQueenside, whiteKingKingside
	private Square enPassant; 
	
	////////////////
	private Pieces[] kingPieces; //WhiteKing, BlackKing
	
	public GameBoard() {
		
		startGame();
		
	}
	
	private void startGame() {
		
		for(int i = 0; i < 8; i++) {
			
			this.board[6][i] = new Pawn(true);
			this.board[1][i] = new Pawn(false);
			
		}
		
		this.board[7][0] = new Rook(true);
		this.board[7][7] = new Rook(true);
		this.board[7][1] = new Knight(true);
		this.board[7][6] = new Knight(true);
		this.board[7][2] = new Bishop(true);
		this.board[7][5] = new Bishop(true);
		this.board[7][3] = new Queen(true);
		this.board[7][4] = new King(true, 7, 4);
		this.board[0][0] = new Rook(false);
		this.board[0][7] = new Rook(false);
		this.board[0][1] = new Knight(false);
		this.board[0][6] = new Knight(false);
		this.board[0][2] = new Bishop(false);
		this.board[0][5] = new Bishop(false);
		this.board[0][3] = new Queen(false);
		this.board[0][4] = new King(false, 0, 4);
		
		this.isWhiteToMove = true;
		this.castlingRights = 0b1111;
		this.enPassant = new Square(-1, -1);
		
		////////////////////
		this.kingPieces = new Pieces[] {this.board[7][4], this.board[0][4]};
		
	}
	
	public Pieces[][] getBoard() { 
		
		return this.board;
		
	}
	
	public boolean isWhiteToMove() {
		
		return this.isWhiteToMove;
		
	}

	public void setWhiteToMove(boolean isWhiteToMove) {
		
		this.isWhiteToMove = isWhiteToMove;
		
	}

	public int getCastlingRights() {
		
		return this.castlingRights;
		
	}

	public void setCastlingRights(int castlingRights) {
		
		this.castlingRights = castlingRights;
		
	}

	public Square getEnPassantSquare() {
		
		return this.enPassant;
		
	}

	public void setEnPassantSquare(int i, int j) {
		
		this.enPassant.setI(i);
		this.enPassant.setJ(j);
		
	}
	
	public Pieces[] getKingPieces() {
		
		return this.kingPieces;
		
	}

	public static void main(String[] args) {
		
		GameBoard b = new GameBoard();
		System.out.print(Arrays.deepToString(b.getBoard()).replace("], ", "]\n"));
		
	}

}
