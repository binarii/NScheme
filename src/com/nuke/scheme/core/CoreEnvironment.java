package com.nuke.scheme.core;

/**
 * Base environment. Cannot have a parent and will contain all system functions.
 */
public class CoreEnvironment extends Environment {
   public CoreEnvironment() {
      super(null);

      Language.addDefaultVariables(this);
   }
}
