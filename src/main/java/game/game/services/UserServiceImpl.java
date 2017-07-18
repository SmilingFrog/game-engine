package game.game.services;

import java.util.List;

import javax.swing.plaf.basic.BasicMenuBarUI;

import game.game.Game;
import game.game.TimeOutException;
import game.game.blueprint.GameBlueprint;
import game.game.builder.GameBuilder;
import game.game.builder.repository.GameBuilderRepository;
import game.game.player.AbstractPlayer;
import game.game.player.Player;
import game.game.player.builder.PlayerBuilder;
import game.game.player.data.PlayerData;
import game.game.repository.GameRepository;
import game.game.responses.NewGameResponse;
import game.game.responses.NewPlayerRegisteredResult;

public class UserServiceImpl implements UserService {

	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;

	public void setAcactiveGamesRepository(GameRepository repo) {
		this.activeGamesRepository = repo;
	}

	public void setActiveGameBuilderRepository(GameBuilderRepository repo) {
		this.activeGameBuilderRepository = repo;
	}

	@Override
	public NewGameResponse createGame(GameBlueprint blueprint) {
		GameBuilder gameBuilder = Game.getGameBuilder();
		addPlayers(blueprint.getPlayersData(), gameBuilder);
		gameBuilder.setPlayersNumber(blueprint.getPlayersNumber());
		activeGameBuilderRepository.add(gameBuilder);
		NewPlayerRegisteredResult registeredPlayer = registerPlayer(blueprint, gameBuilder);
		NewGameResponse result = new NewGameResponse();
		fillResult(registeredPlayer, result);
		return result;
	}

	private void fillResult(NewPlayerRegisteredResult registeredPlayer, NewGameResponse result) {
		result.gameData = registeredPlayer.gameData;
		result.gameId = registeredPlayer.gameId;
		result.playerId = registeredPlayer.playerId;
	}

	private NewPlayerRegisteredResult registerPlayer(GameBlueprint blueprint, GameBuilder gameBuilder) {
		String gameId = gameBuilder.getId();
		return registerNewPlayer(gameId, blueprint.getPlayerDataToRegister());
	}

	private void addPlayers(List<PlayerData> playerDataList, GameBuilder gameBuilder) {
		for (PlayerData playerData : playerDataList) {
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
		Game game = activeGamesRepository.findById(id);
		if (theGameHasAlreadyStarted(gameBuilder, game)) {
			throw new RuntimeException("Can not register new player. The Game has already started!");
		}
		
		try{
		result.playerId = gameBuilder.registerPlayer(playerData);
		}catch(TimeOutException ex){
			activeGameBuilderRepository.remove(gameBuilder);
			throw ex;
		}
		
		result.gameId = gameBuilder.getId();
		if (gameBuilder.canCreateGame()) {
			game = gameBuilder.build();
			activeGamesRepository.add(game);
			result.gameData = game.getGameData();
			activeGameBuilderRepository.remove(gameBuilder);
		} else {
			result.gameData = gameBuilder.getGameData();
		}
		return result;
	}

	private boolean theGameHasAlreadyStarted(GameBuilder gameBuilder, Game game) {
		return gameBuilder == null && game != null;
	}

}
