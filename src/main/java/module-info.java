module ShortestPathFinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports Main;
    exports Rider;
    requires javafx.web;
    requires java.net.http;
    requires org.json;
    requires javafx.graphics;
    requires javafx.base;

    // Export the required packages
    exports User;
    exports User.Gui;
    exports Utils;
    exports shortestpathfinder;  // Make sure to export the ShortestPathFinder package

    // Open the Gui package for JavaFX to use reflection if needed
    opens Main to javafx.fxml;
}
