package p00_CommonB;



public class Dong extends Thread {
	
	private int id;
	private InterfaceSync synchro;
	
	public Dong (int id, InterfaceSync synchro) {
		this.synchro = synchro;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			synchro.letMeDong(id);
			//System.out.println("DOOONG("+id+")");
			synchro.writeString("DOOONG("+id+")\n");
			synchro.dongDone();
		}
	}

}
