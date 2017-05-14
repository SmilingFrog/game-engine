package game.game;

public class IdGeneratorImpl {

	static String[] ids = {"1","2","3","4","5"};
	static int index;
	
	public static String generateId() {
		return ids[(index++)%5];
	}
	
	public static void reset(){
		index = 0;
	}

}
