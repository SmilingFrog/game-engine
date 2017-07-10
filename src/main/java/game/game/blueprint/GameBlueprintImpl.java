package game.game.blueprint;

import java.util.ArrayList;
import java.util.List;

import game.game.player.data.PlayerData;

public class GameBlueprintImpl implements GameBlueprint {

	int playersNum;
	List<PlayerData> players;
	PlayerData playerToRegister;
	
	public GameBlueprintImpl() {
		players = new ArrayList<>();
	}
	

	@Override
	public PlayerData getPlayerDataToRegister() {
		return playerToRegister;
	}

	@Override
	public void setPlayerDataToRegisister(PlayerData playerDataToRegister) {
		playerToRegister = playerDataToRegister;
	}


	@Override
	public void setPlayersNumber(int playerNumber) {
		playersNum = playerNumber;
	}


	@Override
	public int getPlayersNumber() {
		return playersNum;
	}


	@Override
	public void addPlayer(PlayerData playerData) {
		players.add(playerData);
	}


	@Override
	public List<PlayerData> getPlayersData() {
		return players;
	}

}
