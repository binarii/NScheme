package com.nuke.scheme.functions;

import com.nuke.scheme.Environment;

import java.math.BigDecimal;
import java.util.Random;

public class GeneralFunctions {

   public static void addFunctions(Environment envr) {
      envr.putVar("abs", new GeneralFunction(ABS, 1));
      envr.putVar("ceil", new GeneralFunction(CEIL, 1));
      envr.putVar("floor", new GeneralFunction(FLOOR, 1));
      envr.putVar("sqrt", new GeneralFunction(SQRT, 1));
      envr.putVar("mod", new GeneralFunction(MOD, 2));
      envr.putVar("pow", new GeneralFunction(POW, 2));
      envr.putVar("rand", new GeneralFunction(RAND, 0));
      envr.putVar("int", new GeneralFunction(INT, 1));
      envr.putVar("float", new GeneralFunction(FLOAT, 1));
      envr.putVar("round", new GeneralFunction(ROUND, 2));
   }

   private static final int ABS = 0;
   private static final int MOD = 1;
   private static final int POW = 2;
   private static final int CEIL = 3;
   private static final int SQRT = 4;
   private static final int RAND = 5;
   private static final int FLOOR = 6;
   private static final int ROUND = 7;
   private static final int FLOAT = 8;
   private static final int INT = 9;

   private static class GeneralFunction extends Function {
      private int _type;
      private int _argCount;

      public GeneralFunction(int type, int argc) {
         _type = type;
         _argCount = argc;
      }

      @Override
      public Object apply(Object args) {
         validateArgCount(args, _argCount);
         Object x = first(args);
         switch (_type) {
            case ABS:
               return Math.abs(numDouble(x));
            case MOD:
               if (first(args) instanceof Integer && second(args) instanceof Integer) {
                  return numInt(x) % numInt(second(args));
               } else {
                  return numDouble(x) % numDouble(second(args));
               }
            case POW:
               return Math.pow(numDouble(x), numDouble(second(args)));
            case CEIL:
               return Math.ceil(numDouble(x));
            case SQRT:
               return Math.sqrt(numDouble(x));
            case RAND:
               return new Random().nextInt();
            case FLOOR:
               return Math.floor(numDouble(x));
            case ROUND:
               final int roundType = BigDecimal.ROUND_HALF_UP;
               BigDecimal bigdec = new BigDecimal(numDouble(first(args)));
               BigDecimal rounded = bigdec.setScale(numInt(second(args)), roundType);
               return rounded.doubleValue();
            case FLOAT:
               return numDouble(x);
            case INT:
               return numInt(x);
            default:
               return null;
         }
      }
   }
}
