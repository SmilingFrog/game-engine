package game.game;

public interface GameBuilderRepository {

	void save(GameBuilder builder);

	int size();

	GameBuilder getById(String id);

}
