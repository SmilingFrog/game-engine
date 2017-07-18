package game.game.repository;

import game.game.Game;

public interface GameRepository {

	void add(Game game);

	Game findById(String id);

	int size();

	void remove(Game game);


}
