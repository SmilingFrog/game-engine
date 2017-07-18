package game.game.repository;

import java.util.List;

import game.game.Game;

public interface GameRepository {

	void add(Game game);

	Game findById(String id);

	int size();

	void remove(Game game);

	List<String> getAllIds();


}
