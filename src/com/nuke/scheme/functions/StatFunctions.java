package com.nuke.scheme.functions;

import com.nuke.scheme.core.Environment;
import com.nuke.scheme.core.LangUtil;
import com.nuke.scheme.core.Pair;

public class StatFunctions extends LangUtil {

   public static void addFunctions(Environment envr) {
      envr.putVar("+", new StatFunction(PLUS));
      envr.putVar("-", new StatFunction(MINUS));
      envr.putVar("*", new StatFunction(MULT));
      envr.putVar("/", new StatFunction(DIVIDE));
      envr.putVar("sum", new StatFunction(PLUS));
      envr.putVar("max", new StatFunction(MAX));
      envr.putVar("min", new StatFunction(MIN));
      envr.putVar("avg", new AverageFunction());
      envr.putVar("mean", new AverageFunction());
      envr.putVar("variance", new VarianceFunction());
      envr.putVar("stddev", new StddevFunction());
   }

   private static final int PLUS = 0;
   private static final int MINUS = 1;
   private static final int MULT = 2;
   private static final int DIVIDE = 3;
   private static final int MIN = 4;
   private static final int MAX = 5;
   private static final int PLUSSQUARE = 6;

   public static Object applyCompute(Object args, int op, Number result) {
      if (args == null) {
         switch (op) {
            case MINUS:
               return negate(result);
            case DIVIDE:
               return divide(1, result);
            default:
               return result;
         }
      } else {
         while (args instanceof Pair) {
            if (first(args) instanceof Pair && op == PLUS) {
               result = num(applyCompute(first(args), PLUS, result));
               args = rest(args);
               continue;
            }

            Number val = num(first(args));
            args = rest(args);

            switch (op) {
               case PLUS:
                  result = add(result, val);
                  break;
               case MINUS:
                  result = subtract(result, val);
                  break;
               case MULT:
                  result = multiply(result, val);
                  break;
               case DIVIDE:
                  result = divide(result, val);
                  break;
               case MIN:
                  double a = result.doubleValue();
                  double b = val.doubleValue();
                  result = (b < a) ? val : result;
                  break;
               case MAX:
                  double c = result.doubleValue();
                  double d = val.doubleValue();
                  result = (d > c) ? val : result;
                  break;
               case PLUSSQUARE:
                  result = add(result, multiply(val, val));
            }
         }
      }
      return result;
   }

   private static class StatFunction extends Function {
      private int _type;

      public StatFunction(int type) {
         _type = type;
      }

      @Override
      public Object apply(Object args) {
         Object x = first(args);
         Object y = rest(args);
         switch (_type) {
            case PLUS:
               return applyCompute(args, PLUS, 0);
            case MINUS:
               return applyCompute(y, MINUS, num(x));
            case MULT:
               return applyCompute(args, MULT, 1);
            case DIVIDE:
               return applyCompute(y, DIVIDE, num(x));
            case MIN:
               return applyCompute(args, MIN, num(x));
            case MAX:
               return applyCompute(args, MAX, num(x));
            default:
               return null;
         }
      }
   }

   private static class AverageFunction extends Function {
      @Override
      public Object apply(Object args) {
         int length = length(args);
         Number sum = num(applyCompute(args, PLUS, 0));
         return divide(sum, length);
      }
   }

   private static class VarianceFunction extends Function {
      @Override
      public Object apply(Object args) {
         int length = length(args);
         Number sum = num(applyCompute(args, PLUS, 0));
         Number sumSqr = num(applyCompute(args, PLUSSQUARE, 0));

         // (sumSqr - (sum * sum) / n)/n
         return divide(subtract(sumSqr, divide(multiply(sum, sum), length)), length);
      }
   }

   private static class StddevFunction extends Function {
      @Override
      public Object apply(Object args) {
         int length = length(args);
         Number sum = num(applyCompute(args, PLUS, 0));
         Number sumSqr = num(applyCompute(args, PLUSSQUARE, 0));

         // (sumSqr - (sum * sum) / n)/n
         Number sumSquareByN = divide(multiply(sum, sum), length);
         Number variance = divide(subtract(sumSqr, sumSquareByN), length);
         return Math.sqrt(variance.doubleValue());
      }
   }
}
