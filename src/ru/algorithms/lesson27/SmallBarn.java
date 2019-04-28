package ru.algorithms.lesson27;

import java.util.Scanner;

public class SmallBarn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = scanner.nextInt();
                matrix[i][j] = v;
            }
        }
        int width, height, square = 0, limitWidth;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                height = 1;
                limitWidth = findWidth(matrix, i, j, n);
                if (square < limitWidth * height) {
                    square = limitWidth * height;
                }
                for (int x = i + 1; x < m; x++) {
                    height++;
                    width = findWidth(matrix, x, j, n);
                    if (limitWidth > width) {
                        limitWidth = width;
                    }
                    if (width > limitWidth) {
                        width = limitWidth;
                    }
                    if (square < width * height) {
                        square = width * height;
                    }
                }
            }
        }
        System.out.println(square);
    }

    static int findWidth(int[][] matrix, int x, int y, int n) {
        int width = 0;
        while (y < n) {
            if (matrix[x][y] == 1) {
                break;
            }
            width++;
            y++;
        }
        return width;
    }
}
