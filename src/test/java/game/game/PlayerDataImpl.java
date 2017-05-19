package game.game;

public class PlayerDataImpl implements PlayerData {

	PlayerType playerType;
	String playerName;
	String intelect;
	String playerId;
	
	@Override
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public void setIntelect(String intelect) {
		this.intelect = intelect;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public PlayerType getPlayerType() {
		return playerType;
	}

	@Override
	public String getIntelect() {
		return intelect;
	}

	@Override
	public String getPlayerId() {
		return playerId;
	}

	@Override
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

}
