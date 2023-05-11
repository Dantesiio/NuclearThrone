package com.example.nuclear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // Crear un objeto Cursor personalizado
        Cursor cursorPersonalizado = Cursor.cursor("file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/Aim.png");

        // Establecer el cursor personalizado en la escena
        scene.setCursor(cursorPersonalizado);
        stage.setTitle("NuclearThrone.exe");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}