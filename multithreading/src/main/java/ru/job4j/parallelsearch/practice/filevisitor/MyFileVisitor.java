package ru.job4j.parallelsearch.practice.filevisitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private List<Path> pathList = new LinkedList<>();

    public MyFileVisitor(String pattern) {
        try {
            this.matcher = FileSystems.getDefault().getPathMatcher(pattern);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid pattern");
            System.exit(1);
        }
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        find(path);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) throws IOException {
        find(path);
        return FileVisitResult.CONTINUE;
    }

    private void findType(Path path) throws IOException {
        Path filePath = path.getFileName();
        if (Files.probeContentType(path).equals("text/plain")) {
            this.pathList.add(filePath);
        }
    }

    private void find(Path path) {
        Path name = path.getFileName();
        if (this.matcher.matches(name)) {
            System.out.println("Matching file:" + path.getFileName());
        }
    }

    public static void main(String[] args) {
        Path startPath = Paths.get("/home/edge/Edge-mos/Search");
        String pattern = "glob:*.txt";

        try {
            Files.walkFileTree(startPath, new MyFileVisitor(pattern));
            System.out.println("Search completed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
