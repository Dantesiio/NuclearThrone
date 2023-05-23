package com.example.nuclear.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paredes extends Drawing {

    public Paredes(Vector vector){
        pos.setX(200);
        pos.setY(100);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(222, 85, 0));
        gc.fillRect(10,10,80,80);
    }
}

