package board;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Pieces;
import pieces.Queen;
import pieces.Rook;
import utility.Evaluation;
import utility.Position;

public class GameBoard {
	
	private Pieces[][] board; 
	private boolean isWhiteToMove;
	private int castlingRights; //Order of the bits: blackKingQueenside, blackKingKingside, whiteKingQueenside, whiteKingKingside
	private Square enPassant; 
	private Stack<Position> previousGameStates;
	////////////////
	private Pieces[] kingPieces; //WhiteKing, BlackKing
	private List<Pieces> whitePieces;
	private List<Pieces> blackPieces;
	///////////////
	private int parseFenI;
	private int parseFenJ;
	///////////////
	private long[][] zobristBoardArray;
	private long zobristBlackToMove;
	private long[] zobristCastlingRights; //Order of the array: blackKingQueenside, blackKingKingside, whiteKingQueenside, whiteKingKingside
	private long[] zobristEnPassant;
	///////////////
	private long positionHash;
	///////////////
	private HashMap<Long, Evaluation> transpositionTable = new HashMap<Long, Evaluation>();
	
	public GameBoard() {
		
		this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		
	}
	
	public GameBoard(String fen) {
		
		parseFen(fen);
		zobristHashing();
		hashPosition();
		
	}
	/*
	private void startGame() {
		
		this.board = new Pieces[8][8];
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
	*/
	private void parseFen(String fen) {
		
		this.board = new Pieces[8][8]; // 8 x 8 chess board
		this.whitePieces = new ArrayList<Pieces>();
		this.blackPieces = new ArrayList<Pieces>();
		this.castlingRights = 0b0000;
		this.enPassant = new Square(-1, -1); // Needs to be implemented
		this.kingPieces = new Pieces[2];
		this.previousGameStates = new Stack<Position>();
		
		int k = 0;
		for (this.parseFenI = 0; this.parseFenI < 8; this.parseFenI++) {
			for (this.parseFenJ = 0; this.parseFenJ < 8; this.parseFenJ++) {
				piecePlacements(fen.charAt(k));
				k++;
			}
		}
		k++;
		this.isWhiteToMove = (fen.charAt(k) == 'w') ? true : false;
		k = k + 2;
		while (fen.charAt(k) != ' ') {
			
			castlingAbility(fen.charAt(k));
			k++;
			
		}
	}
	
	private void piecePlacements(char c) {
		
		switch (c) {
		
		case 'r':
			
			this.board[this.parseFenI][this.parseFenJ] = new Rook(false, this.parseFenI, this.parseFenJ);
			this.blackPieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'n':
			
			this.board[this.parseFenI][this.parseFenJ] = new Knight(false, this.parseFenI, this.parseFenJ);
			this.blackPieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'b':
			
			this.board[this.parseFenI][this.parseFenJ] = new Bishop(false, this.parseFenI, this.parseFenJ);
			this.blackPieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'q': 
			
			this.board[this.parseFenI][this.parseFenJ] = new Queen(false, this.parseFenI, this.parseFenJ);
			this.blackPieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'k':
			
			this.board[this.parseFenI][this.parseFenJ] = new King(false, this.parseFenI, this.parseFenJ);
			this.blackPieces.add(this.board[this.parseFenI][this.parseFenJ]);
			this.kingPieces[1] = this.board[this.parseFenI][this.parseFenJ];
			break;
			
		case 'p': 
			
			this.board[this.parseFenI][this.parseFenJ] = new Pawn(false, this.parseFenI, this.parseFenJ);
			this.blackPieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'R':
			
			this.board[this.parseFenI][this.parseFenJ] = new Rook(true, this.parseFenI, this.parseFenJ);
			this.whitePieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'N':
			
			this.board[this.parseFenI][this.parseFenJ] = new Knight(true, this.parseFenI, this.parseFenJ);
			this.whitePieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'B':
			
			this.board[this.parseFenI][this.parseFenJ] = new Bishop(true, this.parseFenI, this.parseFenJ);
			this.whitePieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'Q': 
			
			this.board[this.parseFenI][this.parseFenJ] = new Queen(true, this.parseFenI, this.parseFenJ);
			this.whitePieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case 'K':
			
			this.board[this.parseFenI][this.parseFenJ] = new King(true, this.parseFenI, this.parseFenJ);
			this.whitePieces.add(this.board[this.parseFenI][this.parseFenJ]);
			this.kingPieces[0] = this.board[this.parseFenI][this.parseFenJ];
			break;
			
		case 'P': 
			
			this.board[this.parseFenI][this.parseFenJ] = new Pawn(true, this.parseFenI, this.parseFenJ);
			this.whitePieces.add(this.board[this.parseFenI][this.parseFenJ]);
			break;
			
		case '/':
			
			this.parseFenJ = this.parseFenJ - 1;
			break;
			
		case '1':
			
			break;
			
		case '2':
			
			this.parseFenJ = this.parseFenJ + 1;
			break;
			
		case '3':
			
			this.parseFenJ = this.parseFenJ + 2;
			break;
			
		case '4':
			
			this.parseFenJ = this.parseFenJ + 3;
			break;
			
		case '5':
			
			this.parseFenJ = this.parseFenJ + 4;
			break;
			
		case '6':
			
			this.parseFenJ = this.parseFenJ + 5;
			break;
			
		case '7':
			
			this.parseFenJ = this.parseFenJ + 6;
			break;
			
		case '8':
			
			this.parseFenJ = this.parseFenJ + 7;
			break;
		
		}
	}
	
