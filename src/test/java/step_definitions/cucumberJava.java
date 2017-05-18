package step_definitions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.intThat;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.game.GameBlueprint;
import game.game.GameBlueprintImpl;
import game.game.NewGameResponse;
import game.game.PlayerData;
import game.game.PlayerDataImpl;
import game.game.PlayerType;
import game.game.UserService;
import game.game.UserServiceImpl;

public class cucumberJava {
	
	GameBlueprint blueprint = new GameBlueprintImpl();
	
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

	@Given("^The number of HumanPlayers equals (\\d+)$")
	public void the_number_of_HumanPlayers_equals(int expectedNumberOfHumanPlayers) throws Throwable {
	    int numberOfHumanPlayers = 0;
	    List<PlayerData> players = blueprint.getPlayerDataList();
	    for(PlayerData player : players){
	    	if(player.getPlayerType().equals(PlayerType.HUMAN)){
	    		numberOfHumanPlayers++;
	    	}
	    }
	    assertEquals(expectedNumberOfHumanPlayers, numberOfHumanPlayers);
	}

	@When("^I provide the GameBlueprint$")
	public void i_provide_the_GameBlueprint() throws Throwable {
	    UserService userService = new UserServiceImpl();
	    NewGameResponse response = userService.createGame(blueprint);
	    throw new PendingException();
	}

	@Then("^A new Game is created$")
	public void a_new_Game_is_created() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I get a NewGameData with the Game id and Player id$")
	public void i_get_a_NewGameData_with_the_Game_id_and_Player_id() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}


}
