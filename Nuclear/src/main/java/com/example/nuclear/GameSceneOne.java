package com.example.nuclear;

import com.example.nuclear.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class GameSceneOne {
    @FXML
    private Canvas canvas;
    @FXML
    private ImageView fondoIMGVIEW;
    @FXML
    private AnchorPane fondoNivel;
    private GraphicsContext gc;

    private ArrayList<Level> levels;
    private int currentLevel = 0;

    private int shotsFired = 0;

    Image backgroundImage;

    private boolean isAlive = true;
    private boolean Apressed = false;
    private boolean Wpressed = false;
    private boolean Spressed = false;
    private boolean Dpressed = false;

    private Avatar avatar;
    private Paredes paredes;
    private ArrayList<Weapon> weapons;

    private ArrayList<Grenade> grenades;

    private LifeBar lifeBar;

    private BulletBar bulletBar;

    @FXML
    public void initialize() {


        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        String uri = "file:" + HelloApplication.class.getResource("FLoorL1.png").getPath();
        backgroundImage = new Image(uri);
        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnKeyReleased(this::handleKeyReleased);
        canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseMoved(this::onMouseMoved);
        avatar = new Avatar();
        levels = new ArrayList<>();
        weapons = new ArrayList<>();
        grenades = new ArrayList<>();

        //Barras o indicadores

        //De vida
        Image[] lifeImages = {
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\2golpes.png"),   // Imagen para ultimo golpe
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\1golpe.png"),   // Imagen para un  golpes
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\fullVida.png")  // Imagen full de vida

        };

         lifeBar = new LifeBar(lifeImages,10,10);


         //De balas
        Image[] bulletBarImages = {
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\indicadorDebala0.png"),   // Imagen para no tener balas
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\indicadordebala1.png"),   // 1 bala
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\indicador2debala.png"),  // 2 balas
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\indicadordebala3.png"),  // 3 balas
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\indicadorDebala4.png"),  // 4 balas
                new Image("c:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\indicadorDebala5.png")  // full balas
        };

        bulletBar = new BulletBar(bulletBarImages,10,45);







        // Generate the first map
        Level l1 = new Level(0);
        Enemy e = new Enemy(new Vector(400, 100));
        new Thread(e).start();
        l1.getEnemies().add(e);
        l1.getEnemies().add(new Enemy(new Vector(400, 300)));
        levels.add(l1);


        //armas
        // Crear y configurar las armas

        for (int i = 0; i <= 1; i++) {

             levels.get(0).generarArmaAleatoriaEnSuelo("Ak",canvas.getWidth()-25,canvas.getHeight()-25);

        }

        weapons.addAll(l1.getArmasEnSuelo());

        for (int i = 0; i <= 1; i++) {
            levels.get(0).generarGranadasEnElSuelo("Grenade", canvas.getWidth() - 25, canvas.getWidth() - 25);


        }
        grenades.addAll(l1.getGranadasEnSuelo());

        // Generate the second map

        Level l2 = new Level(1);
        l2.setColor(Color.GRAY);
        l2.getEnemies().add(new Enemy(new Vector(100, 100)));
        l2.getEnemies().add(new Enemy(new Vector(100, 300)));
        l2.getEnemies().add(new Enemy(new Vector(300, 300)));
        levels.add(l2);
        drawParedes(0);
        drawParedes(1);
        draw();
    }

    public void handleKeyPressed(KeyEvent event) {
        System.out.println(event.getCode());
        switch (event.getCode()) {
            case W:
                Wpressed = true;
                avatar.keyPressed("W");
                break;
            case A:
                Apressed = true;
                avatar.keyPressed("A");
                break;
            case S:
                Spressed = true;
                avatar.keyPressed("S");
                break;
            case D:
                Dpressed = true;
                avatar.keyPressed("D");
                break;
            case SPACE:
                avatar.keyPressed("SPACE");
                break;
        }
    }

    public void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                Wpressed = false;
                break;
            case A:
                Apressed = false;
                break;
            case S:
                Spressed = false;
                break;
            case D:
                Dpressed = false;
                break;
        }
    }

    public void onKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                Wpressed = false;
                break;
            case A:
                Apressed = false;
                break;
            case S:
                Spressed = false;
                break;
            case D:
                Dpressed = false;
                break;
        }
    }
    private void onMouseMoved(MouseEvent e) {
        double relativePosition = e.getX()-avatar.pos.getX();
        avatar.setFacingRight(
                relativePosition > 0
        );
    }
    private void onMousePressed(MouseEvent e) {
        System.out.println("X: " + e.getX() + " Y: " + e.getY());

        if (avatar.getWeapon() != null) {
            if (shotsFired >= 5) {

                //llamo al hilo
                avatar.getWeapon().reload();

                shotsFired = 0;
                bulletBar.restart();

            } else {

                double diffX = e.getX() - avatar.pos.getX();
                double diffY = e.getY() - avatar.pos.getY();
                Vector diff = new Vector(diffX, diffY);
                diff.normalize();
                diff.setMag(4);

                Bullet bullet = new Bullet(new Vector(avatar.pos.getX(), avatar.pos.getY()), diff);
                Image image = new Image("C:\\Users\\Admin\\Desktop\\Tercer semestre\\Integradora3\\NuclearThrone\\Nuclear\\src\\main\\resources\\com\\example\\nuclear\\bulletLaser.png");
                bullet.setImage(image);

                levels.get(currentLevel).getBullets().add(bullet);
                shotsFired++;
                System.out.println("shots fired "+shotsFired);
                avatar.getWeapon().shoot();

                //disminuir del indicador
                bulletBar.decreaseBullet();



            }
        } else {
            System.out.println("No weapon");
        }
    }


    public void draw() {
        Thread ae = new Thread(() -> {
            while (isAlive) {
                // Draw on the canvas
                Level level = levels.get(currentLevel);

                Platform.runLater(() -> {
                    gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.setFill(Color.BLACK);
                    gc.fillRect(canvas.getWidth(), 0, 10, 10);
                    avatar.draw(gc);
                    avatar.setMoving(Wpressed || Spressed || Dpressed || Apressed);
                    for (int i = 0; i < level.getBullets().size(); i++) {
                        level.getBullets().get(i).draw(gc);
                        if (isOutside(level.getBullets().get(i).pos.getX(), level.getBullets().get(i).pos.getY())) {
                            level.getBullets().remove(i);
                        }
                    }
                    for (int i = 0; i < level.getEnemies().size(); i++) {
                        level.getEnemies().get(i).draw(gc);
                    }
                    for (int i = 0; i < level.getParedes().size(); i++) {
                        level.getParedes().get(i).draw(gc);
                    }

                    //armas
                    // Dibujar las armas
                    for (int i = 0; i < level.getArmasEnSuelo().size(); i++) {
                        level.getArmasEnSuelo().get(i).draw(gc);

                    }

                    for (int i = 0; i < level.getGranadasEnSuelo().size(); i++) {
                        level.getGranadasEnSuelo().get(i).draw(gc);

                    }

                    ///dibuja los indicadores
                    lifeBar.draw(gc);

                    if(avatar.getWeapon()!=null) {
                        bulletBar.draw(gc);
                    }


                });

                // Geometric calculations

                // Paredes
                if (avatar.pos.getX() < 25) {
                    avatar.pos.setX(25);
                }
                if (avatar.pos.getY() > canvas.getHeight() - 25) {
                    avatar.pos.setY(canvas.getHeight() - 25);
                }
                if (avatar.pos.getX() > canvas.getWidth() - 25) {
                    avatar.pos.setX(canvas.getWidth() - 25);
                }
                if (avatar.pos.getY() < 0) {
                    currentLevel = 1;
                    avatar.pos.setY(canvas.getHeight());
                }

                // Collisions



                /// Dentro del bucle de dibujo en el método draw()
                for (int i = 0; i < level.getArmasEnSuelo().size(); i++) {
                    Weapon arma = level.getArmasEnSuelo().get(i);

                    double distance = Math.sqrt(
                            Math.pow(avatar.pos.getX() - arma.getPosX(), 2) +
                                    Math.pow(avatar.pos.getY() - arma.getPosY(), 2)
                    );

                    if (distance < 30) {
                        // El jugador está cerca del arma, puede recojerla
                        avatar.equipWeapon(arma); // Agrega el arma a la lista de armas del jugador
                        level.getArmasEnSuelo().remove(i); // Remueve el arma del suelo
                        break; // Sale del bucle, asumiendo que solo se puede recojer una arma a la vez
                    }



                }






                for (int i = 0; i < level.getBullets().size(); i++) {
                    Bullet bn = level.getBullets().get(i);
                    for (int j = 0; j < level.getEnemies().size(); j++) {
                        Enemy en = level.getEnemies().get(j);

                        double distance = Math.sqrt(
                                Math.pow(en.pos.getX() - bn.pos.getX(), 2) +
                                        Math.pow(en.pos.getY() - bn.pos.getY(), 2)
                        );

                        if (distance < 5) {
                            level.getBullets().remove(i);
                            level.getEnemies().remove(j);
                        }
                    }
                }

                if (Wpressed) {
                    avatar.pos.setY(avatar.pos.getY() - 3);
                }
                if (Apressed) {
                    avatar.pos.setX(avatar.pos.getX() - 3);
                }
                if (Spressed) {
                    avatar.pos.setY(avatar.pos.getY() + 3);
                }
                if (Dpressed) {
                    avatar.pos.setX(avatar.pos.getX() + 3);
                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ae.start();
    }

    public boolean isOutside(double x, double y) {
        return x < -10 || y < -10 || x > canvas.getWidth() || y > canvas.getHeight();
    }

    public void drawParedes(int index) {
        String path = "file:" + HelloApplication.class.getResource("ParedFill.png").getPath();
        int height = 40;
        Level lvl = levels.get(index);
        lvl.getParedes().add(new Paredes(canvas, path, 300, 20));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (10 + height)));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (3 + height * 2)));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (-4 + height * 3)));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (-11 + height * 4)));
    }




}
