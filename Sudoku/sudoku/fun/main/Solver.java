package sudoku.fun.main;

import sudoku.fun.main.Algorithms;
import sudoku.fun.main.Board;

public class Solver {
	
	/* Input Specs
	 *  Integer size of board (in one dimension). Assumes the board is square.
	 *  - This would also be the maximum possible value of each square
	 *  - Must be a perfect square (2,3,4, etc.)
	 *  - e.g. 9

	 * 	A list of tuples to represent known values: (row, column, value)
	 *	- locations indexed from 0
	 *	- e.g. (0,5,9)
	 *	 
	 */
	private static String fileName = "input3.txt";
	
	private static Board myBoard;
	
	public static Integer getN(){
		return myBoard.getSize();
	}
	
	public static void main(String[] args){
		
		System.out.println("Hello!");
		
		// Create the game board
		myBoard = new Board();
		
		// Read the input file
		try {
			myBoard.readInput(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
			System.out.println("\nERROR During Input Parsing! Exiting...");
			return;
		}
		
		// Now solve!
		
		// For easy puzzles, the logic built in to the creation of the board will solve the puzzle by itself...
		// Now analyze what the safe solver has returned
		if(!myBoard.isValid() ){
			// The given board is unsolvable: stop
			System.out.println("The board is unsolvable! Exiting...");
			myBoard.displayBoard();
			return;
		}
		if(myBoard.isComplete()){
			System.out.println("SUCCESS!");
			myBoard.displayBoard();
			return;
		}
		
		// First attempt to do it the logical/smart way (no guessing)
		System.out.println("Attempting smart-solve...");
		try {
			safeSolve(myBoard);
		} catch (SudokuException e) {
			System.out.println("The board is unsolvable! Exiting...");
			myBoard.displayBoard();
			return;
		}

		// Now analyze what the safe solver has returned
		if(!myBoard.isValid() ){
			// The given board is unsolvable: stop
			System.out.println("The board is unsolvable! Exiting...");
			myBoard.displayBoard();
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
			System.out.println("A solution could not be computed :( Jeremy needs better algorithms...");
			myBoard.displayBoard();
		}
	}
	
	
	/** Attempts to fill-in as much of the grid as possible without guessing
		Throws exception if the board cannot be solved safely*/
	private static void safeSolve(Board myBoard) throws SudokuException{

		// TODO
		//Algorithms.doSomething(myBoard);
		
		while(true){
			Board oldBoard = new Board(myBoard);

			// TODO
			//Algorithms.doSomething(myBoard);
			
			// Stop iterating if no new changes were made
			if(myBoard.equals(oldBoard)){
				return;
			}
		}
	}
	
	/** Recursive Brute-force approach to the problem
		Returns a valid Board if a solution is found
		Returns null if no solution is found*/
	private static Board dumbSolve(Board myBoard){
		// Look for the first unknown in the grid
	   	for(int i = 0 ; i < getN() ; i++){
			for(int j = 0 ; j < getN() ; j++){

				// Check if the grid spot is currently has UNKNOWN contents
				if(myBoard.getGrid()[i][j].getValue() == Square.VALUE_UNKNOWN){
					
					for(int k = 0 ; k < getN() ; k++){
						// Make a guess in this grid spot
						if(myBoard.getGrid()[i][j].isPossible(k)){
							Board solution = guess(myBoard,i,j,k);
							if(solution != null){
								// Success! return it
								return solution;
							}
						}
					}
					
				}
				
			}
	   	}
	   	// No unknowns were found, reject this board
	   	return null;
	}
	
	private static Board guess(Board board, Integer guessRow, Integer guessCol, Integer guessValue){
		Board guessBoard = new Board(board);
		try {
			guessBoard.getGrid()[guessRow][guessCol].setValue(guessValue);
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
