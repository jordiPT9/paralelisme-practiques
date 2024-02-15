package p00_CommonA;

public class King extends Thread {

	private int id;
	private Table machine;
	
	public King (int id, Table machine) {
		this.id = id;
		this.machine = machine;
	}
	
	public void run () {
		while (true) {
			machine.putKing(id);
			machine.putCard("KKING("+id+")");
			machine.cardPut();
			try {Thread.sleep(1);} catch (InterruptedException ioex) {}
		}
	}
	
}
