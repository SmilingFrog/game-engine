package game.game;

public interface PlayerData {

	void setPlayerType(PlayerType playerType);

	void setPlayerName(String playerName);

	void setIntelect(String intelect);

	String getPlayerName();

	PlayerType getPlayerType();

	String getIntelect();

	String getPlayerId();

	void setPlayerId(String playerId);

}
