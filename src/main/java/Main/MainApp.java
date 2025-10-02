package Main;

import User.Gui.LoginGUI;  // Import User Login
import Rider.*;            // Import Rider Login
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title label
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("Welcome to the Delivery App");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Buttons for User and Rider login
        Button userLoginButton = new Button("Login as User");
        Button riderLoginButton = new Button("Login as Rider");

        // Style buttons
        userLoginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 200px;");
        riderLoginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 200px;");

        // Set up actions
        userLoginButton.setOnAction(e -> {
            LoginGUI userLogin = new LoginGUI();
            try {
                userLogin.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        riderLoginButton.setOnAction(e -> {
            Rider.LoginGUI riderLogin = new Rider.LoginGUI();
            try {
                riderLogin.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
        });

        // Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, userLoginButton, riderLoginButton);

        // Scene setup
        Scene scene = new Scene(layout, 350, 250);
        primaryStage.setTitle("Delivery App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
