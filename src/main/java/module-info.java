module com.example.cab302javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires org.controlsfx.controls;
    requires com.google.gson;

    opens com.example.cab302javaproject to javafx.fxml, com.google.gson;
    exports com.example.cab302javaproject;
}
