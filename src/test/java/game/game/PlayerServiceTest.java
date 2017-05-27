package game.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerServiceTest {
	
	UserService userService;
	
	GameBlueprint blueprint;
	
	GameRepository gameRepository;
	
	GameBuilderRepository gameBuilderRepository;
	
	PlayerService playerService;
	
	@Before
	public void setup(){
		gameRepository = new GameRepositoryImpl();
		gameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(gameRepository);
		userService.setActiveGameBuilderRepository(gameBuilderRepository);
		
		playerService = new PlayerServiceImpl();
		playerService.setAcactiveGamesRepository(gameRepository);
		playerService.setActiveGameBuilderRepository(gameBuilderRepository);
		blueprint = new GameBlueprintImpl();
		blueprint.setPlayersNumber(2);
	}
	
	@Test
	public void whenGettingGameStatusOfTheGameThatIsNotBuiltYetGetGameBuilderStatus() {
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "BUILDING");
	}
	
	@Test
	public void whenGettingGameStatusOfTheGameThatIsBuiltGetGameStatus() {
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
	}
	
	private void createAllPlayers(GameData gameData) {
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerType(PlayerType.HUMAN);
		playerData.setPlayerName("Tozik");
		gameData.addPlayer(playerData);
		playerData = new PlayerDataImpl();
		playerData.setPlayerType(PlayerType.COMPUTER);
		playerData.setPlayerName("ELECTRO BRAIN");
		playerData.setIntelect("RANDOM");
		gameData.addPlayer(playerData);
	}
	
	private void createNotAllPlayers(GameData gameData) {
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerType(PlayerType.HUMAN);
		playerData.setPlayerName("Tozik");
		gameData.addPlayer(playerData);
		
	}

}
