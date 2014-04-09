package environment;

import java.util.ArrayList;

public class Environment {
	ArrayList<Table> _tables;

	public Environment() {
		this(new Table());
	}

	public Environment(Table t) {
		_tables = new ArrayList<Table>();
		_tables.add(t);
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
}
