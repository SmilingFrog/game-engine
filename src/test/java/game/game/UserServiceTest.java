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

	UserService service;
	
	GameData gameData;
	
	GameRepository gameRepository;
	
	GameBuilderRepository gameBuilderRepository;

	@Before
	public void setup(){
		gameRepository = new GameRepositoryImpl();
		gameBuilderRepository = new GameBuilderRepositoryImpl();
		service = new UserServiceImpl();
		service.setAcactiveGamesRepository(gameRepository);
		service.setActiveGameBuilderRepository(gameBuilderRepository);
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createAllPlayers(gameData);
	}
	
	@Test
	public void whenAllPlayersAreSetInGameDataReturnNewGameStatusPlaying() {
		
		GameData gameStatus = service.createGame(gameData);
		GameData expectedGameData = prepareExpectedData();
		assertTrue(isequal(gameStatus, expectedGameData));
		
	}
	
	@Test
	public void whenNotAllPlayersAreSetInGameDataReturnNewGameStatusBuilding() {
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createNotAllPlayers(gameData);
		GameData gameStatus = service.createGame(gameData);
		GameData expectedGameData = prepareExpectedData();
		expectedGameData.setStatus("BUILDING");
		assertTrue(isequal(gameStatus, expectedGameData));
		
	}
	

	private GameData prepareExpectedData() {
		GameData expectedGameData = new GameDataImpl();
		expectedGameData.setId("1");
		expectedGameData.setStatus("PLAYING");
		return expectedGameData;
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

	private boolean isequal(GameData gameStatus, GameData expectedGameData) {
		return gameStatus.getId().equals(expectedGameData.getId()) &&
				gameStatus.getStatus().equals(expectedGameData.getStatus());
				
	}

}
