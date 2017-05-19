package game.game;

public class IdGeneratorImpl implements IdGenerator {

	long id = 1;
	
	public String generateId() {
		return id++ + "";
	}
	
	public void reset(){
		id = 1;
	}

}
