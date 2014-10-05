package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;

public class TrigFunctions {

   public static void addFunctions(Environment envr) {
      envr.putVar("sin", new SinFunction());
      envr.putVar("cos", new CosFunction());
      envr.putVar("tan", new TanFunction());
   }

   private static class SinFunction extends Function {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.sin(numDouble(first(args)));
      }
   }

   private static class CosFunction extends Function {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.cos(numDouble(first(args)));
      }
   }

   private static class TanFunction extends Function {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.tan(numDouble(first(args)));
      }
   }
}
