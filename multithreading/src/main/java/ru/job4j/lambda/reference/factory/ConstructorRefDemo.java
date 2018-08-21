package ru.job4j.lambda.reference.factory;

public class ConstructorRefDemo {
    // Фабричный метод для объектов разных классов .
    // У каждого класса должен быть свой конструктор ,
    // принимающий один параметр типа Т . А параметр R
    //обозначает тип создаваемого объекта

    static <R, T> R myClassFactory(MyFunc<R, T> constructor, T v) {
        return constructor.func(v);
    }

    public static void main(String[] args) {
        // Создать ссылку на конструктор класса МyClass .
        // В данном случае оператор new обращается к конструктору ,
        // принимающему аргумент

        MyFunc<MyClass<Double>, Double> myClassCons = MyClass<Double>::new;
        MyClass<Double> mc = myClassFactory(myClassCons, 100.1);

        System.out.println(mc.getVal());

        //Создание другого класса
        MyFunc<MyClass2, String> myClassCons2 = MyClass2::new;
        MyClass2 mc2 = myClassFactory(myClassCons2, "Лямбда");
        System.out.println(mc2.getStr());
    }

}
