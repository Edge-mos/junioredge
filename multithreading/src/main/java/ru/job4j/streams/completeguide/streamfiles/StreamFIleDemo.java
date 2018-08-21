package ru.job4j.streams.completeguide.streamfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFIleDemo {
    public static void main(String[] args) {
        List<String> lines1 = new ArrayList<>();
        List<String> lines2 = new ArrayList<>();
        Path path = Paths.get("/home/edge/Edge-mos/Search/fileline.txt");

        // Вариант с Files.lines
        try (Stream<String> stringStream = Files.lines(path)) {
            lines1 = stringStream.collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Error!");
        }

        System.out.println("Files.lines - поток");
        System.out.println(lines1);

        // Вариант с BufferedReader потоком
        try (Stream<String> bufferedStream = Files.newBufferedReader(path).lines()) {
            lines2 = bufferedStream.collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Error!");
        }

        System.out.println("\nBufferedReader - поток");
        System.out.println(lines2);
    }
}
