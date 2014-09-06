package language;

public class LangArithmetic {

	public static Number add(Number n1, Number n2) {
		double result = n1.floatValue() + n2.floatValue();

		if ((n1 instanceof Double) || (n2 instanceof Double)) {
			return new Double(result);
		}
		return new Integer((int) result);
	}

	public static Number subtract(Number n1, Number n2) {
		double result = n1.floatValue() - n2.floatValue();

		if ((n1 instanceof Double) || (n2 instanceof Double)) {
			return new Double(result);
		}
		return new Integer((int) result);
	}

	public static Number multiply(Number n1, Number n2) {
		double result = n1.floatValue() * n2.floatValue();

		if ((n1 instanceof Double) || (n2 instanceof Double)) {
			return new Double(result);
		}
		return new Integer((int) result);
	}

	public static Number divide(Number n1, Number n2) {
		if ((n1 instanceof Double) || (n2 instanceof Double)) {
			double result = n1.floatValue() / n2.floatValue();
			return new Double(result);
		} else {
			int result = n1.intValue() / n2.intValue();
			return new Integer((int) result);
		}
	}

	public static Number negate(Number n1) {
		if (n1 instanceof Double) {
			return -((Number) n1).doubleValue();
		} else {
			return -((Number) n1).intValue();
		}
	}
}
