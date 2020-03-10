package utility;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import board.GameBoard;
import board.Square;
import pieces.Pieces;

public class Evaluation {

	
	public static int miniMax(GameBoard board, int depth, boolean isMaximizingPlayer) {
		
		if (depth == 0) return evaluatePosition(board);
		List<Move> movesList = Move.GenerateLegalMoves(board);
		
		Move bestMove = null;
		
		if (isMaximizingPlayer) {
			
			int evaluation = Integer.MIN_VALUE;
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
				
				int temp = miniMax(board, depth - 1, !isMaximizingPlayer);
				if (temp > evaluation) {
					bestMove = movesList.get(i);
					evaluation = temp;
				}
				board.undoMove();
			}
			if (bestMove != null)
			System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			return evaluation;
		} else {
			
			int evaluation = Integer.MAX_VALUE;
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
				
				int temp = miniMax(board, depth - 1, !isMaximizingPlayer);
				if (temp < evaluation) {
					bestMove = movesList.get(i);
					evaluation = temp;
				}
				board.undoMove();
				
			}
			if (bestMove != null)
			System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			return evaluation;
		}
		
	}
	
	public static int alphaBeta(GameBoard board, int alpha, int beta, int depth, boolean isMaximizingPlayer) {
		
		if (depth == 0) return evaluatePosition(board);
		List<Move> movesList = Move.GenerateLegalMoves(board);
		Move bestMove = null;
		
		if (isMaximizingPlayer) {
			
			if (movesList.size() == 0) {
				
				int evaluation = 0;
				if (Square.isSquareAttacked(board, board.getKingPieces()[0].getSquare(), isMaximizingPlayer)) {
					
					evaluation = Integer.MIN_VALUE + depth;
		
				}
				
				if (evaluation >= beta) {
					return beta;
					
				}

				if (evaluation > alpha) {
					alpha = evaluation;
				}
				
				return alpha;
			}
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
				
				int evaluation = alphaBeta(board, alpha, beta, depth - 1, !isMaximizingPlayer);
				board.undoMove();
				
				if (evaluation >= beta) {
					return beta;
					
				}

				if (evaluation > alpha) {
					alpha = evaluation;
					bestMove = movesList.get(i);
				}
			}
			if (bestMove != null)
			System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			return alpha;
		} else {
			
			if (movesList.size() == 0) {
				
				int evaluation = 0;
				if (Square.isSquareAttacked(board, board.getKingPieces()[1].getSquare(), isMaximizingPlayer)) {
					
					evaluation = Integer.MAX_VALUE - depth;
		
				}
				
				if (evaluation <= alpha) {
					return alpha;
					
				}

				if (evaluation < beta) {
					beta = evaluation;
				}
				
				return beta;
			}
			
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
				
				int evaluation = alphaBeta(board, alpha, beta, depth - 1, !isMaximizingPlayer);
				board.undoMove();
				if (evaluation <= alpha) {
					return alpha;
				}
				
				if (evaluation < beta) {
					
					beta = evaluation;
					bestMove = movesList.get(i);
				}	
			}
			if (bestMove != null)
			System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			return beta;
		}
		
	}
	
	public static int evaluatePosition(GameBoard board) {
		
		int whiteMaterial = 0;
		int blackMaterial = 0;
		int totalMaterial = 0;
		
		for (Pieces piece : board.getWhitePieces()) {
			
			whiteMaterial += piece.pieceValue();
			
		}
		
		for (Pieces piece : board.getBlackPieces()) {
			
			blackMaterial += piece.pieceValue();
			
		}
		
		totalMaterial = whiteMaterial - blackMaterial;
		
		return totalMaterial; 

	}
	
	public static long Perft(int depth, GameBoard board) {
		
		if (depth == 0) return 1;
		
		List<Move> movesList = Move.GenerateLegalMoves(board);
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
	
	public static void main(String[] args) {
		
		GameBoard b = new GameBoard("1rbk1n1r/pp1p1P1p/1qpPpR2/6QP/2P1N3/P1B5/1P2BP2/2K4R w - - 1 29");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, b)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(b, 6, b.isWhiteToMove())));
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, 2, b.isWhiteToMove())));
		
		GameBoard c = new GameBoard("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(5, c)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(c, 5, c.isWhiteToMove())));
		
		GameBoard d = new GameBoard("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(7, d)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(d, 7, d.isWhiteToMove())));
		
		GameBoard e = new GameBoard("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, e)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(e, 5, e.isWhiteToMove())));
		
		GameBoard f = new GameBoard("r2q1rk1/pP1p2pp/Q4n2/bbp1p3/Np6/1B3NBn/pPPP1PPP/R3K2R b KQ - 0 1");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, f)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(f, 5, f.isWhiteToMove())));
		
		GameBoard g = new GameBoard("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(5, g)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(g, 5, g.isWhiteToMove())));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(alphaBeta(g, Integer.MIN_VALUE, Integer.MAX_VALUE, 5, g.isWhiteToMove())));
		
		GameBoard h = new GameBoard("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(5, h)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(h, 5, h.isWhiteToMove())));
		
		
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
