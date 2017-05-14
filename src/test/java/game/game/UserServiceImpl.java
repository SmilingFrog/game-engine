package game.game;

import java.util.List;

public class UserServiceImpl implements UserService {

	GameRepository acactiveGamesRepository;
	GameBuilderRepository activeGameBuilderRepositories;
	
	public void setAcactiveGamesRepository(GameRepository repo){
		this.acactiveGamesRepository = repo;
	}
	
	@Override
	public GameData createGame(GameData gameData) {
		GameData result = null;
		GameBuilder gameBuilder = Game.getGameBuilder();
		List<PlayerData> playerDataList = gameData.getPlayerDataList();
		for(PlayerData playerData : playerDataList){
			Player player = createPlayer(playerData);
			gameBuilder.add(player);
		}
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		if(gameBuilder.allPlayersReady()){
			Game game = gameBuilder.build();
			acactiveGamesRepository.add(game);
		}
		
		
		
		return result;
	}

	private Player createPlayer(PlayerData playerData) {
		PlayerBuilder playerBuilder = PlayerImpl.getPlayerBuilder();
		playerBuilder.setPlayerName(playerData.getPlayerName());
		playerBuilder.setPlayerType(playerData.getPlayerType());
		playerBuilder.setPlayerIntelect(playerData.getIntelect());
		Player player = playerBuilder.build();
		return player;
	}

}
