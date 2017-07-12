package game.game.player;

import game.game.player.data.PlayerData;

public class ComputerPlayer extends AbstractPlayer {

	String gameId;
	
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
		playerData.setPlayerId(playerId);
	}

	@Override
	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setGameId(String id) {
		this.gameId = id;
	}

}
