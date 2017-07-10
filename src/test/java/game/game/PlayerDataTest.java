package game.game;

import static org.junit.Assert.*;

import org.junit.Test;

import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;

public class PlayerDataTest {

	@Test
	public void canCreatePlayerData() {
		PlayerData playerData = new PlayerDataImpl();
		PlayerType playerType = PlayerType.HUMAN;
		playerData.setPlayerType(playerType);
	}

}
