package game.game;

import static org.junit.Assert.*;

import javax.management.RuntimeErrorException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GameBuilderTest {

	GameBuilder gameBuilder;
	GameData gameData;
	GameIdGenerator idGenerator;
	
	@Before
	public void setup(){
		gameBuilder = Game.getGameBuilder();
	}
	
	
	@Test
	public void newGameBuilderEveryTime() {
		GameBuilder anotherGameBuilder = Game.getGameBuilder();
		assertFalse(gameBuilder == anotherGameBuilder);
	}
	
	@Test
	public void whenIdGeneratorisSetGameIdIsGeneratedAndAssignedOnlyOnce(){
		String expectedGameId = "1";
		GameIdGenerator idGenerator = Game.getGameIdGenerator();
		gameBuilder.setIdGenerator(idGenerator);
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		idGenerator.reset();
	}
	
	@Test
	public void whenIdGeneratorisResetGameIdIsGeneratedByNewGeneratorOnlyOnce(){
		String expectedGameId = "1";
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		resetIdGenerator();
		expectedGameId = "2";
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		idGenerator.reset();
	}


	private void resetIdGenerator() {
		idGenerator = Game.getGameIdGenerator();
		gameBuilder.setIdGenerator(idGenerator);
	}
	
	@Test
	public void IdGeneratorReturnedbyGameIsSingleton(){
		GameIdGenerator generator = Game.getGameIdGenerator();
		GameIdGenerator anotherGenerator = Game.getGameIdGenerator();
		assertTrue(generator == anotherGenerator);
	}
	
	@Test
	public void FirstGameBuilderHasId1SecondGameBuilderHasId2(){
		String expectedGameId = "1";
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		gameBuilder = Game.getGameBuilder();
		expectedGameId = "2";
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		Game.getGameIdGenerator().reset();
	}
	
	@Test
	public void canAddPlayersFromPlayerData(){
		createGameDataForHumanComputerGame();
		addPlayersFromPlayerData(gameBuilder);
		GameData gameData = gameBuilder.getGameData();
		assertEquals(null, gameData.getId());
		assertNotNull(gameData.getPlayerDataList());
		assertEquals(2, gameData.getPlayerDataList().size());
	}
	
	private void createGameDataForHumanComputerGame() {
		createGameData(2, PlayerType.HUMAN, PlayerType.COMPUTER);
	}


	@Test
	public void gameBuilderStatusIsBUILDING(){
		createGameDataForHumanComputerGame();
		addPlayersFromPlayerData(gameBuilder);
		GameData gameDataResponse = gameBuilder.getGameData();
		assertEquals("BUILDING", gameDataResponse.getStatus());
	}
	
	
	@Test
	public void whenAddingPlayerOfTypeComputerAssignItAnId(){
		createGameDataForHumanComputerGame();
		addPlayersFromPlayerData(gameBuilder);
		GameData gameDataResponse = gameBuilder.getGameData();
		assertEquals(2, gameDataResponse.getPlayerDataList().size());
		assertThatAllPlayersOfComputerTypeHaveAnId(gameDataResponse);
	}


	private void assertThatAllPlayersOfComputerTypeHaveAnId(GameData gameDataResponse) {
		for(PlayerData playerData : gameDataResponse.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				assertNotNull(playerData.getPlayerId());
			}
		}
	}
	
	@Test
	public void whenRegisteringAPlayerTakeOneHumanPlayerAndAssignItAnId(){
		createGameDataForHumanComputerGame();
		addPlayersFromPlayerData(gameBuilder);
		PlayerData playerToRegister = new PlayerDataImpl();
		playerToRegister.setPlayerName("Toz");
		gameBuilder.registerPlayer(playerToRegister);
		GameData gameDataResponse = gameBuilder.getGameData();
		for(PlayerData playerData : gameDataResponse.getPlayerDataList()){
				assertNotNull(playerData.getPlayerId());
		}
	}
	
	@Test(expected = CantCreateGameException.class)
	public void canCreateGameOnlyWhenAllPlayersHaveIds(){
		createGameDataForHumanComputerGame();
		addPlayersFromPlayerData(gameBuilder);
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		assertFalse(gameBuilder.canCreateGame());
		Game game = gameBuilder.build();
		PlayerData playerToRegister = new PlayerDataImpl();
		playerToRegister.setPlayerName("Toz");
		gameBuilder.registerPlayer(playerToRegister);
		assertTrue(gameBuilder.canCreateGame());
	}
	
	@Test
	public void eachGameBuilderHasItsOwnPlayerIdGenerator(){
		createGameDataForHumanComputerGame();
		addPlayersFromPlayerData(gameBuilder);
		GameBuilder secondGameBuilder = Game.getGameBuilder();
		addPlayersFromPlayerData(secondGameBuilder);
		GameData gameDataResult = gameBuilder.getGameData();
		String playerIdByGameBuilder1 = getPlayerIdAssignedByBuilder1(gameDataResult);
		assertNotNull(playerIdByGameBuilder1);
		String playerIdByGameBuilder2 = getPlayerIdAssignedByBuilder2(secondGameBuilder);
		assertNotNull(playerIdByGameBuilder1);
		assertEquals(playerIdByGameBuilder1, playerIdByGameBuilder2);
	}


	private String getPlayerIdAssignedByBuilder2(GameBuilder secondGameBuilder) {
		GameData gameDataResult;
		String playerIdByGameBuilder2 = null;
		gameDataResult = secondGameBuilder.getGameData();
		for(PlayerData playerData : gameDataResult.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				playerIdByGameBuilder2 = playerData.getPlayerId();
			}
		}
		return playerIdByGameBuilder2;
	}


	private String getPlayerIdAssignedByBuilder1(GameData gameDataResult) {
		String playerIdByGameBuilder1 = null;
		for(PlayerData playerData : gameDataResult.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				playerIdByGameBuilder1 = playerData.getPlayerId();
			}
		}
		return playerIdByGameBuilder1;
	}

	private void addPlayersFromPlayerData(GameBuilder gameBuilder) {
		Player player = null;
		for(PlayerData playerData : gameData.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.HUMAN)){
				player = new HumanPlayer();
				player.setPlayerType(PlayerType.HUMAN);
			}else if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				player = new ComputerPlayer();
				player.setPlayerType(PlayerType.COMPUTER);
			}
			gameBuilder.add(player);
		}
	}

	private void createGameData(int numberOfPlayers, PlayerType ...playerTypes) {
		gameData = new GameDataImpl();
		int playersNumber = numberOfPlayers;
		gameData.setPlayersNumber(playersNumber);
		createPlayers(gameData, playersNumber, playerTypes);
	}
	
	private void createPlayers(GameData gameData, int playersNumber, PlayerType ... playerTypes) {
		if(playerTypes.length != playersNumber){
			throw new RuntimeException("Number of players must equal the number of player Types");
		}
		PlayerData playerData = null;
		for(PlayerType playerType : playerTypes){
			playerData = new PlayerDataImpl();
			playerData.setPlayerType(playerType);
			gameData.addPlayer(playerData);
		}
	}
	

}
