package com.nuke.scheme.core;

public class Pair {

   public static final Pair NULL = new Pair(null, null);

   public Pair(Object a, Object b) {
      this.first = a;
      this.next = b;
   }

   public Object first;
   public Object next;

   @Override
   public String toString() {
      StringBuilder buf = new StringBuilder();
      LangUtil.stringify(this, false, buf);
      return buf.toString();
   }
}
