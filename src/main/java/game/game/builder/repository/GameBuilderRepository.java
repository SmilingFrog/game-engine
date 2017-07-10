package game.game.builder.repository;

import game.game.builder.GameBuilder;

public interface GameBuilderRepository {

	int size();

	GameBuilder getById(String id);

	void add(GameBuilder gameBuilder);

	GameBuilder findById(String id);

	void remove(GameBuilder gameBuilder);

}
