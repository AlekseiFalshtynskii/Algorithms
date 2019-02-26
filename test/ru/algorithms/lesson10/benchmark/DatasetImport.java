package ru.algorithms.lesson10.benchmark;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class DatasetImport {

    public static List<String> parseFile(String strpath) {
        Path path = Paths.get(strpath);
        try (Stream<String> lines = Files.lines(path)) {
            List<String> result = new ArrayList<>();
            lines.forEach(s -> {
                StringTokenizer token = new StringTokenizer(s, " .,<>@-=():_';\"");
                while (token.hasMoreTokens())
                    result.add(token.nextToken());
            });
            return result;
        } catch (IOException ex) {
            // ...
        }
        return null;
    }

    public static void main(String[] argv) {
        System.out.println("import");
        List<String> words = parseFile(new File("test/ru/algorithms/lesson10/benchmark/wiki.train.tokens").getAbsolutePath());
        System.out.println(words.size());
    }
}
