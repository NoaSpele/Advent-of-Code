import java.util.Scanner;

public class Dec04 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String line, r1, r2;
    int start1, start2, end1, end2, sum1=0, sum2=0;

    while (sc.hasNextLine()) {
      line = sc.nextLine();
      r1 = line.split(",")[0];
      r2 = line.split(",")[1];
      start1 = Integer.parseInt(r1.split("-")[0]);
      end1 = Integer.parseInt(r1.split("-")[1]);
      start2 = Integer.parseInt(r2.split("-")[0]);
      end2 = Integer.parseInt(r2.split("-")[1]);

      System.out.println("r1: " + r1 + ", r2: " + r2);
      System.out.println("start1: " + start1 + ", end1: " + end1);
      System.out.println("start2: " + start2 + ", end2: " + end2 + "\n");

      if ((start1 >= start2 && end1 <= end2) || (start2 >= start1 && end2 <= end1)) {
        sum1++;
      }

      if (
        (start1 >= start2 && start1 <= end2) ||
        (end1 >= start2 && end1 <= end2) ||
        (start2 >= start1 && start2 <= end1) ||
        (end2 >= start1 && end2 <= end1)
      ) {
        sum2++;
      }
    }
    System.out.println("===== Done =====");
    System.out.println("Part 1 - Found: " + sum1 + " matches");
    System.out.println("Part 2 - Found: " + sum2 + " matches");
  }
}