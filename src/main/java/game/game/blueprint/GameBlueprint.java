package game.game.blueprint;

import java.util.List;

import game.game.player.data.PlayerData;

public interface GameBlueprint{

	PlayerData getPlayerDataToRegister();

	void setPlayerDataToRegisister(PlayerData playerDataToRegister);
	
	void setPlayersNumber(int playerNumber);
	
	int getPlayersNumber();
	
	void addPlayer(PlayerData playerData);

	List<PlayerData> getPlayersData();

}
