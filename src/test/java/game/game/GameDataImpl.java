package game.game;

import java.util.ArrayList;
import java.util.List;

public class GameDataImpl implements GameData {

	int playersNum;
	String id;
	String status;
	List<PlayerData> playerDataList;
	
	public GameDataImpl() {
		playerDataList = new ArrayList<>();
	}
	
	@Override
	public void setPlayersNumber(int playersNum) {
		this.playersNum = playersNum; 
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void addPlayer(PlayerData playerData) {
		playerDataList.add(playerData);
	}

	@Override
	public List<PlayerData> getPlayerDataList() {
		return playerDataList;
	}

	@Override
	public int getPlayersNumber() {
		return playersNum;
	}

}
