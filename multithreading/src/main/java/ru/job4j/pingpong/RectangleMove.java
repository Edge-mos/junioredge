package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rectangle;
    private boolean reverse = false;

    public RectangleMove(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    private void moveRight() {
        this.rectangle.setX(this.rectangle.getX() + 1);
        this.reverse = this.rectangle.getX() == 300;
    }

    private void moveLeft() {
        this.rectangle.setX(this.rectangle.getX() - 1);
        this.reverse = this.rectangle.getX() != 0;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!this.reverse) {
                this.moveRight();
            } else {
                this.moveLeft();
            }
            this.sleepThread();
        }
    }

    private void sleepThread() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
