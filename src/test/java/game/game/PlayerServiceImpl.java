package game.game;

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
	public GameData getGameStatus(String id) {
		GameData result = null; 
		GameBuilder	builder = activeGameBuilderRepository.findById(id);
		if(builder != null){
			result = builder.getGameData();
		}else{
			Game game = activeGamesRepository.findById(id);
			result = game.getGameData();
		}
		return result;
	}

}
