package environment;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A range of cells specified with the two opposite corners. It implements
 * iterable in order to use easy for loop syntax.
 */
public class Range implements Iterable<Cell> {
	private ArrayList<Cell> _cells;

	public Range(Cell c1, Cell c2, Table table) {
		int startX = Math.min(c1.getX(), c2.getX());
		int startY = Math.min(c1.getY(), c2.getY());
		int endX = Math.max(c1.getX(), c2.getX());
		int endY = Math.max(c1.getY(), c2.getY());

		_cells = new ArrayList<Cell>();
		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j++) {
				_cells.add((Cell) table.getCellAtIndex(i, j));
			}
		}
	}

	@Override
	public Iterator<Cell> iterator() {
		return _cells.iterator();
	}

	public int size() {
		return _cells.size();
	}
}
