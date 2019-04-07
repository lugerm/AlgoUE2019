/*
"""
Matrix - Martina LUGER

File has to be parsed as following:
$ javac luger_ManhattanTouristHVD.java
$ java lugerm_ManhattanTouristHVD.java rmHVD_10_20
$ java lugerm_ManhattanTouristHVD.java rmHVD_999_20
results: rmHVD_10_20: 64.55; rmHVD_999_20: 7223.07;

This assignment builds on the previous task to solve the
Manhattan Tourist Problem for horizontal and vertical edges.
The new task is to extend your existing tool to also consider diagonal edge weights.

As you can expect, we need additional
input for that. In particular, a (N-1)\*(N-1) matrix containing
diagonal edge weights. The input files for this assignment naturally
extend the horizontal/vertical (HV) with a
third matrix (horizonal/vertical/diagonal HVD):
```
G_down: 4 5
  0.60   0.65   0.91   0.94   0.14
  0.85   0.27   0.70   0.31   0.63
  0.63   0.23   0.35   0.77   0.20
  0.37   0.76   0.41   0.30   0.67
---
G_right: 5 4
  0.76   0.41   0.72   0.13
  0.57   0.64   0.62   0.62
  0.37   0.98   0.36   0.24
  0.99   0.77   0.39   0.35
  0.37   0.34   0.62   0.82
---
G_diag: 4 4
  6.74   7.03   2.47   6.25
  4.48   3.75   2.98   3.62
  7.90   3.63   3.67   3.18
  9.30   8.40   9.02   2.58
---
```
In practice, this boils down to add another check in your dynamic programming algorithm
andcomplements the forward algorithm required to compute a global sequence alignment of
two strings (which will be the next task A5).

You should select another pair of input matrices from the *rmHVD_10_X* and *rmHV_999_X* set
and use it as input of your extended Manhattan Tourist implementation.
All specifications from A3 apply here, i.e. your tool should be named
*$githubusername-ManhattanTouristHVD.$suffix* and read from STDIN as follows:
*$githubusername-ManhattanTouristHVD.$suffix < rmHVD_10_5*

Again, your tool should parse the dimension of the input matrix from the first data line
of the input file, without explicit requirement to pass the dimension at runtime (see A3).
Your pull request should include the following:

* your program (source code) that can process HVD Manhattan grids
* weight of the longest path for your input files (one for dim 10, and one for dim 999, respectively)
* two input files you selected. Let me know which file you chose in your ping message,
e.g. `@mtw please review rmHVD_10_20`
"""



*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class lugerm_ManhattanTouristHVD {

    public static void main(String[] args) {

        File file = new File(args[0]);

        double[][] rightMatrix = new double[10000][10000];
        double[][] downMatrix = new double[10000][10000];
        double[][] dMatrix = new double[10000][10000];

        int horCount = 0;
        int verCount = 0;
        int dCount = 0;

        try {
            Scanner scanner = new Scanner(file);
            //skip first line
            String line = scanner.nextLine();

            //Get first matrix
            line = scanner.nextLine();
            horCount = 0;
            while(!line.equals("---")) {
                String[] ar = line.trim().split("\\s+");
                for(int j=0; j<ar.length; j++) {
                    downMatrix[horCount][j] = Double.valueOf(ar[j]);
                }
                horCount++;
                line = scanner.nextLine();
            }

            //Get 2nd matrix
            //skip 1st line
            line = scanner.nextLine();
            line = scanner.nextLine();
            verCount = 0;
            while(!line.equals("---")) {
                String[] ar = line.trim().split("\\s+");
                for(int j=0; j<ar.length; j++) {
                    rightMatrix[verCount][j] = Double.valueOf(ar[j]);
                }
                verCount++;
                line = scanner.nextLine();
            }


            //Get 3rd matrix
            //skip 1st line
            line = scanner.nextLine();
            line = scanner.nextLine();
            dCount = 0;
            while(!line.equals("---")) {
                String[] ar = line.trim().split("\\s+");
                for(int j=0; j<ar.length; j++) {
                    dMatrix[dCount][j] = Double.valueOf(ar[j]);
                }
                dCount++;
                line = scanner.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int size = Math.max(horCount,Math.max(verCount,dCount));

        double[][] result = new double[size][size];
        result[0][0] = 0;



        for (int i = 1; i < size; i++) {
            result[i][0] = result[i - 1][0] + downMatrix[i - 1][0];
            result[0][i] = result[0][i - 1] + rightMatrix[0][i - 1];
        }

        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                double goDown = result[i - 1][j] + downMatrix[i - 1][j];
                double goRight = result[i][j - 1] + rightMatrix[i][j - 1];
                double goDownRight = result[i-1][j - 1] + dMatrix[i-1][j - 1];
                result[i][j] = Math.max(goDown, Math.max(goDownRight, goRight));
            }
        }

        // print after all iterations
        //printMatrix(result, size);

        System.out.println(result[size - 1][size - 1]);
    }

    public static void printMatrix(double[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print( String.format( "%.2f", matrix[i][j] ) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
