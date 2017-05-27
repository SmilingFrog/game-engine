package game.game;

import java.util.List;

public interface GameBlueprint{

	PlayerData getPlayerDataToRegister();

	void setPlayerDataToRegisister(PlayerData playerDataToRegister);
	
	void setPlayersNumber(int playerNumber);
	
	int getPlayersNumber();
	
	void addPlayer(PlayerData playerData);

	List<PlayerData> getPlayersData();

}
