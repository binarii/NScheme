package com.nuke.scheme.functions;

import com.nuke.scheme.core.LangUtil;

/**
 * A general interface for functions within language. They are passed a list
 * of arguments that are already computed.
 */
public abstract class Function extends LangUtil {

   public abstract Object apply(Object args);
}
