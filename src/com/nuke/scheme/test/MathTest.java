package com.nuke.scheme.test;

import com.nuke.scheme.core.*;

public class MathTest {
   public static final String testInput = "(begin " +
           "(define close (lambda (v a d) (< (abs (- v a)) d)))" +
           "(list " +
           "(= (+ 5 5 2 3) 15) " +
           "(= (- 25 5 3) 17) " +
           "(= (- 5) -5) " +
           "(= (- -5) 5) " +
           "(= (* 5 5) 25) " +
           "(= (/ 25 4) 6) " +
           "(close (variance 1 2 3 4 8) 5.84 0.1) " +
           "(close (stddev 1 2 3 4 8) 2.416 0.01) " +
           "(close (mean 2 4 6 10) 5.5 0.01) " +
           "(= (max 1 55 2 3 8 0) 55) " +
           "(= (min 1 55 2 3 8 0) 0) " +
           "(= (abs -5) 5) " +
           "(= (abs 5) 5) " +
           "(= (pow 2 5) 32) " +
           "(close (floor 3.8) 3 0.001) " +
           "(close (ceil 3.2) 4.0 0.001) " +
           "(close (sqrt 2) 1.4 0.1) " +
           ")" + ")";

   public static void main(String[] args) {
      double evalTime, parseTime;
      Timer timer = new Timer();

      Environment env = new CoreEnvironment();
      StringBuilder out = new StringBuilder();

      timer.startTimer();
      Object tokens = Parser.parse(testInput);
      parseTime = timer.stopTimer();

      timer.startTimer();
      Object result = Language.eval(tokens, env);
      LangUtil.stringify(result, true, out);
      evalTime = timer.stopTimer();

      System.out.println(out);

      timer.stopTimer();
      System.out.println("Test completed in: " + (evalTime + parseTime) + "ms");
      System.out.println("Parsing took " + parseTime + "ms");
      System.out.println("Evaluating took " + evalTime + "ms");
   }
}
