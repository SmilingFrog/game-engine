package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.intThat;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.game.GameBlueprint;
import game.game.GameBlueprintImpl;
import game.game.GameBuilderRepository;
import game.game.GameBuilderRepositoryImpl;
import game.game.GameRepository;
import game.game.GameRepositoryImpl;
import game.game.NewGameResponse;
import game.game.PlayerData;
import game.game.PlayerDataImpl;
import game.game.PlayerType;
import game.game.UserService;
import game.game.UserServiceImpl;

public class CucumberJava {
	
	GameBlueprint blueprint; 
	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	UserService userService;
	NewGameResponse response;
	
	public CucumberJava() {
		blueprint = new GameBlueprintImpl();
		activeGamesRepository = new GameRepositoryImpl();
		activeGameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(activeGamesRepository);
		userService.setActiveGameBuilderRepository(activeGameBuilderRepository);
	}
	
	@Given("^The number of players in the GameBlueprint equals the actual size of the list of registered players$")
	public void the_number_of_players_in_the_GameBlueprint_equals_the_actual_size_of_the_list_of_registered_players() throws Throwable {
	    int numberOfPlayers = 2;
	    blueprint.setPlayersNumber(2);
	    assertEquals(numberOfPlayers, blueprint.getPlayersNumber());
	    createPlayers();
	    assertEquals(numberOfPlayers, blueprint.getPlayerDataList().size());
	}

	private void createPlayers() {
		PlayerData playerData = new PlayerDataImpl();
	    playerData.setPlayerType(PlayerType.HUMAN);
	    blueprint.addPlayer(playerData);
	    playerData = new PlayerDataImpl();
	    playerData.setPlayerType(PlayerType.COMPUTER);
	    blueprint.addPlayer(playerData);
	}

	@When("^I provide the GameBlueprint$")
	public void i_provide_the_GameBlueprint() throws Throwable {
	    response = userService.createGame(blueprint);
	    assertNotNull(userService);
	}
	
	@Then("^A new GameBuilder is created$")
	public void a_new_GameBuilder_is_created() throws Throwable {
		assertEquals(1, activeGameBuilderRepository.size());
	}

	@Then("^I get a NewGameData with the Game id and GameData$")
	public void i_get_a_NewGameData_with_the_Game_id_and_GameData() throws Throwable {
		assertEquals(1, response.gameId);
		assertTrue(response.playerId == null);
	}



}
