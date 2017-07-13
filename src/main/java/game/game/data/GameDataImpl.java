package game.game.data;

import java.util.ArrayList;
import java.util.List;

import game.game.data.board.GameBoard;
import game.game.player.Player;
import game.game.player.data.PlayerData;

public class GameDataImpl implements GameData {

	int playersNum;
	String id;
	String status;
	List<PlayerData> playerDataList;
	GameBoard gameBoard;
	PlayerData nextPlayer;
	String winnerId;
	
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

	@Override
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	@Override
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	@Override
	public PlayerData getNextPlayer() {
		return nextPlayer;
	}

	@Override
	public void setNextPlayer(Player nextMovePlayer) {
		nextPlayer = nextMovePlayer.getPlayerData();
	}

	@Override
	public void setWinnerId(String winnerId) {
		this.winnerId=winnerId;
	}

}
