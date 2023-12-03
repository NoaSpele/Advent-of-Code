import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

/*
==== To run the program run the following in terminal: ====
  1) "javac Dec09.java"
  2) "java Dec09 <part> < input.txt" - <part> = 1/2
*/

public class Dec09 {
  private static int[] move(int[] toMove, String move) {
    switch(move) {
    case "R":
      toMove[0]++;
      break;
    case "L":
      toMove[0]--;
      break;
    case "U":
      toMove[1]++;
      break;
    case "D":
      toMove[1]--;
      break;
    }
    return toMove;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    List<String> moves = new ArrayList<>();
    List<String> tPositions = new ArrayList<>();
    List<int[]> positions = new ArrayList<int[]>();
    int numParts = (args[0].equals("1") ? 2 : 10);
    for (int i = 0; i<numParts; i++) {
      int[] startPos = {0,0};
      positions.add(startPos);
    }
    tPositions.add("0,0");
    String line;

    // Populate stacks
    while (sc.hasNextLine()) {
      line = sc.nextLine();
      moves.add(line.split(" ")[0]);
      moves.add(line.split(" ")[1]);
    }

    for (int i=0; i<moves.size()/2; i++) {
      int num = Integer.parseInt(moves.get(i*2+1));
      for (int j=0; j<num; j++) {
        move(positions.get(0), moves.get(i*2));
        for (int k = 1; k<positions.size(); k++) {
          int[] hPos = positions.get(k-1);
          int[] tPos = positions.get(k);
          if ((Math.abs(tPos[0] - hPos[0]) > 1) || (Math.abs(tPos[1] - hPos[1]) > 1)) {
            if ((Math.abs(tPos[0] - hPos[0]) > 1) && (Math.abs(tPos[1] - hPos[1]) == 0)) {
              if (tPos[0] > hPos[0]) {
                tPos[0]--;
              } else {
                tPos[0]++;
              }
            } else if ((Math.abs(tPos[1] - hPos[1]) > 1) && (Math.abs(tPos[0] - hPos[0]) == 0)) {
              if (tPos[1] > hPos[1]) {
                tPos[1]--;
              } else {
                tPos[1]++;
              }
            } else if (Math.abs(tPos[0] - hPos[0]) > 1) {
              if (tPos[0] > hPos[0]) {
                if (tPos[1] > hPos[1]) {
                  tPos[1]--;
                } else {
                  tPos[1]++;
                }
                tPos[0]--;
              } else {
                if (tPos[1] > hPos[1]) {
                  tPos[1]--;
                } else {
                  tPos[1]++;
                }
                tPos[0]++;
              }
            } else if (Math.abs(tPos[1] - hPos[1]) > 1) {
              if (tPos[1] > hPos[1]) {
                if (tPos[0] > hPos[0]) {
                  tPos[0]--;
                } else {
                  tPos[0]++;
                }
                tPos[1]--;
              } else {
                if (tPos[0] > hPos[0]) {
                  tPos[0]--;
                } else {
                  tPos[0]++;
                }
                tPos[1]++;
              }
            } else {
              throw new Error("Should not be reached");
            }
          }
          positions.set(k, tPos);
        }

        String toAdd = positions.get(positions.size()-1)[0] + "," + positions.get(positions.size()-1)[1];
        if (!tPositions.contains(toAdd)) {
          tPositions.add(toAdd);
        }
      }
    }
    System.out.println("Found: " + tPositions.size() + ", unique positions");
  }
}