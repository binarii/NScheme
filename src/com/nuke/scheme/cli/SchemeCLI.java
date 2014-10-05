package com.nuke.scheme.cli;

import com.nuke.scheme.core.Environment;
import com.nuke.scheme.core.LangUtil;
import com.nuke.scheme.core.Language;
import com.nuke.scheme.core.Parser;

import java.util.Scanner;

/**
 * Created by ryanzuklie on 10/5/14.
 */
public class SchemeCLI {

   public static void main(String[] args) {
      Environment env = new Environment(null);
      Scanner scanner = new Scanner(System.in);
      StringBuilder out = new StringBuilder();
      String line;


      Language.addDefaultVariables(env);

      while(!(line = scanner.nextLine()).equalsIgnoreCase("quit")) {
         Object tokens = Parser.parse(line);
         Object result = Language.eval(tokens, env);

         out.setLength(0); // Clear buffer
         LangUtil.stringify(result, false, out);

         System.out.println(out.toString());
      }


   }
}
