package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;
import com.nuke.scheme.core.LangUtil;
import com.nuke.scheme.core.Pair;

public class StatFunctions extends LangUtil {

   public static void addFunctions(Environment envr) {
      envr.putVar("+", ADD);
      envr.putVar("-", SUB);
      envr.putVar("*", MULT);
      envr.putVar("/", DIVIDE);
      envr.putVar("sum", ADD);
      envr.putVar("max", MAX);
      envr.putVar("min", MIN);
      envr.putVar("avg", AVERAGE);
      envr.putVar("mean", AVERAGE);
      envr.putVar("stddev", STDDEV);
      envr.putVar("variance", VARIANCE);
   }

   /**
    * Iterates over all the elements in the argument list, applies the iterate method to the
    * accumulator and each term. If there is only a single value, the singleTerm function is
    * returned. The firstTerm function determines the starting value of the accumulator.
    */
   private static abstract class MathFunction extends Function {
      @Override
      public Object apply(Object args) {
         Number accum;
         accum = firstTerm(num(first(args)));
         args = rest(args);

         if (args == null) {
            return singleTerm(accum);
         }

         while (args instanceof Pair) {
            accum = iterate(accum, num(first(args)));
            args = rest(args);
         }

         return accum;
      }

      protected Number singleTerm(Number accum) {
         return accum;
      }

      protected Number firstTerm(Number accum) {
         return accum;
      }

      protected abstract Number iterate(Number accum, Number val);
   }

   /**
    * Returns the sum of a list of numbers.
    */
   public static final Function ADD = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         return add(accum, val);
      }
   };

   /**
    * Returns the first term, minus all subsequent terms. If a single term, it negates it.
    */
   public static final Function SUB = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         return subtract(accum, val);
      }

      @Override
      protected Number singleTerm(Number accum) {
         return negate(accum);
      }
   };

   /**
    * Multiplies all the terms together.
    */
   public static final Function MULT = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         return multiply(accum, val);
      }
   };

   /**
    * Returns the first term divided by all subsequent terms. If a single term,
    * returns the inverse.
    */
   public static final Function DIVIDE = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         return divide(accum, val);
      }

      @Override
      protected Number singleTerm(Number accum) {
         return divide(1.0f, accum);
      }
   };

   /**
    * Returns the minimum of a list of numbers.
    */
   public static final Function MIN = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         double x = numDouble(accum);
         double y = numDouble(val);

         return (x > y) ? y : x;
      }
   };

   /**
    * Returns the maximum of a list of numbers.
    */
   public static final Function MAX = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         double x = numDouble(accum);
         double y = numDouble(val);

         return (x < y) ? y : x;
      }
   };

   /**
    * Returns the sum of each term squared [E (i * i)]
    */
   public static final Function ADD_SQUARED = new MathFunction() {
      @Override
      protected Number iterate(Number accum, Number val) {
         return add(accum, multiply(val, val));
      }

      @Override
      protected Number firstTerm(Number accum) {
         return multiply(accum, accum);
      }
   };

   /**
    * Return the average of a list of numbers.
    */
   public static final Function AVERAGE = new Function() {
      @Override
      public Object apply(Object args) {
         int length = length(args);
         Number sum = num(ADD.apply(args));
         return divide(sum, length);
      }
   };

   /**
    * Return the variance of a list of numbers.
    */
   public static final Function VARIANCE = new Function() {
      @Override
      public Object apply(Object args) {
         int length = length(args);
         Number sum = num(ADD.apply(args));
         Number sumSqr = num(ADD_SQUARED.apply(args));

         // (sumSqr - (sum * sum) / n)/n
         return divide(subtract(sumSqr, divide(multiply(sum, sum), length)), length);
      }
   };

   /**
    * Return the stddev of a list of numbers.
    */
   public static final Function STDDEV = new Function() {
      @Override
      public Object apply(Object args) {
         int length = length(args);
         Number sum = num(ADD.apply(args));
         Number sumSqr = num(ADD_SQUARED.apply(args));

         // (sumSqr - (sum * sum) / n)/n
         Number sumSquareByN = divide(multiply(sum, sum), length);
         Number variance = divide(subtract(sumSqr, sumSquareByN), length);
         return Math.sqrt(variance.doubleValue());
      }
   };
}
