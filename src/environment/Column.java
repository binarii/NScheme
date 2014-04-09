package environment;

public class Column extends Range {
	private int _width;
	public static final int DEFAULT_SIZE = 64; 
	
	public Column() {
		_width = DEFAULT_SIZE;
	}
	
	public int getWidth() {
		return _width;
	}
	
	public void setWidth(int width) {
		_width = width;
	}
}
