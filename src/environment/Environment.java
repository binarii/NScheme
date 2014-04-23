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
}
