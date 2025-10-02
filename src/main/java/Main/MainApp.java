package Main;  // You can create a new package Main or place it at src/main/java/

import javafx.application.Application;
import User.Gui.LoginGUI;

public class MainApp {
    public static void main(String[] args) {
        // Launch the LoginGUI
        Application.launch(LoginGUI.class, args);
    }
}
