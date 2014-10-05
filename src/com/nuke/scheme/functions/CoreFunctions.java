package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;

public class CoreFunctions {
   public static void addFunctions(Environment envr) {
      envr.putVar("cons", CONS);
      envr.putVar("car", CAR);
      envr.putVar("caar", CAAR);
      envr.putVar("cdr", CDR);
      envr.putVar("cddr", CDDR);
      envr.putVar("list", LIST);
      envr.putVar("null?", NULLQ);
      envr.putVar("null", null);
      envr.putVar("number?", NUMBERQ);
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
    * Boolean comparison checking if the input is null.
    */
   public static final Function NULLQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return first(args) == null;
      }
   };

   /**
    * Boolean comparison checking if the input is a number.
    */
   public static final Function NUMBERQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return (first(args) instanceof Number);
      }
   };
}
