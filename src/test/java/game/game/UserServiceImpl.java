package game.game;

public class UserServiceImpl implements UserService {

	@Override
	public GameData createGame(GameData gameData) {
		return new GameDataImpl();
	}

}
