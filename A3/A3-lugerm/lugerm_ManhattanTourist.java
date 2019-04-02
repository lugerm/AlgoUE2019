/*
Matrix - Martina LUGER
File has to be parsed as following:
$ javac ManhattanTourist.java
$ java lugerm_ManhattanTourist rmHV_10_20.txt
$ java lugerm_ManhattanTourist rmHV_999_20.txt
results: rmHV_10_5: 13.539; rmHV_999_5: 1554.980

# Exercise 3
## Manhattan Tourist Problem - Part 1
The task is to solve the Manhattan Tourist Problem for given, arbitrary grid weights.
Compute the longest path from source (0,0) to sink (N,N) via dynamic programming. In a
first step we restrict the grid to horizontal and vertical edges, respectively.
For an N\*N grid we have (N-1)\*N horizontal edges and N\*(N-1) vertical edges, respectively.
An example input file for a 5*5 grid with horizontal and vertical edges looks like this:

G_down: 4 5
  0.12   0.79   0.50   0.56   0.39
  0.93   0.14   0.82   0.80   0.13
  0.71   0.37   0.49   0.94   0.88
  0.59   0.52   0.40   0.87   0.16
---
G_right: 5 4
  0.43   0.21   0.55   0.61
  0.61   0.89   0.52   0.54
  0.44   0.85   0.74   0.12
  0.56   0.91   0.61   0.24
  0.56   0.42   0.27   0.49
---

The file consists of two matrices for vertical (G_down) and horizontal (G_right) edges.
Each matrix block contains tab-separated floating point values and is closed by three dashes.
Beware that the numbers next to the identifiers G_down and G_right might be misleading, i.e., **do not**
parse the matrix dimensions from these numbers but compute them on the fly when parsing the matrices.

Ensure to check for consistency of the input files. In particular check if you read the correct
number of items inevery line (implicitly assuming that the dimensions obtained from parsing the
first lines of each matrix is correct). A simple check if your tool parses the input as required
is to remove the two integers next to G_down and G_right. Within the *random_matrices* folder you
will find a set of randomly generated input files that follow the above specification.
For the first part of this exercise choose the input file from the *rmHV_10_20 (dimension 10\*10,
horizontal,vertical) set as well as one file from the *rmHV_999_20 (dimension 999\*999).

---

Implement a program that
a) reads from STDIN a file as specified above and
b) computes the weight of the longest path from source to sink.

The program should print the computed floating point number to STDOUT. (Recall that this procedure
essentially corresponds to the forward recursion of the Needleman-Wunsch algorithm for pairwise global sequence
alignment.)

Keep in mind **not to** provide the dimension of the input matrices to your program at runtime. Your implementation
should be able to process input data as specified above in any dimension.
Your pull request should include the following:

* your program (source code: *$githubusername-ManhattanTouristHV.$suffix*
    call: *$githubusername-ManhattanTouristHV.$suffix < rmHV_10_5*)
* wheight of the longest path for your input files 
* let me know which input file you selected 


*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class lugerm_ManhattanTourist {

    public static void main(String[] args) {

        File file = new File(args[0]);

        double[][] rightMatrix = new double[10000][10000];
        double[][] downMatrix = new double[10000][10000];

        int horCount = 0;
        int verCount = 0;

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int size = Math.max(horCount,verCount);

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
                result[i][j] = Math.max(goDown, goRight);
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
