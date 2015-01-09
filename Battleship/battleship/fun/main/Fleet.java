package battleship.fun.main;

import java.util.ArrayList;

public class Fleet {
	private ArrayList<Ship> ships;
	
	private int battleshipCount;
	private int cruiserCount;
	private int destroyerCount;
	private int submarineCount;
   	
   	public Fleet(){
   		battleshipCount = 0;
   		cruiserCount = 0;
   		destroyerCount = 0;
   		submarineCount = 0;
   	   	
   	   	ships = new ArrayList<Ship>();
   	}
   	
   	public Fleet(Fleet other){
   		this.battleshipCount = other.battleshipCount;
   		this.cruiserCount = other.cruiserCount;
   		this.destroyerCount = other.destroyerCount;
   		this.submarineCount = other.submarineCount;
   		
   		this.ships =  new ArrayList<Ship>(other.ships);
   	}
   	
   	public void addShip(Ship newShip){
   		if(newShip instanceof Battleship){
   			battleshipCount++;
   		}else if(newShip instanceof Cruiser){
   			cruiserCount++;
   		}else if(newShip instanceof Destroyer){
   			destroyerCount++;
   		}else if(newShip instanceof Submarine){
   			submarineCount++;
   		}
   		ships.add(newShip);
   	}

	public int getSubmarineCount() {
		return submarineCount;
	}

	public int getDestroyerCount() {
		return destroyerCount;
	}

	public int getCruiserCount() {
		return cruiserCount;
	}

	public int getBattleshipCount() {
		return battleshipCount;
	}
	
	public boolean isValid(){
	   	if(battleshipCount > 1 || cruiserCount > 2 ||
	   			destroyerCount > 3 || submarineCount > 4){
	   		return false;
	   	}
	   	return true;
	}
	
	public boolean isComplete(){
	   	if(battleshipCount != 1 || cruiserCount != 2 ||
	   			destroyerCount != 3 || submarineCount != 4){
	   		return false;
	   	}
	   	return true;
	}
	
}
