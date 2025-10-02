package shortestpathfinder;

import Utils.AutoCompleteTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;

public class ShortestPathApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Rider - Shortest Path Finder");
        titleLabel.setFont(Font.font("Arial", 18));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

        // Sample locations for autocomplete
        var locations = Arrays.asList(
                "Dhanmondi", "Gulshan", "Banani", "Mirpur", "Uttara",
                "Motijheel", "Farmgate", "Bashundhara", "Paltan"
        );

        // Autocomplete text fields
        AutoCompleteTextField location1 = new AutoCompleteTextField(locations);
        location1.setPromptText("Enter Pickup Location");

        AutoCompleteTextField location2 = new AutoCompleteTextField(locations);
        location2.setPromptText("Enter Delivery Location");

        // Button to calculate shortest distance
        Button calculateButton = new Button("Calculate Shortest Route");
        calculateButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");

        // Result label
        Label resultLabel = new Label("Result will be shown here");
        resultLabel.setWrapText(true);
        resultLabel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px; -fx-padding: 5px; -fx-background-color: #f9f9f9;");

        // Layout containers
        VBox inputLayout = new VBox(10, location1, location2, calculateButton);
        inputLayout.setPadding(new Insets(15));
        inputLayout.setStyle("-fx-background-color: #eef2f3; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        VBox layout = new VBox(15, titleLabel, inputLayout, resultLabel);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #ffffff;");

        // Button action
        calculateButton.setOnAction(e -> {
            String loc1Text = location1.getText();
            String loc2Text = location2.getText();
            if (loc1Text.isEmpty() || loc2Text.isEmpty()) {
                resultLabel.setText("Please enter both pickup and delivery locations.");
            } else {
                // Call Dijkstra's algorithm method to calculate the distance and path
                String result = Dijkstra.calculateShortestPath(loc1Text, loc2Text);
                resultLabel.setText(result);
            }
        });

        // Scene setup
        Scene scene = new Scene(layout, 450, 300);
        primaryStage.setTitle("Shortest Path Finder - Rider");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
