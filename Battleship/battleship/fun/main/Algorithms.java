package battleship.fun.main;

import battleship.fun.main.Solver.Item;

public class Algorithms {

	// Fill-in water for zero rows and columns
	public static boolean fillInZeroWater(Board myBoard) throws Exception{
		for(int i = 0; i<10 ; i++){
			if(myBoard.getRowValues()[i] == 0){
				for(int j = 0; j<10 ; j++){
					// Attempt to set contents to water
					myBoard.getGrid()[i][j].setContents(Item.WATER);
				}
			}
		}
		
		for(int j = 0; j<10 ; j++){
			if(myBoard.getColumnValues()[j] == 0){
				for(int i = 0; i<10 ; i++){
					// Attempt to set contents to water
					myBoard.getGrid()[i][j].setContents(Item.WATER);
				}
			}
		}	
		return true;
	}
	
	// Fill-in water for full rows and columns
	public static boolean fillInFullWater(Board myBoard) throws Exception{
		// Do the rows
		for(int i = 0; i<10 ; i++){
			int count = 0;
			for(int j = 0; j<10 ; j++){
				if(myBoard.getGrid()[i][j].getContents() != Item.WATER &&
						myBoard.getGrid()[i][j].getContents()!=	Item.UNKNOWN){
					++count;
				}
			}
			// Check if row is full
			if(count == myBoard.getRowValues()[i]){
				for(int j = 0; j<10 ; j++){
					if(myBoard.getGrid()[i][j].getContents() == Item.UNKNOWN){
						myBoard.getGrid()[i][j].setContents(Item.WATER);
					}
				}
			}
		}
		// Do the columns
		for(int j = 0; j<10 ; j++){
			int count = 0;
			for(int i = 0; i<10 ; i++){
				if(myBoard.getGrid()[i][j].getContents() != Item.WATER &&
						myBoard.getGrid()[i][j].getContents()!=	Item.UNKNOWN){
					++count;
				}
			}
			// Check if column is full
			if(count == myBoard.getColumnValues()[j]){
				for(int i = 0; i<10 ; i++){
					if(myBoard.getGrid()[i][j].getContents() == Item.UNKNOWN){
						// Attempt to set contents to water
						myBoard.getGrid()[i][j].setContents(Item.WATER);
					}
				}
			}
		}
		return true;
	}
	
