package game.game;

public interface Player extends Observer {

	void setPlayerName(String playerName);

	void setPlayerType(PlayerType playerType);

	void setPlayerIntelect(String playerIntelect);
	
	public static class PlayerIdGenerator{

		public static String generateId() {
			return "1";
		}
		
	}

	void setPlayerId(String generateId);

	PlayerData getPlayerData();


}
