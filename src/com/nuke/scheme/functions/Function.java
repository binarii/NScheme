package com.nuke.scheme.functions;

import com.nuke.scheme.core.LangUtil;

/**
 * A general interface for functions within the com.nuke.scheme. They are passed a list
 * of arguments that a already computed.
 */
public abstract class Function extends LangUtil {

   public abstract Object apply(Object args);
}
