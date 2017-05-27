package game.game;

import java.util.List;

import javax.swing.plaf.basic.BasicMenuBarUI;

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
	public NewGameResponse createGame(GameBlueprint blueprint) {
		NewGameResponse result = null;
		GameBuilder gameBuilder = Game.getGameBuilder();
		activeGameBuilderRepository.add(gameBuilder);
		addPlayers(blueprint.getPlayersData(), gameBuilder);
		gameBuilder.setPlayersNumber(blueprint.getPlayersNumber());
		String gameId = gameBuilder.getId();
		NewPlayerRegisteredResult registeredPlayer = registerNewPlayer(gameId, blueprint.getPlayerDataToRegister());
		result = new NewGameResponse();
		if(gameBuilder.canCreateGame()){
			Game game = gameBuilder.build();
			activeGamesRepository.add(game);
			activeGameBuilderRepository.remove(gameBuilder);
			result.gameData = game.getGameData();
			result.gameId = result.gameData.getId();
			result.playerId = registeredPlayer.playerId;
		}else{
			result.gameData = gameBuilder.getGameData();
			result.gameId = gameBuilder.getId();
			result.playerId = registeredPlayer.playerId;
		}
		
		return result;
	}

	private void addPlayers(List<PlayerData> playerDataList, GameBuilder gameBuilder) {
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
	public NewPlayerRegisteredResult registerNewPlayer(String id, PlayerData playerData) {
		NewPlayerRegisteredResult result = new NewPlayerRegisteredResult();
		GameBuilder gameBuilder = activeGameBuilderRepository.findById(id);
		if(theGameHasAlreadyStarted(gameBuilder)){
			throw new RuntimeException("Can not register new player. The Game has already started!");
		}
		result.playerId = gameBuilder.registerPlayer(playerData);
		result.gameId = gameBuilder.getId();
		if(gameBuilder.canCreateGame()){
			Game game = gameBuilder.build();
			activeGamesRepository.add(game);
			result.gameData = game.getGameData();
			activeGameBuilderRepository.remove(gameBuilder);
		}else{
			result.gameData = gameBuilder.getGameData();
		}
		return result;
	}

	private boolean theGameHasAlreadyStarted(GameBuilder gameBuilder) {
		return gameBuilder == null;
	}

}
