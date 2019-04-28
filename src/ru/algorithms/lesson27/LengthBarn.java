package ru.algorithms.lesson27;

import java.util.Arrays;
import java.util.Scanner;

public class LengthBarn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int t = scanner.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(matrix[i], 1);
        }
        for (int i = 0; i < t; i++) {
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            matrix[x][y] = 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i - 1][j] > 0 && matrix[i][j] > 0) {
                    matrix[i][j] = matrix[i - 1][j] + 1;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
                if (j < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
