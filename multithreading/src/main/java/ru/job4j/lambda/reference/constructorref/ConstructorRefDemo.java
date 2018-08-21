package ru.job4j.lambda.reference.constructorref;

public class ConstructorRefDemo {
    public static void main(String[] args) {
        MyFunc myClassRef = MyClassRef::new;
        MyClassRef mc = myClassRef.func(100);
        System.out.println(mc.getVal());


        MyFunc mc2 = MyClassRef::new;


    }
}
