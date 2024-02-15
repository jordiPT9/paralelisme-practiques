package p00_CommonA;

public class Queen extends Thread{

	private int id;
	private Table machine;
	
	public Queen (int id, Table machine) {
		this.id = id;
		this.machine = machine;
	}
	
	public void run () {
		while (true) {
			machine.putQueen(id);
			machine.putCard("QUEEN("+id+")");
			machine.cardPut();
			try {Thread.sleep(1);} catch (InterruptedException ioex) {}
		}
	}
	
}