	private void castlingAbility(char c) {
		
		switch (c) {
		
		case 'K':
			
			setCastlingRights(getCastlingRights() | 0b0001);
			break;
			
		case 'Q':
			
			setCastlingRights(getCastlingRights() | 0b0010);
			break;
			
		case 'k':
			
			setCastlingRights(getCastlingRights() | 0b0100);
			break;
			
		case 'q':
			
			setCastlingRights(getCastlingRights() | 0b1000);
			break;
			
		case '-':
			
			break;
		
		}
	}
	
	public void undoMove() {
		
		//System.out.print(Arrays.deepToString(this.board).replace("], ", "]\n"));
		//System.out.println("\n");
		Position restorePosition = this.previousGameStates.pop();
		
		this.isWhiteToMove = restorePosition.isWhiteToMove();
		this.castlingRights = restorePosition.castlingRights();
		this.enPassant.setI(restorePosition.enPassantI());
		this.enPassant.setJ(restorePosition.enPassantJ());
		this.positionHash = restorePosition.positionHash();
		
		if (isPromotionMove(restorePosition)) {
			
			// Adds back in promoted pawn (internal board and pieceList)
			// Removes promoted piece (internal board and pieceList)
			undoPromotion(restorePosition);
			
		} else {
			
			this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()] = this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()];
			this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()].getSquare().setI(restorePosition.lastIndexI());
			this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()].getSquare().setJ(restorePosition.lastIndexJ());
			
		}
	
		if (restorePosition.castlingKingSide()) restoreKingSideRook(restorePosition.lastIndexI(), restorePosition.lastIndexJ());
		if (restorePosition.castlingQueenSide()) restoreQueenSideRook(restorePosition.lastIndexI(), restorePosition.lastIndexJ()); 
		
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
		
		//System.out.print(Arrays.deepToString(this.board).replace("], ", "]\n"));
		//System.out.println("\n");
	}
	
	private void zobristHashing() {
		
		SecureRandom random = new SecureRandom();
		
		this.zobristBoardArray = new long[64][12];
		this.zobristBlackToMove = random.nextLong();
		this.zobristCastlingRights = new long[4];
		this.zobristEnPassant = new long[8];

		
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 12; j++) {
				
				this.zobristBoardArray[i][j] = random.nextLong();
				
			}
		}
	
		for (int i = 0; i < 4; i++) this.zobristCastlingRights[i] = random.nextLong();
		for (int i = 0; i < 8; i++) this.zobristEnPassant[i] = random.nextLong();
	}
	
	private void hashPosition() {
		
		this.positionHash = 0;
		
		for (Pieces whitePiece : this.whitePieces) {
			
			int square = (whitePiece.getSquare().getI() * 8) + whitePiece.getSquare().getJ();
			int hashIndex = whitePiece.hashIndex();
			
			this.positionHash ^= this.zobristBoardArray[square][hashIndex];
			
		}
		
		for (Pieces blackPiece : this.blackPieces) {
			
			int square = (blackPiece.getSquare().getI() * 8) + blackPiece.getSquare().getJ();
			int hashIndex = blackPiece.hashIndex();
			
			this.positionHash ^= this.zobristBoardArray[square][hashIndex];
			
		}
		
		if (!this.isWhiteToMove) this.positionHash ^= this.zobristBlackToMove;
		
		if ((this.castlingRights & 0b0001) == 0b0001) this.positionHash ^= this.zobristCastlingRights[3];
		if ((this.castlingRights & 0b0010) == 0b0010) this.positionHash ^= this.zobristCastlingRights[2];
		if ((this.castlingRights & 0b0100) == 0b0100) this.positionHash ^= this.zobristCastlingRights[1];
		if ((this.castlingRights & 0b1000) == 0b1000) this.positionHash ^= this.zobristCastlingRights[0];
		
		if (this.enPassant.getJ() != -1) this.positionHash ^= this.zobristEnPassant[this.enPassant.getJ()];
		
	}

	private boolean isPromotionMove(Position restorePosition) {
		
		boolean result = false;
		
		if ((restorePosition.promotedPawn().pieceType().equals("PAWN")) && ((restorePosition.toIndexI() == 0) || (restorePosition.toIndexI() == 7))) {
			
			result = true;
			
		} 
		
		return result;
	}

	private void undoPromotion(Position restorePosition) {
		
		this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()] = restorePosition.promotedPawn();
		this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()].getSquare().setI(restorePosition.lastIndexI());
		this.board[restorePosition.lastIndexI()][restorePosition.lastIndexJ()].getSquare().setJ(restorePosition.lastIndexJ());
		
		if ((this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()] != null) && (this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()].getColour() == true)) {
			
			this.whitePieces.add(restorePosition.promotedPawn());
			this.whitePieces.remove(this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()]);
			this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()] = null; //

		} else if ((this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()] != null) && (this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()].getColour() == false)) {
			
			this.blackPieces.add(restorePosition.promotedPawn());
			this.blackPieces.remove(this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()]);
			this.board[restorePosition.toIndexI()][restorePosition.toIndexJ()] = null; //
			
		}
		
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
	
	public long getPositionHash() {
		
		return this.positionHash;
		
	}
	
	public void setPositionHash(long positionHash) {
		
		this.positionHash = positionHash;
		
	}
	
	public long[][] getZobristBoardArray() {
		
		return this.zobristBoardArray;
		
	}

	public long getZobristBlackToMove() {
		
		return this.zobristBlackToMove;
		
	}

	public long[] getZobristCastlingRights() {
		
		return this.zobristCastlingRights;
		
	}

	public long[] getZobristEnPassant() {
		
		return this.zobristEnPassant;
		
	}

	public HashMap<Long, Evaluation> getTranspositionTable() {
		
		return this.transpositionTable;
		
	}

	private void restoreKingSideRook(int lastIndexI, int lastIndexJ) {
		
		if (this.board[lastIndexI][lastIndexJ].getColour() == true) {
			
			this.board[7][7] = this.board[7][5];
			this.board[7][7].getSquare().setI(7);
			this.board[7][7].getSquare().setJ(7);
			this.board[7][5] = null;
			
		} else {
			
			this.board[0][7] = this.board[0][5];
			this.board[0][7].getSquare().setI(0);
			this.board[0][7].getSquare().setJ(7);
			this.board[0][5] = null;
			
		}
	}
	
	private void restoreQueenSideRook(int lastIndexI, int lastIndexJ) {
		
		if (this.board[lastIndexI][lastIndexJ].getColour() == true) {
			
			this.board[7][0] = this.board[7][3];
			this.board[7][0].getSquare().setI(7);
			this.board[7][0].getSquare().setJ(0);
			this.board[7][3] = null;
			
		} else {
			
			this.board[0][0] = this.board[0][3];
			this.board[0][0].getSquare().setI(0);
			this.board[0][0].getSquare().setJ(0);
			this.board[0][3] = null;
			
		}
	}
	
	public static void main(String[] args) {
		
		GameBoard b = new GameBoard();
		
		System.out.println(b.getPositionHash());
		b.getBoard()[7][6].makeMove(7, 6, 5, 5, b, false);
		System.out.println(b.getPositionHash());
		b.getBoard()[0][6].makeMove(0, 6, 2, 5, b, false);
		System.out.println(b.getPositionHash());
		b.getBoard()[5][5].makeMove(5, 5, 7, 6, b, false);
		System.out.println(b.getPositionHash());
		b.getBoard()[2][5].makeMove(2, 5, 0, 6, b, false);
		System.out.println(b.getPositionHash());
		
	}
}
