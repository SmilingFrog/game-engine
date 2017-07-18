package game.game;

public class NotYourTurnException extends RuntimeException {
	public NotYourTurnException(String message) {
		super(message);
	}
}
