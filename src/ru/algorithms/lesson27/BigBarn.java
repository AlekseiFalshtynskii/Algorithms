package ru.algorithms.lesson27;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class BigBarn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int t = scanner.nextInt();
        int[][] matrix = new int[m][n];
        int square = 0;
        for (int i = 0; i < m; i++) {
            Arrays.fill(matrix[i], 1);
        }
        for (int i = 0; i < t; i++) {
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            matrix[x][y] = 0;
        }

        // длины
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i - 1][j] > 0 && matrix[i][j] > 0) {
                    matrix[i][j] = matrix[i - 1][j] + 1;
                }
            }
        }

        // ширины
        int width, height;
        for (int line = 0; line < m; line++) {
            int[] left = new int[n];
            int[] right = new int[n];
            Stack<Integer> stack = new Stack<>();
            for (int i = n - 1; i >= 0; i--) {
                while (!stack.empty()) {
                    if (matrix[line][i] < matrix[line][stack.peek()]) {
                        left[stack.pop()] = i + 1;
                    } else {
                        break;
                    }
                }
                stack.push(i);
            }
            stack.clear();
            for (int i = 0; i < n; i++) {
                while (!stack.empty()) {
                    if (matrix[line][i] < matrix[line][stack.peek()]) {
                        right[stack.pop()] = i - 1;
                    } else {
                        break;
                    }
                }
                stack.push(i);
            }
            while (!stack.empty()) {
                right[stack.pop()] = n - 1;
            }

            // площадь
            for (int i = 0; i < n; i++) {
                width = right[i] - left[i] + 1;
                height = matrix[line][i];
                if (square < width * height) {
                    square = width * height;
                }
            }
        }
        System.out.println(square);
    }
}
