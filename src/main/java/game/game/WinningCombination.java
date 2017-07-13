package game.game;

import java.util.ArrayList;
import java.util.List;

import game.game.data.board.position.Position;

public class WinningCombination {
	List<Position> positions;
	public WinningCombination(){
		positions = new ArrayList<>();
	}
	public List<Position> getPositions() {
		return positions;
	}
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	public boolean contains(Position movePosition) {
		return positions.contains(movePosition);
	}
	
	public void add(Position pair) {
		positions.add(pair);
	}
	public int getSize() {
		return positions.size();
	}
	public Position getPosition(int i) {
		return positions.get(i);
	}
	
}
