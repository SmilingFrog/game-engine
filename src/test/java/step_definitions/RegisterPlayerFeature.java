package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
import game.game.responses.NewGameResponse;
import game.game.responses.NewPlayerRegisteredResult;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

public class RegisterPlayerFeature {
	
	GameBlueprint blueprint; 
	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	UserService userService;
	NewGameResponse response;
	PlayerData playerDataToRegister;
	String gameId;
	NewPlayerRegisteredResult newPlayerRegisteredResponse;
	
	@Given("^we have an id of the game$")
	public void we_have_an_id_of_the_game() throws Throwable {
		setup();
		gameId = startBuildingGame();
		assertNotNull(gameId);
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
	
	@Given("^the game for two players of types \"(.*?)\" and \"(.*?)\" is building$")
	public void the_game_for_two_players_of_types_and_is_building(String player1, String player2) throws Throwable {
		List<PlayerData> playerDataList = response.gameData.getPlayerDataList();
		assertEquals(2, playerDataList.size());
		assertEquals(PlayerType.HUMAN, playerDataList.get(0).getPlayerType());
		assertEquals(PlayerType.HUMAN, playerDataList.get(1).getPlayerType());
	}

	@Given("^the game status is \"(.*?)\"$")
	public void the_game_status_is(String status) throws Throwable {
		assertEquals(status, response.gameData.getStatus());
	}

	@When("^we register new player of type \"(.*?)\" for the game with the id$")
	public void we_register_new_player_of_type_for_the_game_with_the_id(String arg1) throws Throwable {
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		newPlayerRegisteredResponse = userService.registerNewPlayer(gameId, playerData);
		assertNotNull(newPlayerRegisteredResponse);
	}

	@Then("^the new player is registered and the player id is returned$")
	public void the_new_player_is_registered_and_the_player_id_is_returned() throws Throwable {
		assertNotNull(newPlayerRegisteredResponse.playerId);
	}

	@Then("^the new game status is \"(.*?)\"$")
	public void the_new_game_status_is(String gameStatus) throws Throwable {
		assertEquals(gameStatus, newPlayerRegisteredResponse.gameData.getStatus());
	}

}
