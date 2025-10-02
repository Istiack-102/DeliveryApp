package User.Gui;

import User.UserDB;
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
        Label titleLabel = new Label("User Registration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // Input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        // Instructions
        Label instructions = new Label(
                "Password must be at least 8 characters, contain:\n" +
                        "• 1 uppercase\n" +
                        "• 1 lowercase\n" +
                        "• 1 number\n" +
                        "• 1 special character"
        );
        instructions.setWrapText(true);
        instructions.setFont(Font.font("Arial", 12));

        // Result label
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        // Register button
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, phoneField, instructions, registerButton, resultLabel);

        // Registration action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String phone = phoneField.getText();

            if (!UserDB.isUsernameAvailable(username)) {
                resultLabel.setText("Username already taken.");
                return;
            }

            if (!StrongPass.isStrongPassword(password)) {
                resultLabel.setText("Password is not strong enough.");
                return;
            }

            if (!ValidNumberChecker.isValidBangladeshPhoneNumber(phone)) {
                resultLabel.setText("Invalid phone number.");
                return;
            }

            if (UserDB.registerUser(username, password, phone)) {
                resultLabel.setText("Registration complete!");

                // Go back to login page
                LoginGUI loginGUI = new LoginGUI();
                try { loginGUI.start(new Stage()); }
                catch (Exception ex) { ex.printStackTrace(); }
                primaryStage.close();
            } else {
                resultLabel.setText("Error during registration.");
            }
        });

        // Scene setup
        Scene scene = new Scene(layout, 400, 380);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
