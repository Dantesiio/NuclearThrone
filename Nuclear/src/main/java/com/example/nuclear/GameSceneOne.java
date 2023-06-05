package com.example.nuclear;

import com.example.nuclear.model.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    Image backgroundImage;

    private boolean isAlive = true;
    private boolean Apressed = false;
    private boolean Wpressed = false;
    private boolean Spressed = false;
    private boolean Dpressed = false;

    private Avatar avatar;
    private Paredes paredes;


    private Scene scene;

    public GameSceneOne() {
        canvas = new Canvas();  // Inicialización del objeto canvas
    }
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
        // Generate the first map
        Level l1 = new Level(0);
        Enemy e = new Enemy(new Vector(400, 100));
        new Thread(e).start();
        l1.getEnemies().add(e);
        l1.getEnemies().add(new Enemy(new Vector(400, 300)));
        levels.add(l1);
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

        double diffX = e.getX() - avatar.pos.getX();
        double diffY = e.getY() - avatar.pos.getY();
        Vector diff = new Vector(diffX, diffY);
        diff.normalize();
        diff.setMag(4);

        levels.get(currentLevel).getBullets().add(
                new Bullet(
                        new Vector(avatar.pos.getX(), avatar.pos.getY()),
                        diff
                )
        );
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
        ae.setDaemon(true); // Establecer como daemon para que se detenga cuando se cierre la aplicación
        ae.start();
    }

    public void checkCollisions(){
        return;
    }

    public boolean isOutside(double x, double y) {
        return x < -10 || y < -10 || x > canvas.getWidth() || y > canvas.getHeight();
    }
    private double clampRange(double value,int min, double max){
        if(value<min){
            return min;
        } else if (value>max){
            return max;
        }else {
            return value;
        }
    }
    public void camera(){
        this.scene = canvas.getScene();
        Rectangle camera = new Rectangle();

        camera.widthProperty().bind(scene.widthProperty());
        camera.heightProperty().bind(scene.heightProperty());
        camera.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(avatar.pos.getX() - scene.getWidth() / 2, 0, canvas.getWidth() - scene.getWidth()),
                avatar.getxProperty(), scene.widthProperty()));
        camera.yProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(avatar.pos.getY() - scene.getHeight() / 2, 0, canvas.getHeight() - scene.getHeight()),
                avatar.getyProperty(), scene.heightProperty()));
    }


    public void drawParedes(int index) {
        String path = "file:" + HelloApplication.class.getResource("ParedFill.png").getPath();
        int height = 40;
        Level lvl = levels.get(index);
        lvl.getParedes().add(new Paredes(canvas, path, 100, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 130, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 70, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 40, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 10, 20));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (10 + height)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (3 + height * 2)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-4 + height * 3)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-11 + height * 4)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-18 + height * 5)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-25 + height * 6)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-32 + height * 7)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-39 + height * 8)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-46 + height * 9)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-53 + height * 10)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-60 + height * 11)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-67 + height * 12)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-74 + height * 13)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-81 + height * 14)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-88 + height * 15)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-95 + height * 16)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-102 + height * 17)));
        lvl.getParedes().add(new Paredes(canvas, path, 10, (-109 + height * 18)));





        lvl.getParedes().add(new Paredes(canvas, path, 300, 20));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (10 + height)));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (3 + height * 2)));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (-4 + height * 3)));
        lvl.getParedes().add(new Paredes(canvas, path, 300, (-11 + height * 4)));


        lvl.getParedes().add(new Paredes(canvas, path, 330, 20));


        lvl.getParedes().add(new Paredes(canvas, path, 360, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 390, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 420, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 450, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 480, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 510, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 540, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 570, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 600, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 630, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 660, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 690, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 720, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 750, 20));

        lvl.getParedes().add(new Paredes(canvas, path, 780, 20));





    }
}
