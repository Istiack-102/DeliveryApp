package Rider;

import Rider.RiderDB;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI elements
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Label resultLabel = new Label();

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(usernameField, passwordField, loginButton, registerButton, resultLabel);

        // Login button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (RiderDB.validateRiderLogin(username, password)) {
                resultLabel.setText("Login successful!");
                // Proceed to rider dashboard
                DashboardGUI riderDashboard = new DashboardGUI();
                try {
                    riderDashboard.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                primaryStage.close();  // Close login page
            } else {
                resultLabel.setText("Invalid username or password.");
            }
        });

        // Register button action: Navigate to Rider Registration page
        registerButton.setOnAction(e -> {
            RegisterGUI riderRegisterGUI = new RegisterGUI();
            try {
                riderRegisterGUI.start(new Stage());  // Open the rider registration page
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();  // Close the login page
        });

        // Scene setup
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Rider Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
