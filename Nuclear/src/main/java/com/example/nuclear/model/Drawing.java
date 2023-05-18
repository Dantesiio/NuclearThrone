package com.example.nuclear.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Drawing {

    protected Vector pos = new Vector(0,0);
    public abstract void draw(GraphicsContext gc);


}
