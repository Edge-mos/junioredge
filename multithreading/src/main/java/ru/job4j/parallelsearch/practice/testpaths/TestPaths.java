package ru.job4j.parallelsearch.practice.testpaths;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPaths {
    public static void main(String[] args) {
        Path filePath = Paths.get("/home/edge/Edge-mos/Search/Document");
        System.out.println("File system: " + filePath.getFileSystem());
        System.out.println("File name: " + filePath.getFileName());
        System.out.println("File path: " + filePath);
        System.out.println("File absolute path: " + filePath.toAbsolutePath());
        System.out.println("File URI: " + filePath.toUri());
        System.out.println("Print elements of the path:");

        for (Path element : filePath) {
            System.out.print(element + "/");
        }
    }



}
