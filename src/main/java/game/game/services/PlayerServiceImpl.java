package game.game.services;

import java.util.List;
import java.util.Random;

import game.game.Game;
import game.game.builder.GameBuilder;
import game.game.builder.repository.GameBuilderRepository;
import game.game.data.board.position.Position;
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
		GameBuilder builder = activeGameBuilderRepository.findById(id);
		if (builder != null) {
			result.gameData = builder.getGameData();
			result.gameId = result.gameData.getId();
		} else {
			Game game = activeGamesRepository.findById(id);
			result.gameData = game.getGameData();
			result.gameId = result.gameData.getId();
		}
		return result;
	}

	@Override
	public GameStatusResult makeMove(String id, String playerId, Position position) {
		makeABitOfCleanUp();
		activeGamesRepository.findById(id).makeMove(id, playerId, position);
		return getGameStatus(id);
	}

	private void makeABitOfCleanUp() {
		Random rnd = new Random();
		List<String> gameIdList = activeGamesRepository.getAllIds();
		int randomIndex = rnd.nextInt(gameIdList.size());
		String randomId = gameIdList.get(randomIndex);
		Game game = activeGamesRepository.findById(randomId);
		if(game.isTimeOut(game.lastActivityTime)){
			activeGamesRepository.remove(game);
		}
	}

}
