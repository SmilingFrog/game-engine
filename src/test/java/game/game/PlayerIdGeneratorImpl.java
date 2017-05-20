package game.game;

public class PlayerIdGeneratorImpl implements PlayerIdGenerator {

	long id;
	
	public PlayerIdGeneratorImpl() {
		id = 1;
	}
	
	@Override
	public String generateId() {
		return id++ + "";
	}

}
