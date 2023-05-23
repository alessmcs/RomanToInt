module com.example.testfx1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testfx1 to javafx.fxml;
    exports com.example.testfx1;
}