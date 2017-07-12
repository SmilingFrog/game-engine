package game.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.game.builder.GameBuilder;
import game.game.data.GameData;
import game.game.data.GameDataImpl;
import game.game.data.board.GameBoard;
import game.game.data.board.position.Position;
import game.game.player.ComputerPlayer;
import game.game.player.Player;
import game.game.player.PlayerIdGenerator;
import game.game.player.PlayerIdGeneratorImpl;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;

public class Game {

	static class GameSettings {
		static int defaultXDimension = 3;
		static int defaultYDimension = 3;
	}

	GameBoard gameBoard;
	List<Player> players;
	int playersNumber;
	String id;
	String status;
	Player nextMovePlayer;
	List<Player> subscribedPlayers = null;

	private static GameIdGenerator gameIdGenerator = new GameIdGeneratorImpl();

	private Game() {
		subscribedPlayers = new ArrayList<>();
	}

	private static class InnerGameBuilder implements GameBuilder {

		GameBoard gameBoard;
		List<Player> players;
		String id;
		int playersNumber;
		String status;
		GameIdGenerator idGenerator;
		PlayerIdGenerator playerIdGenerator;

		public InnerGameBuilder() {
			players = new ArrayList<>();
			this.status = "BUILDING";
			playerIdGenerator = new PlayerIdGeneratorImpl();
		}

		@Override
		public Game build() {
			if (!canCreateGame()) {
				throw new CantCreateGameException();
			}
			Game game = new Game();
			game.players = this.players;
			game.playersNumber = this.playersNumber;
			game.status = "PLAYING";
			game.id = this.getId();
			createGameBoard(game);
			injectGameIntoAllComputerPlayers(game);
			subscribePlayers(game);
			game.start();
			return game;
		}

		private void injectGameIntoAllComputerPlayers(Game game) {
			for (Player player : game.players) {
				if (player.getPlayerData().getPlayerType() == PlayerType.COMPUTER) {
					((ComputerPlayer) player).setGame(game);
				}
			}
		}

		private void createGameBoard(Game game) {
			List<Position> positions = new ArrayList<>();
			for (int i = 0; i < Game.GameSettings.defaultXDimension; i++) {
				for (int j = 0; j < Game.GameSettings.defaultXDimension; j++) {
					positions.add(new Position(i, j));
				}
			}
			game.gameBoard = new GameBoard(Game.GameSettings.defaultXDimension, Game.GameSettings.defaultXDimension,
					positions);
		}

		private void subscribePlayers(Game game) {
			for (Player player : game.players) {
				if (player.getPlayerData().getPlayerType() == PlayerType.COMPUTER) {
					game.subscribedPlayers.add(player);
				}
			}
		}

		@Override
		public void add(Player player) {
			PlayerData playerData = player.getPlayerData();
			if (playerData.getPlayerType().equals(PlayerType.COMPUTER)) {
				String playerId = playerIdGenerator.generateId();
				player.setPlayerId(playerId);
				((ComputerPlayer) player).setGameId(id);
			}
			this.players.add(player);
		}

		@Override
		public void setPlayersNumber(int playersNumber) {
			this.playersNumber = playersNumber;
		}

		@Override
		public String getId() {
			if (id == null && idGenerator != null) {
				id = idGenerator.generateId();
			}
			return id;
		}

		@Override
		public boolean canCreateGame() {
			boolean result = false;
			if (playersNumber == players.size() && atLeastOnePlayerIsOfTypeHuman() && allPlayersHaveIds()) {
				result = true;
			}
			return result;
		}

		private boolean allPlayersHaveIds() {
			boolean result = true;

			for (Player player : players) {
				PlayerData playerData = player.getPlayerData();
				String playerId = playerData.getPlayerId();
				if (playerId == null) {
					result = false;
				}
			}

			return result;
		}

		private boolean atLeastOnePlayerIsOfTypeHuman() {
			boolean result = false;
			for (Player player : players) {
				PlayerData playerData = player.getPlayerData();
				if (playerData.getPlayerType().equals(PlayerType.HUMAN)) {
					result = true;
				}
			}
			return result;
		}

