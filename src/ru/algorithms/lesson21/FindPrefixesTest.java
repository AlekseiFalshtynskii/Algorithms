package ru.algorithms.lesson21;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FindPrefixesTest {

    public static void main(String[] args) {
        test();
    }

    static void test() {
        List<Data> dataList = readFile();
        for (Data data : dataList) {
            Integer[] prefixes = findPrefixes(data.text);
            if (!Arrays.equals(data.prefixes, prefixes)) {
                throw new RuntimeException(Arrays.toString(prefixes) + " != " + Arrays.toString(data.prefixes));
            }
        }
        System.out.println("Ok!");
    }

    /**
     * Функция поиска длин подстрок, начинающихся со всех индексов строки, которые являются префиксом для него
     *
     * @param str
     * @return
     */
    static Integer[] findPrefixes(String str) {
        Integer[] prefixes = new Integer[str.length()];
        prefixes[0] = str.length();

        int operations = 0, len_first_prefix = 0, i_first_prefix = 0, count, j;

        for (int i = 1; i < str.length(); i++) {
            count = 0;
            j = i;

            // если обрабатываемый индекс находится в пределах первого префикса - перемещаемся сразу в его конец
            if (len_first_prefix > 0 && str.charAt(j) == str.charAt(j - i) && j < len_first_prefix) {
                count = len_first_prefix - j + i_first_prefix;
                j += len_first_prefix;
            }

            // сравниваем символы пока не встречаем разные
            while (j < str.length() && str.charAt(j) == str.charAt(j - i)) {
                operations++;
                count++;
                j++;
            }

            // запоминаем первый префикс для сдвига
            if (len_first_prefix == 0) {
                len_first_prefix = count;
                i_first_prefix = i;
            }
            operations++;
            prefixes[i] = count;
        }

        // число операций не превосходит две длины строки
        if (operations / str.length() > 2) {
            throw new RuntimeException("O(n) > 2|S|");
        }
        return prefixes;
    }

    static List<Data> readFile() {
        List<Data> dataList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(new File("src/ru/algorithms/lesson21/preprocess_test_cases.txt").getAbsolutePath()))) {
            stream.forEach(line -> {
                String[] split = line.split(" ");
                String str = split[0];
                List<Integer> lst = new ArrayList<>(split.length - 1);
                for (int i = 1; i < split.length; i++) {
                    lst.add(Integer.valueOf(split[i]));
                }
                dataList.add(new Data(str, lst.toArray(new Integer[]{})));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    static class Data {
        String text;
        Integer[] prefixes;

        Data(String text, Integer[] prefixes) {
            this.text = text;
            this.prefixes = prefixes;
        }
    }
}
