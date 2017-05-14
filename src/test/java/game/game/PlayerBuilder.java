package game.game;

public interface PlayerBuilder {

	void setPlayerName(String playerName);

	void setPlayerType(PlayerType playerType);

	void setPlayerIntelect(String intelect);

	Player build();

}
