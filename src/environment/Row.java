package environment;

public class Row extends Range {
	private int _height;
	public static final int DEFAULT_SIZE = 20; 
	
	public Row() {
		_height = DEFAULT_SIZE;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public void setHeight(int height) {
		_height = height;
	}
}
