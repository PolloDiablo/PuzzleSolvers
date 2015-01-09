package battleship.fun.main;

public class Solver {

	/* Input Specs
	 * 	Row values, comma separated
	 * 	Column values, comma separated
	 * 	Then a list of given locations:
	 * 		row, col, ship part
	 * 		Indexed from 0
	 * 
	 * 	Types:
	 * 		SU
	 * 
	 */
	private static String fileName = "input5.txt";
	
	public enum Item {
		SUB, LEFT, RIGHT, TOP, BOTTOM, CENTER, WATER, UNKNOWN, OCCUPIED
	}
	/*public enum Type {
		SUB, DESTROYER, CRUISER, BATTLESHIP, NONE, UNKNOWN
	}*/
	
	public static void main(String[] args){
		
		System.out.println("Hello!");
		
		// Create the game board
		Board myBoard = new Board();
		
		// Read the input file
		try {
			myBoard.readInput(fileName);
		} catch (Exception e) {
			System.out.println("The board is unsolvable! Exiting...");
			return;
		}
		
		myBoard.displayBoard();
		
		// Now solve!
		
		// First attempt to do it the logical/smart way (no guessing)
		System.out.println("Attempting smart-solve...");
		try {
			safeSolve(myBoard);
		} catch (Exception e) {
			System.out.println("The board is unsolvable! Exiting...");
			return;
		}

		// Now analyze what the safe solver has returned
		if(!myBoard.isValid() ){
			// The given board is unsolvable: stop
			System.out.println("The board is unsolvable! Exiting...");
			return;
		}else if(!myBoard.isComplete()){
			System.out.println("Smart-solve was unsuccessful, trying dumb-solve.");
			// We are not done yet, we can try to use the dumb/guessing method
			myBoard = dumbSolve(myBoard);
		}
		
		if(myBoard != null){
			// We have an answer!
			System.out.println("SUCCESS!");
			myBoard.displayBoard();
		}else{
			// No answer found :(
			System.out.println("FAILURE! No solution was found.");
		}

			
	}
	
	
	// Attempts to fill-in as much of the grid as possible without guessing
	// Throws exception if the board cannot be solved safely
	private static void safeSolve(Board myBoard) throws Exception{
		// Fill-in water for zero rows and columns
		Algorithms.fillInZeroWater(myBoard);
		
		while(true){
			Board oldBoard = new Board(myBoard);

			// Fill-in water for full rows and columns
			Algorithms.fillInFullWater(myBoard);
			
			// Fill-in water around known ship locations
			Algorithms.fillInSurroundingWater(myBoard);

			// Fill-in ship parts (based on current ship parts)
			//		Again Fill-in water around known ship locations
			Algorithms.fillInShipParts(myBoard);
			
			// Fill-in 'OCCUPIED' ship parts (based on rows/columns which have unknowns==required)
			Algorithms.fillRowsAndColumns(myBoard);
			
			// Determine true part of 'OCCUPIED' ship parts
			Algorithms.determineTrueOccupied(myBoard);

			// Stop iterating if no new changes were made
			if(myBoard.equals(oldBoard)){
				return;
			}
		}
	}
	
	// Recursive Brute-force approach to the problem
	// 	Returns a valid Board if a solution is found
	// 	Returns null if no solution is found
	private static Board dumbSolve(Board myBoard){
		// Look for the first unknown in the grid
	   	for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				// Check if the grid spot is currently has UNKNOWN contents
				if(myBoard.getGrid()[i][j].getContents() == Item.UNKNOWN){
					// Make a guess that this grid spot is occupied
					Board solution = guess(myBoard,i,j,Item.OCCUPIED);
					if(solution != null){
						// Success! return it
						return solution;
					}
					// If the first guess failed, try it the other way
					return guess(myBoard,i,j,Item.WATER);
				}
			}
	   	}
	   	// No unknowns were found, reject this board
	   	return null;
	}
	
	private static Board guess(Board board, Integer guessRow, Integer guessCol, Item guessContents){
		Board guessBoard = new Board(board);
		try {
			guessBoard.getGrid()[guessRow][guessCol].setContents(guessContents);
		} catch (Exception e) {
			// Guess attempt was invalid
			return null;
		}

		// First attempt to do it the logical/smart way (no guessing)
		try {
			safeSolve(guessBoard);
		} catch (Exception e) {
			// This guess is bad
			return null;
		}
		if(!guessBoard.isValid()){
			// This guess is bad
			return null;
		}else if(guessBoard.isComplete()){
			// This guess worked! :D
			return guessBoard;
		}else{
			// We are not done yet, make another guess trololol
			// Get recursive on dat board
			return dumbSolve(guessBoard);
		}
	}
	
}
