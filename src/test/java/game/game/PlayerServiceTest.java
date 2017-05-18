package game.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerServiceTest {
	
	UserService userService;
	
	GameData gameData;
	
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
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
	}
	
	@Test
	public void whenGettingGameStatusOfTheGameThatIsNotBuiltYetGetGameBuilderStatus() {
		createNotAllPlayers(gameData);
		NewGameResponse response = userService.createGame(gameData);
		String id = response.gameId;
		GameData gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.getId(), "1");
		assertEquals(gameStatus.getStatus(), "BUILDING");
	}
	
	@Test
	public void whenGettingGameStatusOfTheGameThatIsBuiltGetGameStatus() {
		createAllPlayers(gameData);
		NewGameResponse response = userService.createGame(gameData);
		String id = response.gameId;
		GameData gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.getId(), "1");
		assertEquals(gameStatus.getStatus(), "PLAYING");
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
