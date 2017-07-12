package game.game;

import java.util.ArrayList;
import java.util.List;

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

	static class GameSettings{
		static int defaultXDimension = 3;
		static int defaultYDimension = 3;
	}
	
	GameBoard gameBoard;
	List<Player> players;
	int playersNumber;
	String id;
	String status;
	Player nextMovePlayer;
	private static GameIdGenerator gameIdGenerator = new GameIdGeneratorImpl();
	
	private Game(){
	}
	
	private static class InnerGameBuilder implements GameBuilder{
		
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
			if(!canCreateGame()){
				throw new CantCreateGameException();
			}
			Game game = new Game();
			game.players = this.players;
			game.playersNumber = this.playersNumber;
			game.status = "PLAYING";
			game.id = this.getId();
			createGameBoard(game);
			subscribePlayers();
			game.start();
			return game;
		}

		private void createGameBoard(Game game) {
			List<Position> positions = new ArrayList<>();
			for (int i = 0; i < Game.GameSettings.defaultXDimension; i++) {
				for (int j = 0; j < Game.GameSettings.defaultXDimension; j++) {
					positions.add(new Position(i, j));
				}
			}
			game.gameBoard = new GameBoard(Game.GameSettings.defaultXDimension, 
					Game.GameSettings.defaultXDimension, positions);
		}

		private void subscribePlayers() {
			
		}

		@Override
		public void add(Player player) {
			PlayerData playerData = player.getPlayerData();
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				String playerId = playerIdGenerator.generateId();
				player.setPlayerId(playerId);
				((ComputerPlayer)player).setGameId(id);
			}
			this.players.add(player);
		}

		@Override
		public void setPlayersNumber(int playersNumber) {
			this.playersNumber = playersNumber;
		}

		@Override
		public String getId() {
			if(id == null && idGenerator != null){
				id = idGenerator.generateId();
			}
			return id;
		}

		@Override
		public boolean canCreateGame() {
			boolean result = false;
			if(playersNumber == players.size() && 
					atLeastOnePlayerIsOfTypeHuman() &&
					allPlayersHaveIds()){
				result = true;
			}
			return result;
		}

		private boolean allPlayersHaveIds() {
			boolean result = true;
			
			for(Player player : players){
				PlayerData playerData = player.getPlayerData();
				String playerId = playerData.getPlayerId(); 
				if(playerId == null){
					result = false;
				}
			}
			
			return result;
		}

		private boolean atLeastOnePlayerIsOfTypeHuman() {
			boolean result = false;
			for(Player player : players){
				PlayerData playerData = player.getPlayerData();
				if(playerData.getPlayerType().equals(PlayerType.HUMAN)){
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
			for(Player player : players){
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
			for(Player player : players){
				PlayerData playerData = player.getPlayerData();
				if(playerData.getPlayerType().equals(PlayerType.HUMAN) &&
						playerData.getPlayerId() == null){
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
		nextMovePlayer = defineThePlayerToMakeNextMove();
		informSubscribedPlayers();
	}

	private void informSubscribedPlayers() {
		for(Player player : players){
			player.statusChanged();
		}
	}

	private Player defineThePlayerToMakeNextMove() {
		int index = Game.getNextIndex(players.size());
		String id = index + "";
		return null;
	}
	
	private static int getNextIndex(int size){
		if(index >= size){
			index = 0;
		}
		return index++;
	}
	
	private static int index = 0;

	public GameData getGameData() {
		GameData result = new GameDataImpl();
		result.setId(id);
		result.setPlayersNumber(playersNumber);
		result.setStatus(status);
		result.setGameBoard(gameBoard);
		for(Player player : players){
			PlayerData playerData = player.getPlayerData();
			result.addPlayer(playerData);
		}
		
		return result;
	}

	public static GameIdGenerator getGameIdGenerator() {
		return gameIdGenerator;
	}
	

}
