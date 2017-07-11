package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.game.Game;
import game.game.blueprint.GameBlueprint;
import game.game.blueprint.GameBlueprintImpl;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;
import game.game.repository.GameRepository;
import game.game.repository.GameRepositoryImpl;
import game.game.responses.GameStatusResult;
import game.game.responses.NewGameResponse;
import game.game.services.PlayerService;
import game.game.services.PlayerServiceImpl;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

public class GetGameStatusFeature {
	
	GameBlueprint blueprint; 
	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	UserService userService;
	PlayerService playerService;
	NewGameResponse response;
	PlayerData playerDataToRegister;
	String gameId;
	GameStatusResult gameStatus;
	
	@Given("^a game for the players of type \"(.*?)\" and \"(.*?)\" is in the process of building$")
	public void a_game_for_the_players_of_type_and_is_in_the_process_of_building(String player1, String player2) throws Throwable {
		setup();
		gameId = startBuildingGame();
		assertNotNull(gameId);
		assertEquals(player1, response.gameData.getPlayerDataList().get(0).getPlayerType().toString());
		assertEquals(player2, response.gameData.getPlayerDataList().get(1).getPlayerType().toString());
		assertEquals(2, response.gameData.getPlayerDataList().size());
	}
	
	private String startBuildingGame() {
		response = userService.createGame(blueprint);
		return response.gameId;
	}
	
	private void setup() {
		blueprint = new GameBlueprintImpl();
		activeGamesRepository = new GameRepositoryImpl();
		activeGameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(activeGamesRepository);
		userService.setActiveGameBuilderRepository(activeGameBuilderRepository);
		playerService = new PlayerServiceImpl();
		playerService.setAcactiveGamesRepository(activeGamesRepository);
		playerService.setActiveGameBuilderRepository(activeGameBuilderRepository);
		createPlayerDataToRegister();
		Game.getGameIdGenerator().reset();
		prepareBlueprint(PlayerType.HUMAN, PlayerType.HUMAN);
	}
	
	public void createPlayerDataToRegister() {
		playerDataToRegister = new PlayerDataImpl();
		playerDataToRegister.setPlayerName("Toz");
		playerDataToRegister.setPlayerType(PlayerType.HUMAN);
	}

	public void prepareBlueprint(PlayerType... playerTypes) {
		blueprint.setPlayersNumber(playerTypes.length);
		for(PlayerType pt : playerTypes){
			addPlayerOfType(pt);
		}
		 blueprint.setPlayerDataToRegisister(playerDataToRegister);
	}
	
	public void addPlayerOfType(PlayerType playerType) {
		PlayerData playerData = new PlayerDataImpl();
		 playerData.setPlayerType(playerType);
		 blueprint.addPlayer(playerData);
	}

	@Given("^the player has the game id$")
	public void the_player_has_the_game_id() throws Throwable {
		assertNotNull(gameId);
	}

	@When("^the player requests game status and provides the game id$")
	public void the_player_requests_game_status_and_provides_the_game_id() throws Throwable {
		gameStatus = playerService.getGameStatus(gameId);
		assertNotNull(gameStatus);
	}

	@Then("^the player gets game status result with the game status \"(.*?)\"$")
	public void the_player_gets_game_status_result_with_the_game_status(String status) throws Throwable {
		assertEquals(status, gameStatus.gameData.getStatus());
	}

}
