import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

/*
==== To run the program run the following in terminal: ====
  1) "javac Dec9.java"
  2) "java Dec9 <part> < input.txt" - <part> = 1/2
*/

public class Dec21 {
  private static double ans1 = 0;
  private static double ans2 = 0;

  private static class Monkey {
    public String id;
    public String[] names;
    public double[] vals;
    public String operation;
    public int found;
    public Monkey(String[] names, String operation, String id) {
      this.names = names;
      this.operation = operation;
      this.id = id;
      this.vals = new double[2];
      this.found = 0;
    }
  }

  private static class NumMonkey {
    public String name;
    public double val;
    public NumMonkey(String name, double val){
      this.name = name;
      this.val = val;
    }
  }

  private static double calcVal(double a, double b, String op) {
    double ret = 0;
    switch(op) {
      case "+":
        ret = a + b;
        break;
      case "-":
        ret = a - b;
        break;
      case "*":
        ret = a * b;
        break;
      default:
        ret = a / b;
    }
    return ret;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line, data, name;
    List<NumMonkey> numMonkeys = new ArrayList<>();
    List<Monkey> opMonkeys = new ArrayList<>();

    while (sc.hasNextLine()) {
      line = sc.nextLine();
      name = line.split(":")[0];
      data = line.split(":")[1];
      String[] info = data.split(" ");
      if (info.length > 2) {
        String[] names = {info[1], info[3]};
        opMonkeys.add(new Monkey(names, info[2], name));
      } else {
        numMonkeys.add(new NumMonkey(name, Double.parseDouble(info[1])));
      }
    }

    while(numMonkeys.size() > 0 && opMonkeys.size() > 0) {
      NumMonkey curr = numMonkeys.get(0);
      numMonkeys.remove(0);
      opMonkeys = opMonkeys.stream().filter(opMonkey -> {
        for (int i = 0; i < 2; i++) {
          if (opMonkey.names[i].equals(curr.name)) {
            opMonkey.vals[i] = curr.val;
            opMonkey.found++;
          }
        }

        if (opMonkey.found >= 2) {
          if (opMonkey.id.equals("root")) {
            ans1 = calcVal(opMonkey.vals[0], opMonkey.vals[1], opMonkey.operation);
          }
          numMonkeys.add(new NumMonkey(
            opMonkey.id,
            calcVal(opMonkey.vals[0], opMonkey.vals[1], opMonkey.operation)
          ));
        }

        return opMonkey.found < 2 ? true : false;
      }).collect(Collectors.toList());
    }

    System.out.println("Answer for Part 1: " + ans1);
    System.out.println("Answer for Part 2: ");
  }
}