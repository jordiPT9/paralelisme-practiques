package p00_CommonB;


public class Dang extends Thread {
	
	private int id;
	private InterfaceSync synchro;
	
	public Dang (int id, InterfaceSync synchro) {
		this.synchro = synchro;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			synchro.letMeDang(id);
			synchro.writeString("daang("+id+")-");
			synchro.dangDone();
		}
	}
	
}