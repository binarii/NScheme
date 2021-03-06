package com.nuke.scheme.core;

import java.util.HashMap;
import java.util.Map;

/**
 * The environment stores the variable references as well as the table and its
 * references to lookup.
 */
public class Environment {
   Environment _parent;
   HashMap<String, Object> _envrVariables;

   public Environment(Environment parent) {
      _parent = parent;
      _envrVariables = new HashMap<String, Object>();
   }

   public Object findVar(String s) {
      Object obj = _envrVariables.get(s);

      if (obj == null && _parent != null && !_envrVariables.containsKey(s)) {
         return _parent.findVar(s);
      }

      return obj;
   }

   /**
    * Place a new variable in the current environment, will override other
    * variables if they share the same name.
    */
   public void putVar(String k, Object v) {
      _envrVariables.put(k, v);
   }

   /**
    * Set the variable with given name to new value. This function will only
    * set the variable if it exists. The function attempts to find it in the
    * current environment. If the variable is not found, it will attempt to set
    * the variable of the next level higher.
    */
   public void setVar(String k, Object v) {
      Object obj = _envrVariables.get(k);
      if (obj == null) {
         _parent.setVar(k, v);
      } else { // else in this level, set variable
         _envrVariables.put(k, v);
      }
   }

   public Pair getVariables(Pair accum) {
      if(_parent != null) {
         accum = _parent.getVariables(accum);
      }

      for (Map.Entry<String, Object> entry : _envrVariables.entrySet()) {
         String key = entry.getKey();
         Object value = entry.getValue();

         accum = LangUtil.cons(LangUtil.cons(key, value), accum);
      }

      return accum;
   }
}
