/**
 * DocumentWriter
 * An interface for basic document writing. All document writing classes
 * should implement this.
 */
package environment.filesystem;

import environment.Table;

public interface DocumentWriter {
	public void WriteFile(String filename, Table t);
}
