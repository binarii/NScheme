package environment.filesystem;

import environment.Table;

public interface DocumentWriter {
	public void WriteFile(String filename, Table t);
}
