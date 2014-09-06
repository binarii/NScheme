package language;

public class Pair {
	public Pair(Object a, Object b) {
		this.first = a;
		this.next = b;
	}

	public Object first;
	public Object next;

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		LangUtil.stringify(this, false, buf);
		return buf.toString();
	}
}
