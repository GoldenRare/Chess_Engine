package board;

public class Square {
	
	private int i;
	private int j;
	
	public Square(int i, int j) {
		
		this.setI(i);
		this.setJ(j);
		
	}
	
	public static boolean isSquareAttacked(GameBoard board, Square square, boolean isWhiteAttacked) {
		// SHOULD PIECE ON THAT SQUARE COUNT AS ATTACKING?
		if (isPawnAttacking(board, square, isWhiteAttacked)) return true; 
		if (isKnightAttacking(board, square, isWhiteAttacked)) return true;
		if (isKingAttacking(board, square, isWhiteAttacked)) return true;
		if (isDiagonalPieceAttacking(board, square, isWhiteAttacked)) return true;
		if (isHorizontalVerticalPieceAttacking(board, square, isWhiteAttacked)) return true;
		
		return false;
		
	}

	public int getI() {
		
		return this.i;
		
	}

	public void setI(int i) {
		
		this.i = i;
		
	}

	public int getJ() {
		
		return this.j;
		
	}

	public void setJ(int j) {
		
		this.j = j;
		
	}
	
	@Override
	public String toString() {
		
		return "The en passant square is [" + this.i + "][" + this.j + "]";
		
	}
	
	private static boolean isPawnAttacking(GameBoard board, Square square, boolean isWhiteAttacked) {
		
		if (isWhiteAttacked) {
			
			int i = square.getI() - 1;
			int checkForPawnJ = square.getJ() - 1;
			int checkForPawnJ2 = square.getJ() + 1;
			int[] j = {checkForPawnJ, checkForPawnJ2};
			
			for (int k = 0; k < j.length; k++) {
				
				if ((i < 0) || (i > 7) || (j[k] < 0) || (j[k] > 7)) {
					
					j[k] = -1;	
				}
				
				if (j[k] != -1) {
					
					if ((board.getBoard()[i][j[k]] != null) && (board.getBoard()[i][j[k]].pieceType().equals("PAWN"))
							&& (board.getBoard()[i][j[k]].getColour() == false)) {
						
						return true;
						
					}
					
				}
			}
		} else {
			
			int i = square.getI() + 1;
			int checkForPawnJ = square.getJ() - 1;
			int checkForPawnJ2 = square.getJ() + 1;
			int[] j = {checkForPawnJ, checkForPawnJ2};
			
			for (int k = 0; k < j.length; k++) {
				
				if ((i < 0) || (i > 7) || (j[k] < 0) || (j[k] > 7)) {
					
					j[k] = -1;	
				}
				
				if (j[k] != -1) {
					
					if ((board.getBoard()[i][j[k]] != null) && (board.getBoard()[i][j[k]].pieceType().equals("PAWN"))
							&& (board.getBoard()[i][j[k]].getColour() == true)) {
						
						return true;
						
					}
					
				}
			}
		}
		
		
		return false;
	}
	
	private static boolean isKnightAttacking(GameBoard board, Square square, boolean isWhiteAttacked) {
		
		int[] movementsI = {-2, -2, -1, 1, 2, 2, 1, -1};
		int[] movementsJ = {-1, 1, 2, 2, 1, -1, -2, -2};
		
		for (int k = 0; k < movementsI.length; k++) {
			
			int i = square.getI() + movementsI[k];
			int j = square.getJ() + movementsJ[k];
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) {
				
				i = -1;
				j = -1;
				
			}
			
			if (i != -1) {
				if ((board.getBoard()[i][j] != null) && (board.getBoard()[i][j].pieceType().equals("KNIGHT"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				}
			}
			
		}
		return false;
		
	}
	
	private static boolean isKingAttacking(GameBoard board, Square square, boolean isWhiteAttacked) {
		
		int[] movementsI = {-1, -1, -1, 0, 1, 1, 1, 0};
		int[] movementsJ = {-1, 0, 1, 1, 1, 0, -1, -1};
		
		for (int k = 0; k < movementsI.length; k++) {
			
			int i = square.getI() + movementsI[k];
			int j = square.getJ() + movementsJ[k];
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) {
				
				i = -1;
				j = -1;
				
			}
			
			if (i != -1) {
				if ((board.getBoard()[i][j] != null) && (board.getBoard()[i][j].pieceType().equals("KING"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				}
			}
		}
		
		return false;
	}
	
	private static boolean isDiagonalPieceAttacking(GameBoard board, Square square, boolean isWhiteAttacked) {
		
		int lengthOfBoard = 8; //Should be its own class (final int)
		
		//Bottom-Right to Top-Left
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI() - k;
			int j = square.getJ() - k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("BISHOP") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//Top-Left to Bottom-Right
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI() + k;
			int j = square.getJ() + k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("BISHOP") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//Bottom-Left to Top-Right
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI() - k;
			int j = square.getJ() + k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("BISHOP") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//Top-Right to Bottom-Left
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI() + k;
			int j = square.getJ() - k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("BISHOP") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		
		return false;
		
	}
	
	private static boolean isHorizontalVerticalPieceAttacking(GameBoard board, Square square, boolean isWhiteAttacked) {

		int lengthOfBoard = 8; //Should be its own class (final int)

		//Left to Right
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI();
			int j = square.getJ() + k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("ROOK") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//Right to Left
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI();
			int j = square.getJ() - k;
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("ROOK") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//Down to Up
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI() - k;
			int j = square.getJ();
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("ROOK") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//Up to Down
		for (int k = 1; k < lengthOfBoard; k++) {
			
			int i = square.getI() + k;
			int j = square.getJ();
			
			if ((i < 0) || (i > 7) || (j < 0) || (j > 7)) break;
			
			if (board.getBoard()[i][j] != null) {
				if ((board.getBoard()[i][j].pieceType().equals("ROOK") || board.getBoard()[i][j].pieceType().equals("QUEEN"))
						&& (board.getBoard()[i][j].getColour() == !isWhiteAttacked)) {
					
					return true;
					
				} else {
					
					break;
					
				}
			}
		}
		return false;
	}
}
































