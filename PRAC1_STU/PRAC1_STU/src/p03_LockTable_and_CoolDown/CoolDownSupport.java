package p03_LockTable_and_CoolDown;

/* this interface defines the methods required to add
 * a well-synchronized cool-down thread to the system.
 */

public interface CoolDownSupport {

	public void coolDownDone();

	public void coolDownIsReady();

	public void letCoolDownRun();
}
