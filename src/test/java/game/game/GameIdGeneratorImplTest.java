package game.game;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;


public class GameIdGeneratorImplTest {

	@Ignore
	@Test
	public void test() {
		for(int i = 0; i < 10; i++ ){
			System.out.println(new GameIdGeneratorImpl().generateId());
		}
	}

}
