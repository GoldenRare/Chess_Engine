package utility;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import board.GameBoard;
import board.Square;
import pieces.Pieces;

public class Evaluation {

	private long positionKey;
	private Move bestMove;
	private int depth;
	private boolean isCheckmate;
	
	public Evaluation(long positionKey, Move bestMove, int depth, boolean isCheckmate) {
		
		this.positionKey = positionKey;
		this.bestMove = bestMove;
		this.depth = depth;
		this.isCheckmate = isCheckmate;
		
	}
	
	public static final int MATE_SCORE = 999999;
	public static final int[][] WHITE_PAWN_TABLE = { 
			{0,  0,  0,  0,  0,  0,  0,  0},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{5,  5, 10, 25, 25, 10,  5,  5},
			{0,  0,  0, 20, 20,  0,  0,  0},
			{5, -5,-10,  0,  0,-10, -5,  5},
			{5, 10, 10,-20,-20, 10, 10,  5},
			{0,  0,  0,  0,  0,  0,  0,  0}};
	
	public static final int[][] BLACK_PAWN_TABLE = { 
			{0,  0,  0,  0,  0,  0,  0,  0},
			{5, 10, 10,-20,-20, 10, 10,  5},
			{5, -5,-10,  0,  0,-10, -5,  5},
			{0,  0,  0, 20, 20,  0,  0,  0},
			{5,  5, 10, 25, 25, 10,  5,  5},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{0,  0,  0,  0,  0,  0,  0,  0}};
	
