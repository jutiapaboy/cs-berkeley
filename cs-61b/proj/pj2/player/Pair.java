package player;

public class Pair {
	// used to represent chips on the board
	// primarily for use in finding connecting pieces, cleaner implementation

	public static final int BLACK = -1;
	public static final int WHITE = 1;
	public static final int EMPTY = 0;

	private int x;			// the x-coordinate of the piece
	private int y;			// the y-coordinate of the piece
	private int pcolor;	// the color of the piece

	// empty constructor nonsensical in this case

	public Pair(int a, int b, int color) {		// construct a new Pair chip
		// construct an invalid pair
		if ((-1 < a && a < 8) && (-1 < b && b < 8)) {
			x = a;
			y = b;
			pcolor = color;
		} else {
			System.out.println("Invalid input!  Pair not created.");
		}
	}

	public int xCoord() {
		return x;
	}

	public int yCoord() {
		return y;
	}

	public int myColor() {
		return pcolor;
	}

	public int opponentColor() {
		return -pcolor;
		// since white and black are opposite signs
	}

	public boolean equals(Object p) {
		if (x == ((Pair)p).x && y == ((Pair)p).y && pcolor == ((Pair)p).pcolor) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "(" + x + "," + y + "," + pcolor + ")";
	}

}