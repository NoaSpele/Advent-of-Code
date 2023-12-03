import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;
import java.math.BigInteger;

/*
==== To run the program run the following in terminal: ====
  1) "javac Dec17.java"
  2) "java Dec17 < input.txt"
*/

public class Dec17 {
  private static class Shape {
    public int height;
    public List<Integer[]> positions;

    public Shape(int height, List<Integer[]> positions) {
      this.height = height;
      this.positions = positions;
    }

    public boolean canMove(int x, int y, Map<Integer, Set<Integer>> state) {
      for (Integer[] pos : positions) {
        if ((pos[0]+x > 6) || pos[0]+x < 0) return false;
        if (state.containsKey(pos[0] + x)) {
          if (state.get(pos[0] + x).contains(pos[1]+y)) {
            return false;
          }
        }
      };
      return true;
    }

    public void move(int x, int y, Map<Integer, Set<Integer>> state) {
      for (Integer[] pos: positions) {
        if(!state.containsKey(pos[0] + x)) {
          state.put(pos[0] + x, new HashSet<>());
        }
        state.get(pos[0] + x).add(pos[1] + y);
      };
    }
  }

  private static Shape[] createShapes() {
    // ####
    Integer[] p1 = {0,0}, p2 = {1,0}, p3 = {2,0}, p4 = {3,0};
    Shape s1 = new Shape(1, List.of(p1, p2, p3, p4));

    /*.#.
      ###
      .#.*/
    p1 = new Integer[]{1,2}; p2 = new Integer[]{0,1};
    p3 = new Integer[]{1,1}; p4 = new Integer[]{2,1};
    Integer[] p5 = {1,0};
    Shape s2 = new Shape(3, List.of(p1, p2, p3, p4, p5));

    /*..#
      ..#
      ###*/
    p1 = new Integer[]{2,2}; p2 = new Integer[]{2,1}; p3 = new Integer[]{0,0};
    p4 = new Integer[]{1,0}; p5 = new Integer[]{2,0};
    Shape s3 = new Shape(3, List.of(p1, p2, p3, p4, p5));

    /*#
      #
      #
      #*/
    p1 = new Integer[]{0,0}; p2 = new Integer[]{0,1};
    p3 = new Integer[]{0,2}; p4 = new Integer[]{0,3};
    Shape s4 = new Shape(4, List.of(p1, p2, p3, p4));

    /*##
      ##*/
    p1 = new Integer[]{0,1}; p2 = new Integer[]{1,1};
    p3 = new Integer[]{0,0}; p4 = new Integer[]{1,0};
    Shape s5 = new Shape(2, List.of(p1, p2, p3, p4));
    return new Shape[]{s1, s2, s3, s4, s5};
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Shape[] shapes = createShapes();
    Map<Integer, Set<Integer>> state = new HashMap<>();
    int height = 0, turn = 0, shapeIndex = 0;
    double numDroped = 0, upperLimit = 1000000000000.0;
    double ans1 = 0, ans2 = 0;
    List<Integer> hChange = new ArrayList<>();

    String line = sc.nextLine();
    int[] moves = new int[line.length()];
    for (int i = 0; i < moves.length; i++) {
      moves[i] = line.charAt(i) == '<' ? -1 : 1;
    }

    Shape currShape = shapes[0];
    int currMove = moves[0];
    int currX = 2, currHeight = 3;
    while (numDroped < upperLimit) {
      currMove = moves[turn];
      if (currShape.canMove(currX + currMove, currHeight, state)) {
        currX += currMove;
      }

      if (currHeight > 0 && currShape.canMove(currX, currHeight-1, state)) {
        currHeight--;
      } else {
        numDroped++;
        shapeIndex++;
        if (shapeIndex > 4) shapeIndex = 0;
        currShape.move(currX, currHeight, state);
        int newHeight = Math.max(height, currHeight + currShape.height);
        // To print answer to part 1 and to get into repeating pattern
        if (numDroped > 2022) {
          hChange.add(newHeight - height);
        }
        height = newHeight;
        if (numDroped == 2022) ans1 = newHeight;
        // Ensure at least one cycle of looping through turns
        if (hChange.size() > 40 && (hChange.size() % 2 == 0)) {
          boolean foundPattern = true;
          int half = hChange.size()/2;
          int hsum = 0;
          for (int i = 0; i < half; i++) {
            if (hChange.get(i) != hChange.get(half + i)) {
              foundPattern = false;
              break;
            }
            hsum += hChange.get(i);
          }
          if (foundPattern) {
            double modulo = ((upperLimit - numDroped) % half);
            double numRepetitions = ((upperLimit - numDroped - modulo) / half);
            double hsum2 = 0;
            for (int i = 0; i < modulo; i++) {
              hsum2 += hChange.get(i);
            }
            ans2 = hsum2 + numRepetitions * hsum + newHeight;
            break;
          }
        }
        currHeight = height + 3;
        currShape = shapes[shapeIndex];
        currX = 2;
      }
      turn++;
      if(turn > (line.length() - 1)) turn = 0;
    }

    System.out.println("Answer for Part 1: " + ans1);
    System.out.println("Answer for Part 2: " + ans2);
  }
}