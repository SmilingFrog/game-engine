package game.game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	UserService userService;
	
	GameData gameData;
	
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
	}
	
	@Test
	public void whenAllPlayersAreSetInGameDataReturnNewGameStatusPlaying() {
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createAllPlayers(gameData);
		NewGameResponse response = userService.createGame(gameData);
		NewGameResponse expectedGameResponse = prepareExpectedResponse();
		assertTrue(isequal(response, expectedGameResponse));
		
	}
	
	@Test
	public void whenNotAllPlayersAreSetInGameDataReturnNewGameStatusBuilding() {
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createNotAllPlayers(gameData);
		NewGameResponse response = userService.createGame(gameData);
		NewGameResponse expectedResponse = prepareExpectedResponse();
		expectedResponse.gameData.setStatus("BUILDING");
		assertTrue(isequal(response, expectedResponse));
	}
	
	@Test
	public void whenAddingAPlayerSoThatAllPlayersAreRegisteredReturnGameStatusPlaying() {
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createNotAllPlayers(gameData);
		NewGameResponse response = userService.createGame(gameData);
		NewGameResponse expectedResponse = prepareExpectedResponse();
		expectedResponse.gameData.setStatus("BUILDING");
		assertTrue(isequal(response, expectedResponse));
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		GameData gameStatus = userService.registerNewPlayer(id, playerData);
		assertEquals(gameStatus.getId(), "1");
		assertEquals(gameStatus.getStatus(), "PLAYING");
		gameStatus = playerService.getGameStatus(id);
		assertEquals(gameStatus.getId(), "1");
		assertEquals(gameStatus.getStatus(), "PLAYING");
	}
	
	@Test(expected = RuntimeException.class)
	public void whenTryingToRegisterNewUserToTheGameThatHasAlreadyStartedThrowException(){
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createAllPlayers(gameData);
		NewGameResponse response = userService.createGame(gameData);
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		GameData gameStatus;
		gameStatus = userService.registerNewPlayer(id, playerData);
	}
	

	private NewGameResponse prepareExpectedResponse() {
		NewGameResponse expectedResponse = new NewGameResponse();
		expectedResponse.gameId = "1";
		expectedResponse.gameData = new GameDataImpl();
		gameData.setStatus("PLAYING");
		return expectedResponse;
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

	private boolean isequal(NewGameResponse response, NewGameResponse expectedResponse) {
		return response.gameId.equals(expectedResponse.gameId) &&
				response.gameData.getStatus().equals(expectedResponse.gameData.getStatus());
				
	}

}
