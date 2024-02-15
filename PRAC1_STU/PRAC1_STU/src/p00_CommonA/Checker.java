package p00_CommonA;

public class Checker extends Thread{

	private int id;
	private Table machine;
	private volatile int trios, pokers, nothings;
	
	public Checker (int id, Table machine) {
		this.id = id;
		this.machine = machine;
		trios = 0;
		pokers = 0;
		nothings = 0;
	}
	
	public void run () {
		StringBuilder [] slots;
		int j, q, k;
		while (true) {
			machine.startCheck(id);
			
			slots = machine.getContents();
			j=0; q=0; k=0;
			for (int i=0; i<slots.length; i++) {
				// System.out.print(slots[i]+"  ");
				switch (slots[i].toString().substring(0, 5)) {
					case "JACKK": j++; break;
					case "QUEEN": q++; break;
					case "KKING": k++; break;
					default: 
						System.err.println("\nSYNC ERROR");
						System.exit(0);
						break;
				}
			}
			System.out.print("\t=> ");
			if (j==4) {System.out.print("Poker of JACKs"); pokers++;}
			else if (j==3) {System.out.print("Trio of JACKs"); trios++;}
			else if (q==4) {System.out.print("Poker of QUEENs"); pokers++;}
			else if (q==3) {System.out.print("Trio of QUEENs"); trios++;}
			else if (k==4) {System.out.print("Poker of KINGs"); pokers++;}
			else if (k==3) {System.out.print("Trio of KINGs"); trios++;}
			else {System.out.print("NOTHING");nothings++;}
		
			System.out.println();
			
			machine.endCheck(k);
		}
	}
	
	public float[] stats () {
		float [] result = new float[3];
		float total = pokers+trios+nothings;
		result[0] = pokers/total;
		result[1] = trios/total;
		result[2] = nothings/total;
		return result;
	}
	
}
