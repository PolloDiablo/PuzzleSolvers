package battleship.fun.main;

import battleship.fun.main.Solver.Item;
/**
 * 
 * @author Jeremy
 * Represents one square of the grid.
 */
public class Square {
	private final Board parentBoard;
	
	private final int row;
	private final int col;
	
	private Item contents;
	private Ship ownerShip;
	
	// Regular constructor
	public Square(int row,int col, Board parentBoard){
		this.parentBoard = parentBoard;
		this.row = row;
		this.col = col;
		
		// Initially all squares are unknown
		contents = Item.UNKNOWN;
		ownerShip = null;
	}

	public Item getContents() {
		return contents;
	}

	// Set the contents without decrementing the Board counters
	// (Just used by the Board copy constructor)
	public void initializeContents(Item newContents, Ship newOwnerShip) {	
		this.contents = newContents;
		this.ownerShip = newOwnerShip;
	}
	
	// Throws exception if an invalid change is being made
	public void setContents(Item newContents) throws Exception {
		// Parts cannot change ship part type
		if((this.contents != Item.UNKNOWN && this.contents != Item.WATER && this.contents != Item.OCCUPIED)
				&&(this.contents != newContents)){
			throw new Exception();
		}		
			
		// If there is a change in contents from UNKNOWN -> SHIP PART
		if(this.contents == Item.UNKNOWN 
				&&(newContents != Item.UNKNOWN && newContents != Item.WATER)){
			// Then we need to decrement the Board's counters
			if(	!parentBoard.decrementRowSpotsRemaining(row,1) ||
				!parentBoard.decrementColumnSpotsRemaining(col,1)){
				// If this made a row too full, then return false
				throw new Exception();
			}
		}

		this.contents = newContents;
	}
	
	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public Ship getOwnerShip() {
		return ownerShip;
	}
	
	public void setOwnerShip(Ship newOwnerShip) throws Exception {
		// Parts cannot change owner
		if(this.ownerShip != null && this.ownerShip != newOwnerShip){
			throw new Exception();
		}
		this.ownerShip = newOwnerShip;
	}
	
}
