package ru.job4j.lambda.reference.doublereference;

public class ObjectRefDemo {
    // Метод, возвращающий количество экземпляров объекта, найденных по критериям, задаваемым параметром ФИ MyFunc.

    static <T>int counter(T[] vals, MyFunc<T> f, T v) {
        int count = 0;

        for (int i = 0; i < vals.length; i++) {              // Это главный метод - упаковщик.
            if (f.func(vals[i], v)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int count;

        // Создаём массив объектов температур HighTemp
        HighTemp[] weekDayHigh = {new HighTemp(89), new HighTemp(82), new HighTemp(90), new HighTemp(89),
                new HighTemp(91), new HighTemp(84), new HighTemp(83), new HighTemp(89)};

        count = counter(weekDayHigh, HighTemp::sameTemp, new HighTemp(89));
        System.out.println(String.format("Количество дней с температурой 89 = %d", count));

        // А теперь создать и исполь зовать вместе с данным
        // методом еще один массив объектов типа HighTemp.

        HighTemp[] weekDayHigh2 = {new HighTemp(32), new HighTemp(12), new HighTemp(24), new HighTemp(19), new HighTemp(18),
                new HighTemp(12), new HighTemp(-1), new HighTemp(13)};

        count = counter(weekDayHigh2, HighTemp::sameTemp, new HighTemp(12));
        System.out.println("Количество дней с температурой 12 = " + count);

        // А теперь воспользоваться методом lessТhanTTemp () , чтобы
        // выяснить , сколько дней температура была меньше заданной.

        count = counter(weekDayHigh, HighTemp::lessThanTemp, new HighTemp(89));
        System.out.println("Дней, когда температура была меьше 89 = " + count);

        count = counter(weekDayHigh2, HighTemp::lessThanTemp, new HighTemp(19));
        System.out.println("Дней, когда температура была меьше 19 = " + count);
    }
}
