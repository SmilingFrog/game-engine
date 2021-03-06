package game.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import game.game.builder.GameBuilder;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;

public class GameTest {

	GameBuilder builder;
	
	@Before
	public void setup(){
		builder = Game.getGameBuilder();
	}
	
	@Test
	public void canCreateGameBuilder() {
		assertNotNull(builder);
	}
	
	@Ignore
	@Test
	public void canCreateGameWithBuilder(){
		Game game = builder.build();
		assertNotNull(game);
	}
	
	@Test
	public void gameBuilderHasId(){
		assertNotEquals("0", builder.getId());
		assertNotNull(builder.getId());
	}
	
	@Test
	public void saveBuilderInRepository(){
		GameBuilderRepository repo = new GameBuilderRepositoryImpl();
		assertEquals(0, repo.size());
		repo.add(builder);
		assertEquals(1, repo.size());
		assertEquals(repo.getById(builder.getId()), builder);
	}
	
}
