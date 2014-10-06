package com.nuke.scheme.cli;

import com.nuke.scheme.core.*;

import java.util.Scanner;

/**
 * Created by ryanzuklie on 10/5/14.
 */
public class SchemeCLI {

   public static void main(String[] args) {
      Environment env = new CoreEnvironment();
      Scanner scanner = new Scanner(System.in);
      StringBuilder out = new StringBuilder();
      String line;

      while(!(line = scanner.nextLine()).equalsIgnoreCase("quit")) {
         while(parenMatch(line) > 0) {
            line = line + " " + scanner.nextLine();
         }

         if(line.isEmpty()) {
            continue;
         }

         Object tokens = Parser.parse(line);
         Object result = Language.eval(tokens, env);

         out.setLength(0); // Clear buffer
         LangUtil.stringify(result, false, out);

         System.out.println(out.toString());
      }
   }

   public static int parenMatch(String s) {
      int count = 0;
      int length = s.length();

      for(int i = 0; i < length; i++) {
         if(s.charAt(i) == '(') {
            count++;
         } else if (s.charAt(i) == ')') {
            count--;
         }
      }
      return count;
   }
}
