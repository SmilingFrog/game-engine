package game.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameDataTest {

	@Test
	public void canCreatePlayers() {
		GameData gameData = new GameDataImpl();
		int playersNumber = 2;
		gameData.setPlayersNumber(playersNumber);
		PlayerData playerData = new PlayerDataImpl();
		createPlayers(gameData, playerData);
		assertNotNull(gameData.getPlayerDataList());
		assertEquals(2, gameData.getPlayerDataList().size());
	}

	private void createPlayers(GameData gameData, PlayerData playerData) {
		String playerName = "Toz";
		PlayerType playerType = PlayerType.HUMAN;
		playerData.setPlayerName(playerName);
		playerData.setPlayerType(playerType);
		gameData.addPlayer(playerData);
		playerName = "Comp";
		playerType = PlayerType.COMPUTER;
		playerData.setPlayerName(playerName);
		playerData.setPlayerType(playerType);
		gameData.addPlayer(playerData);
	}

}
