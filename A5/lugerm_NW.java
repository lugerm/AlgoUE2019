// javac lugerm_NW.java
// e.g.:
// java lugerm_NW < test17.fa
// edit the settings:
// java lugerm_NW 3 -1 -5 < test17.fa

import java.util.Scanner;


public class lugerm_NW {

  public static final String PRINT_FORMAT = "%-20s %s";

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    // setup

    int match = 1;
    int mismatch = -1;
    int gap = -2;

    if (args.length!=0) {

      match = Integer.valueOf(args[0]);
      System.out.println("match is set to " + match);

      mismatch = Integer.valueOf(args[1]);
      System.out.println("mismatch is set to " + mismatch);

      gap = Integer.valueOf(args[2]);
      System.out.println("gap is set to " + gap);

    }

    //reading
    String s1name = scanner.nextLine().split(" ")[0];
    String s1 = scanner.nextLine();
    int n = s1.length();

    String s2name = scanner.nextLine().split(" ")[0];
    String s2 = scanner.nextLine();
    int m = s2.length();

    //init matrix

    int[][] f = new int[10000][10000];

    for (int i = 0; i <= n; i++) {
      f[i][0] = i * gap;
    }

    for (int j = 1; j <= m; j++) {
      f[0][j] = j * gap;
    }

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {

        int matchScore = getMatchScore(match, mismatch, s1.charAt(i - 1), s2.charAt(j - 1));
        int topLeft = f[i - 1][j - 1] + matchScore;
        int left = f[i][j - 1] + gap;
        int top = f[i - 1][j] + gap;

        f[i][j] = Math.max(topLeft, Math.max(top, left));
      }
    }

    //printMatrix(f, n, m + 1, s1, s2);
    // backtrack

    String result1 = "";
    String result2 = "";
    String decorate = "";
    int i = n;
    int j = m;
    while (i > 0 && j > 0) {
      char c1 = s1.charAt(i - 1);
      char c2 = s2.charAt(j - 1);

      int score = f[i][j];
      int scoreDiag = f[i - 1][j - 1];
      int scoreUp = f[i][j - 1];
      int scoreLeft = f[i - 1][j];

      if (score == (scoreDiag + getMatchScore(match, mismatch, c1, c2))) {
        result1 = c1 + result1;
        result2 = c2 + result2;
        i--;
        j--;
        if (getMatchScore(match, mismatch, c1, c2) == match) {
          decorate = "*" + decorate;
        } else {
          decorate = " " + decorate;
        }

      } else if (score == (scoreLeft + gap)) {
        result1 = c1 + result1;
        result2 = "-" + result2;
        decorate = " " + decorate;
        i--;
      } else if (score == (scoreUp + gap)) {
        result1 = "-" + result1;
        result2 = c2 + result2;
        decorate = " " + decorate;
        j--;
      }

    }

    while (i > 0) {
      result1 = s1.charAt(i - 1) + result1;
      result2 = "-" + result2;
      decorate = " " + decorate;
      i--;
    }

    while (j > 0) {
      result1 = "-" + result1;
      result2 = s2.charAt(j - 1) + result2;
      decorate = " " + decorate;
      j--;
    }

    System.out.println(String.format(PRINT_FORMAT, s1name, result1));
    System.out.println(String.format(PRINT_FORMAT, s2name, result2));
    System.out.println(String.format(PRINT_FORMAT, " ", decorate));

    System.out.println(f[n][m]);

  }

  private static int getMatchScore(int match, int mismatch, char c1, char c2) {
    return c1 == c2 ? match : mismatch;
  }

  private static void printMatrix(int[][] matrix, int n, int m, String s1, String s2) {
    System.out.println(s1);
    System.out.println(s2);
    for (int i = 0; i <= n + 1; i++) {
      for (int j = 0; j <= m; j++) {
        if (i + j <= 1) {
          System.out.print("-" + " \t");
        } else {
          if (i == 0) {
            System.out.print(s2.charAt(j - 2) + "  \t");
          } else if (j == 0) {
            System.out.print(s1.charAt(i - 2) + "  \t");
          } else {
            System.out.print(matrix[i - 1][j - 1] + "  \t");
          }
        }
      }
      System.out.println();
    }
    System.out.println();
  }

}
