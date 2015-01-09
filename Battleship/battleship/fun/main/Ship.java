package battleship.fun.main;

public class Ship {
	protected Square[] parts;
	
	protected Ship(int size){
		setParts(new Square[size]);
	}
	
	// Asserts ownership over all parts
	// Should be called immediately after constructor
	protected void assertOwnership() throws Exception{
		for(int i = 0 ; i <parts.length ; i++){
			parts[i].setOwnerShip(this);
		}
	}

	public Square[] getParts() {
		return parts;
	}

	public void setParts(Square[] parts) {
		this.parts = parts;
	}
}
