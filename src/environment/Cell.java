package environment;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import language.Language;
import language.Parser;
import language.exception.LangParseException;
import view.IViewCell;

public class Cell implements CellListener, IViewCell {
	private List<CellListener> _listeners;

	private String _displayValue;
	private String _inputValue;

	private Color _backgroundColor;
	private Color _foregroundColor;
	private Border _border;
	private Font _font;

	private int _tableX;
	private int _tableY;

	private Table _parent;

	public Cell(int x, int y, Table parent) {
		_listeners = new LinkedList<CellListener>();
		_displayValue = "";
		_inputValue = "";

		_tableX = x;
		_tableY = y;

		_parent = parent;

		_backgroundColor = DEFAULT_BACKGROUND_COLOR;
		_foregroundColor = DEFAULT_FOREGROUND_COLOR;
		_font = DEFAULT_FONT;
		_border = DEFAULT_BORDER;
	}

	public List<CellListener> getListeners() {
		return _listeners;
	}

	public void addListener(CellListener l) {
		_listeners.add(l);
	}

	public void removeListener(CellListener l) {
		_listeners.remove(l);
	}

	public void notifyListeners() {
		for (CellListener l : _listeners) {
			l.update();
		}
	}

	@Override
	public void setString(String s) {
		ClearFormulaRefs();
		_inputValue = s;
		if (s.length() > 0 && s.charAt(0) == '=') {
			AddFormulaRefs();
			update();
		} else {
			_displayValue = s;
		}
		_parent.markDirty(this);
		notifyListeners();
	}

	@Override
	public void select() {
		_border = DEFAULT_S_BORDER;
		_parent.markDirty(this);
	}

	@Override
	public void deselect() {
		_border = DEFAULT_BORDER;
		_parent.markDirty(this);
	}

	@Override
	public String getString() {
		return _displayValue;
	}

	@Override
	public String getInputString() {
		return _inputValue;
	}

	@Override
	public int getX() {
		return _tableX;
	}

	@Override
	public int getY() {
		return _tableY;
	}

	@Override
	public Color getBackground() {
		return _backgroundColor;
	}

	@Override
	public Color getForeground() {
		return _foregroundColor;
	}

	@Override
	public Border getBorder() {
		return _border;
	}

	@Override
	public Font getFont() {
		return _font;
	}

	@Override
	public void setBackground(Color c) {
		_backgroundColor = c;
	}

	@Override
	public void setForeground(Color c) {
		_foregroundColor = c;
	}

	@Override
	public void setBorder(Border b) {
		_border = b;
	}

	@Override
	public void setFont(Font f) {
		_font = f;
	}

	private void ClearFormulaRefs() {
		LinkedList<Cell> refs = Language.getFormulaReferences(_inputValue,
				_parent.getEnvironment());

		for (Cell c : refs) {
			c.removeListener(this);
		}
	}

	private void AddFormulaRefs() {
		LinkedList<Cell> refs = Language.getFormulaReferences(_inputValue.substring(1),
				_parent.getEnvironment());

		for (Cell c : refs) {
			c.addListener(this);
		}
	}

	@Override
	public void update() {
		try {
			Object tokens = Parser.parse(_inputValue.substring(1));
			Object result = Language.eval(tokens, _parent.getEnvironment());

			_displayValue = result.toString();

		} catch (LangParseException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Static section, these variables are class variables
	 */
	public static final Border DEFAULT_BORDER;
	public static final Border DEFAULT_S_BORDER;

	public static final Color DEFAULT_BORDER_COLOR = Color.LIGHT_GRAY;
	public static final Color DEFAULT_S_BORDER_COLOR = Color.BLACK;
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	public static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
	public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 12);

	static {
		DEFAULT_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 1, DEFAULT_BORDER_COLOR);
		Border inner = BorderFactory.createLineBorder(DEFAULT_S_BORDER_COLOR, 1);
		DEFAULT_S_BORDER = BorderFactory.createCompoundBorder(DEFAULT_BORDER, inner);
	}
}
