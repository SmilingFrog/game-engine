package game.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GameBuilderTest {

	GameBuilder gameBuilder;
	GameData gameData;
	
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
		IdGenerator idGenerator = new IdGeneratorImpl();
		gameBuilder.setIdGenerator(idGenerator);
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		idGenerator.reset();
	}
	
	@Test
	public void whenIdGeneratorisResetGameIdIsGeneratedByNewGeneratorOnlyOnce(){
		String expectedGameId = "1";
		IdGenerator idGenerator = new IdGeneratorImpl();
		gameBuilder.setIdGenerator(idGenerator);
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		gameBuilder.setIdGenerator(idGenerator);
		expectedGameId = "2";
		assertEquals(expectedGameId, gameBuilder.getId());
		assertEquals(expectedGameId, gameBuilder.getId());
		idGenerator.reset();
	}
	
	@Test
	public void IdGeneratorReturnedbyGameIsSingleton(){
		IdGenerator generator = Game.getGameIdGenerator();
		IdGenerator anotherGenerator = Game.getGameIdGenerator();
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
		createGameData();
		addPlayersFromPlayerData(gameBuilder);
		GameData gameData = gameBuilder.getGameData();
		assertEquals(null, gameData.getId());
		assertNotNull(gameData.getPlayerDataList());
		assertEquals(2, gameData.getPlayerDataList().size());
	}
	
	@Test
	public void gameBuilderStatusIsBUILDING(){
		createGameData();
		addPlayersFromPlayerData(gameBuilder);
		GameData gameData = gameBuilder.getGameData();
		assertEquals("BUILDING", gameData.getStatus());
	}
	
	
	@Test
	public void whenAddingPlayerOfTypeComputerAssignItAnId(){
		createGameData();
		addPlayersFromPlayerData(gameBuilder);
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		GameData gameDataResponse = gameBuilder.getGameData();
		assertEquals(2, gameDataResponse.getPlayerDataList().size());
		for(PlayerData playerData : gameDataResponse.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				assertNotNull(playerData.getPlayerId());
			}
		}
	}
	
	@Test
	public void whenRegisteringAPlayerTakeOneHumanPlayerAndAssignItAnId(){
		createGameData();
		addPlayersFromPlayerData(gameBuilder);
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		PlayerData playerToRegister = new PlayerDataImpl();
		playerToRegister.setPlayerName("Toz");
		gameBuilder.registerPlayer(playerToRegister);
		GameData gameDataResponse = gameBuilder.getGameData();
		for(PlayerData playerData : gameDataResponse.getPlayerDataList()){
				assertNotNull(playerData.getPlayerId());
		}
	}
	
	@Test
	public void canCreateGameOnlyWhenAllPlayersHaveIds(){
		createGameData();
		addPlayersFromPlayerData(gameBuilder);
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		assertFalse(gameBuilder.canCreateGame());
		PlayerData playerToRegister = new PlayerDataImpl();
		playerToRegister.setPlayerName("Toz");
		gameBuilder.registerPlayer(playerToRegister);
		assertTrue(gameBuilder.canCreateGame());
	}
	
	@Test
	public void eachGameBuilderHasItsOwnPlayerIdGenerator(){
		createGameData();
		addPlayersFromPlayerData(gameBuilder);
		GameBuilder secondGameBuilder = Game.getGameBuilder();
		addPlayersFromPlayerData(secondGameBuilder);
		GameData gameDataResult = gameBuilder.getGameData();
		String playerIdByGameBuilder1 = null;
		for(PlayerData playerData : gameDataResult.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				playerIdByGameBuilder1 = playerData.getPlayerId();
			}
		}
		assertNotNull(playerIdByGameBuilder1);
		String playerIdByGameBuilder2 = null;
		gameDataResult = secondGameBuilder.getGameData();
		for(PlayerData playerData : gameDataResult.getPlayerDataList()){
			if(playerData.getPlayerType().equals(PlayerType.COMPUTER)){
				playerIdByGameBuilder2 = playerData.getPlayerId();
			}
		}
		assertNotNull(playerIdByGameBuilder1);
		assertEquals(playerIdByGameBuilder1, playerIdByGameBuilder2);
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

	private void createGameData() {
		gameData = new GameDataImpl();
		int playersNumber = 2;
		gameData.setPlayersNumber(playersNumber);
		createPlayers(gameData);
	}
	
	private void createPlayers(GameData gameData) {
		PlayerData playerData = new PlayerDataImpl();
		PlayerType playerType = PlayerType.HUMAN;
		playerData.setPlayerType(playerType);
		gameData.addPlayer(playerData);
		playerData = new PlayerDataImpl();
		playerType = PlayerType.COMPUTER;
		playerData.setPlayerType(playerType);
		gameData.addPlayer(playerData);
	}
	

}
