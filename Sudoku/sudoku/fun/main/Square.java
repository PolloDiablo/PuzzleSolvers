package sudoku.fun.main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/** Represents one of the 81 squares on the 9*9 Sudoku board */
public class Square {
	
	public static final Integer VALUE_UNKNOWN = -1;
	public static final Integer VALUE_MINIMUM = 1;
	
	/** Indicates whether the square's value has been determined, initially false
	 * GET only (it is SET internally)*/
	private boolean known;
	
	public boolean isKnown(){
		return known;
	}
		
	/** The value of the square, initially unknown (-1)*/
	private int value;
	
	/** Returns value of square. -1 if unknown
	 * ... you should probably call isKnown() before calling this.*/
	public int getValue(){
		return value;
	}
	
	public String getFormattedValue(){
		return Util.valueToString(value);
	}
	
	/** Set the value of the square.
	 * Throws exception in the following cases:
	 *  - value is not in [1-9]
	 *  - square already has a value
	 *  - given value was pre-determined to be an impossibility
	 *  
	 * Sets all possibilities to false.
	 * Enforces possibilities on all "neighbours" in row, column, and section.
	 */
	public void setValue(int newValue) throws SudokuException{
		if(newValue < VALUE_MINIMUM || newValue > Solver.getN()){
			throw new SudokuException("ERROR setValue(): valid square values are [1-9] only.");
		}
		if(known == true){
			if(newValue != value){
				throw new SudokuException("ERROR setValue(): you cannot change the value of a known square.");
			}else{
				// Attempting to set the value of the square to the same value... do nothing
				// This should really only happen during the "read input" phase
				// 	because some logic is applied even as Squares are created
				return;
			}
		}
		if(possibilities[newValue-1] == false){
			throw new SudokuException("ERROR setValue(): this square may not contain this value.");
		}
		known = true;
		value = newValue;
		// Set all possibility booleans to false
		for(int i = 0 ; i < Solver.getN() ; ++i){
			possibilities[i] = false;
		}
		
		// Set possibility booleans of newValue to false for neighbours
		Iterator<Square> iterator = neighbours.iterator(); 
		while (iterator.hasNext()){
			iterator.next().setImpossibility(newValue);
		}
	}
	
	/** Indicates whether the square may contain each of [1-9], initially all true.*/
	private boolean[] possibilities;
	
	/** Given number 1 thru 9, returns true if this square may contain it.
	 *  This will throw an exception if value < 1 or >= Solver.getN()
	 * */
	public boolean isPossible(int value){
		return(possibilities[value-1]);
	}
	
	/** Sets the possibility of the given value to false
	 * (indicated that this square may not contain the given value)
	 * Does nothing (returns) of this square is already known
	 * Throws exception in the following cases:
	 *  - all possibilities are now false
	 *  - only one possibility remained, and setting the value of the square threw an exception
	 */
	public void setImpossibility(int value) throws SudokuException{
		if(known){
			return;
		}
		
		// Set this possibility to false
		possibilities[value-1] = false;
		
		// Check if all possibilities are now false
		int trueCount = 0;
		for(int i = 0 ; i < Solver.getN() ; ++i){
			if(possibilities[i]){
				++trueCount;
			}
		}
		if(trueCount == 0){
			throw new SudokuException("ERROR setImpossibility(): this square has no remaining possible values.");
		}else if(trueCount == 1){
			for(int i = 0 ; i < Solver.getN() ; ++i){
				if(possibilities[i]){
					setValue(i+1);
				}
			}		
		}
	}
	
	/** The row this square is in.*/
	private final Area row;
	
	/** Returns the row this square is in.*/
	public Area getRow(){
		return row;
	}
	
	/** The column this square is in.*/
	private final Area column;
	
	/** Returns the column this square is in.*/
	public Area getColumn(){
		return column;
	}
	
	/** The section this square is in.*/
	private final Area section;
	
	/** Returns the section this square is in.*/
	public Area getSection(){
		return section;
	}
	
	private Set<Square> neighbours; 
	
	/**
	 * Populates the neighbours list (from the row, column, and section Areas)
	 * This must be called after all Squares and Areas have been created.
	 */
	public void calculateNeighbours(){
		neighbours = new HashSet<Square>(row.getAllSquares());
		neighbours.addAll(column.getAllSquares());
		neighbours.addAll(section.getAllSquares());
		neighbours.remove(this);
	}
	
	public Square(Area row, Area column, Area section) throws SudokuException{
		known = false;
		value = VALUE_UNKNOWN;
		
		// Just makes things easier to ignore the 0 for this array
		possibilities = new boolean[Solver.getN()]; 
		// All possibilities initially true
		for (int i = 0; i < Solver.getN() ; ++i){
			possibilities[i] = true;
		}
		
		this.row = row;
		row.addSquare(this);
		
		this.column = column;
		column.addSquare(this);
		
		this.section = section;
		section.addSquare(this);
	}

}
