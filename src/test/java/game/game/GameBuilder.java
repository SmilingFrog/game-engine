package game.game;

public interface GameBuilder {
	
	Game build();

	String getId();

	void add(Player player);

	void setPlayersNumber(int playersNumber);

	boolean allPlayersReady();

	GameData getGameData();

}
