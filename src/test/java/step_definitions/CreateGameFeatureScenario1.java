package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.intThat;

import java.util.List;

import javax.crypto.spec.PSource;

import org.junit.Before;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.game.blueprint.GameBlueprint;
import game.game.blueprint.GameBlueprintImpl;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;
import game.game.player.AbstractPlayer;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;
import game.game.repository.GameRepository;
import game.game.repository.GameRepositoryImpl;
import game.game.responses.NewGameResponse;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

public class CreateGameFeatureScenario1 {
	
	GameBlueprint blueprint; 
	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	UserService userService;
	NewGameResponse response;
	PlayerData playerDataToRegister;
	
	public CreateGameFeatureScenario1() {
		blueprint = new GameBlueprintImpl();
		activeGamesRepository = new GameRepositoryImpl();
		activeGameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(activeGamesRepository);
		userService.setActiveGameBuilderRepository(activeGameBuilderRepository);
		createPlayerDataToRegister();
	}
	
	@Before
	public void setup(){

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
		response = userService.createGame(blueprint);
		assertNotNull(response);
	}

	@Then("^Human Player is registered$")
	public void human_Player_is_registered() throws Throwable {
		assertNotNull(response.playerId);
	}

	@Then("^NewGameCreatedResponse is returned$")
	public void newgamecreatedresponse_is_returned() throws Throwable {
		assertNotNull(response);
		System.out.println(response);
		assertEquals("PLAYING", response.gameData.getStatus());
	}

}
