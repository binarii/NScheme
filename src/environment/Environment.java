package environment;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment {
	ArrayList<Table> _tables;
	HashMap<String, Object> _envrVariables;

	public Environment() {
		this(new Table());
	}

	public Environment(Table t) {
		_tables = new ArrayList<Table>();
		_tables.add(t);
		_envrVariables = new HashMap<String, Object>();
	}

	public Object findVar(String s) {
		Object cellRef = parseCell(s);
		if (cellRef != null) {
			return cellRef;
		}

		Object rangeRef = parseRange(s);
		if (rangeRef != null) {
			return rangeRef;
		}

		return _envrVariables.get(s);
	}

	public void putVar(String k, Object v) {
		_envrVariables.put(k, v);
	}

	public Table getTable(int id) {
		return _tables.get(id);
	}

	public void addTable(Table table) {
		_tables.add(table);
	}

	public void removeTable(int id) {
		Table table = _tables.get(id);
		_tables.remove(table);
	}

	public Cell parseCell(String s) {
		int x = 0;
		int y = 0;
		char columnChar = s.toLowerCase().charAt(0);
		String rest = s.substring(1);

		if (columnChar >= 'a' && columnChar <= 'z') {
			x = columnChar - 'a';
		} else {
			return null;
		}

		try {
			y = Integer.parseInt(rest) - 1;
		} catch (NumberFormatException e) {
			return null;
		}

		return (Cell) _tables.get(0).getCellAtIndex(x, y);
	}

	public Range parseRange(String s) {
		if (s.contains(":")) {
			String[] tokens = s.split(":");
			if (tokens.length != 2) {
				return null;
			}
			Cell c1 = parseCell(tokens[0]);
			Cell c2 = parseCell(tokens[1]);
			if (c1 == null || c2 == null) {
				return null;
			}

			return new Range(c1, c2, _tables.get(0));
		}
		return null;
	}
}
