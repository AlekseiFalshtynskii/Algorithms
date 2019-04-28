package ru.algorithms.lesson27;

import java.util.Scanner;
import java.util.Stack;

public class WidthBarn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.empty()) {
                if (array[i] < array[stack.peek()]) {
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
                if (array[i] < array[stack.peek()]) {
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
        print(left);
        print(right);
    }

    static void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
