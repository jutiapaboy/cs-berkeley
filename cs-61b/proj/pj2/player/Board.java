/* Board.java */


package player;

/** A public class for Boards that all Players will use to play the game.
 *  
 *  A Board object is represented by a two-dimensional array, where each 
 *  cell contains either EMPTY (represented by the integer 0), BLACK 
 *  (represented by  the integer -1), or WHITE (represented by the integer 1). 
 *  
 *  The Board has several methods which can be called by the player to 
 *  get information about the current state of the game.
 *  
 *  Each Player has an individual Board to keep track of the game.
 */

import list.*;

public class Board {
	public final static int BLACK = -1;		// same as BLACK in MachinePlayer.java
	public final static int EMPTY = 0;		// default entry in new arrays
	public final static int WHITE = 1;		// same as WHITE in MachinePlayer.java
	public final static int DIMENSION = 8;
	
	int[][] GameBoard;

	/**
	 *  Constructor with no arguments, creates a standard empty 8x8 board.
	 **/
	public Board() {
		GameBoard = new int[DIMENSION][DIMENSION];
	}

	/**
	 * copy() copies this Board.
	 * @return a COPY of this Board
	 */
	public Board copy() {
		Board b = new Board();
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				b.GameBoard[i][j] = this.GameBoard[i][j];
			}
		}
		return b;
	}

	/** AMY'S CODE START **/
	/** (Modules 1 and 2) **/
	/**
	 *  isValidMove() checks if the given Move is valid for the given Player's color
	 *  according to the game rules.
	 *  @param move the given Move
	 *  @param color the given Player's color
	 *  @return true if the Move is valid, false otherwise.
	 **/
	public boolean isValidMove(Move move, Player player) {
		int playerColor = ((MachinePlayer) player).PlayerColor();
		boolean result = true;
		
		switch(move.moveKind) {
		case 1:		// if moveKind == ADD
			int addx1 = move.x1;
			int addy1 = move.y1;
			Pair xcoordy1 = new Pair(addx1, addy1, playerColor);
			// if the move is both at a valid coordinate and satisfies the fourth rule,
			// return true, false otherwise.
			return ((isValidCoordinate(xcoordy1)) && satisfiesFourthRule(xcoordy1));
			
		case 2:		// if moveKind == STEP
			int newx = move.x1;
			int oldx = move.x2;
			int newy = move.y1;
			int oldy = move.y2;
			//Pair xcoordy2 = new Pair(stepx2, stepy2, playerColor);
			// First set the old coordinates to EMPTY to see if placing the step piece
			// into the new coordinates would be valid.
			Pair newCoord = new Pair(newx, newy, playerColor);
			if (GameBoard[oldx][oldy] == playerColor && newx != oldx && newy != oldy) {
				// the old coordinate actually belongs to the player
				// the old and new coordinates are different
				GameBoard[oldx][oldy] = EMPTY;
				if (isValidCoordinate(newCoord) && satisfiesFourthRule(newCoord)) {
					// the new coordinates are both valid and satisfy the fourth rule
					// reset the original coordinates and return true
					GameBoard[oldx][oldy] = playerColor;
					return true;
				}
				else {
					// otherwise undo the move and return false
					GameBoard[oldx][oldy] = playerColor;
					return false;
				}
			}
			else {
				return false;
			}
			
		default:		// if moveKind == QUIT
			break;	// move on
		}
		
		return result;
	}
	
	/**
	 *  isValidCoordinate() is a helper method for isValidMove().
	 *  It checks if it is valid to place a piece of the given color in the given coordinates.
	 *  A coordinate is valid if it:
	 *  1) is not a corner square: (0,0), (7,0), (0,7), (7,7)
	 *  2) is not in opponent's goal area:
	 *  					black's goal: (n,0) and (n,7)
	 *  					white's goal: (0,n) and (7,n)
	 *  3) is empty.  IF THE GIVEN COORDINATE IS NOT EMPTY, IT'S NOT VALID.
	 *  @param x the desired x-coordinate
	 *  @param y the desired y-coordinate
	 *  @param color the given Player's color
	 *  @return true if the coordinate is valid, false otherwise.
	 **/
	public boolean isValidCoordinate(Pair chip) {
		int x = chip.xCoord();
		int y = chip.yCoord();
		int playerColor = chip.myColor();
		boolean result = false;
		if (GameBoard[x][y] == 0) {
			switch(playerColor) {
			case -1: // if player is black
				if (x != 0 && x != 7) {
					result = true;
				}
				break;
			case 1: // if player is white
				if (y != 0 && y != 7) {
					result = true;
				}
				break;
			default:
				result = false;
			}
		}
		else {
			// the desired coordinate is not empty
			result = false;
		}
		return result;
	}

	// The fourth rule of the game is that a player may not have more than two chips in a
	// connected group, whether connected orthogonally or diagonally.
	/**
	 *  satisfiesFourthRule() is a helper method for isValidMove().
	 *  It checks if the given chip Pair will satisfy the fourth rule.  (It is not called on empty
	 *  (non-chip) coordinates.  We guarantee this by only creating Pair objects that have
	 *  color either BLACK or WHITE.)
	 *  @param chip the chip we want to verify
	 *  @return true if the pair satisfies the fourth rule, false otherwise.
	 *  */
	public boolean satisfiesFourthRule(Pair chip) {
		if (allAdjacentPieces(chip) > 1) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * allAdjacentPieces() is a helper method for satisfiesFourthRule().
	 * @param chip the given chip we want to inspect
	 * @return the number of pieces adjacent to the given chip, and all the pieces
	 * 					adjacent to those pieces
	 */
	public int allAdjacentPieces(Pair chip) {
		int result = 0;
		SList adpieces = adjacentPieces(chip);
		if (adpieces.length() > 1) {
			result = adpieces.length();
			// once the count is reaches 2, it immediately returns because
			// there is no point in wasting time continuing the count
		}
		else {
			if (adpieces.length() == 1) {
				int surrounding = adjacentPieces((Pair) adpieces.nth(1)).length();
				result = adpieces.length() + surrounding;
			} 
			else {
				if (adpieces.length() == 0) {
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * adjacentPieces() is a helper method for allAdjacentPieces().
	 * @param chip the given chip we want to inspect
	 * @return list of pieces adjacent to chip
	 */
	public SList adjacentPieces(Pair chip) {
		SList listofpairs = new SList();
		int x = chip.xCoord();
		int y = chip.yCoord();
		int pcolor = chip.myColor();
		int ocolor = chip.opponentColor();
		int dirx[] = { -1, -1, 0, 1, 1, 1, 0, -1};
		int diry[] = {0, 1, 1, 1, 0, -1, -1, -1};

		for (int d = 0; d < 8; d++) {
			int i = x + dirx[d];
			int j = y + diry[d];
			if (i < 0 || i > 7 || j < 0 || j > 7) {
				continue;
			}
			if (i == x && j == y) {
				continue;
			}
			if (GameBoard[i][j] == ocolor) {
				continue;
			}
			if (GameBoard[i][j] == pcolor) {
				Pair adjpair = new Pair(i, j, pcolor);
				listofpairs.insertFront(adjpair);
			}
		}
		return listofpairs;
	}

	/**
	 * getValidMoves() finds all valid moves for a Player of the given color.
	 * @param color the color we want to inspect
	 * @return a list of all valid moves
	 * 					NOTE: never returns an empty list, since QUIT is always valid.
	 */
	public SList getValidMoves(Player player) {
		int color = ((MachinePlayer) player).PlayerColor();
		SList listOfMoves = new SList();
		SList listOfCoords = playerPieces(color);

		int numPieces = listOfCoords.length();
		if (numPieces < 10) {
			// if there are less than 10 pieces on the board, evaluate all possible ADD Moves
			for (int x = 0; x < DIMENSION; x++) {	// look at every square
				for (int y = 0; y < DIMENSION; y++) {
					Move potentialAddMove = new Move(x, y);
					// check if adding at (x,y) isValidMove
					if (isValidMove(potentialAddMove, player)) {
						listOfMoves.insertFront(potentialAddMove);
						// only Move add to SList if it isValidMove
					}	// otherwise move on; end loop when all squares have been checked
				}
			}
		}
		else {
			// if there are at least 10 pieces on the board, skip ADD and go to STEP Moves
			for (int i = 1; i <= numPieces; i++) {
				for (int x = 0; x < DIMENSION; x++) {
					for (int y = 0; y < DIMENSION; y++) {
						int oldx = ((Pair) listOfCoords.nth(i)).xCoord();
						int oldy = ((Pair) listOfCoords.nth(i)).yCoord();
						Move potentialStepMove = new Move(x, y, oldx, oldy);
						if (isValidMove(potentialStepMove, player)) {
							listOfMoves.insertFront(potentialStepMove);
						}
					}
				}
			}
		}
		// so that the list of valid moves is NEVER empty,
		// and so that the player will always move when possible
		if (listOfMoves.length() == 0) {
			listOfMoves.insertFront(new Move());
		}
		return listOfMoves;
	}

	/**
	 * playerPieces() is a helper function for getValidMoves().  It finds all the
	 * pieces of the given color on the board.
	 * @param color the color we want to inspect
	 * @return a list of the pieces of this color
	 */
	public SList playerPieces(int playerColor) {
		SList listOfCoords = new SList();
		for (int x = 0; x < DIMENSION; x++) {
			for (int y = 0; y < DIMENSION; y++) {
				if (GameBoard[x][y] == playerColor) {
					Pair coord = new Pair(x, y, playerColor);
					listOfCoords.insertFront(coord);
				}
			}
		}
		return listOfCoords;
	}
	/** AMY'S CODE END **/

	/** ANNIE'S CODE START **/
	/** (Modules 3 and 4) **/
	/**
	 * findConnectingPieces() finds all the pieces that form a connection
	 * with your given chip.
	 * @param chip the chip whose connectors we want to find
	 * @return a list of all chips that connect to the given chip
	 */
	public SList findConnectingPieces(Pair chip) {
		int r = chip.xCoord();
		int c = chip.yCoord();

		int dirx[] = {-1, -1, 0, 1, 1, 1, 0, -1};
		int diry[] = { 0, 1, 1, 1, 0, -1, -1, -1};
		SList l = new SList();

		for (int d=0; d < 8; d++) {
			int x = r;
			int y = c;
			while ((x >= 0 && x < 8) && (y >= 0 && y < 8)) {
				if (GameBoard[x][y] == chip.opponentColor()) {
					break;
					}
				if (GameBoard[x][y] == chip.myColor()) {
					if (x == r && y == c) {
						x += dirx[d];
						y += diry[d]; }
					if ((x >= 0 && x < 8) && (y >= 0 && y < 8)) {
						if (GameBoard[x][y] == chip.myColor()) {
							l.insertEnd(new Pair(x, y, chip.myColor()));
							break; } }
				} else {
					x += dirx[d];
					y += diry[d];
				}}}
		return l;
	}

	
	// Takes in a Player, and returns true if the player has a valid network,
	// false if the Player doesn't have a network.
	/**
	 * containsNetworks() checks if there are any networks of the given color
	 * NEED TO FIX
	 * NEED TO FIX
	 * NEED TO FIX
	 */
	public SList getSuccessors(Pair chip, SList filter) {
		int color = chip.myColor();
		SList[] goals = pairsInGoal(color);
		SList goalStart = goals[0];
		//SList goalEnd = goals[1];

		SList connectingPieces = findConnectingPieces(chip);
		SList states = new SList();
		for (int k=1; k <= connectingPieces.length(); k++) {

			Pair kPair = (Pair) connectingPieces.nth(k);

			if (filter.has(kPair) || goalStart.has(kPair)) {
				continue;
			}

			Object[] temp = new Object[2];
			temp[0] = connectingPieces.nth(k);
			temp[1] = new SList(kPair);
			states.insertEnd(temp);
		}
		return states;
	}

	public static boolean inLine(Pair p1, Pair p2, Pair p3){
		int x1 = p1.xCoord();
		int x2 = p2.xCoord();
		int x3 = p3.xCoord();
		if (x1 == x2 && x2 == x3) {
			return true;
		} else {
			int y1 = p1.yCoord();
			int y2 = p2.yCoord();
			int y3 = p3.yCoord();
			double slope31 = ((double) (y3 - y1)) / (x3 - x1);
			double slope32 = ((double) (y3 - y2)) / (x3 - x2);
			if (slope31 == slope32) {
				return true; }
			else {
				return false;
			}
		}
	}

	public static boolean satisfiesNetworkConditions(SList list, int color) {
		if (list.length() < 6) {
			return false; }
		for (int k = 3; k <= list.length(); k++) {
			Pair kPair = (Pair) list.nth(k);
			Pair k1Pair = (Pair) list.nth(k-1);
			Pair k2Pair = (Pair) list.nth(k-2);
			Pair first = (Pair) list.nth(1);
			Pair last = (Pair) list.nth(list.length());
			if (inLine(kPair, k1Pair, k2Pair)) {
				return false; }
			if (color == -1) {
				if (first.yCoord() != 0 || last.yCoord() != 7) {
					return false; }
			} else {
				if (first.xCoord() != 0 || last.xCoord() != 7) {
					return false;
				}
			}
		}
		return true;
	}

	//REDUNDANT
	public SList[] pairsInGoal(int color) {
		if  (color == -1) {
			SList upRow = new SList();
			SList lastRow = new SList();
			for (int j = 0; j < 8; j++) {
				if (GameBoard[j][0] == color) {
					upRow.insertEnd(new Pair(j, 0, color)); }
				if (GameBoard[j][7] == color) {
					lastRow.insertEnd(new Pair(j, 7, color)); }
			}
			SList[] goals = new SList[2];
			goals[0] = upRow;
			goals[1] = lastRow;
			return goals;
		} else {
			SList leftRow = new SList();
			SList rightRow = new SList();
			for (int j = 0; j < 8; j++) {
				if (GameBoard[0][ j] == color) {
					leftRow.insertEnd(new Pair(0, j, color)); }
				if (GameBoard[7][ j] == color) {
					rightRow.insertEnd(new Pair(7, j, color)); }
			}
			SList[] goals = new SList[2];
			goals[0] = leftRow;
			goals[1] = rightRow;
			return goals;
		}
	}

	// Takes in a Player, and returns true if the player has a valid network,
	// false if the Player doesn't have a network.
	public boolean containsNetworks(Player player) {
		int color = ((MachinePlayer) player).PlayerColor();
		SList[] goals = this.pairsInGoal(color);
		SList goalStart = goals[0];
		SList goalEnd = goals[1];

		if (goalStart.length() == 0 || goalEnd.length() == 0) {
			return false; }

		for (int k = 1; k <= goalStart.length(); k++) {
			Pair kStartPair = (Pair) goalStart.nth(k);

			Object[] node = new Object[2];
			node[0] = kStartPair;
			node[1] = new SList();

			SList testNetwork = (SList) node[1];

			SList waitingPairs = new SList();
			SList filterPairs = new SList();
			filterPairs.insertFront(kStartPair);

			SList firstConnecting = getSuccessors(kStartPair, filterPairs);
			for (int j = 1; j <= firstConnecting.length(); j++) {
				Object[] nState = (Object[]) firstConnecting.nth(j);
				Pair nPair = (Pair) nState[0];
				SList nNetwork = (SList) nState[1];
				SList temp = new SList();
				temp = nNetwork.copy();
				temp.insertFront(kStartPair);
				testNetwork = temp;
				filterPairs.insertFront(kStartPair);
				Object[] childState = new Object[2];
				childState[0] = nPair;
				childState[1] = temp;
				waitingPairs.insertEnd(childState);
			}

			while (waitingPairs.isEmpty() != true) {
				Object[] popNode = (Object[]) waitingPairs.pop();
				Pair popPair = (Pair) popNode[0];
				SList popNetwork = (SList) popNode[1];
				filterPairs = popNetwork.copy();
				SList popConnecting = getSuccessors(popPair, filterPairs);
				for (int j = 1; j <= popConnecting.length(); j++) {
					Object[] nState = (Object[]) popConnecting.nth(j);
					Pair nPair = (Pair) nState[0];
					//SList nNetwork = (SList) nState[1];
					SList temp = new SList();
					temp = popNetwork.copy();
					temp.insertEnd(nPair);
					testNetwork = temp;
					Object[] childState = new Object[2];
					childState[0] = nPair;
					childState[1] = temp;
					
					((MachinePlayer) player).maxConnections = Math.max(((MachinePlayer) player).maxConnections, testNetwork.length());
					
					if (filterPairs.has(childState[0]) == false) {
						filterPairs.insertFront(childState[0]);
						if (satisfiesNetworkConditions(testNetwork, color)) {
							return true;
							}
						waitingPairs.insertEnd(childState);

					} } } }
		return false;
	}
	/** ANNIE'S CODE END **/

	/** AMANDA'S CODE START **/
	/** (Module 5) **/
	// takes in a list of player pieces on board (pair objects?) and returns the total number of connections for all of them combined
	// URGENT NOTE!!! THERE ARE CHANCES OF DUPLICATES!!!
	// but its okay... because it doesn't matter much in evalfn, their values shouldn't matter because they are tiny and shouldn't effect
	// the logic of "a better situation" by much... i dunno if that makes sense...

	// Looks at each individual piece in the given list and finds the number of pieces connected to it.
	// Returns the sum of all these numbers.
	public int numOfConnections(SList pieces) {
		int num = 0;
		if (pieces == null) {
			return num;
		}
		for (int i = 1; i < pieces.length(); i++) {
			Object curpiece = pieces.nth(i);
			num = num + findConnectingPieces((Pair) curpiece).length();
		}
		return num;
	}

	// Takes in a Player, and returns a float number indicating how close the
	// Player is to forming a network. The higher the number, the closer the
	// Player is to forming a network.  The lower the number, the closer the
	// Player's opponent is to forming a network.
	public double evalfn(MachinePlayer me, MachinePlayer opponent) {
		int score = 0;
		int myColor = me.PlayerColor();
		int oppColor = opponent.PlayerColor();
		
		// checks if this Player has performed a winning move, i.e. has formed a network
		// if so, cuts out of evalfn early to return a very HIGH number
		if (this.containsNetworks(me)) {
			return 100000;
		}
		// checks if this Player's opponent has performed a winning move, i.e. has formed a network
		// if so, cuts out of evalfn early to return a very LOW number
		if (this.containsNetworks(opponent)) {
			return -100000;
		}
		
		SList allMyPieces = playerPieces(myColor);
		SList[] myGoalPairs = pairsInGoal(myColor);
		SList myStart = myGoalPairs[0];
		SList myEnd = myGoalPairs[1];
		
		int myTotal = allMyPieces.length();
		int myStartCount = myStart.length();
		int myEndCount = myEnd.length();
		int myFieldCount = myTotal - myStartCount - myEndCount;
		
		if (myStartCount == 1) {
			if (myEndCount == 1) {
				score += 1000;
				/*
				if (myFieldCount == 0) {
					score += 100;			// Player's last move has established a piece in both goals
				}
				else {
					score += 10;			// Player's last move has established a piece on both goals
														// AND at least one more piece not in a goal
				}
				*/
			}
			else {
				score += 2;			// Player's last move has established a piece in one goal only
			}
		}
		
		SList allOppPieces = playerPieces(oppColor);
		SList[] oppGoalPairs = pairsInGoal(oppColor);
		SList oppStart = oppGoalPairs[0];
		SList oppEnd = oppGoalPairs[1];
		
		int oppTotal = allMyPieces.length();
		int oppStartCount = oppStart.length();
		int oppEndCount = oppEnd.length();
		int oppFieldCount = oppTotal - oppStartCount - oppEndCount;
		
		if (oppStartCount == 1) {
			if (oppEndCount == 1) {
				if (oppFieldCount == 0) {
					score -= 90;			// Player's last move has established a piece in both goals
				}
				else {
					score -= 9;			// Player's last move has established a piece on both goals
					// AND at least one more piece not in a goal
				}
			}
			else {
				score -= 1;			// Player's last move has established a piece in one goal only
			}
		}
		
		
		
		
		
		int allMyConnections = numOfConnections(allMyPieces);
		int allOppConnections = numOfConnections(allOppPieces);
		int blockFeature = allMyConnections - allOppConnections;
		
		//System.out.println(allMyConnections + "" + allOppConnections);
		
		
		/*
		// WEIGHT ALL VALUES... a friendly reminder from tmmgamer //
		SList pgoalpairs1 = pairsInGoal(myColor)[0];
		SList pgoalpairs2 = pairsInGoal(myColor)[1];
		SList pplayerpieces = playerPieces(myColor);
		int pgoalWS = pgoalpairs1.length() + pgoalpairs2.length();
		int pfieldWS = pplayerpieces.length() - pgoalWS;
		int pgoalconnectionWS = numofConnections(pgoalpairs1) + numofConnections(pgoalpairs2);
		int pfieldconnectionWS = numofConnections(pplayerpieces);
		// different weights for pieces that connect in field than pieces that 	connect from goals
//		int plongestconnectionWS = findLongestconnection(myColor).length();
		 */

		SList ogoalpairs1 = pairsInGoal(oppColor)[0];
		SList ogoalpairs2 = pairsInGoal(oppColor)[1];
		SList oplayerpieces = playerPieces(oppColor);

		/*
		int ogoalWS = ogoalpairs1.length() + ogoalpairs2.length();
		int ofieldWS = oplayerpieces.length() - ogoalWS;
		int ogoalconnectionWS = numofConnections(ogoalpairs1) + 	numofConnections(ogoalpairs2);
		int ofieldconnectionWS = numofConnections(oplayerpieces);
		// different weights for pieces that connect in field than pieces that 	connect from goals
//		int olongestconnectionWS = findLongestconnection(oppColor).length();

		score = pgoalWS + pfieldWS + pgoalconnectionWS + pfieldconnectionWS
//		+ plongestconnectionWS
		- ogoalWS - ofieldWS - ogoalconnectionWS - ofieldconnectionWS;
//		- olongestconnectionWS;
		
		*/
		score += 50*blockFeature;
		return score;
	}
	/* MORE OF AMANDA'S CODE IN MACHINEPLAYER.JAVA */
	/** AMANDA'S CODE END**/


	/** TEST CODE START **/
	// for testing purposes, adapted from Ocean.java
	public static void paint1(Board b) {
		if (b != null) {
			int width = 8;
			int height = 8;
			/* Draw the ocean. */
			for (int x = 0; x < width + 2; x++) {
				System.out.print("-"); }
			System.out.println();
			for (int y = 0; y < height; y++) {
				System.out.print("|");
				for (int x = 0; x < width; x++) {
					int contents = b.GameBoard[x][y];
					if (contents == BLACK) {
						System.out.print("B");
					} else if (contents == WHITE) {
						System.out.print('W');
					} else {
						System.out.print(' '); }}
				System.out.println("|"); }
			for (int x = 0; x < width + 2; x++) {
				System.out.print("-"); }
			System.out.println(); }}

	// nicer printing =)
	public static void paint(Board b) {
		int width = 41;
		int height = 16;
		for (int x = 0; x < width; x++) {
			System.out.print("-");
		}
		System.out.println();
		for (int y = 0; y < height; y++) {
			if (y%2 != 0) {
				for (int x = 0; x < width; x++) {
					System.out.print("-");
				}
			} else {
				System.out.print("| ");
				for (int x = 0; x < 8; x++) {
					//System.out.print(x + "" + y/2 + " | ");
					int contents = b.GameBoard[x][y/2];
					if (contents == BLACK) {
						System.out.print("BB | ");
					} else if (contents == WHITE) {
						System.out.print("WW | ");
					} else {
						System.out.print("   | ");
					}
				}
			}
			System.out.println();
		}
	}

	// Amanda's test code (main method)
	public static void main(String[] args) {
		MachinePlayer shewchuck = new MachinePlayer(0);
		Board b = shewchuck.board;
		int[][] gb = b.GameBoard;
		
		gb[7][6] = WHITE;
		gb[6][7] = BLACK;
		gb[0][6] = WHITE;
		paint(b);
		
		SList moves = b.getValidMoves(shewchuck);
		for (int i = 1; i <= moves.length(); i++) {
			Move m = (Move)moves.nth(i);
			shewchuck.forceMove(m);
			double e = b.evalfn(shewchuck, shewchuck.opponent);
			System.out.println("\n" + m + " is valued " + e + " for me");
			double oppE = b.evalfn(shewchuck.opponent, shewchuck);
			System.out.println("\n" + m + " is valued " + oppE + " for opponent");
			paint(b);
			shewchuck.undoMove(m);
		}
		
		shewchuck.chooseMove();
		System.out.println("SHEWCHUK BOARD1");
		paint(shewchuck.board);
		/**
		SList moves1 = b.getValidMoves(shewchuck);
		for (int i = 1; i <= moves1.length(); i++) {
			Move m = (Move)moves1.nth(i);
			shewchuck.forceMove(m);
			double e = b.evalfn(shewchuck, shewchuck.opponent);
			System.out.println("\n" + m + " is valued " + e + " for me");
			double oppE = b.evalfn(shewchuck.opponent, shewchuck);
			System.out.println("\n" + m + " is valued " + oppE + " for opponent");
			paint(b);
			shewchuck.undoMove(m);
		}
		
		shewchuck.chooseMove();
		System.out.println("SHEWCHUK BOARD2");
		paint(shewchuck.board);
		**/
		shewchuck.chooseMove();
		System.out.println("SHEWCHUK BOARD");
		paint(shewchuck.board);
		
		
	}
	
	
	
	
	
	
	
	
	
	public static void TEST(String[] args) {
		Board test = new Board();
		int[][] t = test.GameBoard;
		MachinePlayer gary = new MachinePlayer(0);
		gary.board = test;
		//System.out.println(BLACK + "" + gary.PlayerColor());
		
		t[1][1] = WHITE;
		t[2][1] = WHITE;
		t[7][1] = WHITE;
		t[5][2] = WHITE;
		t[7][3] = WHITE;
		t[4][4] = WHITE;
		t[5][5] = WHITE;
		t[7][5] = WHITE;
		t[1][6] = WHITE;
		t[2][6] = WHITE;
		
		t[1][0] = BLACK;
		t[3][0] = BLACK;
		t[4][0] = BLACK;
		t[6][0] = BLACK;
		t[3][5] = BLACK;
		t[3][2] = BLACK;
		t[4][2] = BLACK;
		t[1][3] = BLACK;
		t[6][3] = BLACK;
		t[6][5] = BLACK;
		
		paint(test);
		
		System.out.println("Gary has pieces at:\n" + test.playerPieces(BLACK));
		/*
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				int contents = test.GameBoard[x][y];
				if (contents == BLACK) {
					System.out.println("(" + x + "," + y + ")");
				}
			}
		}
		*/
		
		Move m1 = new Move(2,4,2,1);
		Move m2 = new Move(6,4,5,5);
		boolean b1 = test.isValidMove(m1, gary);
		boolean b2 = test.isValidMove(m2, gary);
		
		System.out.println(m1 + " should be true: " + b1);
		System.out.println(m2 + " should be false: " + b2);
		
		SList moves = test.getValidMoves(gary);
		System.out.println("Gary's valid moves are:\n" + moves);
		//System.out.println("Gary has pieces at:\n" + test.playerPieces(BLACK));
		paint(test);
		
		/*
		//SList pieces = test.playerPieces(BLACK);
		for (int x = 0; x < DIMENSION; x++) { 
			for (int y = 0; y < DIMENSION; y++) {
				if (t[x][y] == BLACK) {
					System.out.println(t[x][y] + " == " + BLACK);
					System.out.println("(" + x + "," + y + ")");
				}
			}
		}
		*/
		
	}
	
	
	
	
	public static void Amanda(String[] args) {
		MachinePlayer edmond = new MachinePlayer(0);
		MachinePlayer amanda = edmond.opponent;
		Board b = edmond.board;
		System.out.println("\nEdmond's starting board");
		paint(b);
		SList ls1 = b.getValidMoves(edmond);
		System.out.println("Edmond's legal moves: \n" + ls1);
		
		Move m1 = new Move(7,3);
		edmond.opponentMove(m1);
		System.out.println("\nEdmond's board after Amanda " + m1);
		paint(b);
		System.out.println("Amanda's board after she " + m1);
		paint(amanda.board);
		SList ls2 = b.getValidMoves(edmond);
		System.out.println("Edmond's legal moves: \n" + ls2);
		
		Move best1 = edmond.chooseMove();
		System.out.println("edmond chose move " + best1);
		edmond.forceMove(best1);
		System.out.println("\nEdmond's board after he " + best1);
		paint(b);
		System.out.println("Amanda's board after Edmond " + best1);
		paint(amanda.board);
		SList ls3 = b.getValidMoves(edmond);
		System.out.println("Edmond's legal moves: \n" + ls3);
		SList ls4 = b.getValidMoves(amanda);
		System.out.println("Amanda''s legal moves: \n" + ls4);
		
		Move best2 = amanda.chooseMove();
		System.out.println("amanda chose move " + best2);
		amanda.forceMove(best2);
		paint(b);
		
		Move best3 = edmond.chooseMove();
		System.out.println("edmond chose move " + best3);
		edmond.forceMove(best3);
		paint(b);
		
	}
	
	// AMANDA's test code
	public static void AmandaTest(String args[]) {
		/*
		Board board = new Board();
		paint(board);
		board.GameBoard[2][4] = WHITE;
		paint(board);
		*/
		 
		
		MachinePlayer w = new MachinePlayer(1);	// WHITE
		MachinePlayer b = new MachinePlayer(0);	// BLACK

		System.out.println("White Player's Board");
		paint(w.board);
		System.out.println("Black Player's Board");
		paint(b.board);
		System.out.println("\n");

		Move w1 = new Move(2, 4);
		Move w2 = new Move(4, 6);
		Move w3 = new Move(2, 4, 1, 1);

		w.forceMove(w1);
		b.opponentMove(w1);

		System.out.println("after W added to [2][4]:");
		System.out.println("White Player's Board");
		paint(w.board);
		System.out.println("Black Player's Board");
		paint(b.board);
		System.out.println("\n");

		w.forceMove(w2);
		b.opponentMove(w2);

		System.out.println("after W added to [4][6]:");
		System.out.println("White Player's Board");
		paint(w.board);
		System.out.println("Black Player's Board");
		paint(b.board);
		System.out.println("\n");

		w.forceMove(w3);
		b.opponentMove(w3);

		System.out.println("after W moved from [2][4] to [1][1]:");
		System.out.println("White Player's Board");
		paint(w.board);
		System.out.println("Black Player's Board");
		paint(b.board);
		System.out.println("\n");
		
		//w.undoMove(w3);
		//b.undoOpponentMove(w3);

		//System.out.println("after W undid move from [2][4] to [1][1]:");
		//System.out.println("White Player's Board");
		//paint(w.board);
		//System.out.println("Black Player's Board");
		//paint(b.board);
		//System.out.println("\n");
		
		b.forceMove(w1);
		w.opponentMove(w1);
		
		System.out.println("after B added to [2][4]:");
		System.out.println("White Player's Board");
		paint(w.board);
		System.out.println("Black Player's Board");
		paint(b.board);
		System.out.println("\n");

		b.undoMove(w1);
		//w.undoOpponentMove(w1);

		System.out.println("after B undid add to [2][4]:");
		System.out.println("White Player's Board");
		paint(w.board);
		System.out.println("Black Player's Board");
		paint(b.board);
		System.out.println("\n");
		
	}

	public static void Amanda2(String[] args) {
		MachinePlayer bubba = new MachinePlayer(0);
		MachinePlayer bOpp = bubba.opponent;
		
		Move first = new Move(7,3);
		Move second = new Move(4,5);
		Move third = new Move(6,4);
		Move fourth = new Move(0,2);
		Move fifth = new Move(2,2);
		Move sixth = new Move(3,1);
		bubba.opponentMove(first);
		bOpp.forceMove(first);
		bubba.opponentMove(second);
		bOpp.forceMove(second);
		bubba.opponentMove(third);
		bOpp.forceMove(third);
		bubba.opponentMove(fourth);
		bOpp.forceMove(fourth);
		bubba.opponentMove(fifth);
		bOpp.forceMove(fifth);
		bubba.opponentMove(sixth);
		bOpp.forceMove(sixth);
		
		/*
		String bubbaString = "bubba's board: [";
		String bubbaOppString = "bubba's opponent's board";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int bubentry = bubba.board.GameBoard[i][j];
				if (bubentry != EMPTY) {
					Pair piece = new Pair(i,j,bubentry);
					bubbaString += piece;
				}
				int oppentry = bubba.opponent.board.GameBoard[i][j];
				if (oppentry != EMPTY) {
					Pair piece = new Pair(i,j,oppentry);
					bubbaOppString += piece;
				}
			}
		}
		bubbaString += "]";
		bubbaOppString += "]";
		System.out.println(bubbaString);
		System.out.println(bubbaOppString);
		*/
		
		
		/*
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				bubba.board.GameBoard[i][j] = WHITE;
			}
		}
		*/
		
		Board b = bubba.board;
		paint(b);
		//int bcolor = bubba.PlayerColor();
		SList bubbaMoves = b.getValidMoves(bubba);
		System.out.println(bubbaMoves);
		//System.out.println("Does WHITE player have network? : " + b.containsNetworks(bubba.opponent));
		//paint(bubba.opponent.board);
		
		/*
		int n = bubbaMoves.length();
		for (int i = 1; i <= n; i++) {
			Move bMove = (Move) bubbaMoves.nth(i);
			bubba.forceMove(bMove);
			double bEval = b.evalfn(bubba, bubba.opponent);
			System.out.println(bMove + " is valued " + bEval);
			paint(bubba.board);
			bubba.undoMove(bMove);
		}
		*/
		
		//System.out.println(beval);
		
		Move best = bubba.chooseMove();
		bubba.forceMove(best);
		bubba.opponent.opponentMove(best);
		System.out.println("BUBBA BOARD");
		paint(bubba.board);
		
		Move best2 = bubba.chooseMove();
		bubba.forceMove(best2);
		bubba.opponent.opponentMove(best2);
		System.out.println("BUBBA BOARD");
		paint(bubba.board);
	}
	














	// part of Annie's test code (main method)
	public static void AnnieTest(String args[]) {
		
		Board g2 = new Board();
		g2.GameBoard[3][0] = -1;
		g2.GameBoard[5][2] = -1;
		g2.GameBoard[2][2] = -1;
		g2.GameBoard[3][3] = -1;
		g2.GameBoard[3][5] = -1;
		g2.GameBoard[1][7] = -1;
		paint(g2);
		MachinePlayer pp = new MachinePlayer(0);
		System.out.println(g2.containsNetworks(pp));
	}



	// Amy's test code (main method)
	public static void AmyTest(String[] args) {

		// isValidCoordinate //
/*
		System.out.println("Testing : isValidCoordinate(Move move, Player player), isValidMove(Move move, Player player), getValidMoves(Player player) ");

		System.out.println("\nTest Cases for - CORNERS");
		Board cornerBoard = new Board();
		cornerBoard.GameBoard = new int[8][8];

		MachinePlayer cWhite = new MachinePlayer(1);
		MachinePlayer cBlack = new MachinePlayer(0);

		Move cWadd = new Move(0, 0); //white
		Move cWstep = new Move(1, 1, 7, 7); //white
		Move cBadd = new Move(0, 7); //black
		Move cBstep = new Move(7, 0, 1, 2); //black
		System.out.println("\n	Testing isValidCoordinate for corners");
		System.out.println("(0, 0) is not a valid coordinate, should be false: " 
				+ cornerBoard.isValidCoordinate(0, 0, 1));
		System.out.println("(7, 0) is not a valid coordinate, should be false: " 
				+ cornerBoard.isValidCoordinate(7, 0, -1));
		System.out.println("(7, 7) is not a valid coordinate, should be false: " 
				+ cornerBoard.isValidCoordinate(7, 7, -1));
		System.out.println("(0, 7) is not a valid coordinate, should be false: " 
				+ cornerBoard.isValidCoordinate(0, 7, 1));

		System.out.println("\n	Testing isValidMove for corners");
		System.out.println("Cannot add white piece to (0, 0), cWadd is not a valid move, should be false: "
				//+ cornerBoard.isValidMove(cWadd, cWhite.PlayerColor()));
				+ cornerBoard.isValidMove(cWadd, cWhite));
		System.out.println("Cannot move white piece (1, 1) to (7, 7), cWstep is not a valid move, should be false: "
				//+ cornerBoard.isValidMove(cWstep, cWhite.PlayerColor()));
				+ cornerBoard.isValidMove(cWstep, cWhite));
		System.out.println("Cannot add black piece to (0, 7), cBadd is not a valid move, should be false: "
				//+ cornerBoard.isValidMove(cBadd, cBlack.PlayerColor()));
				+ cornerBoard.isValidMove(cBadd, cBlack));
		System.out.println("Cannot move black piece (7, 0) to (1, 2), cBstep is not a valid move, should be false: "
				//+ cornerBoard.isValidMove(cBstep, cBlack.PlayerColor()) + " but we assume that this never happens because add move" +
				+ cornerBoard.isValidMove(cBstep, cBlack) + " but we assume that this never happens because add move" +
		" can never place a piece on a corner");

		//paint(cornerBoard);

		System.out.println("\nTest Cases for - GOALS");
		Board goalBoard = new Board();
		goalBoard.GameBoard = new int[8][8];

		MachinePlayer gWhite = new MachinePlayer(1);
		MachinePlayer gBlack = new MachinePlayer(0);

		Move gWadd = new Move(4, 0); //white
		Move gWstep = new Move(2, 7, 0, 5); //white
		Move gBadd = new Move(4, 7); //black
		Move gBstep = new Move(2, 2, 0, 1); //black
		System.out.println("\n	Testing isValidCoordinate for goals");
		System.out.println("White cannot be placed on (4, 0), should be false: " 
				+ goalBoard.isValidCoordinate(4, 0, 1));
		System.out.println("White cannot be placed on (2, 7), should be false: " 
				+ goalBoard.isValidCoordinate(2, 7, 1));
		System.out.println("Black cannot be placed on (0, 3), should be false: " 
				+ goalBoard.isValidCoordinate(0, 3, -1));
		System.out.println("Black cannot be placed on (7, 1), should be false: " 
				+ goalBoard.isValidCoordinate(7, 1, -1));

		System.out.println("\n	Testing isValidMove for goals");
		System.out.println("Cannot add white piece to black goal (4, 0), gWadd is not a valid move, should be false: "
				//+ goalBoard.isValidMove(gWadd, gWhite.PlayerColor()));
				+ goalBoard.isValidMove(gWadd, gWhite));
		System.out.println("Cannot move white piece (2, 7) black goal to (0, 5) white goal, gWstep is not a valid move, should be false: "
				//+ goalBoard.isValidMove(gWstep, gWhite.PlayerColor()) + " but we assume that this never happens because add move" +
				+ goalBoard.isValidMove(gWstep, gWhite) + " but we assume that this never happens because add move" +
		" can never place a white piece in a black goal");
		System.out.println("Can add black piece to (4, 7) black goal, gBadd is a valid move, should be true: "
				//+ goalBoard.isValidMove(gBadd, gBlack.PlayerColor()));
				+ goalBoard.isValidMove(gBadd, gBlack));
		System.out.println("Cannot move black piece (2, 2) to (0, 1) white goal, gBstep is not a valid move, should be false: "
				//+ goalBoard.isValidMove(gBstep, gBlack.PlayerColor()));
				+ goalBoard.isValidMove(gBstep, gBlack));

		//paint(goalBoard);

		System.out.println("\nTest Cases for - EMPTY");
		Board emptyBoard = new Board();
		emptyBoard.GameBoard = new int[8][8];

		// setting up emptyBoard 
		emptyBoard.GameBoard[1][1] = 1;
		emptyBoard.GameBoard[2][2] = -1;

		MachinePlayer eWhite = new MachinePlayer(1);
		MachinePlayer eBlack = new MachinePlayer(0);

		Move eWadd = new Move(1, 1); //white
		Move eWstep = new Move(1, 1, 2, 2); //white
		Move eBadd = new Move(2, 3); //black
		Move eBstep = new Move(2, 2, 1, 1); //black
		System.out.println("\n	Testing isValidCoordinate for goals");
		System.out.println("White cannot be placed on (1, 1), its already taken, should be false: " 
				+ emptyBoard.isValidCoordinate(1, 1, 1));
		System.out.println("White can be placed on (2, 5), not taken, should be true: " 
				+ emptyBoard.isValidCoordinate(2, 5, 1));
		System.out.println("Black cannot be placed on (2, 2), its already taken, should be false: " 
				+ emptyBoard.isValidCoordinate(2, 2, -1));
		System.out.println("Black can be placed on (5, 5), not taken, should be true: " 
				+ emptyBoard.isValidCoordinate(5, 5, -1));

		System.out.println("\n	Testing isValidMove for empty/not empty");
		System.out.println("Cannot put white piece on (1, 1), square already taken, should be false: "
				//+ emptyBoard.isValidMove(eWadd, eWhite.PlayerColor()));
				+ emptyBoard.isValidMove(eWadd, eWhite));
		System.out.println("Cannot move white piece (1, 1) to (2, 2), (2, 2) is already taken, should be false: "
				//+ emptyBoard.isValidMove(eWstep, eWhite.PlayerColor()));
				+ emptyBoard.isValidMove(eWstep, eWhite));
		System.out.println("Can add black piece to (2, 3), square is empty, should be true: "
				//+ emptyBoard.isValidMove(eBadd, eBlack.PlayerColor()));
				+ emptyBoard.isValidMove(eBadd, eBlack));
		System.out.println("Cannot move black piece (2, 2) to (1, 1), (1, 1) is already taken, should be false: "
				//+ emptyBoard.isValidMove(eBstep, eBlack.PlayerColor()));
				+ emptyBoard.isValidMove(eBstep, eBlack));

		// satisfiesFourthRule(Pair pair) //

		System.out.println("\nTest Cases for - FOURTH RULE");
		Board fourthBoard = new Board();
		fourthBoard.GameBoard = new int[8][8];

		// setting up fourthBoard
		fourthBoard.GameBoard[1][1] = BLACK;
		fourthBoard.GameBoard[2][2] = BLACK;

		fourthBoard.GameBoard[5][1] = BLACK;
		fourthBoard.GameBoard[5][2] = WHITE;

		fourthBoard.GameBoard[1][3] = WHITE;
		fourthBoard.GameBoard[2][3] = WHITE;

		fourthBoard.GameBoard[3][4] = BLACK;
		fourthBoard.GameBoard[5][4] = BLACK;
		fourthBoard.GameBoard[4][6] = BLACK;
		fourthBoard.GameBoard[6][7] = BLACK;

		MachinePlayer fWhite = new MachinePlayer(1);
		MachinePlayer fBlack = new MachinePlayer(0);

		Move fBstep1 = new Move(1, 1, 3, 3); // black false
		Move fBstep2 = new Move(1, 1, 3, 1); // black true
		Move fBstep3 = new Move(4, 6, 5, 6); //black true
		Move fBstep4 = new Move(4, 6, 4, 5); //black false
		Move fBstep5 = new Move(5, 1, 2, 7); //black true

		Move fBadd1 = new Move(3, 1); //black false
		Move fBadd2 = new Move(3, 3); //black false
		Move fBadd3 = new Move(2, 5); //black true
		Move fBadd4 = new Move(5, 6); //black false
		Move fBadd5 = new Move(6, 6); //black true
		Move fBadd6 = new Move(0, 0); //black true

		Move fWadd1 = new Move(5, 6); //white true
		Move fWadd2 = new Move(3, 3); //white false

		Pair pair1 = new Pair(3, 3, BLACK);
		Pair pair2 = new Pair(3, 3, WHITE);

		System.out.println("\n	Testing isValidMove for allAdjacentPieces");
		System.out.println("pieces adjacent to (3, 3) should be 2: " + fourthBoard.allAdjacentPieces(pair1));
		System.out.println("pieces adjacent to (3, 3) should be 2: " + fourthBoard.allAdjacentPieces(pair2));

		System.out.println("\n	Testing isValidMove for fourth rule");
		System.out.println("Cannot move black piece (1, 1) to (3, 3), 3-piece connection, should be false: "
				+ fourthBoard.isValidMove(fBstep1, fBlack.PlayerColor()));
		System.out.println("Can move black piece (1, 1) to (3, 1), 2-piece connection, should be true: "
				+ fourthBoard.isValidMove(fBstep2, fBlack.PlayerColor()));
		System.out.println("Can move black piece (4, 6) to (5, 6), 2-piece connection, should be true: "
				+ fourthBoard.isValidMove(fBstep3, fBlack.PlayerColor()));
		System.out.println("Cannot move black piece (4, 6) to (4, 5), 3-piece connection, should be false: "
				+ fourthBoard.isValidMove(fBstep4, fBlack.PlayerColor()));
		System.out.println("Can move black piece (5, 1) to (2, 7), 1-piece connection, should be true: "
				+ fourthBoard.isValidMove(fBstep5, fBlack.PlayerColor()));

		System.out.println("Cannot place black piece on (3, 1), 3-piece connection, should be false: "
				+ fourthBoard.isValidMove(fBadd1, fBlack.PlayerColor()));
		System.out.println("Cannot place black piece on (3, 3), 4-piece connection, should be false: "
				+ fourthBoard.isValidMove(fBadd2, fBlack.PlayerColor()));
		System.out.println("Can place black piece on (2, 5), 2-piece connection, should be true: "
				+ fourthBoard.isValidMove(fBadd3, fBlack.PlayerColor()));
		System.out.println("Cannot place black piece on (5, 6), 3-piece connection, should be false: "
				+ fourthBoard.isValidMove(fBadd4, fBlack.PlayerColor()));
		System.out.println("Can place black piece on (6, 6), 2-piece connection, should be true: "
				+ fourthBoard.isValidMove(fBadd5, fBlack.PlayerColor()));

		System.out.println("Can place white piece on (5, 6), 1-piece connection, should be true: "
				+ fourthBoard.isValidMove(fWadd1, fWhite.PlayerColor()));
		System.out.println("Can place white piece on (3, 3), 3-piece connection, should be false: "
				+ fourthBoard.isValidMove(fWadd2, fWhite.PlayerColor()));
		System.out.println("Can place black piece on (0, 0), 1-piece connection, should be false: "
				+ fourthBoard.isValidMove(fBadd6, fBlack.PlayerColor()));

		// getValidMoves(Player player) //

		System.out.println(fourthBoard.getValidMoves(fBlack.PlayerColor()));
	}


	
	public static void AmyEvalFnTest(String[] args) {
		Board g1 = new Board(); // yes, there are two possible networks
		g1.GameBoard[2][0] = BLACK;
		g1.GameBoard[6][0] = BLACK;
		g1.GameBoard[4][2] = BLACK;
		g1.GameBoard[1][3] = BLACK;
		g1.GameBoard[3][3] = BLACK;
		g1.GameBoard[2][5] = BLACK;
		g1.GameBoard[3][5] = BLACK;
		g1.GameBoard[5][5] = BLACK;
		g1.GameBoard[6][5] = BLACK;
		g1.GameBoard[5][7] = BLACK;

		Board g2 = new Board(); //yes, white piece only blocks one network
		g2.GameBoard[2][0] = BLACK;
		g2.GameBoard[6][0] = BLACK;
		g2.GameBoard[4][2] = BLACK;
		g2.GameBoard[1][3] = BLACK;
		g2.GameBoard[3][3] = BLACK;
		g2.GameBoard[2][5] = BLACK;
		g2.GameBoard[3][5] = BLACK;
		g2.GameBoard[5][5] = BLACK;
		g2.GameBoard[6][5] = BLACK;
		g2.GameBoard[5][7] = BLACK;
		g2.GameBoard[3][4] = WHITE;

		Board g3 = new Board(); // no network, white pieces block both networks
		g3.GameBoard[2][0] = BLACK;
		g3.GameBoard[6][0] = BLACK;
		g3.GameBoard[4][2] = BLACK;
		g3.GameBoard[1][3] = BLACK;
		g3.GameBoard[3][3] = BLACK;
		g3.GameBoard[2][5] = BLACK;
		g3.GameBoard[3][5] = BLACK;
		g3.GameBoard[5][5] = BLACK;
		g3.GameBoard[6][5] = BLACK;
		g3.GameBoard[5][7] = BLACK;
		g3.GameBoard[3][4] = WHITE;
		g3.GameBoard[4][4] = WHITE;

		Board g4 = new Board();  // no, uses 3 pieces in goals
		g4.GameBoard[6][0] = BLACK;
		g4.GameBoard[2][0] = BLACK;
		g4.GameBoard[4][2] = BLACK;
		g4.GameBoard[3][3] = BLACK;
		g4.GameBoard[3][5] = BLACK;
		g4.GameBoard[5][7] = BLACK;

		Board g5 = new Board();  // no, uses 3 pieces in goals
		g5.GameBoard[2][0] = BLACK;
		g5.GameBoard[4][2] = BLACK;
		g5.GameBoard[6][0] = BLACK;
		g5.GameBoard[6][5] = BLACK;
		g5.GameBoard[5][5] = BLACK;
		g5.GameBoard[5][7] = BLACK;

		Board g6 = new Board(); // no, network cannot pass through the same chip twice
		g6.GameBoard[2][0] = BLACK;
		g6.GameBoard[2][5] = BLACK;
		g6.GameBoard[3][5] = BLACK;
		g6.GameBoard[3][3] = BLACK;
		g6.GameBoard[5][5] = BLACK;
		g6.GameBoard[3][5] = BLACK;
		g6.GameBoard[5][7] = BLACK;

		Board g7 = new Board(); // no, network cannot pass through a chip without turning a corner
		g7.GameBoard[6][0] = BLACK;
		g7.GameBoard[4][2] = BLACK;
		g7.GameBoard[3][3] = BLACK;
		g7.GameBoard[3][5] = BLACK;
		g7.GameBoard[2][5] = BLACK;
		g7.GameBoard[2][7] = BLACK;
*/
	}
	/** TEST CODE END **/
}
