package ru.job4j.parallelsearch.practice.filevisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Demo {
    static List<String> pathList = new LinkedList<>();


    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("/home/edge/Edge-mos/Search/xml.xml");

        System.out.println(Files.exists(filePath));
        System.out.println(Files.probeContentType(filePath));
        if (Files.probeContentType(filePath).equals("application/xml")) {
            pathList.add(filePath.toAbsolutePath().toString());
        }

        System.out.println(pathList);
    }
}
