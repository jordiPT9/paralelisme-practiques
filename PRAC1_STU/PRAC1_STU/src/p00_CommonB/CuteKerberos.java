package p00_CommonB;

import java.util.Random;

public class CuteKerberos {
	
	public static volatile int NUMINSTANCES; // set it from outside
	
	private static enum STATE {DINGorDONG, DINGorDANG, ONLYDONG, ONLYDANG};
	private static enum CONTENTS {ding, dang, dong};
	private static volatile STATE state = STATE.DINGorDONG;
	private static volatile int currentId;
	private static volatile int expectedId=0;
	private static volatile int dings = 0;
	private static volatile int dangs = 0;
	private static volatile CONTENTS currentContents;
	
	
	private static void parse (String s) {
		extractContents(s);
		
		if (currentId!=expectedId) {
			System.err.println("ERROR: incorrect id. Expecting "+expectedId);
			System.exit(0);
		}
		expectedId = (expectedId+1)% NUMINSTANCES;
		
		switch (state) {
			case DINGorDONG: 
				if (currentContents==CONTENTS.ding) {
					dings++;
					state = STATE.DINGorDANG;
				}
				else if (currentContents==CONTENTS.dong) {
					state = STATE.DINGorDONG;
				}
				else {
					System.err.println("ERROR: All lines must start with a DING or a DONG");
					System.exit(0);
				}
				break;
				
			case DINGorDANG: 
				if (currentContents==CONTENTS.ding) {
					dings++;
					if (dings<3) state=STATE.DINGorDANG;
					else state=STATE.ONLYDANG;
				}
				else if (currentContents==CONTENTS.dang) {
					dangs++;
					if (dings==dangs) state=STATE.ONLYDONG;
					else state = STATE.ONLYDANG;
				}
				else {
					System.err.println("misplaced DONG");
					System.exit(0);
				}
				break;
				
			case ONLYDANG: 
				if (currentContents==CONTENTS.dang) {
					dangs++;
					if (dings==dangs) state=STATE.ONLYDONG;
					else state=STATE.ONLYDANG;
				}
				else {
					System.err.println("Incorrect element. Expecting a DANG");
					System.exit(0);
				}
				break;
				
			case ONLYDONG:
				if (currentContents==CONTENTS.dong) {
					dings=0;
					dangs=0;
					state = STATE.DINGorDONG;
				}
				else {
					System.err.println("Incorrect element. Expecting a DONG");
					System.exit(0);
				}
				break;
		}
	}
	
	private static void extractContents (String s) {
		if (s.startsWith("DING")) currentContents = CONTENTS.ding;
		else if (s.startsWith("daang(")) currentContents = CONTENTS.dang;
		else if (s.startsWith("DOOONG(")) currentContents= CONTENTS.dong;
		else {
			System.err.println("ERROR: string "+s+" is WEIRD!!! Bad synchro...");
			System.exit(0);
		}
		
		int i = s.indexOf('(');
		int j = s.indexOf(')');
		try {
			currentId = Integer.parseInt(s.substring(i+1, j));
		}
		catch (Exception e) {
			System.err.println("ERROR: string "+s+" is WEIRD!!! Bad synchro...");
			System.exit(0);
		}
		
	}
	
	private static StringBuffer sbuffer;
	private static Random alea = new Random();
	
	public static void writeString (String string ) {
		int n;
		sbuffer = new StringBuffer();
		for (int i=0; i<string.length(); i++) {
			System.out.print(string.charAt(i));
			n = alea.nextInt(100);
			if (n>=90) try {Thread.sleep(1);} catch(Exception ex) {}
			else if (n>=50) Thread.yield();
			sbuffer.append(string.charAt(i));
		}
		parse(sbuffer.toString());
	}
	
}
