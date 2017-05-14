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

	@Mock
	UserService service;
	@Mock
	GameData mockedGameStatus;
	
	GameData gameData;

	@Before
	public void setup(){
		gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createPlayers(gameData);
		
		doReturn("1").when(mockedGameStatus).getId();
		doReturn("PLAYING").when(mockedGameStatus).getStatus();
		doReturn(mockedGameStatus).when(service).createGame(gameData);
	}
	
	@Test
	public void whenAllPlayersAreSetInGameDataReturnNewGameStatus() {
		
		GameData gameStatus = service.createGame(gameData);
		GameData expectedGameData = prepareExpectedData();
		assertTrue(isequal(gameStatus, expectedGameData));
	}
	
	

	private GameData prepareExpectedData() {
		GameData expectedGameData = new GameDataImpl();
		expectedGameData.setId("1");
		expectedGameData.setStatus("PLAYING");
		return expectedGameData;
	}

	private void createPlayers(GameData gameData) {
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerType(PlayerType.HUMAN);
		playerData.setPlayerName("Tozik");
		gameData.addPlayer(playerData);
		playerData = new PlayerDataImpl();
		playerData.setPlayerType(PlayerType.COMPUTER);
		playerData.setPlayerName("ELECTRO BRAIN");
		playerData.setIntelect("RANDOM");
	}

	private boolean isequal(GameData gameStatus, GameData expectedGameData) {
		return gameStatus.getId().equals(expectedGameData.getId()) &&
				gameStatus.getStatus().equals(expectedGameData.getStatus());
				
	}

}
