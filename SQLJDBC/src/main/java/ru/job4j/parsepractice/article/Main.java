package ru.job4j.parsepractice.article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Article> articleList = new LinkedList<>();

        Document doc = Jsoup.connect("http://4pda.ru/").get();
        Elements h2El = doc.getElementsByAttributeValue("class", "list-post-title");
        h2El.forEach(element -> {
            Element aEl = element.child(0);
            String url = aEl.attr("href");
            String title = aEl.child(0).text();

            articleList.add(new Article(url, title));
        });

        articleList.forEach(System.out::println);
    }

}