		@Override
		public GameData getGameData() {
			GameData gameData = new GameDataImpl();
			gameData.setId(id);
			gameData.setPlayersNumber(playersNumber);
			gameData.setStatus(status);
			for (Player player : players) {
				PlayerData playerData = player.getPlayerData();
				gameData.addPlayer(playerData);
			}
			return gameData;
		}

		@Override
		public void setIdGenerator(GameIdGenerator idGenerator) {
			this.idGenerator = idGenerator;
			id = null;
		}

		@Override
		public String registerPlayer(PlayerData playerToRegister) {
			String playerId = null;
			for (Player player : players) {
				PlayerData playerData = player.getPlayerData();
				if (playerData.getPlayerType().equals(PlayerType.HUMAN) && playerData.getPlayerId() == null) {
					player.setPlayerName(playerToRegister.getPlayerName());
					playerId = playerIdGenerator.generateId();
					player.setPlayerId(playerId);
					break;
				}
			}
			return playerId;
		}

	}

	public static GameBuilder getGameBuilder() {
		GameBuilder builder = new InnerGameBuilder();
		builder.setIdGenerator(Game.getGameIdGenerator());
		return builder;
	}

	public void start() {
		nextMovePlayer = defineThePlayerToMakeFirstMove();
		informSubscribedPlayers();
	}

	private Player defineThePlayerToMakeFirstMove() {
		Random rnd = new Random();
		int indexOfPlayer = rnd.nextInt(players.size());
		indexOfNextPlayerToMakeAMove = indexOfPlayer;
		return players.get(indexOfPlayer);
	}

	private void informSubscribedPlayers() {
		for (Player player : subscribedPlayers) {
			player.statusChanged();
		}
	}

	private static int indexOfNextPlayerToMakeAMove = 0;

	private static void getNextIndex(int size) {
		// if(indexOfNextPlayerToMakeAMove >= size){
		// indexOfNextPlayerToMakeAMove = 0;
		// return indexOfNextPlayerToMakeAMove;
		// }
		// return indexOfNextPlayerToMakeAMove++;
		if (indexOfNextPlayerToMakeAMove == 0) {
			indexOfNextPlayerToMakeAMove = 1;
		} else {
			indexOfNextPlayerToMakeAMove = 0;
		}
	}

	public GameData getGameData() {
		GameData result = new GameDataImpl();
		result.setId(id);
		result.setPlayersNumber(playersNumber);
		result.setStatus(status);
		result.setGameBoard(gameBoard);
		result.setNextPlayer(nextMovePlayer);
		for (Player player : players) {
			PlayerData playerData = player.getPlayerData();
			result.addPlayer(playerData);
		}

		return result;
	}

	public static GameIdGenerator getGameIdGenerator() {
		return gameIdGenerator;
	}

	public void makeMove(String gameId, String playerId, Position position) {
		if (notValidGameId(gameId) || notValidPlayerId(playerId)) {
			throw new RuntimeException("wrong game id or player id");
		}
		if (isOccupied(position)) {
			throw new RuntimeException("can`t make move position is occupied");
		}

		List<Position> positions = gameBoard.getPositions();
		int index = positions.indexOf(position);
		markPositionAt(index, playerId);
		getNextIndex(players.size());
		getNextMovePlayer();
		informSubscribedPlayers();
	}

	private void getNextMovePlayer() {
		nextMovePlayer = players.get(indexOfNextPlayerToMakeAMove);
	}

	private void markPositionAt(int index, String playerId) {
		List<Position> positions = gameBoard.getPositions();
		positions.get(index).setMark(playerId);
	}

	private boolean isOccupied(Position position) {
		List<Position> positions = gameBoard.getPositions();
		int index = positions.indexOf(position);
		if (positions.get(index).getMark() != null) {
			return true;
		}
		return false;
	}

	private boolean notValidPlayerId(String playerId) {
		boolean result = true;
		for (Player player : players) {
			if (player.getPlayerData().getPlayerId().equals(playerId)) {
				result = false;
			}
		}
		return result;
	}

	private boolean notValidGameId(String gameId) {
		return id != gameId;
	}

}
