package game.game.responses;

import game.game.data.GameData;

public class NewGameResponse {
	public String gameId;
	public String playerId;
	public GameData gameData;
	@Override
	public String toString() {
		return "NewGameResponse [gameId=" + gameId + ", playerId=" + playerId + ", gameData=" + gameData + "]";
	}
	
}
