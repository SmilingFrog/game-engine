package game.game;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import game.game.blueprint.GameBlueprint;
import game.game.blueprint.GameBlueprintImpl;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;
import game.game.data.GameData;
import game.game.data.board.position.Position;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;
import game.game.repository.GameRepository;
import game.game.repository.GameRepositoryImpl;
import game.game.responses.GameStatusResult;
import game.game.responses.NewGameResponse;
import game.game.responses.NewPlayerRegisteredResult;
import game.game.services.PlayerService;
import game.game.services.PlayerServiceImpl;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

public class PlayerServiceTest {

	UserService userService;

	GameBlueprint blueprint;

	GameRepository gameRepository;

	GameBuilderRepository gameBuilderRepository;

	PlayerService playerService;

	@Before
	public void setup() {
		Game.getGameIdGenerator().reset();
		gameRepository = new GameRepositoryImpl();
		gameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(gameRepository);
		userService.setActiveGameBuilderRepository(gameBuilderRepository);

		playerService = new PlayerServiceImpl();
		playerService.setAcactiveGamesRepository(gameRepository);
		playerService.setActiveGameBuilderRepository(gameBuilderRepository);

		blueprint = new GameBlueprintImpl();
		blueprint.setPlayersNumber(2);

	}

	@After
	public void cleanup() {
		Game.getGameIdGenerator().reset();
	}

	@Test
	public void whenGettingGameStatusOfTheGameThatIsNotBuiltYetGetGameBuilderStatus() {
		prepareBlueprint(0, 2);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "BUILDING");
	}

	@Test
	public void whenGettingGameStatusOfTheGameThatIsBuiltGetGameStatus() {
		prepareBlueprint(1, 1);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
	}

	public void prepareBlueprint(int numberOfComputerPlayers, int numberOfHumanPlayers) {
		blueprint.setPlayersNumber(numberOfComputerPlayers + numberOfHumanPlayers);
		for (int i = 0; i < numberOfComputerPlayers; i++) {
			addPlayerOfType(PlayerType.COMPUTER);
		}
		for (int i = 0; i < numberOfHumanPlayers; i++) {
			addPlayerOfType(PlayerType.HUMAN);
		}
		PlayerData playerDataToRegister = new PlayerDataImpl();
		playerDataToRegister.setPlayerName("Toz");
		playerDataToRegister.setPlayerType(PlayerType.HUMAN);
		blueprint.setPlayerDataToRegisister(playerDataToRegister);
	}

	public void addPlayerOfType(PlayerType playerType) {
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerType(playerType);
		blueprint.addPlayer(playerData);
	}

	@Ignore
	@Test
	public void canMakeMove() {
		prepareBlueprint(1, 1);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		String playerId = response.playerId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
		showGameState(playerId, gameStatus);
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 8; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			gameStatus = playerService.makeMove(id, playerId, new Position(x, y));
			showGameState(playerId, gameStatus);
		}
		showGameState(playerId, gameStatus);
	}
	
	@Ignore
	@Test
	public void simulateGameHumanHuman() {
		prepareBlueprint(0, 2);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		String firstPlayerId = response.playerId;
		GameStatusResult gameStatus = playerService.getGameStatus(id);
		assertNotNull(gameStatus);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "BUILDING");
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		NewPlayerRegisteredResult newPlayerRegistered = 
				userService.registerNewPlayer(id, playerData);
		String secondPlayerId = newPlayerRegistered.playerId;
		gameStatus = playerService.getGameStatus(id);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
		showGameState(firstPlayerId, gameStatus);
		
		String playerId = null;
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 8; i++) {
			int playerIdInt = sc.nextInt();
			playerId = String.valueOf(playerIdInt);
			int x = sc.nextInt();
			int y = sc.nextInt();
			gameStatus = playerService.makeMove(id, playerId, new Position(x, y));
			showGameState(playerId, gameStatus);
		}
		showGameState(playerId, gameStatus);
	}

	private void showGameState(String playerId, GameStatusResult gameStatus) {
		System.out.println("game id: " + gameStatus.gameId);
		System.out.println("player Id: " + playerId);
		System.out.println("game status: " + gameStatus.gameData.getStatus());
		System.out.println("next player: " + gameStatus.gameData.getNextPlayer().getPlayerId());
		System.out.println("winner id: " + gameStatus.gameData.getWinnerId());
		print(gameStatus.gameData.getGameBoard().getPositions());
	}

	private void print(List<Position> positions) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int index = positions.indexOf(new Position(i, j));
				Position position = positions.get(index);
				String mark = " ";
				if (position.getMark() != null) {
					mark = position.getMark();
				}
				sb.append("[");
				sb.append(mark);
				sb.append("]");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

}
