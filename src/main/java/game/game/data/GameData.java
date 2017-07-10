package game.game.data;

import java.util.List;

import game.game.player.data.PlayerData;

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
