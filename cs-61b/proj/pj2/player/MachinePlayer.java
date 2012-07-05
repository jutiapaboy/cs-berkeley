/* MachinePlayer.java */

package player;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
	public final static int BLACK = -1;		// so that the Player color variable matches its Board color
	public final static int WHITE = 1;
	public final static boolean COMPUTER = true;
	public final static boolean HUMAN = false;

	public Board board;

	public boolean side = COMPUTER;

	private int color;
	//comment
	private int depth = 1;				// default search depth; MIGHT NEED TO CHANGE
	private double score;				// assigned by minimax in Board.java
	public MachinePlayer opponent;	// Opponent that will also call minimax
	
	int maxConnections = 0;		// the maximum length of partial networks for this player
													// altered by containsNetworks() in Board.java.

	public MachinePlayer() {
		// to prevent the one-argument constructor from creating an infinite loop when
		// creating an opponent
	}
	
	/** 
	 * ONLY FOR THE GIVEN TWO CONSTRUCTORS IS @PARAM COLOR
	 * 0 OR 1.  FOR ALL OTHER METHODS, COLOR IS EITHER
	 * BLACK = -1 OR WHITE = 1.
	 */
	// Creates a machine player with the given color.  Color is either 0 (black)
	// or 1 (white).  (White has the first move.)
	public MachinePlayer(int color) {
		this();
		if (color != 0 && color != 1) {
			System.out.println("Not a valid input!  Player not created.");
			// This case is put here for testing purposes.
		}
		else {
			board = new Board();
			opponent = new MachinePlayer();
			opponent.opponent = this;
			opponent.board = board;		// share the board
			opponent.side = HUMAN;
			opponent.depth = depth;
			if (color == 0) {
				this.color = BLACK;
				myName = "MachineBlack";
				opponent.color = WHITE;
			}
			else {
				this.color = WHITE;
				myName = "MachineWhite";
				opponent.color = BLACK;
			}
			// the opponent isn't necessarily HUMAN, but the notation works because we
			// only require the boolean side to mean "is this player me?" for minimax
		}
	}

	// Creates a machine player with the given color and search depth.  Color is
	// either 0 (black) or 1 (white).  (White has the first move.)
	public MachinePlayer(int color, int searchDepth) {
		this(color);
		depth = searchDepth;
		opponent.depth = searchDepth;
	}

	// Finds the Player's color.  Can be called from Board class to return a value with  meaning;
	// i.e. the return value is a static variable that does not change between the two classes.
	public int PlayerColor() {
		return color;
	}

	/** AMANDA'S CODE START **/
	/** (Module 6) **/
	// Returns a new move by "this" player.  Internally records the move (updates
	// the internal game board) as a move by "this" player.
	public Move chooseMove() {
		Move m = minimax(side, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
		forceMove(m);
		/*
		//comment
		//SList moves = board.getValidMoves(this);
		//System.out.println(moves);
		String s;
		if (color == BLACK) {
			s = "Black Player's board";
		}
		else {
			s = "White Player's board";
		}
		s += "\n";
		System.out.println(s);
		
		*/
		
		Board.paint(board);
	  System.out.println(" I ADDED TO  " + m +  "   WITH  SCORE " + score);
		return m;
	} 

	// since we are not allowed to change chooseMove's prototype, I created a helper method
	// this is basically the pseudocode given to us in lecture, with the added variable depth (d)
	
	  public Move minimax(boolean side, double alpha, double beta, int d) {
          SList moves = board.getValidMoves(this);
          Move best = (Move)moves.nth(1);
          Move oppBest;
          if (d == 0 && side == COMPUTER) {
                  // base case; machine and human have each taken depth number of turns;
        	  
                  return best;
          }
          if (side == COMPUTER) {
      //  	  System.out.println("COMPUTER COMPUTER COMPUTER COMPUTER COMPUTER COMPUTER COMPUTER ");
        	  score = alpha;
          }
          else {
        	  score = beta;
        //	  System.out.println("HUMAN HUMAN  HUMAN HUMAN HUMAN HUMAN HUMAN HUMAN ");
          }


          for (int i = 1; i <= moves.length(); i++) {
        	  Move m = (Move)moves.nth(i);
        	  forceMove(m);
        //	  System.out.println("\nDEPTH " + d);
      //  	  System.out.println("INDEX " + i + "       MOVE "  + m  +  "SIDE?  " + side);
        //	  System.out.println("ALPHA  " + alpha + "                BETA    " + beta);
        	  //opponent.opponentMove(m);

  			if (side == COMPUTER) {
				oppBest = minimax(!side, alpha, beta, d-1);

				double e = board.evalfn(this, opponent);
		//		score = e;
		//		System.out.println("\n" + m + " is valued " + e + " for me");
				double oppE = board.evalfn(opponent, this);
				opponent.score  = oppE;
		//		System.out.println(m + " is valued " + oppE + " for opponent");
		//		System.out.println("HUMAN ALPHA "  + alpha + "         BETA"  + beta);
		//		System.out.println("HUMAN SCORE  FOR  " + m + " " + score + "      OPPONENT" + opponent.score);
			//	Board.paint(board);
			}
			else {
		//		System.out.println("DO I EVER EVEN GET HERE HERE HERE HERE HERE?????");
				oppBest = minimax(!side, alpha, beta, d);
				double e = board.evalfn(this, opponent);
			//	score = e;
			//	System.out.println("\n" + m + " is valued " + e + " for me");
				double oppE = board.evalfn(opponent, this);
				opponent.score = oppE;
			//	System.out.println(m + " is valued " + oppE + " for opponent");
			//	System.out.println("COMPUTER ALPHA "  + alpha + "         BETA"  + beta);
			//	System.out.println("COMPUTER SCORE  FOR  " + m + " " + score + "      OPPONENT " + opponent.score);
			//	Board.paint(board);
			}
        	  //	Board.paint(board);        

                  // doesn't actually need to be stored--just need opponent to call minimax to
                  // update alpha and beta and score
                  //opponent.undoOpponentMove(m);
                  undoMove(m);
              //    System.out.println("OPPONENT SCORE " + opponent.score + "    MY SCORE " + score);
                  if ((side == COMPUTER) && (opponent.score > score)) {
                          best = m;
                          score = opponent.score;
                          alpha = opponent.score;
                 //         System.out.println("alpha = " + alpha + ", beta = " + beta);
          		//		System.out.println("SIDE COMPUTER WITH SCORE " + score +  "    opponentSCORE " + opponent.score +    "                  ALPHA " + alpha);
                  } else if ((side == HUMAN) && (opponent.score < score)) {
                          best = m;
                          score = opponent.score;
                          beta = opponent.score;
                //          System.out.println("alpha = " + alpha + ", beta = " + beta);
               //             System.out.println("SIDE HUMAN WITH SCORE " + score +  "    opponentSCORE " + opponent.score +  "                 beta " + beta);
                  }
                  if (alpha >= beta) {
                //	  System.out.println("PRUNNNNEDDD YAA"   + best);
                          return best;
                  }
          }
          return best;
  }
	
	/***
	public Move minimax(boolean side, double alpha, double beta, int d) {

		SList moves = board.getValidMoves(this);
		Move best = (Move)moves.nth(1);
		Move oppBest;
		if (d == 0) {
			// base case; machine and human have each taken depth number of turns;
		//	score = board.evalfn(this, opponent);
		//	System.out.println("BESTMOVE  " + best + "  WITH SCORE   "  + score);
			return best;
		}
		if (side == COMPUTER) {
			score = alpha;
		}
		else {
			score = beta;
		}
	//	System.out.println("1    " + "alpha = " + alpha + ", beta = " + beta);
		for (int i = 1; i <= moves.length(); i++) {
			Move m = (Move)moves.nth(i);
			System.out.println("\nDEPTH " + d);
			System.out.println("INDEX " + i + "       MOVE "  + m  +  "SIDE?  " + side);
		//	System.out.println("ALPHA  " + alpha + "                BETA    " + beta);
			forceMove(m);
			//opponent.opponentMove(m);
			/**
			oppBest = opponent.minimax(opponent.side, alpha, beta, d-1);

			double e = board.evalfn(this, opponent);
			score = e;
			System.out.println("\n" + m + " is valued " + e + " for me");
			double oppE = board.evalfn(opponent, this);
			opponent.score  = oppE;
			System.out.println("\n" + m + " is valued " + oppE + " for opponent");
			System.out.println("MY SCORE  FOR  " + m + " " + score + "      OPPONENT SCORE" + opponent.score);

			if (side == HUMAN) {
				oppBest = opponent.minimax(opponent.side, alpha, beta, d-1);

				double e = board.evalfn(this, opponent);
				score = e;
				System.out.println("\n" + m + " is valued " + e + " for me");
				double oppE = board.evalfn(opponent, this);
				opponent.score  = oppE;
				System.out.println(m + " is valued " + oppE + " for opponent");
				System.out.println("HUMAN SCORE  FOR  " + m + " " + score + "      OPPONENT" + opponent.score);
			//	Board.paint(board);
			}
			else {
				oppBest = opponent.minimax(opponent.side, alpha, beta, d);

				double e = board.evalfn(this, opponent);
				score = e;
				System.out.println("\n" + m + " is valued " + e + " for me");
				double oppE = board.evalfn(opponent, this);
				opponent.score = oppE;
				System.out.println(m + " is valued " + oppE + " for opponent");
				System.out.println("COMPUTER SCORE  FOR  " + m + " " + score + "      OPPONENT " + opponent.score);
			//	Board.paint(board);
			}

			// doesn't actually need to be stored--just need opponent to call minimax to
			// update alpha and beta and score
			//opponent.undoOpponentMove(m);
			undoMove(m);
			System.out.println("NOW WE GO HERE WITH SIDE " + side  + " " +  score +    "   OPPONENT SCORE" + opponent.score);
			if ((side == COMPUTER) && (opponent.score > score)) {
				best = m;
				score = opponent.score;
				alpha = opponent.score;
				System.out.println("SIDE COMPUTER WITH SCORE " + score +  "    opponentSCORE " + opponent.score +    "                  ALPHA " + alpha);

		//		System.out.println("2    " + "alpha = " + alpha + ", beta = " + beta);
			} else if ((side == HUMAN) && (opponent.score < score)) {
				best = m;
				score = opponent.score;
				beta = opponent.score;
				//		System.out.println("3    " + "alpha = " + alpha + ", beta = " + beta);
				System.out.println("SIDE HUMAN WITH SCORE " + score +  "    opponentSCORE " + opponent.score +  "                 beta " + beta);
			}
			if (alpha >= beta) {
				return best;
			}
			
		}
		return best;
	}
	***/

	/** AMANDA'S CODE END **/





	// If the Move m is legal, records the move as a move by the opponent
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method allows your opponents to inform you of their moves.
	public boolean opponentMove(Move m) {
		int oppColor = opponent.color;
		if (!board.isValidMove(m, opponent)) {
			return false;
		}
		else {
			if (m.moveKind == Move.ADD) {
				board.GameBoard[m.x1][m.y1] = oppColor;
			}
			if (m.moveKind == Move.STEP) {
				board.GameBoard[m.x1][m.y1] = Board.EMPTY;
				board.GameBoard[m.x2][m.y2] = oppColor;
			}
			// if (m.moveKind == Move.QUIT) {
			// do nothing;
			// }
			return true;
		}
	}

	/*
	// Only called if the Move m was previously played (and is therefore valid).
	// Undoes opponent's previous Move m (undoes the update to internal game
	// board) and returns true.
	public boolean undoOpponentMove(Move m) {
		int oppColor = opponent.color;
		if (m.moveKind == Move.ADD) {
			board.GameBoard[m.x1][m.y1] = Board.EMPTY;
		}
		if (m.moveKind == Move.STEP) {
			board.GameBoard[m.x1][m.y1] = oppColor;
			board.GameBoard[m.x2][m.y2] = Board.EMPTY;
		}
		// if (m.moveKind == Move.QUIT) {
		// do nothing;
		// }
		return true;
	}
	*/

	// If the Move m is legal, records the move as a move by "this" player
	// (updates the internal game board) and returns true.  If the move is
	// illegal, returns false without modifying the internal state of "this"
	// player.  This method is used to help set up "Network problems" for your
	// player to solve.
	public boolean forceMove(Move m) {
		if (!board.isValidMove(m,this)) {
			return false;
		}
		else {
			if (m.moveKind == Move.ADD) {
				board.GameBoard[m.x1][m.y1] = color;
			}
			if (m.moveKind == Move.STEP) {
				board.GameBoard[m.x2][m.y2] = Board.EMPTY;
				board.GameBoard[m.x1][m.y1] = color;
			}
			// if (m.moveKind == Move.QUIT) {
			// do nothing;
			// }
			return true;
		}
	}

	// Only called if the Move m was previously played (and is therefore valid).
	// Undoes opponent's previous Move m (undoes the update to internal game
	// board) and returns true.
	public boolean undoMove(Move m) {
		if (m.moveKind == Move.ADD) {
			board.GameBoard[m.x1][m.y1] = Board.EMPTY;
		}
		if (m.moveKind == Move.STEP) {
			board.GameBoard[m.x2][m.y2] = color;
			board.GameBoard[m.x1][m.y1] = Board.EMPTY;
		}
		// if (m.moveKind == Move.QUIT) {
		// do nothing;
		// }
		return true;
	}
	
}