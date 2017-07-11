package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import game.game.data.board.GameBoard;
import game.game.player.AbstractPlayer;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;
import game.game.repository.GameRepository;
import game.game.repository.GameRepositoryImpl;
import game.game.responses.NewGameResponse;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

public class CreateGameFeature {
	
	GameBlueprint blueprint; 
	GameRepository activeGamesRepository;
	GameBuilderRepository activeGameBuilderRepository;
	UserService userService;
	NewGameResponse response;
	PlayerData playerDataToRegister;
	GameBoard gameBoard;
	
	public CreateGameFeature() {
		
	}
	
	@Given("^The number of players in the GameBlueprint equals (\\d+)$")
	public void the_number_of_players_in_the_GameBlueprint_equals(int expectedNumberOfGamePlayers) throws Throwable {
		assertEquals(expectedNumberOfGamePlayers, blueprint.getPlayersNumber());
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

	@Given("^The PlayerType of the players is COMPUTER and HUMAN$")
	public void the_PlayerType_of_the_players_is_COMPUTER_and_HUMAN() throws Throwable {
		setup();
		prepareBlueprint(PlayerType.COMPUTER, PlayerType.HUMAN);
		assertTrue(existsPlayerOfType(PlayerType.COMPUTER));
	    assertTrue(existsPlayerOfType(PlayerType.HUMAN));
	    assertEquals(2, blueprint.getPlayersNumber());
	}

	private void setup() {
		blueprint = new GameBlueprintImpl();
		activeGamesRepository = new GameRepositoryImpl();
		activeGameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(activeGamesRepository);
		userService.setActiveGameBuilderRepository(activeGameBuilderRepository);
		createPlayerDataToRegister();
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
	
	@Then("^NewGameCreatedResponse is returned with gameId playerId and gameStatus \"(.*?)\"$")
	public void newgamecreatedresponse_is_returned_with_gameId_playerId_and_gameStatus(String gameStatus) throws Throwable {
		assertNotNull(response);
		assertEquals(gameStatus, response.gameData.getStatus());
	}
	
	@Given("^The PlayerType of the players is HUMAN and HUMAN$")
	public void the_PlayerType_of_the_players_is_HUMAN_and_HUMAN() throws Throwable {
		setup();
		prepareBlueprint(PlayerType.HUMAN, PlayerType.HUMAN);
	    assertTrue(existsPlayerOfType(PlayerType.HUMAN));
	    assertTrue(!existsPlayerOfType(PlayerType.COMPUTER));
	    assertEquals(2, blueprint.getPlayersNumber());
	}
	
	@Then("^the GameBoard in the gameData of the NewGameCreatedResponse is null$")
	public void the_GameBoard_in_the_gameData_of_the_NewGameCreatedResponse_is_null() throws Throwable {
		assertNull(response.gameData.getGameBoard());
	}

	@Then("^the GameBoard in the gameData of the NewGameCreatedResponse is not null$")
	public void the_GameBoard_in_the_gameData_of_the_NewGameCreatedResponse_is_not_null() throws Throwable {
		assertNotNull(response.gameData.getGameBoard());
	}

	@Then("^the GameBoard\\.xDimension in the gameData of the NewGameCreatedResponse is \"(.*?)\"$")
	public void the_GameBoard_xDimension_in_the_gameData_of_the_NewGameCreatedResponse_is(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the GameBoard\\.yDimension in the gameData of the NewGameCreatedResponse is \"(.*?)\"$")
	public void the_GameBoard_yDimension_in_the_gameData_of_the_NewGameCreatedResponse_is(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the GameBoard\\.positions\\.size is \"(.*?)\"$")
	public void the_GameBoard_positions_size_is(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}


}
