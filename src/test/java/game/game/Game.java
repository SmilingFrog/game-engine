package game.game;

public class Game {

	private Game(){
		
	}
	
	private static class InnerGameBuilder implements GameBuilder{

		@Override
		public Game build() {
			return new Game();
		}
		
	}
	
	public static GameBuilder getGameBuilder() {
		return new InnerGameBuilder();
	}

}
