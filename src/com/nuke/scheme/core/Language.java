package com.nuke.scheme.core;


import com.nuke.scheme.functions.*;

public class Language extends LangUtil {

   /**
    * The evaluate function recursively evaluates the arguments of the
    * function.
    */
   public static Object eval(Object x, Environment env) {
      while (true) {
         // If the object is a string, find its reference in the environment.
         if (x instanceof String) {
            String s = (String) x;
            Object result = env.findVar(s);
            return result;

         } else if (!(x instanceof Pair)) {
            // Must be an integer or double
            return x;
         } else {
            Object fn = first(x);
            Object args = rest(x);

            // Evaluate if differently
            if (fn.equals("if")) {
               validateArgCount(args, 3);

               Object test = first(args);
               Object conseq = second(args);
               Object alt = third(args);

               Boolean cond = (Boolean) Language.eval(test, env);
               x = cond ? conseq : alt;
            } else if (fn.equals("begin")) {
               while (rest(args) != null) {
                  // If rest is null we are on the last item
                  eval(first(args), env);
                  args = rest(args);
               }
               x = first(args);
            } else if (fn.equals("lambda")) {

               Object arguments = first(args);
               Object function = rest(args);

               return new Closure(arguments, function, env);
            } else if (fn.equals("set!")) {
               validateArgCount(args, 2);

               Object iden = first(args);
               Object value = Language.eval(second(args), env);
               env.putVar(str(iden), value);

               return value;
            } else if (fn.equals("define")) {
               validateArgCount(args, 2);

               Object iden = first(args);
               Object value = Language.eval(second(args), env);
               env.putVar(str(iden), value);

               return value;
            } else {
               /*
                * Otherwise the input is a linked list. In this case
					 * recursively evaluate all parameters and attempt to call
					 * the first argument (a function) on the rest of the
					 * parameters.
					 */
               fn = eval(fn, env);
               args = evalList(args, env);

               // Call the function specified
               if (fn instanceof Function) {
                  if (fn instanceof Closure) {
                     env = ((Closure) fn).bingArgs(args);
                     x = ((Closure) fn).getBody();
                  } else {
                     Function proc = (Function) fn;
                     return proc.apply(args);
                  }
               }
            }
         }
      }
   }

   protected static Pair evalList(Object list, Environment env) {
      if (list == null) {
         return null;
      } else if (!(list instanceof Pair)) {
         error("Illegal argument list");
         return null;
      } else {
         return cons(eval(first(list), env), evalList(rest(list), env));
      }
   }

   /**
    * Add the default variables to the specified environment.
    */
   public static void addDefaultVariables(Environment envr) {

      envr.putVar("#f", false);
      envr.putVar("#t", true);

      envr.putVar("PI", Math.PI);
      envr.putVar("EULER", Math.E);

      CoreFunctions.addFunctions(envr);
      ConditionalFunctions.addFunctions(envr);
      GeneralFunctions.addFunctions(envr);
      StatFunctions.addFunctions(envr);
      TrigFunctions.addFunctions(envr);
   }
}
