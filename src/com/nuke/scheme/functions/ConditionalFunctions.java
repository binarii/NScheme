package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;
import com.nuke.scheme.core.LangUtil;
import com.nuke.scheme.core.Pair;

public class ConditionalFunctions extends LangUtil {

   public static void addFunctions(Environment envr) {
      envr.putVar(">", GT);
      envr.putVar(">=", GTE);
      envr.putVar("<", LT);
      envr.putVar("<=", LTE);
      envr.putVar("=", EQUALS);
      envr.putVar("or", OR);
      envr.putVar("and", AND);
      envr.putVar("not", NOT);
   }

   /**
    * Return true if all numbers are greater than their predecessor.
    */
   public static final Function GT = new Function() {
      @Override
      public Object apply(Object args) {
         while (hasNext(rest(args))) {
            double x = numDouble(first(args));
            double y = numDouble(second(args));
            args = rest(args);

            if (x <= y) {
               return FALSE;
            }
         }
         return TRUE;
      }
   };

   /**
    * Return true if all numbers are greater than or equal to their predecessor.
    */
   public static final Function GTE = new Function() {
      @Override
      public Object apply(Object args) {
         while (hasNext(rest(args))) {
            double x = numDouble(first(args));
            double y = numDouble(second(args));
            args = rest(args);

            if (x < y) {
               return FALSE;
            }
         }
         return TRUE;
      }
   };

   /**
    * Return true if all numbers are less than their predecessor.
    */
   public static final Function LT = new Function() {
      @Override
      public Object apply(Object args) {
         while (hasNext(rest(args))) {
            double x = numDouble(first(args));
            double y = numDouble(second(args));
            args = rest(args);

            if (x >= y) {
               return FALSE;
            }
         }
         return TRUE;
      }
   };

   /**
    * Return true if all numbers are less than or equal to teir predecessor.
    */
   public static final Function LTE = new Function() {
      @Override
      public Object apply(Object args) {
         while (hasNext(rest(args))) {
            double x = numDouble(first(args));
            double y = numDouble(second(args));
            args = rest(args);

            if (x > y) {
               return FALSE;
            }
         }
         return TRUE;
      }
   };

   /**
    * Return true if all numbers are equal.
    */
   public static final Function EQUALS = new Function() {
      @Override
      public Object apply(Object args) {
         while (hasNext(rest(args))) {
            double x = numDouble(first(args));
            double y = numDouble(second(args));
            args = rest(args);

            if (x != y) {
               return FALSE;
            }
         }
         return TRUE;
      }
   };

   /**
    * Return true if any of the booleans are true.
    */
   public static final Function OR = new Function() {
      @Override
      public Object apply(Object args) {
         for (; hasNext(args); args = rest(args)) {
            if (bool(first(args))) {
               return TRUE;
            }
         }
         return FALSE;
      }
   };

   /**
    * Return true if all of the booleans are true.
    */
   public static final Function AND = new Function() {
      @Override
      public Object apply(Object args) {
         for (; hasNext(args); args = rest(args)) {
            if (!bool(first(args))) {
               return FALSE;
            }
         }
         return TRUE;
      }
   };

   /**
    * Return the complement of the boolean.
    */
   public static final Function NOT = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return !bool(first(args));
      }
   };
}
