package p00_CommonB;


public class Ding extends Thread{
	
	private int id;
	private InterfaceSync synchro;
	
	public Ding (int id, InterfaceSync synchro) {
		this.synchro = synchro;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			synchro.letMeDing(id);
			//System.out.print("DING("+id+")-");
			synchro.writeString("DING("+id+")-");
			synchro.dingDone(id);
		}
	}
	
}