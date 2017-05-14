package game.game;

public class PlayerImpl {
	private static class InnerPlayerBuilder implements PlayerBuilder{

		String playerName;
		PlayerType playerType;
		String playerIntelect;
		
		@Override
		public void setPlayerName(String playerName) {
			this.playerName = playerName;
		}

		@Override
		public void setPlayerType(PlayerType playerType) {
			this.playerType = playerType;
		}

		@Override
		public void setPlayerIntelect(String intelect) {
			this.playerIntelect = intelect;
		}

		@Override
		public Player build() {
			return null;
		}
		
	}

	public static PlayerBuilder getPlayerBuilder() {
		return new InnerPlayerBuilder();
	}
}
