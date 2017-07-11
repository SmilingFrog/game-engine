package game.game.services;

import game.game.Game;
import game.game.builder.GameBuilder;
import game.game.builder.repository.GameBuilderRepository;
import game.game.repository.GameRepository;
import game.game.responses.GameStatusResult;

public class PlayerServiceImpl implements PlayerService {

	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	
	@Override
	public void setAcactiveGamesRepository(GameRepository gameRepository) {
		activeGamesRepository = gameRepository;
	}

	@Override
	public void setActiveGameBuilderRepository(GameBuilderRepository gameBuilderRepository) {
		activeGameBuilderRepository = gameBuilderRepository;
	}

	@Override
	public GameStatusResult getGameStatus(String id) {
		GameStatusResult result = new GameStatusResult(); 
		GameBuilder	builder = activeGameBuilderRepository.findById(id);
		if(builder != null){
			result.gameData = builder.getGameData();
			result.gameId = result.gameData.getId();
		}else{
			Game game = activeGamesRepository.findById(id);
			result.gameData = game.getGameData();
			result.gameId = result.gameData.getId();
		}
		return result;
	}

}