package battleship.fun.main;

public class Submarine extends Ship{
	private static final int size=1;	
	
	public Submarine(Square p1) throws Exception{
		super(size);
		parts[0] = p1;
		assertOwnership();
	}
}
