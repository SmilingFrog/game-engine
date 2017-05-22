package game.game;

public abstract class AbstractPlayer implements Player{
	String playerName;
	PlayerType playerType;
	String playerIntelect;
	String playerId;
	PlayerData playerData;
	
	public AbstractPlayer(){
		playerData = new PlayerDataImpl();
	}
	
	private static class InnerPlayerBuilder implements PlayerBuilder{

		String playerName;
		PlayerType playerType;
		String playerIntelect;
		String playerId;
		
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
				fillPlayerProperties(result);
			}else if(playerType == PlayerType.HUMAN){
				result = new HumanPlayer();
				fillPlayerProperties(result);
			}
			return result;
		}

		private void fillPlayerProperties(Player result) {
			result.setPlayerName(this.playerName);
			result.setPlayerType(this.playerType);
			result.setPlayerIntelect(this.playerIntelect);
		}
		
	}
	
	public static PlayerBuilder getPlayerBuilder() {
		return new InnerPlayerBuilder();
	}

	@Override
	public void statusChanged() {
		
	}
}
