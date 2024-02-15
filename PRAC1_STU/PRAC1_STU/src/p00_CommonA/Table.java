package p00_CommonA;

public abstract class Table {
	
	private StringBuilder [] contents;
	protected final int NUM_SLOTS = 4;
	protected volatile int ffs; // first free slot
	
	public Table () {
		this.contents = new StringBuilder[NUM_SLOTS];
		this.ffs=0;
	}
	
	public void putCard (String card) {
		// places the card in the first free slot
		// ...
		Thread.yield();
		contents[ffs] = new StringBuilder();
		Thread.yield();
		for (int i=0; i<card.length(); i++) {
			contents[ffs].append(card.charAt(i));
			System.out.print(card.charAt(i));
			Thread.yield();
		}
		System.out.print(" ");
		ffs++;
	}
	
	public StringBuilder [] getContents () {return this.contents;}
	
	protected abstract void gainExclusiveAccess ();
	protected abstract void releaseExclusiveAccess ();
	
	public abstract void putJack (int id);
	public abstract void putQueen (int id);
	public abstract void putKing (int id);
	public abstract void cardPut (); 
	
	public abstract void startCheck (int id);
	public abstract void endCheck(int id);
}
