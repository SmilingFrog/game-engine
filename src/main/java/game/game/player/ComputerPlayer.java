package game.game.player;

import java.util.List;
import java.util.Random;

import game.game.Game;
import game.game.data.GameData;
import game.game.data.board.GameBoard;
import game.game.data.board.position.Position;
import game.game.player.data.PlayerData;

public class ComputerPlayer extends AbstractPlayer {

	String gameId;
	Game game;
	
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

	@Override
	public void statusChanged() {
		super.statusChanged();
		GameData gameData = game.getGameData();
		if(gameData.getStatus().equals("FINISHED")){
			return;
		}
		if(gameData.getNextPlayer().getPlayerId().equals(playerId)){
			GameBoard gameBoard = gameData.getGameBoard();
			List<Position> positions = gameBoard.getPositions();
			Random rnd = new Random();
			int movePositionIndex = 0;
			do{
			movePositionIndex = rnd.nextInt(positions.size());
			}while(isOccupied(movePositionIndex, positions));
			Position position = getPositionAtIndex(positions, movePositionIndex);
			game.makeMove(gameData.getId(), playerId, position);
		}
	}

	private Position getPositionAtIndex(List<Position> positions, int movePositionIndex) {
		return positions.get(movePositionIndex);
	}

	private boolean isOccupied(int movePositionIndex, List<Position> positions) {
		return positions.get(movePositionIndex).getMark() != null;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
