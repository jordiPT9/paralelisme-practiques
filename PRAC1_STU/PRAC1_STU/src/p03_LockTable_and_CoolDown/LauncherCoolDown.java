package p03_LockTable_and_CoolDown;

import java.util.Scanner;

import p00_CommonA.*;

public class LauncherCoolDown {

	public static void main (String [] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int instances = 10;
		
		LockTableWCD table = new LockTableWCD();
		CoolDownThread coolDown = new CoolDownThread(table);
		
		Jack [] jokers = new Jack[instances];
		Queen [] queens = new Queen[instances];
		King [] kings = new King[instances];
		Checker checker = new Checker(0, table);
		
		for (int i=0; i<jokers.length; i++) {
			jokers[i] = new Jack(i, table);
		}
		
		for (int i=0; i<kings.length; i++) {
			kings[i] = new King(i, table);
		}
		
		for (int i=0; i<queens.length; i++) {
			queens[i] = new Queen(i, table);
		}
		
		checker.start();
		
		System.out.println("\nLaunching Part 3. RE-ENTRANT LOCK and COOL DOWN");
		System.out.print("\nPress return to proceed and press return again to stop the frenzy ");
		scanner.nextLine();
		
		for (int i=0; i<jokers.length; i++) {
			jokers[i].start();
		}
		
		for (int i=0; i<kings.length; i++) {
			kings[i].start();
		}
		
		for (int i=0; i<queens.length; i++) {
			queens[i].start();
		}
		
		coolDown.start();
		
		scanner.nextLine();
		
		System.out.println("\n\n------------TERMINATING...-------------------\n");
		
		float[] stats = checker.stats();
		for (int i=0; i<queens.length; i++) queens[i].stop();
		for (int i=0; i<kings.length; i++) kings[i].stop();
		for (int i=0; i<jokers.length; i++) jokers[i].stop();
		
		
		System.out.println("\n");
		for (int i=0; i<stats.length; i++) 
			System.out.println(stats[i]*100+"%");
		System.out.println();
		scanner.close();
		System.exit(0);
	}
}