	// Fill-in water around known ship locations
	public static void fillInSurroundingWater(Board myBoard) throws Exception{
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){
				Item currentSquareContents = myBoard.getGrid()[i][j].getContents();
				// Cases:  SUB, LEFT, RIGHT, TOP, BOTTOM, CENTER
				if(currentSquareContents == Item.SUB){
					fillNW(myBoard,i,j);
					fillN(myBoard,i,j);
					fillNE(myBoard,i,j);
					fillW(myBoard,i,j);
					fillE(myBoard,i,j);
					fillSW(myBoard,i,j);
					fillS(myBoard,i,j);
					fillSE(myBoard,i,j);
				}else if(currentSquareContents == Item.LEFT){
					fillNW(myBoard,i,j);
					fillN(myBoard,i,j);
					fillNE(myBoard,i,j);
					fillW(myBoard,i,j);
					fillSW(myBoard,i,j);
					fillS(myBoard,i,j);
					fillSE(myBoard,i,j);
				}else if(currentSquareContents == Item.RIGHT){
					fillNW(myBoard,i,j);
					fillN(myBoard,i,j);
					fillNE(myBoard,i,j);
					fillE(myBoard,i,j);
					fillSW(myBoard,i,j);
					fillS(myBoard,i,j);
					fillSE(myBoard,i,j);
				}else if(currentSquareContents == Item.TOP){
					fillNW(myBoard,i,j);
					fillN(myBoard,i,j);
					fillNE(myBoard,i,j);
					fillW(myBoard,i,j);
					fillE(myBoard,i,j);
					fillSW(myBoard,i,j);
					fillSE(myBoard,i,j);
				}else if(currentSquareContents == Item.BOTTOM){
					fillNW(myBoard,i,j);
					fillNE(myBoard,i,j);
					fillW(myBoard,i,j);
					fillE(myBoard,i,j);
					fillSW(myBoard,i,j);
					fillS(myBoard,i,j);
					fillSE(myBoard,i,j);
				}else if(currentSquareContents == Item.CENTER  ||
						 currentSquareContents == Item.OCCUPIED){
					fillNW(myBoard,i,j);
					fillNE(myBoard,i,j);
					fillSW(myBoard,i,j);
					fillSE(myBoard,i,j);
				}
			}
		}
	}

	private static void fillNW(Board myBoard, int i, int j) throws Exception{
		if(i-1 >= 0 && j-1 >=0)
			myBoard.getGrid()[i-1][j-1].setContents(Item.WATER);
	}
	private static void fillN(Board myBoard, int i, int j) throws Exception{
		if(i-1 >= 0)
			myBoard.getGrid()[i-1][j].setContents(Item.WATER);
	}
	private static void fillNE(Board myBoard, int i, int j) throws Exception{
		if(i-1 >= 0 && j+1 <=9)
			myBoard.getGrid()[i-1][j+1].setContents(Item.WATER);
	}
	private static void fillW(Board myBoard, int i, int j) throws Exception{
		if(j-1 >=0)
			myBoard.getGrid()[i][j-1].setContents(Item.WATER);
	}
	private static void fillE(Board myBoard, int i, int j) throws Exception{
		if(j+1 <=9)
			myBoard.getGrid()[i][j+1].setContents(Item.WATER);
	}
	private static void fillSW(Board myBoard, int i, int j) throws Exception{
		if(i+1 <= 9 && j-1 >=0)
			myBoard.getGrid()[i+1][j-1].setContents(Item.WATER);
	}
	private static void fillS(Board myBoard, int i, int j) throws Exception{
		if(i+1 <= 9)
			myBoard.getGrid()[i+1][j].setContents(Item.WATER);
	}
	private static void fillSE(Board myBoard, int i, int j) throws Exception{
		if(i+1 <= 9 && j+1 <=9)
			myBoard.getGrid()[i+1][j+1].setContents(Item.WATER);
	}
	
	// Fill-in ship parts (based on nearby water - i.e. forced length)
	public static void fillInShipParts(Board myBoard) throws Exception{
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){
				Square currentSquare = myBoard.getGrid()[i][j];
				
				// If we have already assigned this Square an owner ship
				if(currentSquare.getOwnerShip() != null){
					// Then we do not need to analyze it
					continue;
				}
				
				// Cases:  SUB, LEFT, RIGHT, TOP, BOTTOM, CENTER
				if(currentSquare.getContents() == Item.LEFT){
					// Check if this is near an edge or water, or row can only contain 2
					if(myBoard.getRowValues()[i]==2 || j+1 == 9 || 
							myBoard.getGrid()[i][j+2].getContents() == Item.WATER){
						// => it must be a 2-er
						myBoard.getGrid()[i][j+1].setContents(Item.RIGHT);
						myBoard.getFleet().addShip(new Destroyer(currentSquare,myBoard.getGrid()[i][j+1]));
					}else{
						if(myBoard.getGrid()[i][j+1].getContents()==Item.UNKNOWN){
							myBoard.getGrid()[i][j+1].setContents(Item.OCCUPIED);
						}
					}
				}else if(currentSquare.getContents() == Item.RIGHT){
					// Check if this is near an edge or water, or row can only contain 2
					if(myBoard.getRowValues()[i]==2 || j-1 == 0 ||
							myBoard.getGrid()[i][j-2].getContents() == Item.WATER){
						// => it must be a 2-er
						myBoard.getGrid()[i][j-1].setContents(Item.LEFT);
						myBoard.getFleet().addShip(new Destroyer(currentSquare,myBoard.getGrid()[i][j-1]));
					}else{
						if(myBoard.getGrid()[i][j-1].getContents()==Item.UNKNOWN){
							myBoard.getGrid()[i][j-1].setContents(Item.OCCUPIED);
						}
					}
				}else if(currentSquare.getContents() == Item.TOP){
					// Check if this is near an edge or water, or row can only contain 2
					if(myBoard.getColumnValues()[j]==2 || i+1 == 9 ||
							myBoard.getGrid()[i+2][j].getContents() == Item.WATER){
						//	=> it must be a 2-er
						myBoard.getGrid()[i+1][j].setContents(Item.BOTTOM);
						myBoard.getFleet().addShip(new Destroyer(currentSquare,myBoard.getGrid()[i+1][j]));
					}else{
						if(myBoard.getGrid()[i+1][j].getContents()==Item.UNKNOWN){
							myBoard.getGrid()[i+1][j].setContents(Item.OCCUPIED);
						}
					}
				}else if(currentSquare.getContents() == Item.BOTTOM){
					// Check if this is near an edge or water, or row can only contain 2
					if(myBoard.getColumnValues()[j]==2 || i-1 == 0 || 
							myBoard.getGrid()[i-2][j].getContents() == Item.WATER){
						//	=> it must be a 2-er
						myBoard.getGrid()[i-1][j].setContents(Item.TOP);
						myBoard.getFleet().addShip(new Destroyer(currentSquare,myBoard.getGrid()[i-1][j]));
					}else{
						if(myBoard.getGrid()[i-1][j].getContents()==Item.UNKNOWN){
							myBoard.getGrid()[i-1][j].setContents(Item.OCCUPIED);
						}
					}
				}else if(currentSquare.getContents() == Item.CENTER){
					// Check if a left-right ship is impossible
					// => it must go up/down
					if(myBoard.getRowValues()[i]<3 || j == 0 || j== 9 || 
							myBoard.getGrid()[i][j+1].getContents() == Item.WATER ||
							myBoard.getGrid()[i][j-1].getContents() == Item.WATER ){
						// We know the ship runs up/down,
						if( (myBoard.getColumnValues()[j]==3) ||
								((i+1 == 9 || myBoard.getGrid()[i+2][j].getContents() == Item.WATER) && 
								(i-1 == 0 || myBoard.getGrid()[i-2][j].getContents() == Item.WATER))
								  ){
							//	=> it must be a 3-er
							myBoard.getGrid()[i-1][j].setContents(Item.TOP);						
							myBoard.getGrid()[i+1][j].setContents(Item.BOTTOM);
							myBoard.getFleet().addShip(new Cruiser(myBoard.getGrid()[i-1][j],currentSquare,myBoard.getGrid()[i+1][j]));
						// If we don't know its a 3-er
						}else{
							// We know its either a 3-er or a 4-er that runs up/down
							myBoard.getGrid()[i-1][j].setContents(Item.OCCUPIED);						
							myBoard.getGrid()[i+1][j].setContents(Item.OCCUPIED);
							// TODO
						}

					// Check if an up/down ship is impossible
					// => it must go left-right	
					}else if(myBoard.getColumnValues()[j]<3 || i == 0 || i== 9 ||
							myBoard.getGrid()[i+1][j].getContents() == Item.WATER ||
							myBoard.getGrid()[i-1][j].getContents() == Item.WATER ){
						// We know the ship runs left/right, do we know its size and/or location?
						if( (myBoard.getRowValues()[i]==3) ||
								((j+1 == 9 || myBoard.getGrid()[i][j+2].getContents() == Item.WATER) && 
								(j-1 == 0 || myBoard.getGrid()[i][j-2].getContents() == Item.WATER))
								  ){
							//	=> it must be a 3-er
							myBoard.getGrid()[i][j-1].setContents(Item.LEFT);	
							myBoard.getGrid()[i][j+1].setContents(Item.RIGHT);
							myBoard.getFleet().addShip(new Cruiser(myBoard.getGrid()[i][j-1],currentSquare,myBoard.getGrid()[i][j+1]));
						// If we don't know its a 3-er
						}else{
							// We know its either a 3-er or a 4-er that runs left-right
							myBoard.getGrid()[i][j-1].setContents(Item.OCCUPIED);	
							myBoard.getGrid()[i][j+1].setContents(Item.OCCUPIED);
							// TODO
						}
						
					}else{
						// We do not know which direction it goes
						// Do nothing
					}
				}
			}
		}
		fillInSurroundingWater(myBoard);
	}
	// Fill-in 'OCCUPIED' ship parts (based on rows/columns which have unknowns==required)
	public static void fillRowsAndColumns(Board myBoard) throws Exception{
		Integer[] rowSpotsRemaining = myBoard.getRowSpotsRemaining();
		for(int i = 0; i<10 ; i++){
			// Find the number of unknowns
			Integer numberOfUnknowns = 0;
			for(int j = 0; j<10 ; j++){
				if(myBoard.getGrid()[i][j].getContents()==Item.UNKNOWN){
					numberOfUnknowns++;
				}
			}
			if(numberOfUnknowns == rowSpotsRemaining[i]){
				// Every remaining spot must be occupied
				for(int j = 0; j<10 ; j++){
					if(myBoard.getGrid()[i][j].getContents()==Item.UNKNOWN){
						myBoard.getGrid()[i][j].setContents(Item.OCCUPIED);
					}
				}
			}
		}
		Integer[] columnSpotsRemaining = myBoard.getColumnSpotsRemaining();
		for(int j = 0; j<10 ; j++){
			// Find the number of unknowns
			Integer numberOfUnknowns = 0;
			for(int i = 0; i<10 ; i++){
				if(myBoard.getGrid()[i][j].getContents()==Item.UNKNOWN){
					numberOfUnknowns++;
				}
			}
			if(numberOfUnknowns == columnSpotsRemaining[j]){
				// Every remaining spot must be occupied
				for(int i = 0; i<10 ; i++){
					if(myBoard.getGrid()[i][j].getContents()==Item.UNKNOWN){
						myBoard.getGrid()[i][j].setContents(Item.OCCUPIED);
					}
				}
			}
		}
	}
	
	// Determine true part of 'OCCUPIED' ship parts
	public static void determineTrueOccupied(Board myBoard) throws Exception{
		for(int i = 0; i<10 ; i++){
			for(int j = 0; j<10 ; j++){ 
				if(myBoard.getGrid()[i][j].getContents()==Item.OCCUPIED){
					Square currentSquare = myBoard.getGrid()[i][j];
					
					boolean leftBlocked =	(j-1<0 || myBoard.getGrid()[i][j-1].getContents() == Item.WATER);
					boolean rightBlocked =	(j+1>9 || myBoard.getGrid()[i][j+1].getContents() == Item.WATER);
					boolean topBlocked =	(i-1<0 || myBoard.getGrid()[i-1][j].getContents() == Item.WATER);
					boolean bottomBlocked =	(i+1>9 || myBoard.getGrid()[i+1][j].getContents() == Item.WATER);
					
					// First check for 1ers
					if(leftBlocked && rightBlocked && bottomBlocked && topBlocked){
						currentSquare.setContents(Item.SUB);						
						myBoard.getFleet().addShip(new Submarine(currentSquare));
						continue;
					}
					
					boolean leftOccupied =	(j-1>=0 && myBoard.getGrid()[i][j-1].getContents() != Item.WATER
								&& myBoard.getGrid()[i][j-1].getContents() != Item.UNKNOWN);
					boolean rightOccupied =	(j+1<=9 && myBoard.getGrid()[i][j+1].getContents() != Item.WATER
							&& myBoard.getGrid()[i][j+1].getContents() != Item.UNKNOWN);
					boolean topOccupied =	(i-1>=0 && myBoard.getGrid()[i-1][j].getContents() != Item.WATER
							&& myBoard.getGrid()[i-1][j].getContents() != Item.UNKNOWN);
					boolean bottomOccupied =(i+1<=9 && myBoard.getGrid()[i+1][j].getContents() != Item.WATER
							&& myBoard.getGrid()[i+1][j].getContents() != Item.UNKNOWN);
					
					boolean left2Blocked =	(j-2<0 || myBoard.getGrid()[i][j-2].getContents() == Item.WATER);
					boolean right2Blocked =	(j+2>9 || myBoard.getGrid()[i][j+2].getContents() == Item.WATER);
					boolean top2Blocked =	(i-2<0 || myBoard.getGrid()[i-2][j].getContents() == Item.WATER);
					boolean bottom2Blocked =(i+2>9 || myBoard.getGrid()[i+2][j].getContents() == Item.WATER);
				
					// Now for 2ers
					if(leftOccupied && left2Blocked && rightBlocked){
						currentSquare.setContents(Item.RIGHT);		
						myBoard.getGrid()[i][j-1].setContents(Item.LEFT);
						myBoard.getFleet().addShip(new Destroyer(myBoard.getGrid()[i][j-1],currentSquare));
						continue;
					}
					if(rightOccupied && right2Blocked && leftBlocked){
						currentSquare.setContents(Item.LEFT);		
						myBoard.getGrid()[i][j+1].setContents(Item.RIGHT);
						myBoard.getFleet().addShip(new Destroyer(currentSquare,myBoard.getGrid()[i][j+1]));
						continue;
					}
					if(topOccupied && top2Blocked && bottomBlocked){
						currentSquare.setContents(Item.BOTTOM);		
						myBoard.getGrid()[i-1][j].setContents(Item.TOP);
						myBoard.getFleet().addShip(new Destroyer(myBoard.getGrid()[i-1][j],currentSquare));
						continue;
					}
					if(bottomOccupied && bottom2Blocked && topBlocked){
						currentSquare.setContents(Item.TOP);		
						myBoard.getGrid()[i+1][j].setContents(Item.BOTTOM);
						myBoard.getFleet().addShip(new Destroyer(currentSquare,myBoard.getGrid()[i+1][j]));
						continue;
					}
					
					// Now for 3ers
					if(leftOccupied && left2Blocked && rightOccupied && right2Blocked){ //Center case
						currentSquare.setContents(Item.CENTER);		
						myBoard.getGrid()[i][j-1].setContents(Item.LEFT);
						myBoard.getGrid()[i][j+1].setContents(Item.RIGHT);
						myBoard.getFleet().addShip(new Cruiser(myBoard.getGrid()[i][j-1],currentSquare,myBoard.getGrid()[i][j+1]));
						continue;
					}
					if(topOccupied && top2Blocked && bottomOccupied && bottom2Blocked ){ //Center case
						currentSquare.setContents(Item.CENTER);		
						myBoard.getGrid()[i-1][j].setContents(Item.TOP);
						myBoard.getGrid()[i+1][j].setContents(Item.BOTTOM);
						myBoard.getFleet().addShip(new Cruiser(myBoard.getGrid()[i-1][j],currentSquare,myBoard.getGrid()[i+1][j]));
						continue;
					}
					//TODO other 3er cases (OCCUPIED is an end piece)
					
					
					// Now for 4ers
					boolean left2Occupied =	(j-2>=0 && myBoard.getGrid()[i][j-2].getContents() != Item.WATER
							&& myBoard.getGrid()[i][j-2].getContents() != Item.UNKNOWN);
					boolean right2Occupied =	(j+2<=9 && myBoard.getGrid()[i][j+2].getContents() != Item.WATER
						&& myBoard.getGrid()[i][j+2].getContents() != Item.UNKNOWN);
					boolean top2Occupied =	(i-2>=0 && myBoard.getGrid()[i-2][j].getContents() != Item.WATER
						&& myBoard.getGrid()[i-2][j].getContents() != Item.UNKNOWN);
					boolean bottom2Occupied =(i+2<=9 && myBoard.getGrid()[i+2][j].getContents() != Item.WATER
							&& myBoard.getGrid()[i+2][j].getContents() != Item.UNKNOWN);
					
					
					if(leftOccupied && rightOccupied && right2Occupied){ //Center-left case
						myBoard.getGrid()[i][j-1].setContents(Item.LEFT);
						currentSquare.setContents(Item.CENTER);		
						myBoard.getGrid()[i][j+1].setContents(Item.CENTER);
						myBoard.getGrid()[i][j+2].setContents(Item.RIGHT);
						myBoard.getFleet().addShip(new Battleship(myBoard.getGrid()[i][j-1],
								currentSquare,myBoard.getGrid()[i][j+1],myBoard.getGrid()[i][j+2]));
						continue;
					}
					
					if(left2Occupied && leftOccupied && rightOccupied){ //Center-right case
						myBoard.getGrid()[i][j-2].setContents(Item.LEFT);
						myBoard.getGrid()[i][j-1].setContents(Item.CENTER);
						currentSquare.setContents(Item.CENTER);		
						myBoard.getGrid()[i][j+1].setContents(Item.RIGHT);
						myBoard.getFleet().addShip(new Battleship(myBoard.getGrid()[i][j-2],myBoard.getGrid()[i][j-1],
								currentSquare,myBoard.getGrid()[i][j+1]));
						continue;
					}
					
					if(topOccupied && bottomOccupied && bottom2Occupied){ //Center-top case
						myBoard.getGrid()[i-1][j].setContents(Item.TOP);
						currentSquare.setContents(Item.CENTER);		
						myBoard.getGrid()[i+1][j].setContents(Item.CENTER);
						myBoard.getGrid()[i+2][j].setContents(Item.BOTTOM);
						myBoard.getFleet().addShip(new Battleship(myBoard.getGrid()[i-1][j],
								currentSquare,myBoard.getGrid()[i+1][j],myBoard.getGrid()[i+2][j]));
						continue;
					}
					
					if(top2Occupied && topOccupied && bottomOccupied){ //Center-bottom case
						myBoard.getGrid()[i-2][j].setContents(Item.TOP);
						myBoard.getGrid()[i-1][j].setContents(Item.CENTER);
						currentSquare.setContents(Item.CENTER);		
						myBoard.getGrid()[i+1][j].setContents(Item.BOTTOM);
						myBoard.getFleet().addShip(new Battleship(myBoard.getGrid()[i-2][j],myBoard.getGrid()[i-1][j],
								currentSquare,myBoard.getGrid()[i+1][j]));
						continue;
					}
					// TODO other 4-er cases (OCCUPIED is an end piece)
					
				}
			} 
		}
	}
	
	// TODO Fill-in ship parts (based on what we have remaining)
	//		Again Fill-in water around known ship locations
	
	// TODO If 4-er location unknown, see if we can determine its location
	//		Again Fill-in water around known ship locations	
	
	// TODO If 3-er locations unknown, see if we can determine their location
	//		Again Fill-in water around known ship locations	
	
	//TODO If 2-er locations unknown, see if we can determine their location
	//		Again Fill-in water around known ship locations	
	
	//TODO If 1-er locations unknown, see if we can determine their location
	//		Again Fill-in water around known ship locations	
	
	
	
}
