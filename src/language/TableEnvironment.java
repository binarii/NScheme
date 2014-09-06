package language;

import environment.Cell;
import environment.Range;
import environment.Table;

/**
 * The table environment will be the root for all environments, the class will
 * find variables differently by using parsing rather than a hash table.
 */
public class TableEnvironment extends Environment {
	Table _table;

	public TableEnvironment(Environment parent) {
		super(parent);
	}

	public TableEnvironment(Table table) {
		super(null);
		_table = table;
	}

	@Override
	public Object findVar(String s) {
		Cell cellRef = parseCell(s);
		if (cellRef != null) {
			if (cellRef.getDisplay() instanceof String) {
				return Parser.atomize(cellRef.getString());
			}
			return cellRef.getDisplay();
		}

		Range rangeRef = parseRange(s);
		if (rangeRef != null) {
			Pair result = null;
			for (Cell c : rangeRef) {
				if (c.getDisplay() instanceof String) {
					result = new Pair(Parser.atomize(c.getString()), result);
				} else {
					result = new Pair(c.getDisplay(), result);
				}
			}
			return LangUtil.reverse(result);
		}

		// We have reached the base environment and still not found the key,
		// return error.
		return LangUtil.error("identifier not found: " + s);
	}

	@Override
	public void putVar(String k, Object v) {
		// Dont allow addition of variables
	}

	@Override
	public void setVar(String k, Object v) {

	}

	@Override
	public Table getTable() {
		// This will directly return the table
		return _table;
	}

	// Parse cell identifier notation (eg A2)
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

		return (Cell) _table.getCellAtIndex(x, y);
	}

	// Parse range notation (eg A1:B5)
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
			return new Range(c1, c2, _table);
		}
		return null;
	}
}
