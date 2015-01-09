package battleship.fun.main;

import org.apache.commons.io.FileUtils;
import battleship.fun.main.Solver.Item;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Board {
	

	private Square[][] grid;
	private Integer[] rowValues;
	private Integer[] columnValues;
	
	private Integer[] rowSpotsRemaining;
	private Integer[] columnSpotsRemaining;
	
	private Fleet fleet;
	
	public Square[][] getGrid(){
		return grid;
	}
	
	public Integer[] getRowValues(){
		return rowValues;
	}
	
	public Integer[] getColumnValues(){
		return columnValues;
	}
	
	// Regular constructor
	public Board(){
		this.grid = new Square[10][10];	
		for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				grid[i][j] = new Square(i,j,this);
			}
		}
		this.rowValues = new Integer[10];
		this.columnValues =  new Integer[10];
		this.rowSpotsRemaining =  new Integer[10];
		this.columnSpotsRemaining =  new Integer[10];
		
		this.fleet = new Fleet();
	}
	
	// Copy constructor
	public Board(Board other){
		this.grid = new Square[10][10];
		for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				grid[i][j] = new Square(i,j,this);
			}
		}
	   	for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				this.grid[i][j].initializeContents(other.grid[i][j].getContents(),other.grid[i][j].getOwnerShip());
			}
		}
		
		this.rowValues = new Integer[10];
		this.rowValues = other.rowValues;
		
		this.columnValues =  new Integer[10];
		this.columnValues = other.columnValues;
		
		this.rowSpotsRemaining =  new Integer[10];
		for(int i = 0 ; i < 10 ; i++){
			this.rowSpotsRemaining[i] = other.rowSpotsRemaining[i];
		}
		
		this.columnSpotsRemaining =  new Integer[10];
		for(int j = 0 ; j < 10 ; j++){
			this.columnSpotsRemaining[j] = other.columnSpotsRemaining[j];
		}
		
		this.fleet = new Fleet(other.fleet);
	}
	
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Board)){
           return false;  	     
       }
       Board other = (Board)obj;
       
	   	for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				if(this.grid[i][j].getContents() != other.grid[i][j].getContents()){
					return false;
				}
			}
	   	}
	   	
	   	for(int i = 0 ; i < 10 ; i++){
	   		if(this.rowSpotsRemaining[i] != other.rowSpotsRemaining[i]
	   			||this.columnSpotsRemaining[i] != other.columnSpotsRemaining[i]){
	   			return false;
	   		}
	   	}
	   	
	   	
       if(this.rowValues != other.rowValues ||
    		   this.columnValues != other.columnValues){
    	   return false;
       }
       return true;
    }
	
	
	public void readInput(String fileName) throws Exception{
		
		System.out.println("Reading Input...");
		// Read input lines
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Store row values (1st row of input)
		System.out.println(" line "+ 0 + ": " + lines.get(0));
		String[] parts = lines.get(0).split(",");
		assert(parts.length == 10);
		for(int i = 0 ; i < 10 ; i++){
			rowSpotsRemaining[i] = rowValues[i] = Integer.parseInt(parts[i]);	
			// (Remaining spots are initially the same)
		}
		
		// Store column values (2nd row of input)
		System.out.println(" line "+ 1 + ": " + lines.get(1));
		parts = lines.get(1).split(",");
		assert(parts.length == 10);
		for(int i = 0 ; i < 10 ; i++){
			columnSpotsRemaining[i] = columnValues[i] = Integer.parseInt(parts[i]);	
			// (Remaining spots are initially the same)
		}
		
		// Fill in the known items in the grid (remaining rows of input)	
		for(int i = 2 ; i < lines.size() ; i++){
			System.out.println(" line "+ i + ": " + lines.get(i));
			parts = lines.get(i).split(",");
			assert(parts.length == 3);
			int row = Integer.parseInt(parts[0]);
			int col = Integer.parseInt(parts[1]);
			Item val;
			switch (parts[2].toUpperCase()){
				case "S":
					val = Item.SUB;
					fleet.addShip(new Submarine(grid[row][col]));
					break;
				case "L":
					val = Item.LEFT;
					break;
				case "R":
					val = Item.RIGHT;
					break;
				case "T":
					val = Item.TOP;
					break;
				case "B":
					val = Item.BOTTOM;
					break;
				case "C":
					val = Item.CENTER;
					break;
				case "W":
					val = Item.WATER;
					break;
				default:
					val = Item.UNKNOWN;
			}
			grid[row][col].setContents(val);
		}
		System.out.println("Done.");
	}
	
	public void displayBoard(){
		System.out.println("=======================");
		System.out.println("  0 1 2 3 4 5 6 7 8 9");
		String str;
		for(int i = 0 ; i < 10 ; i++){
			System.out.print(i);
			for(int j = 0 ; j < 10 ; j++){
				switch (grid[i][j].getContents()){
				case SUB:
					str = "S";
					break;
				case LEFT:
					str = "L";
					break;
				case RIGHT:
					str = "R";
					break;
				case TOP:
					str = "T";
					break;
				case BOTTOM:
					str = "B";
					break;
				case CENTER:
					str = "C";
					break;
				case WATER:
					str = " ";
					break;
				case OCCUPIED:
					str = ".";
					break;
				case UNKNOWN:
				default:
					str = " ";	
				}
				System.out.print('|'+str);
			}
			System.out.println("|" + rowValues[i]);
		}
		// Print the bottom numbers
		System.out.print(" ");
		for(int i = 0 ; i < 10 ; i++){
			System.out.print(" "+ columnValues[i]);
		}
		
		System.out.println("\n=======================");
	}

	public Integer[] getColumnSpotsRemaining() {
		return columnSpotsRemaining;
	}

	// Used in the Square class
	public boolean decrementColumnSpotsRemaining(Integer col, Integer amount) {
		columnSpotsRemaining[col] = columnSpotsRemaining[col] - amount;
		if(columnSpotsRemaining[col] < 0){
			return false;
		}else{
			return true;
		}
	}

	public Integer[] getRowSpotsRemaining() {
		return rowSpotsRemaining;
	}

	// Used in the Square class
	public boolean decrementRowSpotsRemaining(Integer row, Integer amount) {
		rowSpotsRemaining[row] = rowSpotsRemaining[row] - amount;
		if(rowSpotsRemaining[row] < 0){
			return false;
		}else{
			return true;
		}
	}

	public Fleet getFleet() {
		return fleet;
	}
	
	// Check for inconsistencies/errors
	//	- Overfull rows or columns
	//	- Not enough empty space for remaining Battleships
	//	- Adjacent Battleships
	//	- Too many of one kind of Battleship
	// return false if board is bad
	public boolean isValid(){
		//	- Overfull rows or columns
	   	for(int i = 0 ; i < 10 ; i++){
	   		if(rowSpotsRemaining[i] < 0 || columnSpotsRemaining[i] < 0 ){
	   			return false;
	   		}
	   	}
	   	
		// Not enough empty space for remaining Battleships
	   	// TODO
	   	
		//	- Adjacent Battleships
	   	for(int i = 1 ; i < 9 ; i++){
			for(int j = 1 ; j < 9 ; j++){
				// Check if this Board spot is occupied
				if(grid[i][j].getContents() != Item.UNKNOWN
						&& grid[i][j].getContents() != Item.WATER){
					// Check if any adjacent spots are occupied, by a different ship!
				   	for(int k = i-1 ; k < i+2 ; k++){
						for(int l = j-1 ; l < j+2 ; l++){
							if(grid[k][l].getContents() != Item.UNKNOWN
									&& grid[k][l].getContents() != Item.WATER
									&& grid[k][l].getOwnerShip() != grid[i][j].getOwnerShip()){
								return false;
							}
						}
				   	}
					
				}
			}
	   	}

	   	if(!fleet.isValid()){
	   		return false;
	   	}
		
		return true;	
	}
	
	/**
	 * Ensure that:
	 *  - No unknowns
	 * 	- All rows/columns are full
	 * 	- All ships are placed
	 *  Does NOT check for errors
	 * @return
	 */
	public boolean isComplete(){
		// Ensure there are no unknowns
	   	for(int i = 0 ; i < 10 ; i++){
			for(int j = 0 ; j < 10 ; j++){
				if(grid[i][j].getContents() == Item.UNKNOWN
						|| grid[i][j].getContents() == Item.OCCUPIED){
					return false;
				}
			}
	   	}
	   	
	   	// Ensure all rows/columns are full
	   	for(int i = 0 ; i < 10 ; i++){
	   		if(rowSpotsRemaining[i] != 0 || columnSpotsRemaining[i] != 0 ){
	   			return false;
	   		}
	   	}
	   	
	   	// Ensure all ships are placed
	   	if(!fleet.isComplete()){
	   		return false;
	   	}
	   	
		return true;
	}
}
