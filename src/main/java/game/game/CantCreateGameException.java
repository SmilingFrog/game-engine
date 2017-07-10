package game.game;

public class CantCreateGameException extends RuntimeException {
	public CantCreateGameException(){
		super("Can`t create a Game. Not all Players registered");
	}
}
