package game.game;

import java.util.HashMap;
import java.util.Map;

public class GameBuilderRepositoryImpl implements GameBuilderRepository {

	private Map<String, GameBuilder> repo;
	
	public GameBuilderRepositoryImpl() {
		repo = new HashMap<>();
	}
	
	@Override
	public void save(GameBuilder builder) {
		repo.put(builder.getId(), builder);
	}

	@Override
	public int size() {
		return repo.size();
	}

	@Override
	public GameBuilder getById(String id) {
		return repo.get(id);
	}

}
