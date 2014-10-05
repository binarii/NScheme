package com.nuke.scheme.functions;

import com.nuke.scheme.Environment;

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

   public static final Function CONS = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 2);
         return cons(first(args), second(args));
      }
   };

   public static final Function CAR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return first(args);
      }
   };

   public static final Function CAAR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return second(args);
      }
   };

   public static final Function CDR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return rest(args);
      }
   };

   public static final Function CDDR = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return rest(rest(args));
      }
   };

   public static final Function LIST = new Function() {
      @Override
      public Object apply(Object args) {
         return args;
      }
   };

   public static final Function NULLQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return first(args) == null;
      }
   };

   public static final Function NUMBERQ = new Function() {
      @Override
      public Object apply(Object args) {
         validateArgCount(args, 1);
         return (first(args) instanceof Number);
      }
   };
}
