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
        openWindow("hello-view.fxml");
    }
    public static void openWindow(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Scene scene = new Scene(fxmlLoader.load());
            Cursor cursorPersonalizado = Cursor.cursor("file:/C:/Users/David/OneDrive/Escritorio/NuclearThrone/Nuclear/src/texturas/Aim.png");
            scene.setCursor(cursorPersonalizado);
            Stage stage = new Stage();
            stage.setTitle("NuclearThrone.exe");
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}