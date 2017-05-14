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
	public void canCreateGameWithBuilder(){
		GameBuilder builder = Game.getGameBuilder();
		Game game = builder.build();
		assertNotNull(game);
	}
	
	@Test
	public void gameBuilderHasId(){
		GameBuilder builder = Game.getGameBuilder();
		assertNotEquals("0", builder.getId());
		assertNotNull(builder.getId());
	}
	
	@Test
	public void saveBuilderInRepository(){
		GameBuilder builder = Game.getGameBuilder();
		GameBuilderRepository repo = new GameBuilderRepositoryImpl();
		assertEquals(0, repo.size());
		repo.save(builder);
		assertEquals(1, repo.size());
		assertEquals(repo.getById(builder.getId()), builder);
	}
	
	
	
	

}
