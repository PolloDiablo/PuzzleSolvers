package sudoku.fun.main;

import java.io.File;
import java.io.IOException;
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
	
	private Set<Square>[] rows;
	private Set<Square>[] columns;
	private Set<Square>[] sections;
	
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
		System.out.println(" line "+ 0 + ": " + lines.get(0));
		this.size = Integer.parseInt(lines.get(0));	

		// Populate known values (all remaining input)
		for(int i = 1 ; i < lines.size() ; i++){
	
			String[] parts = lines.get(i).split("\\(");
			for(int j = 0 ; j < parts.length ; j++){
				if(parts[j].length() > 5){
					System.out.println("TEST: " + parts[j]);
					
					// TODO
				}
				
			}
			
			
			
		}
		
		
	}
	
	public void displayBoard(){
		// TODO
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
