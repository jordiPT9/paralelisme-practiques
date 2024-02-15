package p00_CommonB;

public interface InterfaceSync {
	
	public static final int N = 5;
	
	public void letMeDing (int id);
	public void dingDone (int id);
	
	public void letMeDang (int id);	
	public void dangDone ();
	
	public void letMeDong (int id);
	public void dongDone ();
	
	public default void writeString (String string ) {
		CuteKerberos.writeString(string);
	}
		
}
