package game.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

	List<Player> players;
	int playersNumber;
	String id;
	String status;
	Player nextMovePlayer;
	
	private Game(){
	}
	
	private static class InnerGameBuilder implements GameBuilder{
		
		List<Player> players;
		String id;
		int playersNumber;
		String status;
		
		public InnerGameBuilder() {
			players = new ArrayList<>();
			this.status = "BUILDING";
			this.id = IdGeneratorImpl.generateId();
			IdGeneratorImpl.reset();
		}
		
		@Override
		public Game build() {
			Game game = new Game();
			game.players = this.players;
			game.playersNumber = this.playersNumber;
			game.id = IdGeneratorImpl.generateId();
			IdGeneratorImpl.reset();
			game.status = "PLAYING";
			subscribePlayers();
			game.start();
			return game;
		}

		private void subscribePlayers() {
			
		}

		@Override
		public void add(Player player) {
			this.players.add(player);
		}

		@Override
		public void setPlayersNumber(int playersNumber) {
			this.playersNumber = playersNumber;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public boolean allPlayersReady() {
			return playersNumber == players.size();
		}

		@Override
		public GameData getGameData() {
			GameData gameData = new GameDataImpl();
			gameData.setId(id);
			gameData.setPlayersNumber(playersNumber);
			gameData.setStatus(status);
			return gameData;
		}
		
	}
	
	public static GameBuilder getGameBuilder() {
		return new InnerGameBuilder();
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
		
		return result;
	}
	

}
