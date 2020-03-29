package utility;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import board.GameBoard;
import pieces.Pieces;

public class Move {

	private Pieces piece;
	private int toIndexI;
	private int toIndexJ;
	private char promotionPiece;
	
	public Move(Pieces piece, int toIndexI, int toIndexJ) {
		
		this(piece, toIndexI, toIndexJ, ' ');

	}
	
	public Move(Pieces piece, int toIndexI, int toIndexJ, char promotionPiece) {
		
		this.piece = piece;
		this.toIndexI = toIndexI;
		this.toIndexJ = toIndexJ;
		this.promotionPiece = promotionPiece;
		
	}
	
	public static List<Move> GenerateLegalMoves(GameBoard board) {
		
		List<Move> movesList = new ArrayList<Move>();
		List<Pieces> whitePieces = new ArrayList<Pieces>(board.getWhitePieces());
		List<Pieces> blackPieces = new ArrayList<Pieces>(board.getBlackPieces());
		
		if (board.isWhiteToMove()) {
			
			for (Pieces whitePiece : whitePieces) {
				
				if (whitePiece.pieceType().equals("PAWN")) GenerateLegalPawnMoves(movesList, whitePiece, board);
				if (whitePiece.pieceType().equals("KNIGHT")) GenerateLegalKnightMoves(movesList, whitePiece, board);
				if (whitePiece.pieceType().equals("BISHOP")) GenerateLegalDiagonalMoves(movesList, whitePiece, board);
				if (whitePiece.pieceType().equals("ROOK")) GenerateLegalHorizontalVerticalMoves(movesList, whitePiece, board);
				if (whitePiece.pieceType().equals("QUEEN")) {
					
					GenerateLegalDiagonalMoves(movesList, whitePiece, board);
					GenerateLegalHorizontalVerticalMoves(movesList, whitePiece, board);
				}
				if (whitePiece.pieceType().equals("KING")) GenerateLegalKingMoves(movesList, whitePiece, board);
			}
		} else {
			
			for (Pieces blackPiece : blackPieces) {
	
				if (blackPiece.pieceType().equals("PAWN")) GenerateLegalPawnMoves(movesList, blackPiece, board);
				if (blackPiece.pieceType().equals("KNIGHT")) GenerateLegalKnightMoves(movesList, blackPiece, board);
				if (blackPiece.pieceType().equals("BISHOP")) GenerateLegalDiagonalMoves(movesList, blackPiece, board);
				if (blackPiece.pieceType().equals("ROOK")) GenerateLegalHorizontalVerticalMoves(movesList, blackPiece, board);
				if (blackPiece.pieceType().equals("QUEEN")) {
					
					GenerateLegalDiagonalMoves(movesList, blackPiece, board);
					GenerateLegalHorizontalVerticalMoves(movesList, blackPiece, board);
					
				}
				if (blackPiece.pieceType().equals("KING")) GenerateLegalKingMoves(movesList, blackPiece, board);
			}
		}
		
		return movesList;
		
	}
	
