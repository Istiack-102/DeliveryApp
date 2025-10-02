package Rider;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Rider Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // Input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Buttons
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        // Result label
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, registerButton, resultLabel);

        // Login button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (RiderDB.validateRiderLogin(username, password)) {
                resultLabel.setText("Login successful!");
                DashboardGUI riderDashboard = new DashboardGUI();
                try {
                    riderDashboard.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                primaryStage.close();
            } else {
                resultLabel.setText("Invalid username or password.");
            }
        });

        // Register button action
        registerButton.setOnAction(e -> {
            RegisterGUI riderRegisterGUI = new RegisterGUI();
            try {
                riderRegisterGUI.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        // Scene setup
        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setTitle("Rider Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
