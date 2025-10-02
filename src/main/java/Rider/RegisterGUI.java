package Rider;

import Utils.StrongPass;
import Utils.ValidNumberChecker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RegisterGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title
        Label titleLabel = new Label("Rider Registration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // Input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        TextField vehicleInfoField = new TextField();
        vehicleInfoField.setPromptText("Vehicle Information");

        // Instructions
        Label instructions = new Label(
                "Password must be at least 8 characters and contain:\n" +
                        "1 uppercase, 1 lowercase, 1 number, 1 special char"
        );
        instructions.setFont(Font.font("Arial", 12));

        // Result label
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        // Register button
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // Layout
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                titleLabel,
                usernameField,
                passwordField,
                nameField,
                phoneField,
                vehicleInfoField,
                instructions,
                registerButton,
                resultLabel
        );

        // Register button action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String vehicleInfo = vehicleInfoField.getText();

            // Check password strength
            if (!StrongPass.isStrongPassword(password)) {
                resultLabel.setText("Password is not strong enough. Please follow the instructions.");
                return;
            }

            // Check username availability
            if (!RiderDB.isUsernameAvailable(username)) {
                resultLabel.setText("Username is already taken. Please choose another.");
                return;
            }

            // Check valid phone number
            if (!ValidNumberChecker.isValidBangladeshPhoneNumber(phone)) {
                resultLabel.setText("Enter a valid phone number.");
                return;
            }

            // Register rider
            if (RiderDB.registerRider(username, password, name, phone, vehicleInfo)) {
                resultLabel.setText("Registration successful!");
                LoginGUI riderLoginGUI = new LoginGUI();
                try {
                    riderLoginGUI.start(new Stage());  // Open rider login
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                primaryStage.close();
            } else {
                resultLabel.setText("Registration failed. Please check the details.");
            }
        });

        // Scene setup
        Scene scene = new Scene(layout, 400, 450);
        primaryStage.setTitle("Rider Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
