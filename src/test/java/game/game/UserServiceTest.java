package game.game;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import game.game.blueprint.GameBlueprint;
import game.game.blueprint.GameBlueprintImpl;
import game.game.builder.repository.GameBuilderRepository;
import game.game.builder.repository.GameBuilderRepositoryImpl;
import game.game.data.GameData;
import game.game.data.GameDataImpl;
import game.game.data.board.GameBoard;
import game.game.data.board.position.Position;
import game.game.player.PlayerType;
import game.game.player.data.PlayerData;
import game.game.player.data.PlayerDataImpl;
import game.game.repository.GameRepository;
import game.game.repository.GameRepositoryImpl;
import game.game.responses.GameStatusResult;
import game.game.responses.NewGameResponse;
import game.game.responses.NewPlayerRegisteredResult;
import game.game.services.NoNewPlayersException;
import game.game.services.PlayerService;
import game.game.services.PlayerServiceImpl;
import game.game.services.UserService;
import game.game.services.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	UserService userService;

	GameBlueprint blueprint;

	GameRepository gameRepository;

	GameBuilderRepository gameBuilderRepository;

	PlayerService playerService;

	@Before
	public void setup() {
		setDependencies();
		Game.getGameIdGenerator().reset();
	}

	@After
	public void cleanup() {
		gameRepository = null;
		gameBuilderRepository = null;
		userService = null;
		playerService = null;
		blueprint = null;
	}

	public void setDependencies() {
		gameRepository = new GameRepositoryImpl();
		gameBuilderRepository = new GameBuilderRepositoryImpl();
		userService = new UserServiceImpl();
		userService.setAcactiveGamesRepository(gameRepository);
		userService.setActiveGameBuilderRepository(gameBuilderRepository);
		playerService = new PlayerServiceImpl();
		playerService.setAcactiveGamesRepository(gameRepository);
		playerService.setActiveGameBuilderRepository(gameBuilderRepository);
		blueprint = new GameBlueprintImpl();
	}

	@Test
	public void whenNumberOfPlayersIs2AndPlayersAreHumanAndComputer() {
		prepareBlueprint(1, 1);
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedGameResponse = prepareExpectedResponse("PLAYING");
		assertTrue(isequal(response, expectedGameResponse));
		GameData gameData = response.gameData;
		print(gameData.getGameBoard().getPositions());
		System.out.println(gameData.getNextPlayer().getPlayerName());
		System.out.println(gameData.getWinnerId());
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

	@Test
	public void whenNotAllPlayersAreSetInGameDataReturnNewGameStatusBuilding() {
		int computerPlayers = 1;
		int humanPlayers = 2;
		prepareBlueprint(computerPlayers, humanPlayers);
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedResponse = prepareExpectedResponse("BUILDING");
		expectedResponse.gameData.setStatus("BUILDING");
		assertTrue(isequal(response, expectedResponse));

	}

	@Test
	public void whenAddingAPlayerSoThatAllPlayersAreRegisteredReturnGameStatusPlaying() {
		int computerPlayers = 1;
		int humanPlayers = 2;
		prepareBlueprint(computerPlayers, humanPlayers);
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedResponse = prepareExpectedResponse("BUILDING");
		expectedResponse.gameData.setStatus("BUILDING");
		assertTrue(isequal(response, expectedResponse));
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		NewPlayerRegisteredResult gameStatus = userService.registerNewPlayer(id, playerData);
		assertEquals(gameStatus.gameId, "1");
		assertEquals(gameStatus.gameData.getStatus(), "PLAYING");
		GameStatusResult gameStatusResult = playerService.getGameStatus(id);
		assertEquals(gameStatusResult.gameId, "1");
		assertEquals(gameStatusResult.gameData.getStatus(), "PLAYING");
		GameData gameData = gameStatusResult.gameData;
		print(gameData.getGameBoard().getPositions());
		System.out.println(gameData.getNextPlayer().getPlayerName());
	}

	@Test(expected = TimeOutException.class)
	public void givenTimeHasRunOutAndTryingToRegisterNewPlayerThrowException() {
		int computerPlayers = 1;
		int humanPlayers = 2;
		prepareBlueprint(computerPlayers, humanPlayers);
		NewGameResponse response = userService.createGame(blueprint);
		NewGameResponse expectedResponse = prepareExpectedResponse("BUILDING");
		expectedResponse.gameData.setStatus("BUILDING");
		assertTrue(isequal(response, expectedResponse));
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);

		try {
			Thread.currentThread().sleep(Game.GameSettings.maximumInactiveSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		;

		NewPlayerRegisteredResult gameStatus = userService.registerNewPlayer(id, playerData);

	}

	@Test(expected=WrongIdException.class)
	public void whenRegisterNewPlayerForTheGameThatDoesNotExistAndIsNotBuildingThrowException() {
		String id = "6";
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		NewPlayerRegisteredResult gameStatus = userService.registerNewPlayer(id, playerData);
	}

	@Test(expected = NoNewPlayersException.class)
	public void whenTryingToRegisterNewUserToTheGameThatHasAlreadyStartedThrowException() {
		int computerPlayers = 1;
		int humanPlayers = 1;
		prepareBlueprint(computerPlayers, humanPlayers);
		NewGameResponse response = userService.createGame(blueprint);
		String id = response.gameId;
		PlayerData playerData = new PlayerDataImpl();
		playerData.setPlayerName("Vasya");
		playerData.setPlayerType(PlayerType.HUMAN);
		NewPlayerRegisteredResult gameStatus;
		gameStatus = userService.registerNewPlayer(id, playerData);
	}

	private NewGameResponse prepareExpectedResponse(String gameStatus) {
		NewGameResponse expectedResponse = new NewGameResponse();
		expectedResponse.gameId = "1";
		expectedResponse.gameData = new GameDataImpl();
		expectedResponse.gameData.setStatus(gameStatus);
		if (gameStatus.equals("PLAYING")) {
			int xDimension = 3;
			int yDimension = 3;
			List<Position> positions = new ArrayList<Position>();
			for (int i = 0; i < xDimension; i++) {
				for (int j = 0; j < yDimension; j++) {
					positions.add(new Position(i, j));
				}
			}
			GameBoard gameBoard = new GameBoard(xDimension, yDimension, positions);
			expectedResponse.gameData.setGameBoard(gameBoard);
		}
		return expectedResponse;
	}

	private boolean isequal(NewGameResponse response, NewGameResponse expectedResponse) {
		boolean result = response.gameId.equals(expectedResponse.gameId)
				&& response.gameData.getStatus().equals(expectedResponse.gameData.getStatus());
		if (response.gameData.getStatus().equals(expectedResponse.gameData.getStatus())
				&& expectedResponse.gameData.getStatus().equals("PLAYING")) {
			boolean isGameBoardEqual = response.gameData.getGameBoard() != null;
			isGameBoardEqual = isGameBoardEqual && response.gameData.getGameBoard().getX() == 3;
			isGameBoardEqual = isGameBoardEqual && response.gameData.getGameBoard().getY() == 3;
			isGameBoardEqual = isGameBoardEqual && response.gameData.getGameBoard().getPositions().size() == 9;
			result = result && isGameBoardEqual;
		}
		return result;
	}

}
