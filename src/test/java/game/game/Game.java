package game.game;

import java.util.ArrayList;
import java.util.List;

public class Game {

	List<Player> players;
	int playersNumber;
	String id;
	
	private Game(){
	}
	
	private static class InnerGameBuilder implements GameBuilder{
		
		List<Player> players;
		String id;
		int playersNumber;
		
		public InnerGameBuilder() {
			players = new ArrayList<>();
		}
		
		@Override
		public Game build() {
			Game game = new Game();
			game.players = this.players;
			game.playersNumber = this.playersNumber;
			game.id = IdGeneratorImpl.generateId();
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
		
	}
	
	public static GameBuilder getGameBuilder() {
		return new InnerGameBuilder();
	}

}
