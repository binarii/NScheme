package environment.filesystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import environment.Environment;
import environment.Table;

public class CSVReader implements DocumentReader {

	@Override
	public Table readFile(String filename) {
		Table importedTable = new Table();
		
		BufferedReader reader = null;
		String readLine = "";
		String splitBy = ",";
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			for(int y = 0; y < importedTable.getHeight(); y++) {
				readLine = reader.readLine();
				String[] values = readLine.split(splitBy);
				for(int x = 0; x < importedTable.getWidth(); x++) {
					importedTable.getCellAtIndex(x, y).setString(values[x]);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return importedTable;
	}

}
