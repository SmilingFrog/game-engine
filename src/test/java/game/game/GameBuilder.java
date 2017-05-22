package game.game;

public interface GameBuilder {
	
	Game build();

	String getId();

	void add(Player player);

	void setPlayersNumber(int playersNumber);

	boolean canCreateGame();

	GameData getGameData();

	void setIdGenerator(GameIdGenerator idGenerator);

	String registerPlayer(PlayerData playerToRegister);

}
