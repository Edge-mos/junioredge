package ru.job4j.parallelsearch;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void test() throws IOException, InterruptedException {
        ParallelSearch parallelSearch = new ParallelSearch("/home/edge/Edge-mos/Search", "Ubuntu555",
                Arrays.asList("txt", "xml", "html"));
//        parallelSearch.searchFiles();
//        parallelSearch.readFiles();
        parallelSearch.init();
        for (String s : parallelSearch.result()) {
            System.out.println(s);
        }



    }

}