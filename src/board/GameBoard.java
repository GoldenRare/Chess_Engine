package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Pieces;
import pieces.Queen;
import pieces.Rook;
import utility.Position;

public class GameBoard {
	
	private Pieces[][] board = new Pieces[8][8]; 
	private boolean isWhiteToMove;
	private int castlingRights; //Order of the bits: blackKingQueenside, blackKingKingside, whiteKingQueenside, whiteKingKingside
	private Square enPassant; 
	private Stack<Position> previousGameStates;
	
	////////////////
	private Pieces[] kingPieces; //WhiteKing, BlackKing
	private List<Pieces> whitePieces;
	private List<Pieces> blackPieces;
	///////////////
	
	public GameBoard() {
		
		startGame();
		
	}
	
	public GameBoard(String fen) {
		
		parseFen(fen);
		
	}
	
	private void startGame() {
		
		this.whitePieces = new ArrayList<Pieces>();
		this.blackPieces = new ArrayList<Pieces>();
		
		for(int j = 0; j < 8; j++) {
			
			this.board[6][j] = new Pawn(true, 6, j);
			this.board[1][j] = new Pawn(false, 1, j);
			this.whitePieces.add(this.board[6][j]);
			this.blackPieces.add(this.board[1][j]);
			
			
		}
		
		this.board[7][0] = new Rook(true, 7, 0);
		this.board[7][7] = new Rook(true, 7, 7);
		this.board[7][1] = new Knight(true, 7, 1);
		this.board[7][6] = new Knight(true, 7, 6);
		this.board[7][2] = new Bishop(true, 7, 2);
		this.board[7][5] = new Bishop(true, 7, 5);
		this.board[7][3] = new Queen(true, 7, 3);
		this.board[7][4] = new King(true, 7, 4);
		this.board[0][0] = new Rook(false, 0, 0);
		this.board[0][7] = new Rook(false, 0, 7);
		this.board[0][1] = new Knight(false, 0, 1);
		this.board[0][6] = new Knight(false, 0, 6);
		this.board[0][2] = new Bishop(false, 0, 2);
		this.board[0][5] = new Bishop(false, 0, 5);
		this.board[0][3] = new Queen(false, 0, 3);
		this.board[0][4] = new King(false, 0, 4);
		
		this.whitePieces.add(this.board[7][0]);
		this.whitePieces.add(this.board[7][7]);
		this.whitePieces.add(this.board[7][1]);
		this.whitePieces.add(this.board[7][6]);
		this.whitePieces.add(this.board[7][2]);
		this.whitePieces.add(this.board[7][5]);
		this.whitePieces.add(this.board[7][3]);
		this.whitePieces.add(this.board[7][4]);
		this.blackPieces.add(this.board[0][0]);
		this.blackPieces.add(this.board[0][7]);
		this.blackPieces.add(this.board[0][1]);
		this.blackPieces.add(this.board[0][6]);
		this.blackPieces.add(this.board[0][2]);
		this.blackPieces.add(this.board[0][5]);
		this.blackPieces.add(this.board[0][3]);
		this.blackPieces.add(this.board[0][4]);
		
		this.isWhiteToMove = true;
		this.castlingRights = 0b1111;
		this.enPassant = new Square(-1, -1);
		
		////////////////////
		this.kingPieces = new Pieces[] {this.board[7][4], this.board[0][4]};
		this.previousGameStates = new Stack<Position>();
		
	}
	
	private void parseFen(String fen) {
		
	}
	
	//The Pieces internal square
	//Print onto the board
	public void undoMove() {
		
		//System.out.println(this.getPreviousGameStates().size());
		Position restorePosition = this.previousGameStates.pop();
		//System.out.println(this.getPreviousGameStates().size());
		//System.out.println(restorePosition.enPassantI() + " " + restorePosition.enPassantJ());
		
		this.isWhiteToMove = restorePosition.isWhiteToMove();
		//System.out.println(restorePosition.lastIndexI() + " " + restorePosition.lastIndexJ() + " " + restorePosition.toIndexI() + " " + restorePosition.toIndexJ() + " Undo");
		this.castlingRights = restorePosition.castlingRights();
		this.enPassant.setI(restorePosition.enPassantI());
		this.enPassant.setJ(restorePosition.enPassantJ());
		this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()] = this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()];
		this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()].getSquare().setI(restorePosition.lastIndexI());
		this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()].getSquare().setJ(restorePosition.lastIndexJ());
		
		if (restorePosition.castlingKingSide()) restoreKingSideRook(restorePosition.lastIndexI(), restorePosition.lastIndexJ()); //TODO
		if (restorePosition.castlingQueenSide()) restoreQueenSideRook(restorePosition.lastIndexI(), restorePosition.lastIndexJ()); //TODO
		
		if (restorePosition.isEnPassantMove()) {
			
			this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()] = null;
			this.board[restorePosition.lastIndexI()][restorePosition.toIndexJ()] = restorePosition.capturedPiece();
			
			
		} else {
			
			this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()] = restorePosition.capturedPiece();
		
		}
		
		if ((restorePosition.capturedPiece() != null) && (restorePosition.capturedPiece().getColour() == true)) {
			
			this.whitePieces.add(restorePosition.capturedPiece());

		} else if ((restorePosition.capturedPiece() != null) && (restorePosition.capturedPiece().getColour() == false)) {
			
			this.blackPieces.add(restorePosition.capturedPiece());
			
		}
		//System.out.print(Arrays.deepToString(this.getBoard()).replace("], ", "]\n"));
		//System.out.println("\n");
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

	public Stack<Position> getPreviousGameStates() {
		
		return this.previousGameStates;
		
	}

	public void addGameState(Position gameState) {
		
		this.previousGameStates.push(gameState);
	}
	
	public List<Pieces> getWhitePieces() {
		
		return this.whitePieces;
		
	}
	
	public List<Pieces> getBlackPieces() {
		
		return this.blackPieces;
		
	}
	
	private void restoreKingSideRook(int lastIndexI, int lastIndexJ) {
		
		if (this.board[lastIndexI][lastIndexJ].getColour() == true) {
			
			this.board[7][7] = this.board[7][5];
			this.board[7][5] = null;
			
		} else {
			
			this.board[0][7] = this.board[0][5];
			this.board[0][5] = null;
			
		}
	}
	
	private void restoreQueenSideRook(int lastIndexI, int lastIndexJ) {
		
		if (this.board[lastIndexI][lastIndexJ].getColour() == true) {
			
			this.board[7][0] = this.board[7][3];
			this.board[7][3] = null;
			
		} else {
			
			this.board[0][0] = this.board[0][3];
			this.board[0][3] = null;
			
		}
	}
	
	public static void main(String[] args) {
		
		GameBoard b = new GameBoard();
		System.out.print(Arrays.deepToString(b.getBoard()).replace("], ", "]\n"));
		
	}

}
