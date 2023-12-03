import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

/*
==== To run the program run the following in terminal: ====
  1) "javac Dec05.java"
  2) "java Dec05 <part> < input.txt" - <part> = 1/2
*/

public class Dec05 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line;
    List<Deque<String>> stacks = new ArrayList<>();
    String type = args[0];

    // Populate stacks
    while (sc.hasNextLine()) {
      line = sc.nextLine();

      if (line.equals("")) {
        break;
      }

      for (int i=0; i<line.length()+1; i+=4) {
        if (i/4 >= stacks.size()) stacks.add(new ArrayDeque<>());
        if (line.charAt(i) == '[') stacks.get(i/4).addLast("" + line.charAt(i+1));
      }
    }

    // Make moves
    while (sc.hasNextLine()) {
      line = sc.nextLine();
      String[] data = line.split(" ");
      int num = Integer.parseInt(data[1]);
      int from = Integer.parseInt(data[3]) - 1;
      int to = Integer.parseInt(data[5]) - 1;

      if (type.equals("1")) {
        for (int i = 0; i<num; i++) {
          stacks.get(to).push(stacks.get(from).pop());
        }
      } else if (type.equals("2")) {
        Stack<String> temp = new Stack<>();
        for (int i = 0; i<num; i++) {
          temp.push(stacks.get(from).pop());
        }
        for (int i = 0; i<num; i++) {
          stacks.get(to).push(temp.pop());
        }
      }
    }
    StringBuilder sb = new StringBuilder();
    stacks.forEach(stack -> {
      if(stack.size() > 0) sb.append(stack.peek());
    });

    System.out.println("Answer for Part " + type + ": " + sb.toString());
  }
}