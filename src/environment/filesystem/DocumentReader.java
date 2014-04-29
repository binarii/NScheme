package environment.filesystem;

import environment.Table;

/**
 * DocumentReader An interface for basic document reading. All document reading
 * classes should implement this.
 */
public interface DocumentReader {
	public Table readFile(Table dest, String filename);
}
