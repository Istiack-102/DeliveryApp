package Rider;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI elements for Rider Dashboard
        ListView<String> orderList = new ListView<>();
        Button acceptOrderButton = new Button("Accept Order");
        Button completeDeliveryButton = new Button("Complete Delivery");

        // Add dummy orders
        orderList.getItems().addAll("Order 1", "Order 2", "Order 3");

        // Handle order acceptance and completion
        acceptOrderButton.setOnAction(e -> {
            String selectedOrder = orderList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                // Logic to accept the order
                System.out.println("Order accepted: " + selectedOrder);
            }
        });

        completeDeliveryButton.setOnAction(e -> {
            String selectedOrder = orderList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                // Logic to complete the delivery
                System.out.println("Delivery completed for: " + selectedOrder);
            }
        });

        // Layout for Rider Dashboard
        VBox layout = new VBox(10);
        layout.getChildren().addAll(orderList, acceptOrderButton, completeDeliveryButton);

        // Scene setup
        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setTitle("Rider Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
