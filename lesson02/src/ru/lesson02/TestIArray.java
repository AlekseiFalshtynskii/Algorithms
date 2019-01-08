package ru.lesson02;

public class TestIArray {
    private static final int BLOCK_SIZE = 5;

    public static void main(String[] args) {
        IArray<Integer> iArray = new IArray<>(BLOCK_SIZE);
        for (int i = 0; i < BLOCK_SIZE * 3; i++) {
            iArray.add(i);
            System.out.println(iArray);
        }
        iArray.add(0, 16);
        System.out.println(iArray);
        iArray.add(2, 17);
        System.out.println(iArray);
        iArray.add(4, 18);
        System.out.println(iArray);
        iArray.add(5, 19);
        System.out.println(iArray);
        iArray.add(7, 20);
        System.out.println(iArray);
        iArray.add(9, 21);
        System.out.println(iArray);

        for (int i = 0; i < BLOCK_SIZE * 3 + 6; i++) {
            System.out.println("iArray[" + i + "] = " + iArray.get(i));
        }

        for (int i = 0; i < BLOCK_SIZE * 3 + 6; i++) {
            iArray.remove(0);
            System.out.println(iArray);
        }
    }
}
