package ru.job4j.lambda.reference.objectreference;

public class RefDemoTest {
    static String strOp(StringFunc sf, String in) {
        return sf.func(in);
    }

    public static void main(String[] args) {
        String in = "Лямбдa обращается на";
        MyStringOps mso = new MyStringOps();
        String res = strOp(mso::stringReverse, in);
        System.out.println(res);
    }
}
