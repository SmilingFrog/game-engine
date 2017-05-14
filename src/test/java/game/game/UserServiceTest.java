package game.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserServiceTest {

	@Test
	public void whenAllPlayersAreSetInGameDataReturnNewGameStatus() {
		UserService service = new UserServiceImpl();
		GameData gameData = new GameDataImpl();
		gameData.setPlayersNumber(2);
		createPlayers(gameData);
		
		GameData gameStatus = service.createGame(gameData);
		GameData expectedGameData = new GameDataImpl();
		expectedGameData.setId("1");
		expectedGameData.setStatus("PLAYING");
		
		assertTrue(isequal(gameStatus, expectedGameData));
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
