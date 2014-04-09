package environment.filesystem;
import environment.Environment;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter implements DocumentWriter {

	@Override
	public void WriteFile(String filename, Environment e) {
		try {
			FileWriter writer = new FileWriter(filename);
			
			int width = e.getTable(0).getWidth();
			int height = e.getTable(0).getHeight();
			
			String valueToWrite = "";
			
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					valueToWrite = e.getTable(0).getCellAtIndex(x, y).getString();
					if(valueToWrite.equals("")) {
						valueToWrite = " ";
					}
					writer.append(valueToWrite);
					writer.append(',');
				}
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
	}
}
