package game.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void canCreateGameBuilder() {
		GameBuilder builder = Game.getGameBuilder();
		assertNotNull(builder);
	}
	
	@Test
	public void cantCreateGameWithNew(){
		//Game game = new Game();
	}
	
	@Test
	public void canCreateGameWithBuilder(){
		GameBuilder builder = Game.getGameBuilder();
		Game game = builder.build();
		assertNotNull(game);
	}
	

}
