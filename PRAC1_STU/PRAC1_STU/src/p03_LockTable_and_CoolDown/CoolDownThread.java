package p03_LockTable_and_CoolDown;



public class CoolDownThread extends Thread {

	private CoolDownSupport CDSupport;
	
	public CoolDownThread (CoolDownSupport support) {
		CDSupport = support;
	}
	
	public void run () {
		
		while (true) {
			try {Thread.sleep(1000);} catch (InterruptedException ie) {}
			
			/* COMPLETE */
			// aquests dos els treurem
			CDSupport.coolDownIsReady();
			CDSupport.letCoolDownRun();
			
			System.out.println("\n");
			System.out.print("\tCOOLING DOWN: ");
			for (int i=3; i>=0; i--) {
				System.out.print(i+" ");
				try {Thread.sleep(350);} catch(InterruptedException ie) {}
			}
			System.out.println("\n");
			
			// aquest el deixarem
			CDSupport.coolDownDone();
		}
	}
	
}
