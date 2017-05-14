package game.game;

import java.util.List;

public interface GameData {


	void setPlayersNumber(int playersNum);

	void setId(String id);

	void setStatus(String status);

	String getId();

	String getStatus();

	void addPlayer(PlayerData playerData);

	List<PlayerData> getPlayerDataList();

	int getPlayersNumber();

}
