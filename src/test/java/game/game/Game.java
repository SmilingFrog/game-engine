package game.game;

public class Game {

	private Game(){
		
	}
	
	private static class InnerGameBuilder implements GameBuilder{

		@Override
		public Game build() {
			return new Game();
		}

		@Override
		public String getId() {
			return "1";
		}
		
	}
	
	public static GameBuilder getGameBuilder() {
		return new InnerGameBuilder();
	}

}
