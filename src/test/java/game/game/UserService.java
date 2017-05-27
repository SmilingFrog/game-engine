package game.game;

public interface UserService {

	NewGameResponse createGame(GameBlueprint blueprint);
	public void setAcactiveGamesRepository(GameRepository repo);
	public void setActiveGameBuilderRepository(GameBuilderRepository repo);
	NewPlayerRegisteredResult registerNewPlayer(String id, PlayerData playerData);
}
