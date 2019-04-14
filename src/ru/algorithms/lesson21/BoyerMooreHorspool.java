package ru.algorithms.lesson21;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BoyerMooreHorspool {

    public static void main(String[] args) throws UnsupportedEncodingException {
        test();
    }

    static void test() {
        List<Data> dataList = readFile();
        for (Data data : dataList) {
            Integer[] matches = findMatches(data.text, data.pattern);
            if (!Arrays.equals(data.matches, matches)) {
                throw new RuntimeException(Arrays.toString(matches) + " != " + Arrays.toString(data.matches));
            }
        }
        System.out.println("Ok!");
    }

    /**
     * Поиск всех вхождений подстроки в строку
     *
     * @param txt
     * @param ptrn
     * @return
     */
    static Integer[] findMatches(String txt, String ptrn) {
        List<Integer> matches = new ArrayList<>();
        char[] text = txt.toCharArray();
        char[] pattern = ptrn.toCharArray();

        Integer[] preprocessed = badCharacterTable(pattern);
        int shift = 0, j;
        while (shift <= text.length - pattern.length) {
            j = pattern.length - 1;
            while (text[shift + j] == pattern[j]) {
                j--;
                if (j < 0) {
                    matches.add(shift);
                    break;
                }
            }
            shift += preprocessed[text[shift + pattern.length - 1]];
        }
        return matches.toArray(new Integer[]{});
    }

    /**
     * Построение таблицы сдвигов символов в паттерне от конца (для ASCII символов)
     *
     * @param pattern
     * @return
     */
    static Integer[] badCharacterTable(char[] pattern) {
        Integer[] table = new Integer[256];
        Arrays.fill(table, pattern.length);
        for (int i = 0; i < pattern.length - 1; i++) {
            table[pattern[i]] = pattern.length - 1 - i;
        }
        return table;
    }

    static List<Data> readFile() {
        List<Data> dataList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(new File("src/ru/algorithms/lesson21/string_matching_test_cases.tsv").getAbsolutePath()))) {
            stream.forEach(line -> {
                String[] split = line.split("\t");
                String text = split[0];
                String pattern = split[1];
                Integer[] matches = new Integer[]{};
                if (split.length == 3) {
                    String[] splitMatches = split[2].split(" ");
                    matches = new Integer[splitMatches.length];
                    for (int i = 0; i < matches.length; i++) {
                        matches[i] = Integer.valueOf(splitMatches[i]);
                    }
                }
                dataList.add(new Data(text, pattern, matches));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    static class Data {
        String text;
        String pattern;
        Integer[] matches;

        Data(String text, String pattern, Integer[] matches) {
            this.text = text;
            this.pattern = pattern;
            this.matches = matches;
        }
    }
}
