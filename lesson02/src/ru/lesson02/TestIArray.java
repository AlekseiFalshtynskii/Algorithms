package ru.lesson02;

public class TestIArray {

    public static void main(String[] args) {
        IArray<Integer> iArray = new IArray<>(4);
        for (int i = 0; i < 6; i++) {
            iArray.add(i);
            System.out.println(iArray);
        }
        iArray.add(2, 6);
        System.out.println(iArray);
        iArray.add(3, 7);
        System.out.println(iArray);
        iArray.add(0, 8);
        System.out.println(iArray);
        iArray.add(1, 9);
        System.out.println(iArray);

        iArray.remove(4);
        System.out.println(iArray);
        iArray.remove(4);
        System.out.println(iArray);


        for (int i = 0; i < 8; i++) {
            System.out.println("iArray[" + i + "] = " + iArray.get(i));
        }
    }
}
