package game.game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import game.game.blueprint.GameBlueprint;
import game.game.blueprint.GameBlueprintImpl;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;
import game.game.data.GameDataImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	UserService userService;
	
	GameBlueprint blueprint;
	
	GameRepository gameRepository;
	
	GameBuilderRepository gameBuilderRepository;
	
	PlayerService playerService;

	@Before
	public void setup(){
		setDependencies();
	}

	public void setDependencies() {
		gameRepository = new GameRepositoryImpl();
		gameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(gameRepository);
		userService.setActiveGameBuilderRepository(gameBuilderRepository);
		playerService = new PlayerServiceImpl();
		playerService.setAcactiveGamesRepository(gameRepository);
		playerService.setActiveGameBuilderRepository(gameBuilderRepository);
		blueprint = new GameBlueprintImpl();
	}
	
	@Test
	public void whenNumberOfPlayersIs2AndPlayersAreHumanAndComputer() {
		prepareBlueprint(1,1);
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedGameResponse = prepareExpectedResponse();
		System.out.println(response);
		assertTrue(isequal(response, expectedGameResponse));
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
	
	@Test
	public void whenNotAllPlayersAreSetInGameDataReturnNewGameStatusBuilding() {
		int computerPlayers = 1;
		int humanPlayers = 2;
		prepareBlueprint(computerPlayers, humanPlayers);
		Game.getGameIdGenerator().reset();
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedResponse = prepareExpectedResponse();
		expectedResponse.gameData.setStatus("BUILDING");
		System.out.println(response);
		System.out.println(response.gameId + "  " + expectedResponse.gameId);
		System.out.println(response.gameData.getStatus() + " " + expectedResponse.gameData.getStatus());
		assertTrue(isequal(response, expectedResponse));
		Game.getGameIdGenerator().reset();
	}
	
	@Test
	public void whenAddingAPlayerSoThatAllPlayersAreRegisteredReturnGameStatusPlaying() {
		int computerPlayers = 1;
		int humanPlayers = 2;
		prepareBlueprint(computerPlayers, humanPlayers);
		Game.getGameIdGenerator().reset();
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedResponse = prepareExpectedResponse();
		expectedResponse.gameData.setStatus("BUILDING");
		System.out.println(response);
		System.out.println(response.gameId + "  " + expectedResponse.gameId);
		System.out.println(response.gameData.getStatus() + " " + expectedResponse.gameData.getStatus());
		assertTrue(isequal(response, expectedResponse));
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		NewPlayerRegisteredResult gameStatus = userService.registerNewPlayer(id, playerData);
		System.out.println(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
		GameStatusResult gameStatusResult = playerService.getGameStatus(id);
		System.out.println(gameStatusResult);
		assertEquals(gameStatusResult.gameId, "1");
		assertEquals(gameStatusResult.gameData.getStatus(), "PLAYING");
		Game.getGameIdGenerator().reset();
	}
	
	@Test(expected = RuntimeException.class)
	public void whenTryingToRegisterNewUserToTheGameThatHasAlreadyStartedThrowException(){
		int computerPlayers = 1;
		int humanPlayers = 1;
		prepareBlueprint(computerPlayers, humanPlayers);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		NewPlayerRegisteredResult gameStatus;
		gameStatus = userService.registerNewPlayer(id, playerData);
		Game.getGameIdGenerator().reset();
	}
	

	private NewGameResponse prepareExpectedResponse() {
		NewGameResponse expectedResponse = new NewGameResponse();
		expectedResponse.gameId = "1";
		expectedResponse.gameData = new GameDataImpl();
		expectedResponse.gameData.setStatus("PLAYING");
		PlayerData playerData = new PlayerDataImpl();
		return expectedResponse;
	}
	
	private boolean isequal(NewGameResponse response, NewGameResponse expectedResponse) {
		return response.gameId.equals(expectedResponse.gameId) &&
				response.gameData.getStatus().equals(expectedResponse.gameData.getStatus());
				
	}

}
