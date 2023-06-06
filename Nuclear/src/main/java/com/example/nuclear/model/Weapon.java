package com.example.nuclear.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Weapon extends Drawing {
    private String name;




    private int posX;
    private int posY;

    private int bullets;

    private boolean reload;

    public Weapon(String name, int posX, int posY) {
        this.name = name;




        this.posX = posX;
        this.posY = posY;
        this.bullets=5;
        this.reload=false;

    }



    public void shoot(){


            bullets--;
            System.out.println("las balas de la clase son: "+bullets);




    }


    public boolean isReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public void reload() {

        reload=true;
        System.out.println("Recargando...");
        new Thread(()-> {

            while (bullets == 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();

        bullets = 5; // Restablecer el número de balas después de la recarga
        System.out.println("Recarga completa. Balas: " + bullets);
    }





// el error tal vez sea que debo hacer el reload pero en la controller

    @Override
    public void draw(GraphicsContext gc) {
        if (reload) {
            System.out.println("awdadasdawd");
            Image reloadingImage= new Image("C:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\raygun.jpg");
            double centerX = gc.getCanvas().getWidth() / 2 - reloadingImage.getWidth() / 2;
            double centerY = gc.getCanvas().getHeight() / 2 - reloadingImage.getHeight() / 2;
            gc.drawImage(reloadingImage, centerX, centerY);
            reload=false;
        }

            // Cargar la imagen del arma
            Image image = new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\raygunEdit-removebg-preview.png");

            // Especificar el ancho y alto deseados
            double width = 30;  // Ancho deseado
            double height = 30; // Alto deseado

            // Dibujar la imagen en el GraphicsContext con el tamaño especificado
            gc.drawImage(image, posX, posY, width, height);



    }


    // Getters and setters


    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }









    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
