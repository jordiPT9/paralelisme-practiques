package p02_ImpLockTable;

import p00_CommonA.Table;

public class ImplicitLockTable extends Table {

	private boolean tableIsFreeToUse = true;
	private volatile char firstCard;

	protected void gainExclusiveAccess() {
		while (true) {
			synchronized (this) {
				if (tableIsFreeToUse) {
					tableIsFreeToUse = false;
					return; // ahora que hemos bloqueado la tabla salimos de la espera infinita
				}
			}
		}
	}

	protected void releaseExclusiveAccess() {
		synchronized (this) {
			tableIsFreeToUse = true;
		}
	}

	public void putJack(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (ffs == 3 && firstCard!= 'j')) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		
		if(ffs == 0) {
			firstCard = 'j';
		}
	}

	public void putQueen(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (ffs == 3 && firstCard!= 'q')) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		
		if(ffs == 0) {
			firstCard = 'q';
		}
	}

	public void putKing(int id) {
		this.gainExclusiveAccess();
		while ((this.ffs >= 4) || (ffs == 3 && firstCard!= 'k')) {
			this.releaseExclusiveAccess();
			Thread.yield();
			this.gainExclusiveAccess();
		}
		
		if(ffs == 0) {
			firstCard = 'k';
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
		firstCard = ' ';
		this.releaseExclusiveAccess();
	}
}