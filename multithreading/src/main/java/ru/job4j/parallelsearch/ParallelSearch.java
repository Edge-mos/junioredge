package ru.job4j.parallelsearch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.lock.implemented.Lock;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**ParallelSearch.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 23.07.2018.
 */
@ThreadSafe
public class ParallelSearch {
    /**
     * путь до файла.
     */
    private final String root;
    /**
     * текст для поиска.
     */
    private final String text;
    /**
     * расширения.
     */
    private final List<String> types;
    private Lock lock1 = new Lock();
    /**
     * проверка на завершение потока.
     */
    private volatile boolean finishSearch = false;

    /**
     * Список для добавления путей к файлам, которые были найдены по расширению.
     */
    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    /**
     * Список путей к файлам, в которых найдено ключевое слово.
     */
    private final List<String> paths = new ArrayList<>();


    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.types = this.setPatterns(exts);
    }

    public void init() throws InterruptedException {
        Thread search = new Thread() {
            @Override
            public void run() {
                System.out.println("Стартует поток search");

                Path filePath = Paths.get(ParallelSearch.this.root);
                FileVisitor fileVisitor = new FileVisitor(ParallelSearch.this.types);

                try {
                    Files.walkFileTree(filePath, fileVisitor);
                    for (Path path : fileVisitor.searchResult()) {
                        ParallelSearch.this.lock1.lock();
                        ParallelSearch.this.files.add(path.toString());
                        ParallelSearch.this.lock1.unlock();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ParallelSearch.this.finishSearch = true;
                System.out.println("Поток search завершён");
            }
        };

        Thread read = new Thread() {
            @Override
            public void run() {
                System.out.println("Стартует поток read");

                while (!ParallelSearch.this.finishSearch || !ParallelSearch.this.files.isEmpty()) {
                        ParallelSearch.this.lock1.lock();
                        String path = ParallelSearch.this.files.poll();
                        ParallelSearch.this.lock1.unlock();

                        if (path != null) {
                            ParallelSearch.this.searchWord(Paths.get(path));
                        }
                }
                System.out.println("Поток read завершён");
            }
        };
        search.start();
        read.start();
        search.join();
        read.join();
    }

    private void searchWord(Path path) {

        try (Stream<String> stringStream = Files.lines(path)) {
            boolean res = stringStream.anyMatch(s -> s.contains(this.text));
            if (res) this.paths.add(path.toString());
        } catch (IOException e) {
            System.out.println("No file!");
        }
    }

    public List<String> result() {
        return this.paths;
    }

    private List<String> setPatterns(List<String> exts) {
        List<String> tmp = new LinkedList<>();
        for (String ext : exts) {
            tmp.add("glob:*." + ext);
        }
        return tmp;
    }
}
