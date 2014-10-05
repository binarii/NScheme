package com.nuke.scheme.core;

import java.util.LinkedList;

public class LangUtil extends LangArithmetic {

   public static final Boolean TRUE = Boolean.TRUE;
   public static final Boolean FALSE = Boolean.FALSE;

   public static Number num(Object x) {
      if (x instanceof Number) {
         return (Number) x;
      } else {
         return num(error("Expected a number, got: " + x));
      }
   }

   public static double numDouble(Object x) {
      if (x instanceof Number) {
         return ((Number) x).doubleValue();
      } else {
         return numDouble(error("Expected a number, got: " + x));
      }
   }

   public static int numInt(Object x) {
      if (x instanceof Number) {
         return ((Number) x).intValue();
      } else {
         return numInt(error("Expected a number, got: " + x));
      }
   }

   public static boolean bool(Object x) {
      if (x instanceof Boolean) {
         return (Boolean) x;
      } else {
         return bool(error("Expected a boolean, got: " + x));
      }
   }

   public static Pair pair(Object x) {
      if (x instanceof Pair) {
         return (Pair) x;
      } else {
         return pair(error("Expected a pair, got: " + x));
      }
   }

   public static String str(Object x) {
      if (x instanceof String) {
         return (String) x;
      } else {
         return str(error("Expected a string, got: " + x));
      }
   }

   public static Object error(String message) {
      System.err.println("ERROR: " + message);
      throw new RuntimeException(message);
   }

   public static void validateArgCount(Object x, int desired) {
      int length = length(x);
      if (length != desired)
         error("argument count mismatch, expected " + desired + " got " + length);
   }

   public static int length(Object x) {
      int count = 0;

      while (x instanceof Pair) {
         count++;
         x = ((Pair) x).next;
      }

      return count;
   }

   public static Object first(Object x) {
      return (x instanceof Pair) ? ((Pair) x).first : null;
   }

   public static Object second(Object x) {
      return first(rest(x));
   }

   public static Object third(Object x) {
      return first(rest(rest(x)));
   }

   public static Object rest(Object x) {
      return (x instanceof Pair) ? ((Pair) x).next : null;
   }

   public static Pair cons(Object a, Object b) {
      return new Pair(a, b);
   }

   public static Pair list(Object a) {
      return new Pair(a, null);
   }

   public static Pair list(Object a, Object b) {
      return new Pair(a, new Pair(b, null));
   }

   public static Pair reverse(Object x) {
      Pair result = null;
      while (x instanceof Pair) {
         result = cons(first(x), result);
         x = ((Pair) x).next;
      }
      return result;
   }

   public static void stringify(Object x, boolean quoted, StringBuilder buf) {
      if (x == null) {
         buf.append("()");
      } else if (x instanceof Number) {
         buf.append(x);
      } else if (x instanceof String) {
         if (quoted) {
            buf.append("\"");
            buf.append(x);
            buf.append("\"");
         } else {
            buf.append(x);
         }
      } else if (x instanceof Boolean) {
         if ((Boolean) x) {
            buf.append("#t");
         } else {
            buf.append("#f");
         }
      } else if (x instanceof Pair) {
         stringifyPair((Pair) x, quoted, buf);
      }
   }

   public static void stringifyPair(Pair x, boolean quoted, StringBuilder buf) {
      buf.append("[");
      stringify(first(x), quoted, buf);
      Object tail = rest(x);

      while (tail instanceof Pair) {
         buf.append(", ");
         stringify(((Pair) tail).first, quoted, buf);
         tail = ((Pair) tail).next;
      }
      buf.append("]");
   }

   public static Pair listToPair(LinkedList<Object> list) {
      if (list.size() == 0) {
         return null;
      } else {
         return new Pair(list.pop(), listToPair(list));
      }
   }
}
