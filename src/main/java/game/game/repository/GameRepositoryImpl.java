package game.game.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	@Override
	public void remove(Game game) {
		games.remove(game);
	}

	@Override
	public List<String> getAllIds() {
		Set<Entry<String, Game>> entries = games.entrySet();
		List<String> idList = new ArrayList<String>();
		for(Entry<String, Game> entry : entries){
			idList.add(entry.getKey());
		}
		return idList;
	}

}
