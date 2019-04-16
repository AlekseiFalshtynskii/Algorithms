package ru.algorithms.lesson24;

import java.util.Arrays;

public class RLE {

    static byte[] compress(byte[] source) {
        byte[] compressed = new byte[2];
        int size = 0;
        int counter;

        for (int i = 0; i < source.length; i++) {
            counter = 1;
            while (i < source.length - 1 && source[i] == source[i + 1] && counter < 255) {
                counter++;
                i++;
            }
            if (compressed.length == size) {
                compressed = grow(compressed);
            }
            compressed[size++] = (byte) counter;
            compressed[size++] = source[i];
        }
        return Arrays.copyOfRange(compressed, 0, size);
    }

    static byte[] decompress(byte[] source) {
        byte[] decompressed = new byte[2];
        int size = 0;

        for (int i = 0; i < source.length; i += 2) {
            int count = source[i] < 0 ? source[i] + 256 : source[i];
            byte c = source[i + 1];
            for (int j = 0; j < count; j++) {
                if (decompressed.length == size) {
                    decompressed = grow(decompressed);
                }
                decompressed[size++] = c;
            }
        }
        return Arrays.copyOfRange(decompressed, 0, size);
    }

    private static byte[] grow(byte[] bytes) {
        return Arrays.copyOf(bytes, bytes.length * 2);
    }
}
