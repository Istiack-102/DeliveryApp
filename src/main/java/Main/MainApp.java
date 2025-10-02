package Main;

import User.Gui.LoginGUI;  // Import User Login from User.Gui package
import Rider.*;  // Import Rider Login directly from Rider package
  // Import Rider Register directly from Rider package
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons for User and Rider login
        Button userLoginButton = new Button("Login as User");
        Button riderLoginButton = new Button("Login as Rider");

        // Set up actions for both buttons
        userLoginButton.setOnAction(e -> {
            LoginGUI userLogin = new LoginGUI();
            try {
                userLogin.start(new Stage());  // Open User Login
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();  // Close MainApp window
        });

        riderLoginButton.setOnAction(e -> {
            Rider.LoginGUI riderLogin = new Rider.LoginGUI();  // Correct usage of Rider Login
            try {
                riderLogin.start(new Stage());  // Open Rider Login
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();  // Close MainApp window
        });

        // Layout for MainApp
        VBox layout = new VBox(10);
        layout.getChildren().addAll(userLoginButton, riderLoginButton);

        // Scene setup for MainApp
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Welcome to the Delivery App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
