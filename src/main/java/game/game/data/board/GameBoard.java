package game.game.data.board;

import java.util.List;

import game.game.data.board.position.Position;

public class GameBoard {

	int xDimension;
	int yDimension;
	List<Position> positions;
	
	public GameBoard(int xDimension, int yDimension, List<Position> positions) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.positions = positions;
	}

	public int getX() {
		return xDimension;
	}

	public int getY() {
		return yDimension;
	}

	public List<Position> getPositions() {
		return positions;
	}

}
