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
		prepareBlueprint(0, 2);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "BUILDING");
		Game.getGameIdGenerator().reset();
	}
	
	@Test
	public void whenGettingGameStatusOfTheGameThatIsBuiltGetGameStatus() {
		prepareBlueprint(1, 1);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
		Game.getGameIdGenerator().reset();
	}
	
	public void prepareBlueprint(int numberOfComputerPlayers, int numberOfHumanPlayers) {
		blueprint.setPlayersNumber(numberOfComputerPlayers + numberOfHumanPlayers);
		for(int i = 0; i < numberOfComputerPlayers; i++){
			addPlayerOfType(PlayerType.COMPUTER);			
		}
		for(int i = 0; i < numberOfHumanPlayers; i++){
			addPlayerOfType(PlayerType.HUMAN);
		}		
		PlayerData playerDataToRegister = new PlayerDataImpl();
		playerDataToRegister.setPlayerName("Toz");
		playerDataToRegister.setPlayerType(PlayerType.HUMAN);
		blueprint.setPlayerDataToRegisister(playerDataToRegister);
	}
	
	public void addPlayerOfType(PlayerType playerType) {
		PlayerData playerData = new PlayerDataImpl();
		 playerData.setPlayerType(playerType);
		 blueprint.addPlayer(playerData);
	}

	
}
