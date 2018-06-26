package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rectangle;

    public RectangleMove(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private void moveRight() {
        this.rectangle.setX(this.rectangle.getX() + 1);
    }

    private void moveLeft() {
        this.rectangle.setX(this.rectangle.getX() - 1);
    }

    private void sleepThread() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void exit() {
        Thread.interrupted();
    }

    @Override
    public void run() {
        while (true) {
            while (this.rectangle.getX() != 300) {
                this.moveRight();
                this.sleepThread();
            }
            while (this.rectangle.getX() != 0) {
                this.moveLeft();
                this.sleepThread();
            }
            if (Thread.interrupted()) {
                return;
            }
        }
    }
}
