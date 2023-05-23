package com.example.nuclear.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Avatar extends Drawing {
    private Image[] images;
    private int imageIndex;

    public Avatar() {
        pos.setX(100);
        pos.setY(100);
        imageIndex = 0;
        loadImages();
    }

    private void loadImages() {
        // Carga las imágenes para cada estado del personaje
        String[] imagePaths = {
                "file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/W1.png",
                "file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/W2.png",
                "file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/W3.png",
                "file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/W4.png",
                "file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/W5.png",
                "file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/W6.png"
        };

        images = new Image[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            images[i] = new Image(imagePaths[i]);
        }
    }

    private void changeImage() {
        // Cambia el índice de imagen y vuelve a cargar la imagen correspondiente
        imageIndex++;
        if (imageIndex >= images.length) {
            imageIndex = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Dibuja la imagen del personaje en la posición actual
        Image currentImage = images[imageIndex];
        gc.drawImage(currentImage, pos.getX() - 25, pos.getY() - 25, 50, 50);
    }

    public void keyPressed(String keyCode) {
        // Maneja el evento de tecla presionada
        if (keyCode.equals("W")) {
            changeImage();
        }if (keyCode.equals("A")) {
            changeImage();
        }if (keyCode.equals("S")) {
            changeImage();
        }if (keyCode.equals("D")) {
            changeImage();
        }
    }
}
