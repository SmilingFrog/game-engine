package game.game;

public interface UserService {

	GameData createGame(GameData gameData);
	public void setAcactiveGamesRepository(GameRepository repo);
	public void setActiveGameBuilderRepository(GameBuilderRepository repo);
	GameData registerNewPlayer(String id, PlayerData playerData);
}
