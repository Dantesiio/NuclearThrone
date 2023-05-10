package com.example.nuclear;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class HelloController {
    @FXML
    private AnchorPane miAnchorPane;
    @FXML
    private AnchorPane innerAnchorPane;
    @FXML
    private Button play;
    @FXML
    private Label welcomeText;

    public void initialize() {
        // Establecer la imagen de fondo
        Image imagenDeFondo = new Image("file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/FondoGame.gif");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(imagenDeFondo, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background background = new Background(backgroundImage);
        miAnchorPane.setBackground(background);

        // Set innerAnchorPane to fill its parent
        AnchorPane.setTopAnchor(innerAnchorPane, 0.0);
        AnchorPane.setBottomAnchor(innerAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(innerAnchorPane, 0.0);
        AnchorPane.setRightAnchor(innerAnchorPane, 0.0);
    }
}
