package sudoku.fun.main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Area {

	private Set<Square> mySquares;
	
	public Set<Square> getUnknownSquares(){
		Set<Square> unknowns = new HashSet<Square>();
		Iterator<Square> iterator = getIterator(); 
		while (iterator.hasNext()){
			if(!iterator.next().isKnown()){
				unknowns.add(iterator.next());
			}	
		}
		return unknowns;
	}
	
	public Set<Square> getAllSquares(){
		return mySquares;
	}
	
	public Iterator<Square> getIterator(){
		return mySquares.iterator();
	}
	
	// Constructor
	public Area(){
		mySquares = new HashSet<Square>();
	}
	
	public void addSquare(Square newSquare) throws SudokuException{
		if(mySquares.size() <= Solver.getN()){
			mySquares.add(newSquare);
		}
		else{
			throw new SudokuException("ERROR addSquare(): Area size is too large.");
		}		
	}
	
	/**
	 * Returns true if one of the Squares in this Area has a known value of x
	 */
	public boolean knownContains(Integer x){
		Iterator<Square> iterator = getIterator(); 
		while (iterator.hasNext()){
			if(iterator.next().getValue() == x){
				return true;
			}	
		}
		return false;
	}
		
	/**
	 * Returns the subset of Squares which may contain x
	 * 
	 * Returns empty set if there are no possibilities
	 * 	(Either x already has a known position, an error was made, or the board is invalid)
	 */
	public Set<Square> possibilitiesContains(Integer x) throws SudokuException{
		
		Set<Square> returnSet = new HashSet<Square>();
		
		// If we know the location of x within this area, then it can't be within the possibilities of any other Square
		if(this.knownContains(x)){
			return returnSet;
		}
		
		Iterator<Square> iterator = getIterator(); 
		while (iterator.hasNext()){
			if(iterator.next().isPossible(x)){
				returnSet.add(iterator.next());
			}	
		}
		
		if(returnSet.isEmpty()){
			throw new SudokuException("ERROR possibilitiesContains(): no known or possible Squares for x: "+ x);
		}
		return returnSet;
	}
}
