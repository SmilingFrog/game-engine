package game.game;

import static org.junit.Assert.*;

import org.junit.Before;
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
		addPlayersFromPlayerData();
		GameData gameData = gameBuilder.getGameData();
		assertEquals(null, gameData.getId());
		assertNotNull(gameData.getPlayerDataList());
		assertEquals(2, gameData.getPlayerDataList().size());
	}
	
	@Test
	public void AddedButNotRegisteredYetPlayersDoNotHaveId(){
		createGameData();
		addPlayersFromPlayerData();
		GameData gameData = gameBuilder.getGameData();
		for(PlayerData playerData : gameData.getPlayerDataList()){
			assertNull(playerData.getPlayerId());
		}
	}
	
	@Test
	public void gameBuilderStatusIsBUILDING(){
		createGameData();
		addPlayersFromPlayerData();
		GameData gameData = gameBuilder.getGameData();
		assertEquals("BUILDING", gameData.getStatus());
	}
	
	@Test
	public void whenGameDataContainsAtLeastOneHumanPlayerCanBuildGame(){
		createGameData();
		addPlayersFromPlayerData();
		gameBuilder.setPlayersNumber(gameData.getPlayersNumber());
		assertTrue(gameBuilder.canCreateGame());
	}

	private void addPlayersFromPlayerData() {
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
		PlayerData playerData = new PlayerDataImpl();
		createPlayers(gameData, playerData);
	}
	
	private void createPlayers(GameData gameData, PlayerData playerData) {
		PlayerType playerType = PlayerType.HUMAN;
		playerData.setPlayerType(playerType);
		gameData.addPlayer(playerData);
		playerType = PlayerType.COMPUTER;
		playerData.setPlayerType(playerType);
		gameData.addPlayer(playerData);
	}
	

}
