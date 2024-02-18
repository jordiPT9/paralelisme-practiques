package p04_DDD_CandS;

import java.util.concurrent.atomic.*;

import p00_CommonB.*;

public class CSSync implements InterfaceSync {

	private enum State {
		DING_OR_DONG,
		DING_OR_DANG,
		ONLY_DONG,
		ONLY_DANG
	}

	private AtomicBoolean isBoardAvailable = new AtomicBoolean(true);

	private volatile State state = State.DING_OR_DONG;
	private volatile int expectedId = 0;
	private volatile int dings = 0;
	private volatile int dangs = 0;

	private final int numInstances;

	public CSSync(int numInstances) {
		this.numInstances = numInstances;
	}

	public void letMeDing(int id) {
		boolean stop = false;

		while (!stop) {
			while (!this.isBoardAvailable.compareAndSet(true, false)) {
				backOff(); // yield
			}

			if (canDing(id)) {
				stop = true;
			} else {
				this.isBoardAvailable.set(true);
			}
		}

		this.expectedId = id + 1;
	}

	public void dingDone(int id) {
		this.dings++;

		if (this.expectedId >= numInstances) {
			expectedId = 0;
		}

		if (this.dings < 3) {
			this.state = State.DING_OR_DANG;
		} else if (this.dings == 3) {
			this.state = State.ONLY_DANG;
		}
		this.isBoardAvailable.set(true);
	}

	public void letMeDang(int id) {
		boolean stop = false;

		while (!stop) {
			while (!this.isBoardAvailable.compareAndSet(true, false)) {
				backOff(); // yield
			}

			if (canDang(id)) {
				stop = true;
			} else {
				this.isBoardAvailable.set(true);
			}
		}

		this.expectedId = id + 1;
	}

	public void dangDone() {
		this.dangs++;

		if (this.expectedId >= numInstances) {
			this.expectedId = 0;
		}

		if (this.dangs >= this.dings) {
			this.state = State.ONLY_DONG;
		} else if (this.dangs < this.dings) {
			this.state = State.ONLY_DANG;
		}

		this.isBoardAvailable.set(true);
	}

	public void letMeDong(int id) {
		boolean stop = false;

		while (!stop) {
			while (!this.isBoardAvailable.compareAndSet(true, false)) {
				backOff(); // yield
			}
			if (canDong(id)) {
				stop = true;
			} else {
				this.isBoardAvailable.set(true);
			}
		}

		this.expectedId = id + 1;
	}

	public void dongDone() {
		this.dings = 0;
		this.dangs = 0;

		if (this.expectedId >= numInstances) {
			this.expectedId = 0;
		}

		this.state = State.DING_OR_DONG;
		this.isBoardAvailable.set(true);
	}

	// use this method instead of Thread.yield()
	public void backOff() {
		try {
			Thread.sleep(0, 1);
		} catch (InterruptedException ie) {
		}
	}

	private boolean canDing(int id) {
		return (this.expectedId == id) && (this.state == State.DING_OR_DONG || state == State.DING_OR_DANG);
	}

	private boolean canDang(int id) {
		return (this.expectedId == id) && (this.state == State.DING_OR_DANG || this.state == State.ONLY_DANG);
	}

	private boolean canDong(int id) {
		return (this.expectedId == id) && (this.state == State.DING_OR_DONG || state == State.ONLY_DONG);
	}
}