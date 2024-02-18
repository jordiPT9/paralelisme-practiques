package p03_LockTable_and_CoolDown;

import java.util.concurrent.locks.ReentrantLock;

import p00_CommonA.*;

public class LockTableWCD extends Table implements CoolDownSupport {

	private ReentrantLock lock = new ReentrantLock();
	private volatile boolean coolDownReady = false;
	private volatile boolean kingInSecondSlot = false;

	protected void gainExclusiveAccess() {
		lock.lock();
	}

	protected void releaseExclusiveAccess() {
		lock.unlock();
	}

	public void putJack(int id) {
		this.gainExclusiveAccess();
		while (handHas1FreeSlot() || coolDownIsReadyAndKingInSecondSlot()) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		this.kingInSecondSlot = false;
	}

	public void putQueen(int id) {
		this.gainExclusiveAccess();
		while (isHandFull() || isLastPositionAndIdEven(id) || coolDownIsReadyAndKingInSecondSlot()) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		this.kingInSecondSlot = false;
	}

	public void putKing(int id) {
		this.gainExclusiveAccess();
		while (handHas1FreeSlot() || coolDownIsReadyAndKingInSecondSlot()) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}

		if (this.ffs == 1) {
			this.kingInSecondSlot = true;
		}
	}

	public void cardPut() {
		this.releaseExclusiveAccess();
	}

	public void startCheck(int id) {
		this.gainExclusiveAccess();
		while (!isHandFull()) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
	}

	public void endCheck(int id) {
		this.ffs = 0;
		this.releaseExclusiveAccess();
	}

	public void coolDownDone() {
		this.ffs = 0;
		coolDownReady = false;
		kingInSecondSlot = false;
		this.releaseExclusiveAccess();
	}

	@Override
	public void coolDownIsReady() {
		this.gainExclusiveAccess();
		coolDownReady = true;
		this.releaseExclusiveAccess();
	}

	@Override
	public void letCoolDownRun() {
		this.gainExclusiveAccess();
		while (!coolDownIsReadyAndKingInSecondSlot()) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
	}

	// created by us
	private boolean isHandFull() {
		return (this.ffs >= 4);
	}

	// created by us
	private boolean isLastPositionAndIdEven(int id) {
		return this.ffs == 3 && id % 2 != 0;
	}

	// created by us
	private boolean handHas1FreeSlot() {
		return this.ffs >= 3;
	}

	// created by us
	private boolean coolDownIsReadyAndKingInSecondSlot() {
		return coolDownReady && kingInSecondSlot;
	}
}