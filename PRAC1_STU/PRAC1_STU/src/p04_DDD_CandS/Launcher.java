package p04_DDD_CandS;

import java.util.Scanner;

import p00_CommonB.*;

public class Launcher {

		public static void main (String [] args) throws Exception {
			int INSTANCES = 10;
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nLaunching CandS solution with n="+INSTANCES+"\n");
			System.out.print("Press RETURN to start. Press return again to stop the frenzy");
			scanner.nextLine();
			
			InterfaceSync synchro = new CSSync(INSTANCES);
			CuteKerberos.NUMINSTANCES = INSTANCES;
			
			Ding [] dings = new Ding[INSTANCES];
			Dang [] dangs = new Dang[INSTANCES];
			Dong [] dongs = new Dong[INSTANCES];
			
			for (int i=0; i<dings.length; i++) {
				dings[i] = new Ding(i, synchro);
			}
			for (int i=0; i<dangs.length; i++) {
				dangs[i] = new Dang(i, synchro);
			}
			for (int i=0; i<dongs.length; i++) {
				dongs[i] = new Dong(i, synchro);
			}
			
			for (int i=0; i<dings.length; i++) {
				dings[i].start();
			}
			for (int i=0; i<dongs.length; i++) {
				dongs[i].start();
			}
			for (int i=0; i<dangs.length; i++) {
				dangs[i].start();
			}
			
			
			scanner.nextLine();
			
			System.out.println("\n\nCandS TERMINATING...\n");
			
			System.exit(0);
		}
}
