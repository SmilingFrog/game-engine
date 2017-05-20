package game.game;

public class HumanPlayer extends AbstractPlayer {

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		playerData.setPlayerName(playerName);
	}

	@Override
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
		playerData.setPlayerType(playerType);
	}

	@Override
	public void setPlayerIntelect(String playerIntelect) {
		this.playerIntelect = playerIntelect;
		playerData.setIntelect(playerIntelect);
	}

	@Override
	public void setPlayerId(String generateId) {
		this.playerId = generateId;
		playerData.setPlayerId(generateId);
	}

	@Override
	public PlayerData getPlayerData() {
		return playerData;
	}

}