	private static void GenerateLegalPawnMoves(List<Move> movesList, Pieces piece, GameBoard board) {
		
		if (piece.getColour() == true) {
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 1, piece.getSquare().getJ(), board, false, 'Q')) {
				
				board.undoMove();
				if (piece.getSquare().getI() - 1 == 0) {
					
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ(), 'Q'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ(), 'R'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ(), 'B'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ(), 'N'));
					
				} else {
					
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ()));
					
				}
				
				
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 2, piece.getSquare().getJ(), board, false, ' ')) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() - 2, piece.getSquare().getJ()));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1, board, false, 'Q')) {
				
				board.undoMove();
				if (piece.getSquare().getI() - 1 == 0) {
					
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1, 'Q'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1, 'R'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1, 'B'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1, 'N'));
					
				} else {
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() - 1));
				}
				
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1, board, false, 'Q')) {
				
				board.undoMove();
				if (piece.getSquare().getI() - 1 == 0) {
					
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1, 'Q'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1, 'R'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1, 'B'));
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1, 'N'));
					
				} else {
					movesList.add(new Move(piece, piece.getSquare().getI() - 1, piece.getSquare().getJ() + 1));
				}
				
				
			}
		} else {
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 1, piece.getSquare().getJ(), board, false, 'q')) {
				
				board.undoMove();
				if (piece.getSquare().getI() + 1 == 7) {
					
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ(), 'q'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ(), 'r'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ(), 'b'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ(), 'n'));
					
				} else {
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ()));
				}
				
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 2, piece.getSquare().getJ(), board, false, ' ')) {
				
				board.undoMove();
				movesList.add(new Move(piece, piece.getSquare().getI() + 2, piece.getSquare().getJ()));
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1, board, false, 'q')) {
				
				board.undoMove();
				if (piece.getSquare().getI() + 1 == 7) {
					
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1, 'q'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1, 'r'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1, 'b'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1, 'n'));
					
				} else {
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() - 1));
				}
				
				
			}
			
			if (piece.makeMove(piece.getSquare().getI(), piece.getSquare().getJ(), 
					piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1, board, false, 'q')) {
				
				board.undoMove();
				if (piece.getSquare().getI() + 1 == 7) {
					
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1, 'q'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1, 'r'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1, 'b'));
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1, 'n'));
					
				} else {
					movesList.add(new Move(piece, piece.getSquare().getI() + 1, piece.getSquare().getJ() + 1));
				}
				
				
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
		long nodes = 0;
		
		for (int i = 0; i < movesList.size(); i++) {
			
			int lastIndexI = movesList.get(i).getPiece().getSquare().getI();
			int lastIndexJ = movesList.get(i).getPiece().getSquare().getJ();
			int toIndexI = movesList.get(i).getToIndexI();
			int toIndexJ = movesList.get(i).getToIndexJ();
			char promotionPiece = movesList.get(i).getPromotionPiece();
			//System.out.println(lastIndexI + " " + lastIndexJ + " " + toIndexI + " " + toIndexJ + " " + promotionPiece);
			
			if (movesList.get(i).getPiece().pieceType().equals("PAWN")) {
				
				movesList.get(i).getPiece().makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false, promotionPiece);
				
			} else {
				
				movesList.get(i).getPiece().makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false);
				
			}
			
			nodes += Perft(depth - 1, board);
			board.undoMove();
			
		}
		
		return nodes;
	}
	
	public static void Divide(int depth, GameBoard board) {
		
		List<Move> movesList = GenerateLegalMoves(board);
		int total = 0;

		for (int i = 0; i < movesList.size(); i++) {
			
			int lastIndexI = movesList.get(i).getPiece().getSquare().getI();
			int lastIndexJ = movesList.get(i).getPiece().getSquare().getJ();
			int toIndexI = movesList.get(i).getToIndexI();
			int toIndexJ = movesList.get(i).getToIndexJ();
			char promotionPiece = movesList.get(i).getPromotionPiece();
			
			if (movesList.get(i).getPiece().pieceType().equals("PAWN")) {
				
				movesList.get(i).getPiece().makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false, promotionPiece);
				
			} else {
				
				movesList.get(i).getPiece().makeMove(lastIndexI, lastIndexJ, toIndexI, toIndexJ, board, false);
				
			}
			
			long result = Perft(depth, board);
			board.undoMove();
			System.out.println(lastIndexI + " " + lastIndexJ + " " + toIndexI + " " + toIndexJ + ": " + result);
			total += result;
			
		}
		
		System.out.println("Moves: " + movesList.size());
        System.out.println("Total: " + total);
		
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {

			return true;

		}

		if (obj == null) {

			return false;

		}

		if (this.getClass() != obj.getClass()) {

			return false;

		}
		
		Move other = (Move) obj;
		
		if ((this.piece != other.piece) || (this.toIndexI != other.toIndexI) || (this.toIndexJ != other.toIndexJ)
				|| (this.promotionPiece != other.promotionPiece)) {
			
			return false;
			
		}
		
		return true;
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
	
	public char getPromotionPiece() {
		
		return this.promotionPiece;
		
	}

	public static void main(String[] args) {
		
		GameBoard b = new GameBoard();
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, b)));
		
		GameBoard c = new GameBoard("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(5, c)));
		
		GameBoard d = new GameBoard("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(7, d)));
		
		GameBoard e = new GameBoard("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, e)));
		
		GameBoard f = new GameBoard("r2q1rk1/pP1p2pp/Q4n2/bbp1p3/Np6/1B3NBn/pPPP1PPP/R3K2R b KQ - 0 1");
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, f)));
		
		GameBoard g = new GameBoard("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(5, g)));
		
		GameBoard h = new GameBoard("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(5, h)));
		
		
		/*
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
		*/
	}
}
