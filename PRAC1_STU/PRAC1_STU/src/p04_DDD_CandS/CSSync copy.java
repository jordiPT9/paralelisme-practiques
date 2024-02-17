package p04_DDD_CandS;

import java.util.concurrent.atomic.*;

import p00_CommonB.*;

public class CSSync implements InterfaceSync {

	private AtomicBoolean isBoardBusy = new AtomicBoolean(true);

	private volatile boolean dingWritten = false;
	private int dingCount = 0;
	private volatile boolean dangWritten = false;
	private int dangCount = 0;
	private volatile boolean dongWritten = false;
	private int dongCount = 0;

	private int currentId = 0;

	private final int NUMINSTANCES;

	public CSSync(int numinstances) {
		this.NUMINSTANCES = numinstances;
	}

	public void letMeDing(int id) {
		boolean continueLooping = true;

		// bucle que comprueba infinitamente si podemos acceder a la tabla y podemos
		// hacer DONG
		while (continueLooping) {
			// intentamos pedir acceso a la tabla
			while (!isBoardBusy.compareAndSet(false, true)) {
				backOff();
			}

			// si el id del thread que quiere hacer el ding es el que toca (es consecutivo)
			if (itsExpectedId(id)) {
				// si puede hacer ding
				if (this.canDing()) {
					// salimos del bucle para escribir el ding (por tanto la tabla sigue bloqueada)
					continueLooping = false;
				} else {
					// liberamos la tabla ya que ya hemos comprovado lo que teniamos que comprovar
					isBoardBusy.set(false);
				}
			} else {
				// salimos del bucle ya que es un thread que tiene un id que no le toca escribir
				continueLooping = false;
			}
		}
	}

	private boolean itsExpectedId(int id) {

		return id == (this.currentId + 1);
	}

	public void dingDone(int id) {
		this.dingWritten = true;
		this.dingCount++;
		if (currentId == this.NUMINSTANCES) {
			this.expected = currentId++;
		} else {
			currentId = 0;
		}
		this.isBoardBusy.set(false);
	}

	public void letMeDang(int id) {
		boolean continueLooping = true;

		// bucle que comprueba infinitamente si podemos acceder a la tabla y podemos
		// hacer DONG
		while (continueLooping) {
			// intentamos pedir acceso a la tabla
			while (!isBoardBusy.compareAndSet(false, true)) {
				backOff();
			}

			// si el id del thread que quiere hacer el ding es el que toca (es consecutivo)
			if (itsExpectedId(id)) {
				// si puede hacer dang
				if (this.canDang()) {
					// salimos del bucle para escribir el ding (por tanto la tabla sigue bloqueada)
					continueLooping = false;
				} else {
					// liberamos la tabla ya que ya hemos comprovado lo que teniamos que comprovar
					isBoardBusy.set(false);
				}
			} else {
				// salimos del bucle ya que es un thread que tiene un id que no le toca escribir
				continueLooping = false;
			}
		}
	}

	public void dangDone() {
		this.dangWritten = true;
		this.dangCount++;
		this.isBoardBusy.set(false);
	}

	public void letMeDong(int id) {
		boolean continueLooping = true;

		// bucle que comprueba infinitamente si podemos acceder a la tabla y podemos
		// hacer DONG
		while (continueLooping) {
			// intentamos pedir acceso a la tabla
			while (!isBoardBusy.compareAndSet(false, true)) {
				backOff();
			}

			// si el id del thread que quiere hacer el ding es el que toca (es consecutivo)
			if (itsExpectedId(id)) {
				// si puede hacer ding
				if (this.canDong()) {
					// salimos del bucle para escribir el ding (por tanto la tabla sigue bloqueada)
					continueLooping = false;
				} else {
					// liberamos la tabla ya que ya hemos comprovado lo que teniamos que comprovar
					isBoardBusy.set(false);
				}
			} else {
				// salimos del bucle ya que es un thread que tiene un id que no le toca escribir
				continueLooping = false;
			}
		}
	}

	public void dongDone() {
		this.dongWritten = true;
		this.dongCount++;
		this.isBoardBusy.set(false);
	}

	private boolean canDing() {
		return (this.dongWritten || this.dingWritten && this.dingCount < 3);
	}

	private boolean canDang() {
		return (this.dingWritten || (this.dangWritten && (this.dingCount == this.dangCount + 1)));
	}

	private boolean canDong() {
		return (this.dingWritten || this.dangWritten);
	}

	// use this method instead of Thread.yield()
	public void backOff() {
		try {
			Thread.sleep(0, 1);
		} catch (InterruptedException ie) {
		}
	}
}