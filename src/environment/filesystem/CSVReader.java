package environment.filesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import language.TableEnvironment;
import environment.Table;

public class CSVReader implements DocumentReader {

	@Override
	public Table readFile(Table dest, String filename) {

		BufferedReader reader = null;
		String readLine = "";
		String splitBy = ",";

		try {
			reader = new BufferedReader(new FileReader(filename));
			for (int y = 0; y < dest.getHeight(); y++) {
				readLine = reader.readLine();
				String[] values = readLine.split(splitBy);
				for (int x = 0; x < dest.getWidth(); x++) {
					if(values[x].equals(" ")) {
						values[x] = "";
					}
					
					dest.getCellAtIndex(x, y).setInput(values[x]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dest;
	}
}
