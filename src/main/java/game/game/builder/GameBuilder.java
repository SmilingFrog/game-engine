package game.game.builder;

import game.game.Game;
import game.game.GameIdGenerator;
import game.game.data.GameData;
import game.game.player.Player;
import game.game.player.data.PlayerData;

public interface GameBuilder {
	
	Game build();

	String getId();

	void add(Player player);

	void setPlayersNumber(int playersNumber);

	boolean canCreateGame();

	GameData getGameData();

	void setIdGenerator(GameIdGenerator idGenerator);

	String registerPlayer(PlayerData playerToRegister);

}
