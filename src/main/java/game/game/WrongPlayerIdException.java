package game.game;

public class WrongPlayerIdException extends RuntimeException {
	public WrongPlayerIdException(String message) {
		super(message);
	}
}
