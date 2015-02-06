package sudoku.fun.main;


/** Represents one of the 81 squares on the 9*9 Sudoku board */
public class Square {
	
	/** Indicates whether the square's value has been determined, initially false*/
	// GET only (it is SET internally)
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
	
	/** Set the value of the square.
	 * Throws exception in the following cases:
	 *  - value is not in [1-9]
	 *  - square already has a value
	 *  - given value was pre-determined to be an impossibility
	 *  
	 TODO: enforces possibilities on all "neighbours" (row, col, section)
	 */
	public void setValue(int newValue) throws Exception{
		if(value<1 || value > 9){
			throw new Exception("ERROR setValue(): valid square values are [1-9] only.");
		}
		if(known == true){
			throw new Exception("ERROR setValue(): you cannot change the value of a known square.");
		}
		if(possibilities[newValue] == false){
			throw new Exception("ERROR setValue(): this square may not contain this value.");
		}
		known = true;
		value = newValue;
		// Set all other possibility booleans to false
		for(int i = 1 ; i <= 9 ; ++i){
			if(i != newValue){
				possibilities[i] = false;
			}
		}
		
		//TODO: enforces possibilities on all "neighbours" (row, col, section)
	}
	
	/** Indicates whether the square may contain each of [1-9], initially all true.*/
	//Internal Note, these values are stored in array locations [1-9], 0 is empty, (for simplicity)
	private boolean[] possibilities;
	
	/** Given number 1 thru 9, returns true if this square may contain it.*/
	public boolean isPossible(int value){
		return(possibilities[value]);
	}
	
	/** Sets the possibility of the given value to false
	 * (indicated that this square may not contain the given value)
	 * Throws exception in the following cases:
	 *  - all possibilities are now false
	 *  - only one possibility remained, and setting the value of the square threw an exception
	 */
	public void setImpossibility(int value) throws Exception{
		// Set this possibility to false
		possibilities[value] = false;
		
		// Check if all possibilities are now false
		int trueCount = 0;
		for(int i = 1 ; i <= 9 ; ++i){
			if( possibilities[i] ){
				++trueCount;
			}
		}
		if(trueCount == 0){
			throw new Exception("ERROR setImpossibility(): this square has no remaining possible values.");
		}else if(trueCount == 1){
			// Only one possibility remaining
			setValue(value);
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
	
	public Square(Area row, Area column, Area section){
		known = false;
		value = -1;
		possibilities = new boolean[10]; // Just makes things easier to ignore the
		for (int i = 1; i <=9 ; ++i){
			possibilities[i] = false;
		}
		this.row = row;
		this.column = column;
		this.section = section;
	}
	
}
