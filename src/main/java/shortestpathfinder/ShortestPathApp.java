package shortestpathfinder;

import Utils.AutoCompleteTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Arrays;

public class ShortestPathApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label titleLabel = new Label("Rider - Shortest Path Finder (OpenStreetMap)");
        titleLabel.setFont(Font.font("Arial", 18));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

        var locations = Arrays.asList(
                "Dhanmondi", "Gulshan", "Banani", "Mirpur", "Uttara",
                "Motijheel", "Farmgate", "Bashundhara", "Paltan"
        );

        AutoCompleteTextField location1 = new AutoCompleteTextField(locations);
        location1.setPromptText("Enter Pickup Location");

        AutoCompleteTextField location2 = new AutoCompleteTextField(locations);
        location2.setPromptText("Enter Delivery Location");

        Button calculateButton = new Button("Calculate Shortest Route");
        calculateButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");

        Label resultLabel = new Label("Result will be shown here");
        resultLabel.setWrapText(true);
        resultLabel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px; -fx-padding: 5px; -fx-background-color:git #f9f9f9;");

        WebView mapView = new WebView();
        mapView.setPrefHeight(400);

        VBox inputLayout = new VBox(10, location1, location2, calculateButton);
        inputLayout.setPadding(new Insets(15));
        inputLayout.setStyle("-fx-background-color: #eef2f3; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        VBox layout = new VBox(15, titleLabel, inputLayout, resultLabel, mapView);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #ffffff;");

        calculateButton.setOnAction(e -> {

            // ✅ Force all locations to be inside Dhaka
            String origin = location1.getText().trim() ;
            String destination = location2.getText().trim() ;

            if (origin.isEmpty() || destination.isEmpty()) {
                resultLabel.setText("Please enter both pickup and delivery locations.");
                return;
            }

            // Geocode origin
            double[] start = OSMGeocoder.geocode(origin);
            if (start == null) {
                resultLabel.setText("Could not find location: " + origin);
                return;
            }

            // Geocode destination
            double[] end = OSMGeocoder.geocode(destination);
            if (end == null) {
                resultLabel.setText("Could not find location: " + destination);
                return;
            }

            // Get route from OSRM
            OSRMDirections.RouteResult route = OSRMDirections.getRoute(start[0], start[1], end[0], end[1]);
            if (route == null) {
                resultLabel.setText("No route found between these locations.");
                return;
            }

            double distanceKm = route.distanceMeters / 1000.0;
            double durationMin = route.durationSeconds / 60.0;

            resultLabel.setText(String.format(
                    "Shortest Distance: %.2f km\nEstimated Time: %.1f minutes",
                    distanceKm, durationMin
            ));

            // Show route on map
            OSMMapView.loadRoute(mapView, route.jsLatLngArray);
        });

        Scene scene = new Scene(layout, 800, 700);
        primaryStage.setTitle("Shortest Path Finder - OSM/OSRM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
