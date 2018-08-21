package ru.job4j.lambda.simple.exeptionlambda;

public class LambdaExeptionDemo {
    public static void main(String[] args) {
        double[] values = {1., 2., 3., 4.};
        NumArrayFunc average = arr -> {
            if (arr.length == 0) throw new EmptyArrayExeption();
            double sum = 0;
            for (double v : arr) {
                sum += v;
            }
            return sum / arr.length;
        };

        System.out.println(String.format("Среднее арифметическое в массиве = %f", average.func(values)));

        average.func(new double[0]);
    }
}
