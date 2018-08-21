package ru.job4j.lambda.reference.comparatorref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UseMethodRef {
    // Метод compare(), совместимый с аналогичным методом, определённым в интерфейсе Comparator<T>.
    static int compareMC(MyClass a, MyClass b) {
        return a.getVal() - b.getVal();
    }

    public static void main(String[] args) {
        List<MyClass> list = new ArrayList<>(Arrays.asList(new MyClass(1), new MyClass(4), new MyClass(2),
                new MyClass(9), new MyClass(3), new MyClass(7)));

        // Найти максимальное значение, используя метод compareMC().

        MyClass max = Collections.max(list, UseMethodRef::compareMC);
        System.out.println(max.getVal());
    }
}
