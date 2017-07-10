package game.game.player;

import game.game.Observer;
import game.game.player.data.PlayerData;

public interface Player extends Observer {

	void setPlayerName(String playerName);

	void setPlayerType(PlayerType playerType);

	void setPlayerIntelect(String playerIntelect);

	void setPlayerId(String generateId);

	PlayerData getPlayerData();


}
