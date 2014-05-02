package language.functions;

import language.Environment;
import language.Language;
import language.Pair;

/**
 * The user defined function is a list that represents the parsed input and the
 * argument list that needs to be bound when the funciton is called. In order to
 * call the function, bind the parameters sequentially to the argument list in a
 * new environment and evaluate the linked list with these new definitions.
 */
public class Closure extends Function {

	protected Object _vars;
	protected Object _funct;
	protected Environment _parent;

	public Closure(Object vars, Object func, Environment parent) {
		_vars = vars;
		_funct = func;
		_parent = parent;
	}

	@Override
	public Object apply(Object vals) {
		Environment temp = bingArgs(vals);

		return Language.eval(_funct, temp);
	}

	public Environment bingArgs(Object vals) {
		validateArgCount(vals, length(_vars));
		Environment temp = new Environment(_parent);

		Object vars = _vars;
		while (vars instanceof Pair) {
			String iden = str(first(vars));
			temp.putVar(iden, first(vals));
			vars = rest(vars);
			vals = rest(vals);
		}
		return temp;
	}

	public Object getBody() {
		return _funct;
	}

	@Override
	public String toString() {
		return _funct.toString();
	}
}
