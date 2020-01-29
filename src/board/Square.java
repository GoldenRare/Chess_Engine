package board;

public class Square {
	
	private int i;
	private int j;
	
	public Square(int i, int j) {
		
		this.setI(i);
		this.setJ(j);
		
	}
	
	public static boolean isSquareAttacked(Square square, boolean isWhiteAttacked) {
		
		
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
	
	public String toString() {
		
		return "The en passant square is [" + this.i + "][" + this.j + "]";
		
	}

}
