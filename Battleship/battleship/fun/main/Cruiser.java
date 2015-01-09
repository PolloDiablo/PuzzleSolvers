package battleship.fun.main;

public class Cruiser extends Ship{
	private static final int size=3;	
	
	public Cruiser(Square p1, Square p2, Square p3) throws Exception{
		super(size);
		parts[0] = p1;
		parts[1] = p2;
		parts[2] = p3;
		assertOwnership();
	}
}
