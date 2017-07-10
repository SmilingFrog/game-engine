package game.game.services;

import game.game.builder.repository.GameBuilderRepository;
import game.game.repository.GameRepository;
import game.game.responses.GameStatusResult;

public interface PlayerService {

	void setAcactiveGamesRepository(GameRepository gameRepository);

	void setActiveGameBuilderRepository(GameBuilderRepository gameBuilderRepository);

	GameStatusResult getGameStatus(String id);

}
