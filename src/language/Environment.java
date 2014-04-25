package language;

import java.util.HashMap;

import environment.Table;

public class Environment {
	Environment _parent;
	HashMap<String, Object> _envrVariables;

	public Environment(Environment parent) {
		_parent = parent;
		_envrVariables = new HashMap<String, Object>();
	}

	public Object findVar(String s) {
		Object obj = _envrVariables.get(s);
		if (obj == null) {
			return _parent.findVar(s);
		}

		return obj;
	}

	public void putVar(String k, Object v) {
		_envrVariables.put(k, v);
	}

	public Table getTable() {
		return _parent.getTable();
	}
}
