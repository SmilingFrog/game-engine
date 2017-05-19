package game.game;

import java.util.List;

public class UserServiceImpl implements UserService {

	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	
	public void setAcactiveGamesRepository(GameRepository repo){
		this.activeGamesRepository = repo;
	}
	
	public void setActiveGameBuilderRepository(GameBuilderRepository repo){
		this.activeGameBuilderRepository = repo;
	}
	
	@Override
	public NewGameResponse createGame(GameData gameData) {
		NewGameResponse result = null;
		GameBuilder gameBuilder = Game.getGameBuilder();
		addPlayers(gameData, gameBuilder);
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		if(gameBuilder.canCreateGame()){
			Game game = gameBuilder.build();
			activeGamesRepository.add(game);
			result = new NewGameResponse();
			result.gameData = game.getGameData();
			result.gameId = result.gameData.getId();
			String playerId = null;
			for(PlayerData player : gameData.getPlayerDataList()){
				if(player.getPlayerType().equals(PlayerType.HUMAN)){
					playerId = player.getPlayerId();
				}
			}
			result.playerId = playerId;	
		}else{
			activeGameBuilderRepository.add(gameBuilder);
			result = new NewGameResponse();
			result.gameData = gameBuilder.getGameData();
		}
		
		return result;
	}

	private void addPlayers(GameData gameData, GameBuilder gameBuilder) {
		List<PlayerData> playerDataList = gameData.getPlayerDataList();
		for(PlayerData playerData : playerDataList){
			Player player = createPlayer(playerData);
			gameBuilder.add(player);
		}
	}

	private Player createPlayer(PlayerData playerData) {
		PlayerBuilder playerBuilder = AbstractPlayer.getPlayerBuilder();
		playerBuilder.setPlayerName(playerData.getPlayerName());
		playerBuilder.setPlayerType(playerData.getPlayerType());
		playerBuilder.setPlayerIntelect(playerData.getIntelect());
		Player player = playerBuilder.build();
		return player;
	}

	@Override
	public GameData registerNewPlayer(String id, PlayerData playerData) {
		GameData result = null;
		GameBuilder gameBuilder = activeGameBuilderRepository.findById(id);
		if(theGameHasAlreadyStarted(gameBuilder)){
			throw new RuntimeException("Can not register new player. The Game has already started!");
		}
		Player player = createPlayer(playerData);
		gameBuilder.add(player);
		if(gameBuilder.canCreateGame()){
			Game game = gameBuilder.build();
			activeGamesRepository.add(game);
			result = game.getGameData();
			activeGameBuilderRepository.remove(gameBuilder);
		}else{
			result = gameBuilder.getGameData();
		}
		return result;
	}

	private boolean theGameHasAlreadyStarted(GameBuilder gameBuilder) {
		return gameBuilder == null;
	}


}
