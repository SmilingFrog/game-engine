package game.game.data;

import java.util.List;

import game.game.data.board.GameBoard;
import game.game.player.Player;
import game.game.player.data.PlayerData;

public interface GameData {


	void setPlayersNumber(int playersNum);

	void setId(String id);

	void setStatus(String status);

	String getId();

	String getStatus();

	void addPlayer(PlayerData playerData);

	List<PlayerData> getPlayerDataList();

	int getPlayersNumber();

	GameBoard getGameBoard();

	void setGameBoard(GameBoard gameBoard);

	PlayerData getNextPlayer();

	void setNextPlayer(Player nextMovePlayer);

	void setWinnerId(String winnerId);

}
