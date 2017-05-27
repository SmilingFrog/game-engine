package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.intThat;

import java.util.List;

import javax.crypto.spec.PSource;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.game.AbstractPlayer;
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
	PlayerData playerDataToRegister;
	
	public CucumberJava() {
		blueprint = new GameBlueprintImpl();
		activeGamesRepository = new GameRepositoryImpl();
		activeGameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(activeGamesRepository);
		userService.setActiveGameBuilderRepository(activeGameBuilderRepository);
		createPlayerDataToRegister();
	}
	
	@Given("^The number of players in the GameBlueprint equals (\\d+)$")
	public void the_number_of_players_in_the_GameBlueprint_equals(int expectedNumberOfGamePlayers) throws Throwable {
		prepareBlueprint();
		assertEquals(expectedNumberOfGamePlayers, blueprint.getPlayersNumber());
	}

	public void prepareBlueprint() {
		blueprint.setPlayersNumber(2);
		 addPlayerOfType(PlayerType.COMPUTER);
		 addPlayerOfType(PlayerType.HUMAN);
		 blueprint.setPlayerDataToRegisister(playerDataToRegister);
	}

	public void addPlayerOfType(PlayerType playerType) {
		PlayerData playerData = new PlayerDataImpl();
		 playerData.setPlayerType(playerType);
		 blueprint.addPlayer(playerData);
	}

	@Given("^The PlayerType of the players is COMPUTER and HUMAN$")
	public void the_PlayerType_of_the_players_is_COMPUTER_and_HUMAN() throws Throwable {
	    assertTrue(existsPlayerOfType(PlayerType.COMPUTER));
	    assertTrue(existsPlayerOfType(PlayerType.HUMAN));
	}

	private boolean existsPlayerOfType(PlayerType playerType) {
		boolean result = false;
		for(PlayerData playerData : blueprint.getPlayersData()){
			if(playerData.getPlayerType().equals(playerType)){
				result = true;
			}
		}
		return result;
	}

	@Given("^GameBluePrint contains PlayerData to register HUMAN Player$")
	public void gameblueprint_contains_PlayerData_to_register_HUMAN_Player() throws Throwable {
		PlayerData expected =  blueprint.getPlayerDataToRegister();
		assertNotNull(expected);
		assertEquals(PlayerType.HUMAN, blueprint.getPlayerDataToRegister().getPlayerType());
	}

	public void createPlayerDataToRegister() {
		playerDataToRegister = new PlayerDataImpl();
		playerDataToRegister.setPlayerName("Toz");
		playerDataToRegister.setPlayerType(PlayerType.HUMAN);
	}
	
	@When("^I provide the GameBlueprint$")
	public void i_provide_the_GameBlueprint() throws Throwable {
		userService.createGame(blueprint);
		throw new PendingException();
	}

	@Then("^A new GameBuilder is created$")
	public void a_new_GameBuilder_is_created() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^Human Player is registered$")
	public void human_Player_is_registered() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^NewGameCreatedResponse is returned$")
	public void newgamecreatedresponse_is_returned() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

}
