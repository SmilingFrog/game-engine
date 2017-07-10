package game.game.player.builder;

import game.game.player.Player;
import game.game.player.PlayerType;

public interface PlayerBuilder {

	void setPlayerName(String playerName);

	void setPlayerType(PlayerType playerType);

	void setPlayerIntelect(String intelect);

	Player build();

}
