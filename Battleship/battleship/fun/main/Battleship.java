package battleship.fun.main;

public class Battleship extends Ship{

	private static final int size=4;	
	
	public Battleship(Square p1, Square p2, Square p3, Square p4) throws Exception{
		super(size);
		parts[0] = p1;
		parts[1] = p2;
		parts[2] = p3;
		parts[3] = p4;
		assertOwnership();
	}
}
