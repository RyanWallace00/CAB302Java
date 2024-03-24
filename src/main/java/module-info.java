module com.example.cab302javaproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cab302javaproject to javafx.fxml;
    exports com.example.cab302javaproject;
}