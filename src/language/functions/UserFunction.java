package language.functions;

import java.util.LinkedList;

import language.Environment;
import language.Language;
import language.exception.ArgumentTypeException;
import language.exception.LangParseException;

public class UserFunction implements Function {
	
	LinkedList<Object> _args;
	Object _funct;
	Environment _parent;
	
	public UserFunction(LinkedList<Object> args, Object func, Environment parent) {
		_args = args;
		_funct = func;
		_parent = parent;
	}

	@Override
	public Object eval(LinkedList<Object> args) throws LangParseException {
		LangMath.validateParamCount(args, _args.size());
		Environment temp = new Environment(_parent);
		
		// Create temporary environment
		for(int i = 0; i < _args.size(); i++) {
			Object obj = _args.get(i);
			Object value = args.get(i);
			if(obj instanceof String) {
				String s = (String)obj;
				temp.putVar(s, value);
			} else {
				throw new ArgumentTypeException(String.class, obj.getClass(), 0);
			}
		}
		
		return Language.eval(_funct, temp);
	}

	@Override
	public String toString() {
		return _funct.toString();
	}

}