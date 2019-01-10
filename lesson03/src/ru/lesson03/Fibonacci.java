package ru.lesson03;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;

public class Fibonacci {
    private static final int N = 101;

    public static void main(String[] args) {
        System.out.println(simple());
        System.out.println(matrix());
    }

    /**
     * Классическое вычисление
     * @return
     */
    private static BigInteger simple() {
        BigInteger a = valueOf(1L);
        BigInteger b = valueOf(1L);
        BigInteger f = valueOf(0L);
        if (N == 1 || N == 2) {
            return a;
        }
        for (long i = 1; i <= N - 2; i++) {
            f = a.add(b);
            a = b;
            b = f;
        }
        return f;
    }

    /**
     * Матричное вычисление
     * @return
     */
    private static BigInteger matrix() {
        BigInteger[][] matrixA = new BigInteger[][]{{valueOf(1L), valueOf(1L)}, {valueOf(1L), valueOf(0L)}};
        BigInteger[][] matrixB = new BigInteger[][]{{valueOf(1L), valueOf(1L)}, {valueOf(1L), valueOf(0L)}};
        BigInteger[][] matrixF = new BigInteger[][]{{null, null}, {null, null}};
        for (int i = 1; i < N; i++) {
            matrixF[0][0] = matrixB[0][0].multiply(matrixA[0][0]).add(matrixB[0][1].multiply(matrixA[1][0]));
            matrixF[0][1] = matrixB[0][0].multiply(matrixA[0][1]).add(matrixB[0][1].multiply(matrixA[1][1]));
            matrixF[1][0] = matrixB[1][0].multiply(matrixA[0][0]).add(matrixB[1][1].multiply(matrixA[1][0]));
            matrixF[1][1] = matrixB[1][0].multiply(matrixA[0][1]).add(matrixB[1][1].multiply(matrixA[1][1]));

            matrixB[0][0] = matrixF[0][0];
            matrixB[0][1] = matrixF[0][1];
            matrixB[1][0] = matrixF[1][0];
            matrixB[1][1] = matrixF[1][1];
        }
        return matrixF[0][1];
    }
}
