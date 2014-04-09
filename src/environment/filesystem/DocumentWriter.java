package environment.filesystem;

import java.net.URI;

import environment.Environment;

public interface DocumentWriter {
	public void WriteFile(String filename, Environment e);
}
