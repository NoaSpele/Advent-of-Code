import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.Math;

/*
==== To run the program run the following in terminal: ====
  1) "javac Dec25.java"
  2) "java Dec25 <part> < input.txt" - <part> = 1/2
*/

public class Dec25 {
  private static long getValFromChar(char c) {
    if (c == '-') return -1;
    if (c == '=') return -2;
    return c - '0';
  }

  private static char getCharFromVal(long v) {
    if (v == 4) return '-';
    if (v == 3) return '=';
    return (char) (v + '0');
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line;

    Long sum = 0L;
    // Sum values SNAFU -> dec
    while (sc.hasNextLine()) {
      line = sc.nextLine();
      for (int i = 0; i < line.length(); i++) {
        sum += getValFromChar(line.charAt(i)) * (long) Math.pow(5, line.length() - (i+1));
      }
    }

    // Find out string dec -> SNAFU
    int coef = 0;
    while (sum >= Math.pow(5,coef+1)) coef++;
    ArrayList<Long> vals = new ArrayList<>();
    vals.add(0L);
    while (coef >= 0) {
      long fac = sum / (long) Math.pow(5, coef);
      sum = sum % (long) Math.pow(5, coef);
      vals.add(fac);
      coef--;
    }

    String out = "";
    for (int i = vals.size()-1; i > 0; i--) {
      if (vals.get(i) > 2) vals.set(i-1, vals.get(i-1)+1);
      out = getCharFromVal(vals.get(i) % 5L) + out;
    }
    if (vals.get(0) > 0) out = '1' + out;

    System.out.println("Answer for Part 1: " + out);
    System.out.println("Answer for Part 2: ");
  }
}