package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;

import java.math.BigDecimal;
import java.util.Random;

public class GeneralFunctions {

   public static final Random rnd = new Random();

   public static void addFunctions(Environment envr) {
      envr.putVar("abs", ABS);
      envr.putVar("mod", MOD);
      envr.putVar("pow", POW);
      envr.putVar("int", INT);
      envr.putVar("float", FLOAT);
      envr.putVar("round", ROUND);
      envr.putVar("floor", FLOOR);
      envr.putVar("ceil", CEIL);
      envr.putVar("sqrt", SQRT);
      envr.putVar("rand", RAND);
      envr.putVar("length", LENGTH);
   }

   /**
    * Return the absolute value of the given number.
    */
   public static final Function ABS = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         Object x = first(args);
         return (numDouble(x) < 0) ? negate(num(x)) : x;
      }
   };

   /**
    * Return the modulus of the two parameters.
    */
   public static final Function MOD = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 2);
         Object x = first(args);
         Object y = second(args);

         if (x instanceof Integer && y instanceof Integer) {
            return numInt(x) % numInt(y);
         } else {
            return numDouble(x) % numDouble(y);
         }
      }
   };

   /**
    * Return the first argument raised the power of the second argument.
    */
   public static final Function POW = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 2);
         return Math.pow(numDouble(first(args)), numDouble(second(args)));
      }
   };

   /**
    * Round the given value up to the nearest integer (return float).
    */
   public static final Function CEIL = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.ceil(numDouble(first(args)));
      }
   };

   /**
    * Round the given value down to the nearest integer (return float).
    */
   public static final Function FLOOR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.floor(numDouble(first(args)));
      }
   };

   /**
    * Return the square root of the given parameter
    */
   public static final Function SQRT = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return Math.sqrt(numDouble(first(args)));
      }
   };

   /**
    * Return a random number in [0, 1).
    */
   public static final Function RAND = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 0);
         return rnd.nextFloat();
      }
   };

   /**
    * Return the given number cast as an integer.
    */
   public static final Function INT = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return numInt(first(args));
      }
   };

   /**
    * Return the given number cast as a float.
    */
   public static final Function FLOAT = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return numDouble(first(args));
      }
   };

   /**
    * Round the given value to the specified number of decimal places.
    */
   public static final Function ROUND = new Function() {
      @Override
      public Object apply(Object args) {
         final int roundType = BigDecimal.ROUND_HALF_UP;
         BigDecimal bigdec = new BigDecimal(numDouble(first(args)));
         BigDecimal rounded = bigdec.setScale(numInt(second(args)), roundType);
         return rounded.doubleValue();
      }
   };

   /**
    * Return the length of the argument as list
    */
   public static final Function LENGTH = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return length(first(args));
      }
   };
}
