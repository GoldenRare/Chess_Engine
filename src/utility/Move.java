package utility;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import board.GameBoard;
import pieces.Pieces;

public class Move {

	private Pieces piece;
	private int toIndexI;
	private int toIndexJ;
	
	public Move(Pieces piece, int toIndexI, int toIndexJ) {
		
		this.piece = piece;
		this.toIndexI = toIndexI;
		this.toIndexJ = toIndexJ;
		
	}
	
	// NEED TO CHECK FOR INDEXOUTOFBOUNDS EXCEPTION
	// CAN PROBABLY GET RID OF THE USE OF UNDOMOVE()
	public static List<Move> GenerateLegalMoves(GameBoard board) {
		
		List<Move> movesList = new ArrayList<Move>();
		
		if (board.isWhiteToMove()) {
			
			for (Pieces piece : board.getWhitePieces()) {
				
				if (piece.pieceType().equals("PAWN")) GenerateLegalPawnMoves(movesList, piece, board);
				if (piece.pieceType().equals("KNIGHT")) GenerateLegalKnightMoves(movesList, piece, board);
				if (piece.pieceType().equals("BISHOP")) GenerateLegalDiagonalMoves(movesList, piece, board);
				if (piece.pieceType().equals("ROOK")) GenerateLegalHorizontalVerticalMoves(movesList, piece, board);
				if (piece.pieceType().equals("QUEEN")) {
					
					GenerateLegalDiagonalMoves(movesList, piece, board);
					GenerateLegalHorizontalVerticalMoves(movesList, piece, board);
				}
				if (piece.pieceType().equals("KING")) GenerateLegalKingMoves(movesList, piece, board);
			}
		} else {
			
			for (Pieces piece : board.getBlackPieces()) {
				
				if (piece.pieceType().equals("PAWN")) GenerateLegalPawnMoves(movesList, piece, board);
				if (piece.pieceType().equals("KNIGHT")) GenerateLegalKnightMoves(movesList, piece, board);
				if (piece.pieceType().equals("BISHOP")) GenerateLegalDiagonalMoves(movesList, piece, board);
				if (piece.pieceType().equals("ROOK")) GenerateLegalHorizontalVerticalMoves(movesList, piece, board);
				if (piece.pieceType().equals("QUEEN")) {
					
					GenerateLegalDiagonalMoves(movesList, piece, board);
					GenerateLegalHorizontalVerticalMoves(movesList, piece, board);
					
				}
				if (piece.pieceType().equals("KING")) GenerateLegalKingMoves(movesList, piece, board);
			}
		}
		
		return movesList;
		
	}
	
