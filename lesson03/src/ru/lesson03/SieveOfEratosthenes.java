package ru.lesson03;

public class SieveOfEratosthenes {
    private static final int N = 101;

    public static void main(String[] args) {
        simple();
        memorySaving();
    }

    /**
     * Простая реализация с оптимизацией из урока
     */
    private static void simple() {
        byte[] arr = new byte[N + 1];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            if (i != 2 && i % 2 == 0) {
                continue;
            }
            if (i * i >= arr.length) {
                break;
            }
            if (arr[i] == 0) {
                for (int j = i * i; j < arr.length; j = j + i) {
                    arr[j] = 1;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    /**
     * Оптимизация за счет исключения четных чисел и памяти с использованием бит
     */
    private static void memorySaving() {
        byte[] bytes = new byte[N / 8 + 1];
        for (int i = 3; i <= N; i = i + 2) {
            if (i * i > N) {
                break;
            }
            if (getBit(bytes, i) == 0) {
                for (int j = i * i; j <= N; j = j + i) {
                    if (j % 2 == 0) {
                        continue;
                    }
                    setBit(bytes, j);
                }
            }
        }
        System.out.print("2 ");
        for (int i = 3; i <= N; i = i + 2) {
            if (getBit(bytes, i) == 0) {
                System.out.print(i + " ");
            }
        }
    }

    private static int getBit(byte[] bytes, int n) {
        int i = n / 16;
        int b = bytes[i];
        int position = i > 0 ? (n % (i * 16)) / 2 : n / 2;
        return (b >> position) & 1;
    }

    private static void setBit(byte[] bytes, int n) {
        int i = n / 16;
        int position = i > 0 ? (n % (i * 16)) / 2 : n / 2;
        bytes[i] = (byte) (bytes[i] | (1 << position));
    }
}
