package p04_DDD_CandS;

import java.util.concurrent.atomic.*;

import p00_CommonB.*;

public class CSSync implements InterfaceSync {

	private AtomicBoolean canAccess = new AtomicBoolean(true);

	private enum STATE {
		DINGorDONG,
		DINGorDANG,
		ONLYDONG,
		ONLYDANG
	};

	private volatile STATE state = STATE.DINGorDONG;
	private volatile int expectedId = 0;
	private volatile int dings = 0;
	private volatile int dangs = 0;

	private final int NUMINSTANCES;

	public CSSync(int numinstances) {
		this.NUMINSTANCES = numinstances;
	}

	public void letMeDing(int id) {
		boolean canGo = false;
		while (!canGo) {
			while (!canAccess.compareAndSet(true, false)) {
				backOff();
			}
			if (expectedId == id) {
				if (state == STATE.DINGorDONG) {
					canGo = true;
				} else if (state == STATE.DINGorDANG) {
					canGo = true;
				} else {

					canAccess.set(true);
				}
			} else {
				canAccess.set(true);
			}

		}

	}

	public void dingDone(int id) {
		expectedId = id + 1;
		if (expectedId >= NUMINSTANCES) {
			expectedId = 0;
		}
		dings++;
		if (dings < 3) {
			state = STATE.DINGorDANG;
		} else if (dings == 3)
			state = STATE.ONLYDANG;
		canAccess.set(true);

	}

	public void letMeDang(int id) {
		boolean canGo = false;
		while (!canGo) {
			while (!canAccess.compareAndSet(true, false)) {
				backOff();
			}
			if (expectedId == id) {
				if (state == STATE.DINGorDANG) {
					canGo = true;
				} else if (state == STATE.ONLYDANG) {
					canGo = true;
				} else
					canAccess.set(true);
			} else {
				canAccess.set(true);
			}
		}
		expectedId = id + 1;
	}

	public void dangDone() {
		if (expectedId >= NUMINSTANCES) {
			expectedId = 0;
		}
		dangs++;
		if (dangs >= dings) {
			state = STATE.ONLYDONG;
		} else if (dangs < dings) {
			state = STATE.ONLYDANG;
		}
		canAccess.set(true);
	}

	public void letMeDong(int id) {
		boolean canGo = false;
		while (!canGo) {
			while (!canAccess.compareAndSet(true, false)) {
				backOff();
			}
			if (expectedId == id) {
				if (state == STATE.DINGorDONG) {
					canGo = true;
				} else if (state == STATE.ONLYDONG) {
					canGo = true;
				} else {
					canAccess.set(true);
				}
			} else {
				canAccess.set(true);
			}
		}
		expectedId = id + 1;
	}

	public void dongDone() {
		dings = 0;
		dangs = 0;
		if (expectedId >= NUMINSTANCES) {
			expectedId = 0;
		}
		this.state = STATE.DINGorDONG;
		canAccess.set(true);
	}

	// use this method instead of Thread.yield()
	public void backOff() {
		try {
			Thread.sleep(0, 1);
		} catch (InterruptedException ie) {
		}
	}

}