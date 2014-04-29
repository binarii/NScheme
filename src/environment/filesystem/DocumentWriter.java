package environment.filesystem;

import environment.Table;

/**
 * DocumentWriter An interface for basic document writing. All document writing
 * classes should implement this.
 */
public interface DocumentWriter {
	public void WriteFile(String filename, Table t);
}
