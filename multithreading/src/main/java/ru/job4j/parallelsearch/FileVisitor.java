package ru.job4j.parallelsearch;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
/**FileVisitor.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 23.07.2018.
 */
public class FileVisitor extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;
    private List<String> extensions;
    private List<Path> result = new LinkedList<>();

    public FileVisitor(List<String> extensions) {
        this.extensions = extensions;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        this.findMany(dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        this.findMany(file);
        return FileVisitResult.CONTINUE;
    }

    public void findMany(Path path) {
        Path fileName = path.getFileName();
        for (String extension : extensions) {
            this.matcher = FileSystems.getDefault().getPathMatcher(extension);
            if (this.matcher.matches(fileName)) {
                this.result.add(path);
            }
        }
    }

    public List<Path> searchResult() {
        return this.result;
    }
}
