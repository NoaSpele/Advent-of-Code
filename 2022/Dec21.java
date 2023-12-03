import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

/*
==== To run the program run the following in terminal: ====
  1) "javac Dec21.java"
  2) "java Dec21 < input.txt"
*/

public class Dec21 {
  private static class Monkey {
    public String id;
    public String[] names;
    public Long[] vals;
    public String operation;
    public int found;
    public Monkey(String[] names, String operation, String id) {
      this.names = names;
      this.operation = operation;
      this.id = id;
      this.vals = new Long[2];
      this.found = 0;
    }
  }

  private static class NumMonkey {
    public String name;
    public Long val;
    public NumMonkey(String name, Long val){
      this.name = name;
      this.val = val;
    }
  }

  private static Long calcVal(Long a, Long b, String op) {
    Long ret = 0L;
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

  private static Long calcInv(Long ans, Long val, String op) {
    Long ret;
    switch(op) {
      case "+":
        ret = ans - val;
        break;
      case "-":
        ret = ans + val;
        break;
      case "*":
        ret = ans / val;
        break;
      default:
        ret = ans * val;
    }
    return ret;
  }

  private static Monkey findMonkey(String name, List<Monkey> list) {
    for (Monkey m: list) {
      if (m.id.equals(name)) {
        return m;
      }
    }
    return null;
  }

  private static Long solveEq(Monkey m, Long ans, List<Monkey> list) {
    Long val = null;
    String nextMonkey = null;
    int pos;
    if (m.vals[0] != null) {
      val = m.vals[0];
      nextMonkey = m.names[1];
      pos = 2;
    } else {
      val = m.vals[1];
      nextMonkey = m.names[0];
      pos = 1;
    }

    if (nextMonkey.equals("humn")) {
      if ((m.operation.equals("/") || m.operation.equals("-")) && pos == 2) {
        return calcVal(ans, ans, m.operation);
      }
      return calcInv(ans, val, m.operation);
    }

    if ((m.operation.equals("/") || m.operation.equals("-")) && pos == 2) {
      return solveEq(findMonkey(nextMonkey, list), calcVal(val, ans, m.operation), list);
    }
    return solveEq(findMonkey(nextMonkey, list), calcInv(ans, val, m.operation), list);
  }

  private static Long getAns(List<NumMonkey> numMonkeys, List<Monkey> opMonkeys) {
    List<NumMonkey> orgNumMonkeys = new ArrayList<>(numMonkeys);
    List<Monkey> orgOpMonkeys = new ArrayList<>(opMonkeys);

    while(numMonkeys.size() > 0 && opMonkeys.size() > 0) {
      NumMonkey curr = numMonkeys.get(0);
      numMonkeys.remove(0);
      for (int j = opMonkeys.size()-1; j >= 0; j--) {
        Monkey opMonkey = opMonkeys.get(j);
        for (int i = 0; i < 2; i++) {
          if (opMonkey.names[i].equals(curr.name)) {
            opMonkey.vals[i] = curr.val;
            opMonkey.found++;
          }
        }

        if (opMonkey.found >= 2) {
          if (opMonkey.id.equals("root")) {
            return calcVal(opMonkey.vals[0], opMonkey.vals[1], opMonkey.operation);
          }
          numMonkeys.add(new NumMonkey(
            opMonkey.id,
            calcVal(opMonkey.vals[0], opMonkey.vals[1], opMonkey.operation)
          ));
        }

        if (opMonkey.found >= 2) {
          opMonkeys.remove(j);
        }
      }
    }

    Monkey root = findMonkey("root", opMonkeys);
    Long solved;
    String next;
    if (root.vals[0] == null) {
      solved = root.vals[1];
      next = root.names[0];
    } else {
      solved = root.vals[0];
      next = root.names[1];
    }
    return solveEq(findMonkey(next, opMonkeys), solved, opMonkeys);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line, data, name;
    List<NumMonkey> numMonkeys1 = new ArrayList<>();
    List<Monkey> opMonkeys1 = new ArrayList<>();
    List<NumMonkey> numMonkeys2 = new ArrayList<>();
    List<Monkey> opMonkeys2 = new ArrayList<>();

    while (sc.hasNextLine()) {
      line = sc.nextLine();
      name = line.split(":")[0];
      data = line.split(":")[1];
      String[] info = data.split(" ");
      if (info.length > 2) {
        String[] names1 = {info[1], info[3]};
        String[] names2 = {info[1], info[3]};
        opMonkeys1.add(new Monkey(names1, info[2], name));
        opMonkeys2.add(new Monkey(names2, info[2], name));
      } else {
        numMonkeys1.add(new NumMonkey(name, Long.parseLong(info[1])));
        if (!name.equals("humn")) {
          numMonkeys2.add(new NumMonkey(name, Long.parseLong(info[1])));
        }
      }
    }

    Long ans1 = getAns(numMonkeys1, opMonkeys1);
    Long ans2 = getAns(numMonkeys2, opMonkeys2);

    System.out.println("Answer for Part 1: " + ans1);
    System.out.println("Answer for Part 2: " + ans2);
  }
}