package ru.algorithms.lesson24;

import java.util.Arrays;

public class RLEImproved {

    static byte[] compress(byte[] source) {
        byte[] compressed = new byte[2];
        int size = 0;
        int counter;
        int start;
        boolean equals;

        for (int i = 0; i < source.length; i++) {
            equals = false;
            counter = 1;

            while (i < source.length - 1 && source[i] == source[i + 1] && counter < Byte.MAX_VALUE) {
                counter++;
                i++;
                equals = true;
            }
            if (equals) {
                compressed = grow(compressed, size);
                compressed[size++] = (byte) counter;

                compressed = grow(compressed, size);
                compressed[size++] = source[i];
            } else {
                counter = 0;
                start = i;
                while (i < source.length - 1 && source[i] != source[i + 1] && counter < Byte.MAX_VALUE) {
                    counter++;
                    i++;
                }
                compressed = grow(compressed, counter + size, (counter + size) * 2);
                compressed[size++] = (byte) (-1 * counter);
                for (int k = start; k < start + counter; k++) {
                    compressed = grow(compressed, size);
                    compressed[size++] = source[k];
                }
                if (i == source.length - 1) {
                    compressed = grow(compressed, size);
                    compressed[size - counter - 1] -= 1;

                    compressed = grow(compressed, size);
                    compressed[size++] = source[source.length - 1];
                } else {
                    i--;
                }
            }

        }
        return Arrays.copyOfRange(compressed, 0, size);
    }

    static byte[] decompress(byte[] source) {
        byte[] decompressed = new byte[2];
        int size = 0;

        for (int i = 0; i < source.length; i++) {
            int count = source[i];
            if (count < 0) {
                for (int j = count; j < 0; j++) {
                    decompressed = grow(decompressed, size);
                    decompressed[size++] = source[++i];
                }
            } else {
                byte c = source[++i];
                for (int j = 0; j < count; j++) {
                    decompressed = grow(decompressed, size);
                    decompressed[size++] = c;
                }
            }
        }
        return Arrays.copyOfRange(decompressed, 0, size);
    }

    private static byte[] grow(byte[] bytes, int size) {
        return grow(bytes, size, bytes.length * 2);
    }

    private static byte[] grow(byte[] bytes, int size, int newSize) {
        if (bytes.length <= size) {
            return Arrays.copyOf(bytes, newSize);
        }
        return bytes;
    }
}
