package environment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import language.Environment;
import view.IViewCell;
import view.IViewTable;

public class Table implements IViewTable {
	private Environment _envr;
	private Cell _selected;
	private Cell[][] _cells;
	private int[] _rowSizes;
	private int[] _colSizes;

	private int _width;
	private int _height;

	private Queue<IViewCell> _dirtyCells;
	private List<TableListener> _listeners;

	public static final int DEFAULT_WIDTH = 26;
	public static final int DEFAULT_HEIGHT = 100;

	public static final int DEFAULT_ROW_SIZE = 20;
	public static final int DEFAULT_COL_SIZE = 64;

	public Table() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Table(int width, int height) {
		_width = width;
		_height = height;

		_dirtyCells = new LinkedList<IViewCell>();
		_listeners = new ArrayList<TableListener>();

		initializeCells();
		initializeTableFields();
	}

	public void initializeCells() {
		_cells = new Cell[_width][_height];

		_cells = new Cell[_width][];
		for (int x = 0; x < _width; x++) {

			_cells[x] = new Cell[_height];
			for (int y = 0; y < _height; y++) {
				_cells[x][y] = new Cell(x, y, this);
			}
		}

	}

	public void initializeTableFields() {
		_rowSizes = new int[_height];
		_colSizes = new int[_width];

		for (int i = 0; i < _rowSizes.length; i++) {
			_rowSizes[i] = DEFAULT_ROW_SIZE;
		}

		for (int i = 0; i < _colSizes.length; i++) {
			_colSizes[i] = DEFAULT_COL_SIZE;
		}
	}

	public void setEnvironment(Environment envr) {
		_envr = envr;
	}

	public Environment getEnvironment() {
		return _envr;
	}

	public void addListener(TableListener listener) {
		_listeners.add(listener);
	}

	public void removeAllListeners() {
		_listeners.clear();
	}

	public void notifyListeners(IViewCell cell) {
		for (TableListener l : _listeners) {
			l.updateTableListener(cell);
		}
	}

	public void setSelected(Cell cell) {
		if (_selected == cell) {
			return;
		} else if (_selected != null) {
			_selected.deselect();
		}

		_selected = cell;
		_selected.select();
	}

	public void shiftSelection(int x, int y) {
		setSelected((Cell) shiftCell(_selected, x, y));
	}

	public IViewCell shiftCell(IViewCell cell, int x, int y) {
		if (cell == null) {
			return null;
		}

		int newX = cell.getX() + x;
		int newY = cell.getY() + y;
		return getCellAtIndex(newX, newY);
	}

	@Override
	public IViewCell getCellAtIndex(int x, int y) {
		x = Math.min(_width - 1, Math.max(0, x));
		y = Math.min(_height - 1, Math.max(0, y));
		return _cells[x][y];
	}

	@Override
	public IViewCell getCellAtPosition(int x, int y) {
		int indexX = 0;
		int indexY = 0;

		for (int screenX = getColSize(0); screenX < x; screenX += getColSize(indexX)) {
			indexX++;
		}

		for (int screenY = getRowSize(0); screenY < y; screenY += getRowSize(indexY)) {
			indexY++;
		}

		return getCellAtIndex(indexX, indexY);
	}

	@Override
	public IViewCell getSelectedCell() {
		return _selected;
	}

	@Override
	public int getWidth() {
		return _width;
	}

	@Override
	public int getHeight() {
		return _height;
	}

	@Override
	public int getRowSize(int row) {
		return _rowSizes[row];
	}

	@Override
	public int getColSize(int col) {
		return _colSizes[col];
	}

	@Override
	public int getRowStartPixel(int row) {
		int screenPos = 0;

		for (int i = 0; i < row; i++) {
			screenPos += getRowSize(i);
		}

		return screenPos;
	}

	@Override
	public int getColStartPixel(int col) {
		int screenPos = 0;

		for (int i = 0; i < col; i++) {
			screenPos += getColSize(i);
		}

		return screenPos;
	}

	@Override
	public int getDisplayWidth() {
		return getColStartPixel(_width);
	}

	@Override
	public int getDisplayHeight() {
		return getRowStartPixel(_height);
	}

	@Override
	public IViewCell nextDirtyCell() {
		return _dirtyCells.remove();
	}

	@Override
	public boolean isDirtyQueueEmpty() {
		return _dirtyCells.isEmpty();
	}

	public void markDirty(IViewCell cell) {
		_dirtyCells.add(cell);
		notifyListeners(cell);
	}
}
