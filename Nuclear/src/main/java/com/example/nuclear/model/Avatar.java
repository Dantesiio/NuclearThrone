package com.example.nuclear.model;

import com.example.nuclear.HelloApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Avatar extends Drawing implements Runnable {
    private Image[] images;
    private int imageIndex;
    private boolean isMoving;
    private boolean isFacingRight = true;

    public Avatar() {
        pos.setX(100);
        pos.setY(100);
        imageIndex = 0;
        loadImages();
    }

    private void loadImages() {
        // Load the images for each character state
        String[] imagePaths = {
                "W1.png",
                "W2.png",
                "W3.png",
                "W4.png",
                "W5.png",
                "W6.png"
        };

        images = new Image[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            String imagePath = "file:" + HelloApplication.class.getResource(imagePaths[i]).getPath();
            images[i] = new Image(imagePath);
        }
    }

    private void changeImage() {
        // Change the image index and reload the corresponding image
        imageIndex++;
        if (imageIndex >= images.length) {
            imageIndex = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Draw the character image at the current position
        gc.drawImage(images[imageIndex], isFacingRight ? pos.getX() - 25 : pos.getX() + 25,
                pos.getY() - 25, isFacingRight ? 50 : -50, 50);
    }

    public void keyPressed(String keyCode) {
        // Handle the key pressed event
        if (keyCode.equals("W") || keyCode.equals("A") || keyCode.equals("S") || keyCode.equals("D")) {
            changeImage();
        }
    }

    @Override
    public void run() {
        while (true) {
            imageIndex = (imageIndex + 1) % 6;
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }
}
