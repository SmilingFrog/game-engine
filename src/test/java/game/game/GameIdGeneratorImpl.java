package game.game;

public class GameIdGeneratorImpl implements GameIdGenerator {

	long id = 1;
	
	public String generateId() {
		return id++ + "";
	}
	
	public void reset(){
		id = 1;
	}

}
