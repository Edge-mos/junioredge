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

/**ParallelSearch.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 23.07.2018.
 */
@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> types;
    private Lock lock1 = new Lock();
    private Lock lock2 = new Lock();

    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();

    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.types = this.setPatterns(exts);

    }

//    public void searchFiles() throws IOException {
//        Path filePath = Paths.get(this.root);
//        FileVisitor fileVisitor = new FileVisitor(types);
//        Files.walkFileTree(filePath, fileVisitor);
//        for (Path path : fileVisitor.searchResult()) {
//            this.files.add(path.toString());
//        }
//    }
//
//    public void readFiles() throws IOException {
//        while (this.files.size() != 0) {
//            Path filePath = Paths.get(this.files.poll());
//            BufferedReader br = Files.newBufferedReader(filePath);
//            String str;
//
//            while ((str = br.readLine()) != null) {
//                if (str.contains(this.text)) {
//                    this.paths.add(filePath.toString());
//                    break;
//                }
//            }
//        }
//    }

    public void init() throws InterruptedException {
        Thread search = new Thread() {
            @Override
            public void run() {
                ParallelSearch.this.lock1.lock();
                Path filePath = Paths.get(ParallelSearch.this.root);
                FileVisitor fileVisitor = new FileVisitor(ParallelSearch.this.types);

                try {
                    Files.walkFileTree(filePath, fileVisitor);
                    for (Path path : fileVisitor.searchResult()) {
                        ParallelSearch.this.files.add(path.toString());
                    }
                    ParallelSearch.this.lock1.unlock();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread read = new Thread() {
            @Override
            public void run() {

                try {
                    ParallelSearch.this.lock1.lock();
                    while (ParallelSearch.this.files.size() != 0) {
                        Path filePath = Paths.get(ParallelSearch.this.files.poll());
                        BufferedReader br = Files.newBufferedReader(filePath);
                        ParallelSearch.this.lock1.unlock();

                        ParallelSearch.this.lock2.lock();
                        String str;
                        while ((str = br.readLine()) != null) {
                            if (str.contains(ParallelSearch.this.text)) {
                                ParallelSearch.this.paths.add(filePath.toString());
                                break;
                            }
                        }
                        ParallelSearch.this.lock2.unlock();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };
        search.start();
        read.start();
        search.join();
        read.join();
    }

    synchronized List<String> result() {
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
