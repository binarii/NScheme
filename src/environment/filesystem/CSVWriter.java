package environment.filesystem;

import java.io.FileWriter;
import java.io.IOException;

import environment.Table;

/**
 * CSVWriter - Extends from Document Writer. Used to write the current document
 * to a Comma Separated Value sheet (CSV).
 */
public class CSVWriter implements DocumentWriter {

	@Override
	public void WriteFile(String filename, Table t) {
		try {
			FileWriter writer = new FileWriter(filename);

			int width = t.getWidth();
			int height = t.getHeight();

			String valueToWrite = "";

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					valueToWrite = t.getCellAtIndex(x, y).getInput().toString();
					if (valueToWrite.equals("")) {
						valueToWrite = " ";
					}
					writer.append(valueToWrite);
					writer.append(',');
				}
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
