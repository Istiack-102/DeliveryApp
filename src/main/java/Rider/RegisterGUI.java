package Rider;
import Utils.StrongPass;
import Rider.RiderDB;
import Utils.ValidNumberChecker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RegisterGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // UI elements
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        TextField vehicleInfoField = new TextField();
        vehicleInfoField.setPromptText("Vehicle Information");

        Button registerButton = new Button("Register");
        Label resultLabel = new Label();


        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(usernameField, passwordField, nameField, phoneField, vehicleInfoField, registerButton, resultLabel);

        // Register button action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String vehicleInfo = vehicleInfoField.getText();
            // Check if the password is strong
            if (!StrongPass.isStrongPassword(password)) {
                resultLabel.setText("Password is not strong enough. Please follow the instructions.");
                return; // Exit the method early if password is weak
            }

            // Check if the username is available
            if (!RiderDB.isUsernameAvailable(username)) {
                resultLabel.setText("Username is already taken. Please choose another.");
                return; // Exit the method early if username is taken
            }
            if (!ValidNumberChecker.isValidBangladeshPhoneNumber((phone))){
                resultLabel.setText("Enter a valid number");
                return;
            }

            // Register the rider and check if the registration was successful
            if (RiderDB.registerRider(username, password, name, phone, vehicleInfo)) {
                resultLabel.setText("Registration successful!");

                // After successful registration, go to the rider login page
                LoginGUI riderLoginGUI = new LoginGUI();
                try {
                    riderLoginGUI.start(new Stage());  // Open the rider login page
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                primaryStage.close();  // Close the registration page
            } else {
                resultLabel.setText("Registration failed. Please check the details.");
            }
        });

        // Scene setup
        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setTitle("Rider Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
