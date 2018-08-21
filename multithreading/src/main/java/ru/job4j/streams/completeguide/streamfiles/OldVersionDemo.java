package ru.job4j.streams.completeguide.streamfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OldVersionDemo {
    public static void main(String[] args) {
        List<String> lines1 = new ArrayList<>();
        List<String> lines2 = new ArrayList<>();
        Path path = Paths.get("/home/edge/Edge-mos/Search/fileline.txt");

        // Первый выриант
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines1.add(line);
            }

        } catch (IOException ignored) {
            System.out.println("Something goes wrong");
        }

        System.out.println("BufferedReader:");
        System.out.println(lines1);

        // Второй вариант
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNext()) {
                lines2.add(scanner.nextLine());
            }
        } catch (IOException ignored) {
            System.out.println("Something goes wrong");
        }

        System.out.println("\nScanner:");
        System.out.println(lines2);

    }
}
