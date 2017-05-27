package game.game;

public class NewGameResponse {
	public String gameId;
	public String playerId;
	public GameData gameData;
	@Override
	public String toString() {
		return "NewGameResponse [gameId=" + gameId + ", playerId=" + playerId + ", gameData=" + gameData + "]";
	}
	
}
