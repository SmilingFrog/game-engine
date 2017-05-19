package game.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerDataTest {

	@Test
	public void canCreatePlayerData() {
		PlayerData playerData = new PlayerDataImpl();
		PlayerType playerType = PlayerType.HUMAN;
		playerData.setPlayerType(playerType);
	}

}
