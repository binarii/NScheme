package environment.filesystem;

import environment.Table;

public interface DocumentReader {
	public Table readFile(Table dest, String filename);
}
