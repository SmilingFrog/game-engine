package game.game;

import java.util.ArrayList;
import java.util.List;

public class GameBlueprintImpl implements GameBlueprint {

	int playersNum;
	List<PlayerData> players;
	
	public GameBlueprintImpl() {
		players = new ArrayList<>();
	}
	
	@Override
	public void setPlayersNumber(int playersNum) {
		this.playersNum = playersNum;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlayer(PlayerData playerData) {
		players.add(playerData);
	}

	@Override
	public List<PlayerData> getPlayerDataList() {
		return players;
	}

	@Override
	public int getPlayersNumber() {
		return playersNum;
	}

}
