module com.example.cab302javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires org.controlsfx.controls;


    opens com.example.cab302javaproject to javafx.fxml;
    exports com.example.cab302javaproject;
}