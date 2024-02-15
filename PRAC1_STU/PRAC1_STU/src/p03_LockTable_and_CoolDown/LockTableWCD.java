package p03_LockTable_and_CoolDown;

import java.util.concurrent.locks.ReentrantLock;

import p00_CommonA.*;

public class LockTableWCD extends Table implements CoolDownSupport{
	
	private ReentrantLock lock = new ReentrantLock();
	
	/* Declare and initialize here the required simple-typed variables */
	
	protected void gainExclusiveAccess() {
		/* COMPLETE */
	}


	protected void releaseExclusiveAccess() {
		/* COMPLETE */
	}


	public void putJack(int id) {
		/* COMPLETE */
	}

	
	public void putQueen(int id) {
		/* COMPLETE */
	}

	
	public void putKing(int id) {
		/* COMPLETE */
	}


	public void cardPut() {
		/* COMPLETE */
	}

	
	public void startCheck(int id) {
		/* COMPLETE */
	}

	
	public void endCheck(int id) {
		/* COMPLETE */
	}


	
	// --- IMPLEMENTATION of the CoolDownSupport interface
	
	
	public void coolDownDone() {
		/* COMPLETE */
	}

}
