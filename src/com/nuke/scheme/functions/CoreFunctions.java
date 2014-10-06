package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;
import com.nuke.scheme.core.Pair;

public class CoreFunctions {
   public static void addFunctions(Environment envr) {
      envr.putVar("cons", CONS);
      envr.putVar("car", CAR);
      envr.putVar("caar", CAAR);
      envr.putVar("cdr", CDR);
      envr.putVar("cddr", CDDR);
      envr.putVar("list", LIST);
      envr.putVar("null", Pair.NULL);
      envr.putVar("null?", NULLQ);
      envr.putVar("pair?", PAIRQ);
      envr.putVar("number?", NUMBERQ);
      envr.putVar("boolean?", BOOLEANQ);
      envr.putVar("procedure?", PROCEDUREQ);
   }

   /**
    * Return a pair consisting of the first and second argument.
    */
   public static final Function CONS = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 2);
         return cons(first(args), second(args));
      }
   };

   /**
    * Return the first element of a list.
    */
   public static final Function CAR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return first(args);
      }
   };

   /**
    * Return the second element of a list.
    */
   public static final Function CAAR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return second(args);
      }
   };

   /**
    * Return the rest of the list.
    */
   public static final Function CDR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return rest(args);
      }
   };

   /**
    * Return the rest of the rest of the list.
    */
   public static final Function CDDR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return rest(rest(args));
      }
   };

   /**
    * Return a list composed of the arguments.
    */
   public static final Function LIST = new Function() {
      @Override
      public Object apply(Object args) {
         return args;
      }
   };

   /**
    * Return true if the argument is null.
    */
   public static final Function NULLQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return first(args) == Pair.NULL;
      }
   };

   /**
    * Return true if the type of the argument is number.
    */
   public static final Function NUMBERQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return (first(args) instanceof Number);
      }
   };

   /**
    * Return true if the type of the argument is boolean.
    */
   public static final Function BOOLEANQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return (first(args) instanceof Boolean);
      }
   };

   /**
    * Return true if the type of the argument is pair.
    */
   public static final Function PAIRQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return (first(args) instanceof Pair);
      }
   };

   /**
    * Return true if the type of the argument is function.
    */
   public static final Function PROCEDUREQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return (first(args) instanceof Function);
      }
   };
}
