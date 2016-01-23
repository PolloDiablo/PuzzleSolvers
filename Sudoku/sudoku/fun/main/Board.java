package sudoku.fun.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class Board {

	private Integer size;
	
	public Integer getSize(){
		return size;
	}
	
	private Square[][] grid;
	
	public Square[][] getGrid(){
		return grid;
	}
	
	
	private Area[] rows;
	private Area[] columns;
	private Area[] sections;
	
	// Regular constructor
	public Board(){
		size = -1;
	}
	
	// Copy constructor
	public Board(Board other){
		this.size = other.size;
	}
	
    @Override
    public boolean equals(Object obj) {
    	
        if (!(obj instanceof Board)){
            return false;  	     
        }
        Board other = (Board)obj;
        
        if( this.size != other.size){
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
		
		// Get the puzzle size (first row of input)
		size = Integer.parseInt(lines.get(0));	
		
		// Create the board structure
		grid = new Square[size][size];
		rows = new Area[size];
		columns = new Area[size];
		sections = new Area[size];
		for(int i = 0 ; i < size ; i++){
			rows[i] = new Area();
			columns[i] = new Area();
			sections[i] = new Area();
		}
		// Create the squares
		for(int rowNumber = 0 ; rowNumber < size ; rowNumber++){
			for(int columnNumber = 0 ; columnNumber < size ; columnNumber++){
				int sectionSize = (int)Math.sqrt(size);
				int sectionNumber = sectionSize*(columnNumber/sectionSize) + (rowNumber/sectionSize);
				grid[rowNumber][columnNumber] = new Square(rows[rowNumber], columns[columnNumber], sections[sectionNumber]);	
			}			
		}
		
		// Verify that everything was created properly
		for(int i = 0 ; i < this.size ; i++){
			// All rows should have N elements
			if(rows[i].getAllSquares().size() != this.size){
				throw new Exception("ERROR readInput() rows not full populated");
			}
			if(columns[i].getAllSquares().size() != this.size){
				throw new Exception("ERROR readInput() columns not full populated");
			}
			if(sections[i].getAllSquares().size() != this.size){
				throw new Exception("ERROR readInput() sections not full populated");
			}
		}
		
		// Calculate neighbours for each square
		for(int i = 0 ; i < this.size ; i++){
			// All rows should have N elements
			for(Square s : rows[i].getAllSquares()){
				s.calculateNeighbours();
			}
		}

		// Populate known values (all remaining input)
		for(int i = 1 ; i < lines.size() ; i++){
	
			String[] parts = lines.get(i).split("\\(");
			for(int j = 0 ; j < parts.length ; j++){
				if(parts[j].length() > 5){
					String[] numbers = parts[j].split(",|\\)");			
					int rowNumber = Integer.parseInt(numbers[0]);
					int columnNumber = Integer.parseInt(numbers[1]);
					int value = Util.stringToValue(numbers[2]);					
					grid[rowNumber][columnNumber].setValue(value);
				}
			}
		}
		
		
	}
	
	public void displayBoard(){
		
		int boxSize = (int) Math.sqrt(size); // e.g. 3 on a 9x9 board

		// Create border
		String border = "O";
		for(int i = 0 ; i < size ; i++){
			if( ((i+1) % boxSize) == 0 ){
				border += "-O";
			}else{
				border += "--";
			}
		}
		// Print top
		System.out.println(border);
		
		// Print middle
		for(int i = 0 ; i < size ; i++){
			System.out.print("|");			
			for(int j = 0 ; j < size ; j++){
				System.out.print(""+grid[i][j].getFormattedValue());
				if( (j+1) % boxSize == 0){
					System.out.print("|");
				}else
				{
					System.out.print(" ");
				}
			}
			
			if( ((i+1) % boxSize) == 0 ){
				System.out.println("\n"+border);
			}else{
				System.out.println("");
			}
			
		}
	}
	
	/** Check for inconsistencies/errors
		- Two of the same number in a row/column/set
		- TODO
	 return false if board is bad*/
	public boolean isValid(){
		
		// TODO
		
		return true;
	}
	
	/**
	 * Ensure that:
	 *  - No unknowns
	 * 	- isValid()
	 * 		Since no duplicates in any row/column/set implies one of each number in every row/column/set
	 * @return
	 */
	public boolean isComplete(){

		// TODO
		
		if(!isValid()){
			return false;
		}
		
		return true;
	}
	
}
