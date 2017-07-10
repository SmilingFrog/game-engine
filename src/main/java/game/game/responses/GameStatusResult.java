package game.game.responses;

import game.game.data.GameData;

public class GameStatusResult {

	public GameData gameData;
	public String gameId;
	@Override
	public String toString() {
		return "GameStatusResult [gameData=" + gameData + ", gameId=" + gameId + "]";
	}
	
	

}
