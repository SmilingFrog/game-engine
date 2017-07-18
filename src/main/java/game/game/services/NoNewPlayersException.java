package game.game.services;

public class NoNewPlayersException extends RuntimeException {
	public NoNewPlayersException(String message) {
		super(message);
	}
}
