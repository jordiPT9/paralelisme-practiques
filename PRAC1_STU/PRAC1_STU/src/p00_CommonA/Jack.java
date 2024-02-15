package p00_CommonA;

public class Jack extends Thread {
	private int id;
	private Table machine;
	
	public Jack (int id, Table machine) {
		this.id = id;
		this.machine = machine;
	}
	
	public void run () {
		while (true) {
			machine.putJack(id);
			machine.putCard("JACKK("+id+")");
			machine.cardPut();
			try {Thread.sleep(1);} catch (InterruptedException ioex) {}
		}
	}
}
