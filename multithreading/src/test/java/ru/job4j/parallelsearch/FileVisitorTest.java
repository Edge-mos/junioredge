package ru.job4j.parallelsearch;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileVisitorTest {
    private Path path = Paths.get("/home/edge/Edge-mos/Search");
    //private String pattern = "glob:*.txt";
    private List<String> exts = Arrays.asList("glob:*.txt", "glob:*.xml", "glob:*.odt");
    private FileVisitor fv = new FileVisitor(exts);

    @Test
    public void test() throws IOException {
        Files.walkFileTree(path, fv);

        List<Path> paths = fv.searchResult();
        for (Path file : paths) {
            System.out.println(file);
        }


    }


}