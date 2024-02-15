package p01_MutexTable;

import java.util.concurrent.Semaphore;

import p00_CommonA.Table;

public class MutexTable extends Table {

	private Semaphore mutex;

	public MutexTable() {
		super();
		this.mutex = new Semaphore(1);
	}

	protected void gainExclusiveAccess() {
		try {
			this.mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void releaseExclusiveAccess() {
		this.mutex.release();
	}

	public void putJack(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (this.ffs == 0 && id != 0) || (this.ffs == 3 && id != 1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
	}

	public void putQueen(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (this.ffs == 0 && id != 0) || (this.ffs == 3 && id != 1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
	}

	public void putKing(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (this.ffs == 0 && id != 0) || (this.ffs == 3 && id != 1)) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
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
}