	public static final int[][] WHITE_KNIGHT_TABLE = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
			{-40,-20,  0,  0,  0,  0,-20,-40},
			{-30,  0, 10, 15, 15, 10,  0,-30},
			{-30,  5, 15, 20, 20, 15,  5,-30},
			{-30,  0, 15, 20, 20, 15,  0,-30},
			{-30,  5, 10, 15, 15, 10,  5,-30},
			{-40,-20,  0,  5,  5,  0,-20,-40},
			{-50,-40,-30,-30,-30,-30,-40,-50}};
	
	public static final int[][] BLACK_KNIGHT_TABLE = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
			{-40,-20,  0,  5,  5,  0,-20,-40},
			{-30,  5, 10, 15, 15, 10,  5,-30},
			{-30,  0, 15, 20, 20, 15,  0,-30},
			{-30,  5, 15, 20, 20, 15,  5,-30},
			{-30,  0, 10, 15, 15, 10,  0,-30},
			{-40,-20,  0,  0,  0,  0,-20,-40},
			{-50,-40,-30,-30,-30,-30,-40,-50}};
	
	public static final int[][] WHITE_BISHOP_TABLE = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}};
	
	public static final int[][] BLACK_BISHOP_TABLE = {
			{-20,-10,-10,-10,-10,-10,-10,-20},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}};
	
	public static final int[][] WHITE_ROOK_TABLE = {
			{0,  0,  0,  0,  0,  0,  0,  0},
			{5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{0,  0,  0,  5,  5,  0,  0,  0}};
	
	public static final int[][] BLACK_ROOK_TABLE = {
			{0,  0,  0,  5,  5,  0,  0,  0},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{5, 10, 10, 10, 10, 10, 10,  5},
			{0,  0,  0,  0,  0,  0,  0,  0}};
	
	public static final int[][] WHITE_QUEEN_TABLE = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{-5,  0,  5,  5,  5,  5,  0, -5},
			{0,  0,  5,  5,  5,  5,  0, -5},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}};
	
	public static final int[][] BLACK_QUEEN_TABLE = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{0,  0,  5,  5,  5,  5,  0, -5},
			{-5,  0,  5,  5,  5,  5,  0, -5},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}};
	
	//Middle Game
	public static final int[][] WHITE_KING_TABLE = {
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-20,-30,-30,-40,-40,-30,-30,-20},
			{-10,-20,-20,-20,-20,-20,-20,-10},
			{20, 20,  0,  0,  0,  0, 20, 20},
			{20, 30, 10,  0,  0, 10, 30, 20}};
	
	public static final int[][] BLACK_KING_TABLE = {
			{20, 30, 10,  0,  0, 10, 30, 20},
			{20, 20,  0,  0,  0,  0, 20, 20},
			{-10,-20,-20,-20,-20,-20,-20,-10},
			{-20,-30,-30,-40,-40,-30,-30,-20},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30}};
	
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
			//if (bestMove != null)
			//System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					//bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			return alpha;
		} else {
			
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
			//if (bestMove != null)
			//System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					//bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			return beta;
		}
		
	}
	
	public static int alphaBetaIterativeDeepening(GameBoard board, int alpha, int beta, int depth, boolean isMaximizingPlayer) {
		
		if (depth == 0) return evaluatePosition(board);
		List<Move> movesList = Move.GenerateLegalMoves(board);
		Move bestMove = null;
		boolean isCheckmate = false;

		/////////////////
		boolean storePVMove = true;
		Map<Long, Evaluation> transpositionTable = board.getTranspositionTable();
		long key = board.getPositionHash();
		
		if (transpositionTable.containsKey(key) && transpositionTable.get(key).positionKey == key) {
			
			Evaluation eval = transpositionTable.get(key);
			bestMove = eval.bestMove;
			if (depth <= eval.depth) storePVMove = false; //Could just return evaluation
		
		}
		
		if (bestMove != null) {
			
			int index = movesList.indexOf(bestMove);
			if (index != -1) {
				Move temp = movesList.get(0);
				movesList.set(0, bestMove);
				movesList.set(index, temp);
			}
		}
		if (isMaximizingPlayer) {
			
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
			
				int evaluation = alphaBetaIterativeDeepening(board, alpha, beta, depth - 1, !isMaximizingPlayer);
				board.undoMove();
				
				if (evaluation >= beta) {
					return beta;
					
				}

				if (evaluation > alpha) {
					alpha = evaluation;
					if (alpha == MATE_SCORE) isCheckmate = true;
					if (i != -1) bestMove = movesList.get(i);
				}
			}
			
			if ((bestMove != null) && (storePVMove)) transpositionTable.put(key, new Evaluation(key, bestMove, depth, isCheckmate));
			return alpha;
		} else {
			
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
	
				int evaluation = alphaBetaIterativeDeepening(board, alpha, beta, depth - 1, !isMaximizingPlayer);
				board.undoMove();
				if (evaluation <= alpha) {
					return alpha;
				}
				
				if (evaluation < beta) {
					
					beta = evaluation;
					if (beta == -MATE_SCORE) isCheckmate = true;
					if (i != -1) bestMove = movesList.get(i);
				}	
			}
			if ((bestMove != null) && (storePVMove)) transpositionTable.put(key, new Evaluation(key, bestMove, depth, isCheckmate));
			return beta;
		}
		
	}
	
	public static void iterativeDeepening(GameBoard board, int depthMax) {
		
		for (int depth = 1; depth < depthMax; depth++) {
			
			alphaBetaIterativeDeepening(board, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, board.isWhiteToMove());
			Move bestMove = board.getTranspositionTable().get(board.getPositionHash()).bestMove;
			System.out.println(bestMove.getPiece().getSquare().getI() + " " + bestMove.getPiece().getSquare().getJ() + " " + 
					bestMove.getToIndexI() + " " + bestMove.getToIndexJ() + " " + bestMove.getPromotionPiece());
			if (board.getTranspositionTable().get(board.getPositionHash()).isCheckmate) break;
			
		}
	}
	
	public static int evaluatePosition(GameBoard board) {
	
		if (isCheckmate(board)) return (!board.isWhiteToMove()) ? MATE_SCORE : -MATE_SCORE;
		return materialEvaluation(board);
		
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
	
	private static int materialEvaluation(GameBoard board) {
		
		int whiteMaterial = 0;
		int blackMaterial = 0;
		int totalMaterial = 0;
		
		for (Pieces whitePiece : board.getWhitePieces()) {
			
			int i = whitePiece.getSquare().getI();
			int j = whitePiece.getSquare().getJ();
			
			whiteMaterial += whitePiece.pieceValue();
			
			if (whitePiece.pieceType().equals("PAWN")) whiteMaterial += Evaluation.WHITE_PAWN_TABLE[i][j];
			if (whitePiece.pieceType().equals("KNIGHT")) whiteMaterial += Evaluation.WHITE_KNIGHT_TABLE[i][j];
			if (whitePiece.pieceType().equals("BISHOP")) whiteMaterial += Evaluation.WHITE_BISHOP_TABLE[i][j];
			if (whitePiece.pieceType().equals("ROOK")) whiteMaterial += Evaluation.WHITE_ROOK_TABLE[i][j];
			if (whitePiece.pieceType().equals("QUEEN")) whiteMaterial += Evaluation.WHITE_QUEEN_TABLE[i][j];
			if (whitePiece.pieceType().equals("KING")) whiteMaterial += Evaluation.WHITE_KING_TABLE[i][j];
			
		}
		
		for (Pieces blackPiece : board.getBlackPieces()) {
			
			int i = blackPiece.getSquare().getI();
			int j = blackPiece.getSquare().getJ();
			
			blackMaterial += blackPiece.pieceValue();
			
			if (blackPiece.pieceType().equals("PAWN")) blackMaterial += Evaluation.BLACK_PAWN_TABLE[i][j];
			if (blackPiece.pieceType().equals("KNIGHT")) blackMaterial += Evaluation.BLACK_KNIGHT_TABLE[i][j];
			if (blackPiece.pieceType().equals("BISHOP")) blackMaterial += Evaluation.BLACK_BISHOP_TABLE[i][j];
			if (blackPiece.pieceType().equals("ROOK")) blackMaterial += Evaluation.BLACK_ROOK_TABLE[i][j];
			if (blackPiece.pieceType().equals("QUEEN")) blackMaterial += Evaluation.BLACK_QUEEN_TABLE[i][j];
			if (blackPiece.pieceType().equals("KING")) blackMaterial += Evaluation.BLACK_KING_TABLE[i][j];
			
		}
		
		totalMaterial = whiteMaterial - blackMaterial;
		
		return totalMaterial;
		
	}
	
	private static boolean isCheckmate(GameBoard board) {
		
		boolean result = false;
		if (Move.GenerateLegalMoves(board).isEmpty()) {
			int i, j;
			if (board.isWhiteToMove()) {
				
				i = board.getKingPieces()[0].getSquare().getI();
				j = board.getKingPieces()[0].getSquare().getJ();
				if (Square.isSquareAttacked(board, new Square(i, j), true)) result = true;
				
			} else {
				
				i = board.getKingPieces()[1].getSquare().getI();
				j = board.getKingPieces()[1].getSquare().getJ();
				if (Square.isSquareAttacked(board, new Square(i, j), false)) result = true;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		//7K/P1p1p1p1/2P1P1Pk/6pP/3p2P1/1P6/3P4/8 w - - 0 1
		GameBoard b = new GameBoard("r5k1/pR4pp/2p1b3/8/r4p2/2K2P2/5P1P/8 b - - 0 5");
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(Perft(6, b)));
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(miniMax(b, 6, b.isWhiteToMove())));
		Instant start = Instant.now();
		//System.out.println(NumberFormat.getNumberInstance(Locale.US).format(alphaBeta(b, Integer.MIN_VALUE, Integer.MAX_VALUE, 5, b.isWhiteToMove())));
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toSeconds();
		System.out.println("Algorithm took " + timeElapsed + " seconds.\n");
		start = Instant.now();
		iterativeDeepening(b, 50);
		finish = Instant.now();
		timeElapsed = Duration.between(start, finish).toSeconds();
		System.out.println("Algorithm took " + timeElapsed + " seconds.\n");
		
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
