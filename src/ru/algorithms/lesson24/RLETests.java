package ru.algorithms.lesson24;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class RLETests {

    private static final String PATH = "src/ru/algorithms/lesson24/";

    public static void main(String[] args) throws IOException {
        testText();
        testPhoto();
        testVideo();
        testExe();
    }

    private static void testText() throws IOException {
        test("text.txt", "text_compressed.txt", "text_decompressed.txt");
        System.out.println("Text ok!");
    }

    private static void testPhoto() throws IOException {
        test("photo.jpg", "photo_compressed.jpg", "photo_decompressed.jpg");
        System.out.println("Photo ok!");
    }

    private static void testVideo() throws IOException {
        test("video.mp4", "video_compressed.jpg", "video_decompressed.jpg");
        System.out.println("Video ok!");
    }

    private static void testExe() throws IOException {
        test("boxworld.exe", "boxworld_compressed.exe", "boxworld_decompressed.exe");
        System.out.println("Exe ok!");
    }

    private static void test(String sourceFile, String compressedFile, String decompressedFile) throws IOException {
        byte[] source = Files.readAllBytes(Paths.get(new File(PATH + sourceFile).getAbsolutePath()));
        byte[] compressed = RLE.compress(source);
        byte[] decompressed = RLE.decompress(compressed);
        Files.write(Paths.get(new File(PATH + compressedFile).getAbsolutePath()), compressed);
        Files.write(Paths.get(new File(PATH + decompressedFile).getAbsolutePath()), decompressed);
        if (!Arrays.equals(source, decompressed)) {
            throw new RuntimeException("Fail!");
        }
    }
}
