package runner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application class for the Sales Monitoring System.
 * Initializes and displays the main application window.
 */
public class SalesMonitoringSystem extends Application {
    /**
     * Starts the JavaFX application.
     * Loads the main FXML layout, applies CSS styling, and configures the primary
     * stage.
     * 
     * @param stage The primary stage for this application
     * @throws Exception If FXML or CSS resources cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(root, 1400, 850);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Sales Monitoring System");
        stage.setScene(scene);
        stage.setMinWidth(1200);
        stage.setMinHeight(700);
        stage.show();
    }
}