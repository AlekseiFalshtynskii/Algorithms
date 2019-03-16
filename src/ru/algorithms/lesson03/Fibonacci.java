package ru.algorithms.lesson03;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

public class Fibonacci {

    public static void main(String[] args) {
        for (int n = 1; n < 201; n++) {
            System.out.println(simple(n));
            System.out.println(matrix(n));
        }
    }

    /**
     * Классическое вычисление
     *
     * @return
     */
    private static BigInteger simple(int n) {
        BigInteger a = valueOf(1L);
        BigInteger b = valueOf(1L);
        BigInteger f = valueOf(0L);
        if (n == 1 || n == 2) {
            return a;
        }
        for (long i = 1; i <= n - 2; i++) {
            f = a.add(b);
            a = b;
            b = f;
        }
        return f;
    }

    /**
     * Матричное вычисление
     *
     * @return
     */
    private static BigInteger matrix(int n) {
        if (n == 1 || n == 2) {
            return valueOf(1L);
        }
        BigInteger[][] matrixA = new BigInteger[][]{{valueOf(1L), valueOf(1L)}, {valueOf(1L), valueOf(0L)}};
        BigInteger[][] matrixB = new BigInteger[][]{{valueOf(2L), valueOf(1L)}, {valueOf(1L), valueOf(1L)}};
        BigInteger[][] matrixC = new BigInteger[][]{{valueOf(2L), valueOf(1L)}, {valueOf(1L), valueOf(1L)}};
        BigInteger[][] matrixF = new BigInteger[][]{{null, null}, {null, null}};
        int i;
        for (i = 2; 2 * i <= n; i = 2 * i) {
            multiply(matrixC, matrixC, matrixF);
            copy(matrixF, matrixC);
        }
        for (int j = 0; j < (n % i) / 2; j++) {
            multiply(matrixB, matrixC, matrixF);
            copy(matrixF, matrixC);
        }
        if (n % 2 == 1) {
            multiply(matrixA, matrixC, matrixF);
        }
        return matrixF[0][1];
    }

    private static void multiply(BigInteger[][] matrixA, BigInteger[][] matrixB, BigInteger[][] matrixF) {
        matrixF[0][0] = matrixB[0][0].multiply(matrixA[0][0]).add(matrixB[0][1].multiply(matrixA[1][0]));
        matrixF[0][1] = matrixB[0][0].multiply(matrixA[0][1]).add(matrixB[0][1].multiply(matrixA[1][1]));
        matrixF[1][0] = matrixB[1][0].multiply(matrixA[0][0]).add(matrixB[1][1].multiply(matrixA[1][0]));
        matrixF[1][1] = matrixB[1][0].multiply(matrixA[0][1]).add(matrixB[1][1].multiply(matrixA[1][1]));
    }

    private static void copy(BigInteger[][] matrixFrom, BigInteger[][] matrixTo) {
        matrixTo[0][0] = matrixFrom[0][0];
        matrixTo[0][1] = matrixFrom[0][1];
        matrixTo[1][0] = matrixFrom[1][0];
        matrixTo[1][1] = matrixFrom[1][1];
    }
}
