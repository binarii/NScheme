package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;

public class TrigFunctions {

   public static void addFunctions(Environment envr) {
      envr.putVar("sin", SIN);
      envr.putVar("cos", COS);
      envr.putVar("tan", TAN);
   }

   private static final Function SIN = new Function(){
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.sin(numDouble(first(args)));
      }
   };

   private static final Function COS = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.cos(numDouble(first(args)));
      }
   };

   private static final Function TAN = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.tan(numDouble(first(args)));
      }
   };
}
