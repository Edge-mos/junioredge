package ru.job4j.lambda.reference.staticreference;

public class RefDemo {
    private static String stringOp(StringFunc sf, String str) {
        return sf.func(str);
    }

    public static void main(String[] args) {
        String in = "Лямбдa обращается на";
        String result = stringOp(MyStringOps::reverse, in);
        //StringFunc stringFunc = MyStringOps::reverse; мы просто сослали свой функц. интерфейс на подходящий метод.
        // до этого мы писали само лямбда выражение, а тут мы просто сослались на подходящий метод.
        // это позволяет писать практически универсальные методы.
        System.out.println(result);
    }
}
