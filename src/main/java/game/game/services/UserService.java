package game.game.services;

import game.game.NewPlayerRegisteredResult;
import game.game.blueprint.GameBlueprint;
import game.game.builder.repository.GameBuilderRepository;
import game.game.player.data.PlayerData;
import game.game.repository.GameRepository;
import game.game.responses.NewGameResponse;

public interface UserService {

	NewGameResponse createGame(GameBlueprint blueprint);
	public void setAcactiveGamesRepository(GameRepository repo);
	public void setActiveGameBuilderRepository(GameBuilderRepository repo);
	NewPlayerRegisteredResult registerNewPlayer(String id, PlayerData playerData);
}
