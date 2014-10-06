package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;

public class TrigFunctions {

   public static final double PI = Math.PI;
   public static final double toDegrees = 180.0 * PI;
   public static final double toRadians = PI / 180.0;

   public static void addFunctions(Environment envr) {
      envr.putVar("exp", EXP);
      envr.putVar("log", LOG);

      envr.putVar("sin", SIN);
      envr.putVar("cos", COS);
      envr.putVar("tan", TAN);

      envr.putVar("sind", SIND);
      envr.putVar("cosd", COSD);
      envr.putVar("tand", TAND);
   }

   /**
    * Return e raised to the power of the argument.
    */
   private static final Function EXP = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.exp(numDouble(first(args)));
      }
   };

   /**
    * Return the base 10 logarithm of the argument.
    */
   public static final Function LOG = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.log(numDouble(first(args)));
      }
   };

   /**
    * Return the sine of the argument in radians.
    */
   private static final Function SIN = new Function(){
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.sin(numDouble(first(args)));
      }
   };

   /**
    * Return the sine of the argument in degrees.
    */
   private static final Function SIND = new Function(){
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.sin(numDouble(first(args)) * toDegrees);
      }
   };

   /**
    * Return the cosine of the argument in radians.
    */
   private static final Function COS = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.cos(numDouble(first(args)));
      }
   };

   /**
    * Return the cosine of the argument in radians.
    */
   private static final Function COSD = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.cos(numDouble(first(args)) * toDegrees);
      }
   };

   /**
    * Return the tangent of the argument in radians.
    */
   private static final Function TAN = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.tan(numDouble(first(args)));
      }
   };

   /**
    * Return the tangent of the argument in radians.
    */
   private static final Function TAND = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.tan(numDouble(first(args)) * toDegrees);
      }
   };
}
