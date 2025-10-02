package Rider;

import Utils.DBUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import shortestpathfinder.ShortestPathApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardGUI extends Application {

    private TableView<OrderItem> orderTable;

    @Override
    public void start(Stage primaryStage) {
        // TableView setup
        orderTable = new TableView<>();
        TableColumn<OrderItem, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userCol.setMinWidth(150);

        TableColumn<OrderItem, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setMinWidth(200);

        orderTable.getColumns().addAll(userCol, addressCol);

        Button acceptOrderButton = new Button("Accept Order");
        Button refreshButton = new Button("Refresh Orders");

        // Style buttons
        acceptOrderButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        refreshButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        Label title = new Label("Rider Dashboard - Pending Orders");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
        layout.getChildren().addAll(title, orderTable, acceptOrderButton, refreshButton);

        // Load orders from database
        loadOrdersFromDatabase();

        // Refresh button
        refreshButton.setOnAction(e -> loadOrdersFromDatabase());

        // Accept order button
        acceptOrderButton.setOnAction(e -> {
            OrderItem selectedOrder = orderTable.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                // Delete order from DB
                if (deleteOrderFromDB(selectedOrder.getUsername())) {
                    orderTable.getItems().remove(selectedOrder); // remove from UI
                    System.out.println("Order accepted and removed: " + selectedOrder);

                    // Open ShortestPathApp for delivery
                    ShortestPathApp shortestPathApp = new ShortestPathApp();
                    Stage deliveryStage = new Stage();
                    try {
                        shortestPathApp.start(deliveryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to accept order: " + selectedOrder);
                }
            } else {
                System.out.println("No order selected.");
            }
        });

        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("Rider Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadOrdersFromDatabase() {
        orderTable.getItems().clear();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT username, address FROM Billing";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String address = rs.getString("address");
                    orderTable.getItems().add(new OrderItem(username, address));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteOrderFromDB(String username) {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "DELETE FROM Billing WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class OrderItem {
        private final String username;
        private final String address;

        public OrderItem(String username, String address) {
            this.username = username;
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public String getAddress() {
            return address;
        }

        @Override
        public String toString() {
            return username + " | " + address;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
