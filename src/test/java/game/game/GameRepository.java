package game.game;

public interface GameRepository {

	void add(Game game);

	Game findById(String id);


}
