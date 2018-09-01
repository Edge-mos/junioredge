package ru.job4j.parallelsearch.practice.testfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestFiles {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/home/edge/Edge-mos/Search/new.txt");

        System.out.println("Exist: " + Files.exists(path));
        System.out.println("Is directory: " + Files.isDirectory(path));
        System.out.println("regular file : " + Files.isRegularFile(path));
        System.out.println("attribute size : " + Files.getAttribute(path, "size", LinkOption.NOFOLLOW_LINKS));
        System.out.println("executable : " + Files.isExecutable(path));
        System.out.println("read all lines : " + Files.readAllLines(path));
        System.out.println("content type : " + Files.probeContentType(path));

    }
}
