package User.Gui;

import User.UserDB;
import Utils.StrongPass;
import Utils.ValidNumberChecker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        Button registerButton = new Button("Register");
        Label resultLabel = new Label();

        Label instructions = new Label(
                "Password must be at least 8 characters, contain:\n" +
                        "1 uppercase, 1 lowercase, 1 number, 1 special char"
        );

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(usernameField, passwordField, phoneField, instructions, registerButton, resultLabel);

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

        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
