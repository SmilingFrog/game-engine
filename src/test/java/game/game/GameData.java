package game.game;

public interface GameData {


	void setPlayersNumber(int playersNum);

	void setId(String id);

	void setStatus(String status);

	String getId();

	String getStatus();

	void addPlayer(PlayerData playerData);

}
