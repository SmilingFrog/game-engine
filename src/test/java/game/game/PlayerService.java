package game.game;

public interface PlayerService {

	void setAcactiveGamesRepository(GameRepository gameRepository);

	void setActiveGameBuilderRepository(GameBuilderRepository gameBuilderRepository);

	GameStatusResult getGameStatus(String id);

}
