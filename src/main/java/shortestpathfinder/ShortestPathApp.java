package shortestpathfinder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ShortestPathApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI elements
        TextField location1 = new TextField();
        location1.setPromptText("Enter Location 1");
        TextField location2 = new TextField();
        location2.setPromptText("Enter Location 2");
        Button calculateButton = new Button("Calculate Shortest Distance and Route");
        Label resultLabel = new Label("Result: ");

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(location1, location2, calculateButton, resultLabel);

        // Button Action
        calculateButton.setOnAction(e -> {
            String loc1 = location1.getText();
            String loc2 = location2.getText();
            // Call Dijkstra's algorithm method to calculate the distance and path
            String result = Dijkstra.calculateShortestPath(loc1, loc2);
            resultLabel.setText(result);
        });

        // Scene setup
        Scene scene = new Scene(layout, 400, 250);
        primaryStage.setTitle("Shortest Path Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
