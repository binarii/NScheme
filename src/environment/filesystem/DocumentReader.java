/**
 * DocumentReader
 * An interface for basic document reading. All document reading classes
 * should implement this.
 */

package environment.filesystem;

import environment.Table;

public interface DocumentReader {
	public Table readFile(Table dest, String filename);
}
