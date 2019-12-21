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
	
	public GameBoard() {
		
		startGame();
		
	}
	
	public Pieces[][] startGame() {
		
		for(int i = 0; i < 8; i++) {
			
			board[6][i] = new Pawn(true);
			board[1][i] = new Pawn(false);
			
		}
		
		board[7][0] = new Rook(true);
		board[7][7] = new Rook(true);
		board[7][1] = new Knight(true);
		board[7][6] = new Knight(true);
		board[7][2] = new Bishop(true);
		board[7][5] = new Bishop(true);
		board[7][3] = new Queen(true);
		board[7][4] = new King(true);
		board[0][0] = new Rook(false);
		board[0][7] = new Rook(false);
		board[0][1] = new Knight(false);
		board[0][6] = new Knight(false);
		board[0][2] = new Bishop(false);
		board[0][5] = new Bishop(false);
		board[0][3] = new Queen(false);
		board[0][4] = new King(false);
		
		
		return board;
		
	}
	
	public Pieces[][] getBoard() {
		
		return board;
		
	}
	
	public static void main(String[] args) {
		
		GameBoard b = new GameBoard();
		System.out.print(Arrays.deepToString(b.getBoard()).replace("], ", "]\n"));
		
	}

}
