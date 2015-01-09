package battleship.fun.main;

public class Destroyer extends Ship{
	private static final int size=2;	
	
	public Destroyer(Square p1, Square p2) throws Exception{
		super(size);
		parts[0] = p1;
		parts[1] = p2;
		assertOwnership();
	}
}
