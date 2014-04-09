package environment.filesystem;

import environment.Environment;
import environment.Table;

public interface DocumentReader {
	public Table readFile(String filename);
}
