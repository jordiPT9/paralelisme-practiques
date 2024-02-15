package p04_DDD_CandS;

import java.util.concurrent.atomic.*;

import p00_CommonB.*;

public class CSSync implements InterfaceSync {
	
	/* Declare and initialize your single instance of AtomicBoolean */
	
	/* Declare and initialize all the simple-typed variables required */
	
	private final int NUMINSTANCES; 
	
	public CSSync (int numinstances) {
		this.NUMINSTANCES = numinstances;
	}
	
	public void letMeDing(int id) {
		/* COMPLETE */
	}

	public void dingDone(int id) {
		/* COMPLETE */
	}


	public void letMeDang(int id) {
		/* COMPLETE */
	}


	public void dangDone() {
		/* COMPLETE */
	}

	
	public void letMeDong(int id) {
		/* COMPLETE */
	}

	
	public void dongDone() {
		/* COMPLETE */
	}

	// use this method instead of Thread.yield()
	public void backOff () {
		try {Thread.sleep(0,1);} catch (InterruptedException ie) {}
	}
	
}
