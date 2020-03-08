package board;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import utility.Position;

public class GameBoard_GUI implements ActionListener{

	private boolean flag = true;
	private List<JButton> squares = new ArrayList<JButton>();
	private GameBoard board;
	private int lastIndexI;
	private int lastIndexJ;
	
	public GameBoard_GUI() {
		
		JFrame f = new JFrame();
		
		int x = 100;
		int y = 100;
		boolean color = false;
		
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				ImageIcon img = new ImageIcon("piece_images/white_pawn.png");
				//b.setIcon(img);
				Image scaledImg = img.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
				ImageIcon newImg = new ImageIcon(scaledImg);
				JButton b = new JButton(newImg);
				b.addActionListener(this);
				b.setBounds(x, y, 100, 100);
				if(color) {
					
					b.setBackground(new Color(0, 255, 255));
					
				} else {
					
					b.setBackground(Color.white);
					
				}
		
				color = !color;
				this.squares.add(b);
				f.add(b);
				x += 100;
			}
			color = !color;
			x = 100;
			y += 100;
		}
		
		f.setSize(1000, 1000);
		f.setLayout(null);
		f.setVisible(true);
		
	}
	
	public GameBoard_GUI(GameBoard board) {
		
		JFrame f = new JFrame();
		
		int x = 100;
		int y = 100;
		boolean color = false;
		this.board = board;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				JButton b = new JButton();
				b.addActionListener(this);
				b.setBounds(x, y, 100, 100);
				if(color) {
					
					b.setBackground(new Color(0, 255, 255));
					
				} else {
					
					b.setBackground(Color.white);
					
				}
				
				if (this.board.getBoard()[i][j] != null) {
					
					this.board.getBoard()[i][j].printPiece(b);
					
				}
		
				color = !color;
				this.squares.add(b);
				f.add(b);
				x += 100;
				
			}
			
			color = !color;
			x = 100;
			y += 100;
		}
		
		f.setTitle("Creating a Chess Engine from Scratch");
		f.setSize(1000, 1000);
		f.setLayout(null);
		f.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		this.flag = !this.flag;
		int toIndexI = this.squares.indexOf(ae.getSource()) / 8;
		int toIndexJ = this.squares.indexOf(ae.getSource()) % 8;
		
		if (this.flag) {
			
			try {
				
				//System.out.println(Position.isMyKingInCheck(this.lastIndexI, this.lastIndexJ, toIndexI, toIndexJ, this.board)); // Debugging
				this.board = this.board.getBoard()[this.lastIndexI][this.lastIndexJ].makeMove(this.lastIndexI, this.lastIndexJ, toIndexI, toIndexJ, this.board, this.squares);
				
			} catch (NullPointerException e) {
				
				System.out.println("Invalid Move!");
			}
			
			//System.out.println(Arrays.deepToString(this.board.getBoard()).replace("], ", "]\n")); // Debugging
			//System.out.println();
			//System.out.println(this.board.getCastlingRights()); // Debugging
			//System.out.println(this.board.getEnPassantSquare().toString()); // Debugging
			//System.out.println(Square.isSquareAttacked(this.board, new Square(4, 4), false)); //Debugging
			//System.out.println(this.board.getKingPieces()[0].getSquare().toString()); // Debugging
			
		}
		
		this.lastIndexI = toIndexI;
		this.lastIndexJ = toIndexJ;
		//System.out.println("[" + toIndexI + "][" + toIndexJ + "]"); //Debugging
		
	}
	
	/*
	public void updateBoard(Pieces[][] board) {
		
		int k = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (this.board[i][j] != null) {
					
					this.board[i][j].printPiece(this.squares.get(k));
					
				} else {
					
					this.squares.get(k).setIcon(null);
				}
				k++;
			}
		}
		
	}
	*/
	
	public static void main(String[] args) {
		
		//GameBoard_GUI gameBoard = new GameBoard_GUI();
		GameBoard b = new GameBoard("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
		new GameBoard_GUI(b);
		
	}
}

