package ru.job4j.pingpong;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PingPong extends Application {
    private static final String JOB4J = "Пинг-понг www.job4j.ru";

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        int limitX = 310;
        int limitY = 300;
        Group group = new Group();
        Rectangle rectangle = new Rectangle(50, 100, 10, 10);
        group.getChildren().add(rectangle);
        Thread calculatingMove = new Thread(new RectangleMove(rectangle));
        calculatingMove.start();
        primaryStage.setScene(new Scene(group, limitX, limitY));
        primaryStage.setTitle(JOB4J);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> calculatingMove.interrupt());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
