package game.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import game.game.blueprint.GameBlueprint;
import game.game.blueprint.GameBlueprintImpl;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;
import game.game.data.GameData;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;
import game.game.repository.GameRepository;
import game.game.repository.GameRepositoryImpl;
import game.game.responses.GameStatusResult;
import game.game.responses.NewGameResponse;
import game.game.services.PlayerService;
import game.game.services.PlayerServiceImpl;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

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
	
	@Ignore
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
