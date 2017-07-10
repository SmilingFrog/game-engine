package game.game;

import game.game.data.GameData;

public class NewPlayerRegisteredResult {

	public String playerId;
	public GameData gameData;
	public String gameId;
	@Override
	public String toString() {
		return "NewPlayerRegisteredResult [playerId=" + playerId + ", gameData=" + gameData + ", gameId=" + gameId
				+ "]";
	}
	

}
