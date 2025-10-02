package User;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Order extends Application {

    private double totalBill = 0.0;

    @Override
    public void start(Stage primaryStage) {
        // Title Label
        Label titleLabel = new Label("Order Menu");
        titleLabel.setFont(new Font("Arial", 24));

        // Menu Items in GridPane for better alignment
        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(20);
        menuGrid.setVgap(10);
        menuGrid.setPadding(new Insets(10));

        CheckBox pizzaCheckBox = new CheckBox("Pizza - $10");
        CheckBox burgerCheckBox = new CheckBox("Burger - $5");
        CheckBox pastaCheckBox = new CheckBox("Pasta - $8");
        CheckBox saladCheckBox = new CheckBox("Salad - $4");

        menuGrid.add(pizzaCheckBox, 0, 0);
        menuGrid.add(burgerCheckBox, 1, 0);
        menuGrid.add(pastaCheckBox, 0, 1);
        menuGrid.add(saladCheckBox, 1, 1);

        // Calculate bill button and label
        Button calculateButton = new Button("Calculate Bill");
        calculateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        Label billLabel = new Label("Total Bill: $0.00");
        billLabel.setFont(new Font("Arial", 16));

        // Payment button
        Button paymentButton = new Button("Proceed to Payment");
        paymentButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        // Button actions
        calculateButton.setOnAction(e -> {
            totalBill = 0.0;
            if (pizzaCheckBox.isSelected()) totalBill += 10;
            if (burgerCheckBox.isSelected()) totalBill += 5;
            if (pastaCheckBox.isSelected()) totalBill += 8;
            if (saladCheckBox.isSelected()) totalBill += 4;
            billLabel.setText("Total Bill: $" + totalBill);
        });

        paymentButton.setOnAction(e -> {
            Payment paymentPage = new Payment();
            Stage paymentStage = new Stage();
            try {
                paymentPage.start(paymentStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, menuGrid, calculateButton, billLabel, paymentButton);

        // Scene
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setTitle("Order Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
