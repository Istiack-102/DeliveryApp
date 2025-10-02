package User.Gui;

import User.Order;
import User.UserDB;
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

    private static String loggedInUsername = null;  // To store the username of the logged-in user

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("User Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // Input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Result label
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        // Buttons
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, registerButton, resultLabel);

        // Login action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (UserDB.validateLogin(username, password)) {
                resultLabel.setText("Login successful!");
                loggedInUsername = username;

                try {
                    Order orderPage = new Order();
                    Stage orderStage = new Stage();
                    orderPage.start(orderStage);
                    primaryStage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                resultLabel.setText("Invalid username or password.");
            }
        });

        // Register page
        registerButton.setOnAction(e -> {
            RegisterGUI registerGUI = new RegisterGUI();
            try {
                registerGUI.start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
