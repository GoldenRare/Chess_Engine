package pieces;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import board.GameBoard;
import utility.Position;

public class Knight extends Pieces {

	public double value = 10; //Unsure
	
	public Knight(boolean isWhite, int i, int j) {
		
		super(isWhite, i, j);
		super.pieceType = "KNIGHT";
		super.pieceValue = 300;
		
	}
	
	public void printPiece(JButton b) {
		
		if (super.isWhite) {
			
			ImageIcon img = new ImageIcon("piece_images/white_knight.png");
			Image scaledImg = img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
			
		} else {
			
			ImageIcon img = new ImageIcon("piece_images/black_knight.png");
			Image scaledImg = img.getImage().getScaledInstance(105, 105, Image.SCALE_DEFAULT);
			ImageIcon newImg = new ImageIcon(scaledImg);
			b.setIcon(newImg);
		}
		
		
	}
	
	public GameBoard makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, List<JButton> b) {
		// Consider adding new function for this
		// Consider bitboards
		// DOES MOVE LEAVE KING IN CHECK
		
		if (oldBoard.isWhiteToMove() != oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour()) {
			
			return oldBoard;
			
		}
		
		if ((oldBoard.getBoard()[toIndexI][toIndexJ] != null) && (oldBoard.getBoard()[lastIndexI][lastIndexJ].getColour() == oldBoard.getBoard()[toIndexI][toIndexJ].getColour())) {
			
			return oldBoard;
			
		}
		// Technically not Magnitude
		int magnitudeOfMove = ((toIndexI - lastIndexI) * (toIndexI - lastIndexI)) + ((toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ));
		int knightMagnitude = 5;
		
		if (magnitudeOfMove == knightMagnitude) {
			
			if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return oldBoard;
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			GameBoard newBoard = oldBoard;
			newBoard.getBoard()[toIndexI][toIndexJ] = newBoard.getBoard()[lastIndexI][lastIndexJ];
			printPiece(b.get(toIndexI * 8 + toIndexJ));
			newBoard.getBoard()[lastIndexI][lastIndexJ] = null;
			b.get(lastIndexI * 8 + lastIndexJ).setIcon(null);
			newBoard.setWhiteToMove(!newBoard.isWhiteToMove());
			newBoard.setEnPassantSquare(-1, -1);
			this.square.setI(toIndexI);
			this.square.setJ(toIndexJ);
			
			//row * 8 + col
			return newBoard;
			
		}
		
		//row * 8 + col
		return oldBoard;
		
	}
	
	public boolean makeMove(int lastIndexI, int lastIndexJ, int toIndexI, int toIndexJ, GameBoard oldBoard, boolean checkPseudoMove) {
		
		if (isPieceColourNotTheSideToMove(lastIndexI, lastIndexJ, oldBoard)) return false;
		if (isToAndFromSquareTheSameColourPiece(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;
		if ((!checkPseudoMove) && (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard))) return false;
		
		int magnitudeOfMove = ((toIndexI - lastIndexI) * (toIndexI - lastIndexI)) + ((toIndexJ - lastIndexJ) * (toIndexJ - lastIndexJ));
		int knightMagnitude = 5;
		
		if (magnitudeOfMove == knightMagnitude) {
			
			//if (Position.isMyKingInCheck(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard)) return false;
			
			oldBoard.addGameState(new Position(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard, false, false, false));
			updateGameBoard(lastIndexI, lastIndexJ, toIndexI, toIndexJ, oldBoard);
			this.square.setI(toIndexI);
			this.square.setJ(toIndexJ);
			
			//row * 8 + col
			return true;
			
		}
		
		//row * 8 + col
		return false;
		
	}
}
