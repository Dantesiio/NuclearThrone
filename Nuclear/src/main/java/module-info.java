module com.example.nuclear {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nuclear to javafx.fxml;
    exports com.example.nuclear;
}