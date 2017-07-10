package game.game.builder.repository;

import java.util.HashMap;
import java.util.Map;

import game.game.builder.GameBuilder;

public class GameBuilderRepositoryImpl implements GameBuilderRepository {

	private Map<String, GameBuilder> repo;
	
	public GameBuilderRepositoryImpl() {
		repo = new HashMap<>();
	}

	@Override
	public int size() {
		return repo.size();
	}

	@Override
	public GameBuilder getById(String id) {
		return repo.get(id);
	}

	@Override
	public void add(GameBuilder gameBuilder) {
		repo.put(gameBuilder.getId(), gameBuilder);
		
	}

	@Override
	public GameBuilder findById(String id) {
		return repo.get(id);
	}

	@Override
	public void remove(GameBuilder gameBuilder) {
		repo.remove(gameBuilder.getGameData().getId());
	}

}
