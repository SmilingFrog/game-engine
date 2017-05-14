package game.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

	List<Player> players;
	int playersNumber;
	String id;
	String status;
	
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
			return game;
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

	public GameData getGameData() {
		GameData result = new GameDataImpl();
		result.setId(id);
		result.setPlayersNumber(playersNumber);
		result.setStatus(status);
		
		return result;
	}
	

}