	private static void GenerateLegalPawnMoves(List<Move> movesList, Pieces piece, GameBoard board) {
		
		if (piece.getColour() == true) {
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 1, piece.getSquare().getJ(), board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ()));
				
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 2, piece.getSquare().getJ(), board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() - 2, piece.getSquare().getJ()));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1));
				
			}
		} else {
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 1, piece.getSquare().getJ(), board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ()));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 2, piece.getSquare().getJ(), board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() + 2, piece.getSquare().getJ()));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1));
				
			}
		}
	}
	
	private static void GenerateLegalKnightMoves(List<Move> movesList, Pieces piece, GameBoard board) {
		
		int[] movementsI = {-2, -2, -1, 1, 2, 2, 1, -1};
		int[] movementsJ = {-1, 1, 2, 2, 1, -1, -2, -2};
		
		for (int k = 0; k < movementsI.length; k++) {
			
			int i = piece.getSquare().getI() + movementsI[k];
			int j = piece.getSquare().getJ() + movementsJ[k];
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			}
		}
		
	}
	// Can make unnecessary moves
	private static void GenerateLegalDiagonalMoves(List<Move> movesList, Pieces piece, GameBoard board) {
		
		int lengthOfBoard = 8; //Should be its own class (final int)
		
		//Bottom-Right to Top-Left
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI() - k;
			int j = piece.getSquare().getJ() - k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		//Top-Left to Bottom-Right
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI() + k;
			int j = piece.getSquare().getJ() + k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		//Bottom-Left to Top-Right
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI() - k;
			int j = piece.getSquare().getJ() + k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		//Top-Right to Bottom-Left
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI() + k;
			int j = piece.getSquare().getJ() - k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}	
	}
	// Can make unnecessary moves
	private static void GenerateLegalHorizontalVerticalMoves(List<Move> movesList, Pieces piece, GameBoard board) {
		
		int lengthOfBoard = 8; //Should be its own class (final int)

		//Left to Right
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI();
			int j = piece.getSquare().getJ() + k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		//Right to Left
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI();
			int j = piece.getSquare().getJ() - k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		//Down to Up
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI() - k;
			int j = piece.getSquare().getJ();
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		//Up to Down
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = piece.getSquare().getI() + k;
			int j = piece.getSquare().getJ();
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			} else {
				
				//break;
				
			}
		}
		
		
	}
	
	// NEED TO INCLUDE CASTLING MOVEMENTS
	private static void GenerateLegalKingMoves(List<Move> movesList, Pieces piece, GameBoard board) {
		
		int[] movementsI = {-1, -1, -1, 0, 1, 1, 1, 0, 0, 0};
		int[] movementsJ = {-1, 0, 1, 1, 1, 0, -1, -1, 2, -2};
		
		for (int k = 0; k < movementsI.length; k++) {
			
			int i = piece.getSquare().getI() + movementsI[k];
			int j = piece.getSquare().getJ() + movementsJ[k];
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) continue;
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), i, j, board, false)) {
				
				board.undoMove();
				movesList.add(new Move(piece, i, j));
				
			}
		}
	}
	
	public static long Perft(int depth, GameBoard board) {
		
		if (depth == 0) return 1;
		
		List<Move> movesList = GenerateLegalMoves(board);
		//System.out.println(movesList.size());
		long nodes = 0;
		
		for (int i = 0; i < movesList.size(); i++) {
			
			int lastIndexI = movesList.get(i).getPiece().getSquare().getI();
			int lastIndexJ = movesList.get(i).getPiece().getSquare().getJ();
			int toIndexI = movesList.get(i).getToIndexI();
			int toIndexJ = movesList.get(i).getToIndexJ();
			//System.out.println(lastIndexI + " " + lastIndexJ + " " + toIndexI + " " + toIndexJ);
			
			movesList.get(i).getPiece().makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false);
			//System.out.print(Arrays.deepToString(board.getBoard()).replace("], ", "]\n"));
			//System.out.println("\n");
			nodes += Perft(depth - 1, board);
			//System.out.println(nodes);
			
			board.undoMove();
			//System.out.print(Arrays.deepToString(board.getBoard()).replace("], ", "]\n"));
			//System.out.println("\n");
			
		}
		
		return nodes;
	}

	public Pieces getPiece() {
		
		return this.piece;
		
	}

	public int getToIndexI() {
		
		return this.toIndexI;
		
	}

	public int getToIndexJ() {
		
		return this.toIndexJ;
		
	}
	
	public static void main(String[] args) {
		
		GameBoard b = new GameBoard();
		System.out.println("PERFORMANCE TEST\n");
		
		System.out.println("By Move 1 there are 20 different chess positions:");
		Instant start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(1, b)) + " different chess positions.");
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Algorithm took " + timeElapsed + " milliseconds.\n");
		
		System.out.println("By Move 2 there are 400 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(2, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Algorithm took " + timeElapsed + " milliseconds.\n");
		
		System.out.println("By Move 3 there are 8,902 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(3, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Algorithm took " + timeElapsed + " milliseconds.\n");
		
		System.out.println("By Move 4 there are 197,281 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(4, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Algorithm took " + timeElapsed + " milliseconds.\n");
		
		System.out.println("By Move 5 there are 4,865,609 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(5, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toSeconds();
		System.out.println("Algorithm took " + timeElapsed + " seconds.\n");
		
		System.out.println("By Move 6 there are 119,060,324 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(6, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toSeconds();
		System.out.println("Algorithm took " + timeElapsed + " seconds.\n");
		
		System.out.println("By Move 7 there are 3,195,901,860 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(7, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toMinutes();
		System.out.println("Algorithm took " + timeElapsed + " minutes.\n");
		
		System.out.println("By Move 8 there are 84,998,978,956 different chess positions:");
		start = Instant.now();
		System.out.println("Algorithm found " + NumberFormat.getNumberInstance(Locale.US).format(Perft(8, b)) + " different chess positions.");
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toMinutes();
		System.out.println("Algorithm took " + timeElapsed + " minutes.\n");
		
	}
}
