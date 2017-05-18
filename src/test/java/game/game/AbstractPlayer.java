package game.game;

public abstract class AbstractPlayer implements Player{
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
			Player result = null;
			if(playerType == PlayerType.COMPUTER){
				result = new ComputerPlayer();
			}else if(playerType == PlayerType.HUMAN){
				result = new HumanPlayer();
			}
			return result;
		}
		
	}
	
	public static PlayerBuilder getPlayerBuilder() {
		return new InnerPlayerBuilder();
	}

	@Override
	public void statusChanged() {
		
	}
}
