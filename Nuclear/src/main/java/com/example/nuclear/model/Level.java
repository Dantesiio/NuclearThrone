package com.example.nuclear.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Level {


    private int id;
    private Color color;
    private Image img;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;


    private ArrayList<Paredes> paredes;

    private ArrayList<Weapon> armasEnSuelo;

    private ArrayList<Grenade> granadasEnSuelo;

    // Resto de atributos y métodos de la clase Nivel



    public Level(int id){
        this.id = id;
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        paredes = new ArrayList<>();
        armasEnSuelo=new ArrayList<>();
        granadasEnSuelo=new ArrayList<>();

    }




    public void generarArmaAleatoriaEnSuelo(String name,double mapaAncho, double mapaAlto) {
        int randomX = (int) (Math.random() * mapaAncho);
        int randomY = (int) (Math.random() * mapaAlto);

        Vector posicion = new Vector(randomX, randomY);


        Weapon armaEnSuelo = new Weapon(name,randomX ,randomY);



        armasEnSuelo.add(armaEnSuelo);
    }

    public void generarGranadasEnElSuelo(String name,double mapaAncho, double mapaAlto) {
        int randomX = (int) (Math.random() * mapaAncho);
        int randomY = (int) (Math.random() * mapaAlto);

        Vector posicion = new Vector(randomX, randomY);


        Grenade grenade = new Grenade(name,randomX ,randomY);



        granadasEnSuelo.add(grenade);
    }


    public ArrayList<Grenade> getGranadasEnSuelo() {
        return granadasEnSuelo;
    }

    public void setGranadasEnSuelo(ArrayList<Grenade> granadasEnSuelo) {
        this.granadasEnSuelo = granadasEnSuelo;
    }

    public ArrayList<Weapon> getArmasEnSuelo() {
        return armasEnSuelo;
    }

    public void setArmasEnSuelo(ArrayList<Weapon> armasEnSuelo) {
        this.armasEnSuelo = armasEnSuelo;
    }





    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public ArrayList<Paredes> getParedes() {
        return paredes;
    }

    public void setParedes(ArrayList<Paredes> paredes) {
        this.paredes = paredes;
    }
}
