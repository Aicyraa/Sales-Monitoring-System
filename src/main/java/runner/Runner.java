package runner;

import javafx.application.Application;

/**
 * Main entry point for the Sales Monitoring System application.
 * Launches the JavaFX application.
 */
public class Runner {
    /**
     * Application entry point.
     * Launches the SalesMonitoringSystem JavaFX application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Application.launch(SalesMonitoringSystem.class, args);
    }
}