package com.nuke.scheme.functions;

import com.nuke.scheme.Environment;

public class CoreFunctions {
	public static void addFunctions(Environment envr) {
		envr.putVar("cons", new CoreFunction(CONS, 2));
		envr.putVar("car", new CoreFunction(CAR, 1));
		envr.putVar("cdr", new CoreFunction(CDR, 1));
		envr.putVar("list", new CoreFunction(LIST, -1));
		envr.putVar("null?", new CoreFunction(NULLQ, 1));
		envr.putVar("null", null);
		envr.putVar("number?", new CoreFunction(NUMBERQ, 1));
	}

	private static final int CONS = 0;
	private static final int CAR = 1;
	private static final int CDR = 2;
	private static final int LIST = 3;
	private static final int NULLQ = 4;
	private static final int NUMBERQ = 5;

	private static class CoreFunction extends Function {
		private int _type;
		private int _argc;

		public CoreFunction(int type, int argc) {
			_type = type;
			_argc = argc;
		}

		@Override
		public Object apply(Object args) {
			if (_argc != -1) {
				validateArgCount(args, _argc);
			}
			Object x = first(args);
			Object y = second(args);

			switch (_type) {
			case CONS:
				return cons(x, y);
			case CAR:
				return first(x);
			case CDR:
				return rest(x);
			case LIST:
				return args;
			case NULLQ:
				return x == null;
			case NUMBERQ:
				return (x instanceof Number);
			default:
				return null;
			}
		}
	}
}
