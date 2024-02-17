package p03_LockTable_and_CoolDown;

import java.util.concurrent.locks.ReentrantLock;

import p00_CommonA.*;

public class LockTableWCD extends Table implements CoolDownSupport {

	private ReentrantLock lock = new ReentrantLock();
	private volatile boolean cooldownReady = false;
	private volatile boolean kingPutInPos1 = false;

	/* Declare and initialize here the required simple-typed variables */

	protected void gainExclusiveAccess() {
		lock.lock();
	}

	protected void releaseExclusiveAccess() {
		lock.unlock();
	}

	public void putJack(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 3) || (cooldownReady && kingPutInPos1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		this.kingPutInPos1 = false;
	}

	public void putQueen(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (this.ffs == 3 && id % 2 != 0) || (cooldownReady && kingPutInPos1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		this.kingPutInPos1 = false;
	}

	public void putKing(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 3) || (cooldownReady && kingPutInPos1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}

		if (this.ffs == 1) {
			this.kingPutInPos1 = true;
		}
	}

	public void cardPut() {
		this.releaseExclusiveAccess();
	}

	public void startCheck(int id) {
		this.gainExclusiveAccess();
		while (this.ffs < 4) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
	}

	public void endCheck(int id) {
		this.ffs = 0;
		this.releaseExclusiveAccess();
	}

	// --- IMPLEMENTATION of the CoolDownSupport interface

	public void coolDownDone() {
		this.ffs = 0;
		cooldownReady = false;
		kingPutInPos1 = false;
		this.releaseExclusiveAccess();
	}

	@Override
	public void coolDownIsReady() {
		this.gainExclusiveAccess();
		cooldownReady = true;
		this.releaseExclusiveAccess();
	}

	@Override
	public void letCoolDownRun() {
		this.gainExclusiveAccess();
		while (!(cooldownReady && kingPutInPos1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
	}
}