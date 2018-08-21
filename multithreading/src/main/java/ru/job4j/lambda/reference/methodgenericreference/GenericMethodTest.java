package ru.job4j.lambda.reference.methodgenericreference;

public class GenericMethodTest {
    static <T>int myOp(MyFunc<T> f, T[] vals, T v) {
        return f.func(vals, v);
    }


    public static void main(String[] args) {
        Integer[] array = {2, 4, 55, 12, 52, 76, 5, 8, 2};
        String[] strArr = {"One", "Two", "Two"};

        int repeatInts = myOp(MyArrayOps::<Integer>countMatching, array, 2);
        int repeatStr = myOp(MyArrayOps::<String>countMatching, strArr, "Two");

        System.out.println("Найденно совпадение чисел в массиве = " + repeatInts);
        System.out.println("Найденно совпадение строк в массиве = " + repeatStr);


    }
}
