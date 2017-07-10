package game.game.repository;

import java.util.HashMap;
import java.util.Map;

import game.game.Game;

public class GameRepositoryImpl implements GameRepository {

	Map<String, Game> games;
	
	public GameRepositoryImpl() {
		games = new HashMap<String, Game>();
	}
	
	@Override
	public void add(Game game) {
		games.put(game.getGameData().getId(), game);
	}

	@Override
	public Game findById(String id) {
		return games.get(id);
	}

	@Override
	public int size() {
		return games.size();
	}

}
