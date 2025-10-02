package User;

import User.Gui.LoginGUI;
import Utils.DBUtil;
import Utils.CardValidator;
import Utils.AutoCompleteTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.Arrays;

public class Payment extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Sample locations for autocomplete
        var locations = Arrays.asList(
                "Dhanmondi", "Gulshan", "Banani", "Mirpur", "Uttara",
                "Motijheel", "Farmgate", "Bashundhara", "Paltan"
        );

        // Autocomplete billing address field
        AutoCompleteTextField addressField = new AutoCompleteTextField(locations);
        addressField.setPromptText("Enter Billing Address");

        TextField cardHolderField = new TextField();
        cardHolderField.setPromptText("Card Holder Name");

        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number (no spaces)");

        TextField expiryMonthField = new TextField();
        expiryMonthField.setPromptText("Expiry Month (MM)");

        TextField expiryYearField = new TextField();
        expiryYearField.setPromptText("Expiry Year (YYYY)");

        PasswordField cvvField = new PasswordField();
        cvvField.setPromptText("CVV (3 digits)");

        Button submitPaymentButton = new Button("Submit Payment");
        Label statusLabel = new Label();

        // Button action to submit payment
        submitPaymentButton.setOnAction(e -> {
            String address = addressField.getText().trim();
            String cardHolder = cardHolderField.getText().trim();
            String cardNumber = cardNumberField.getText().trim().replaceAll("\\s+", "");
            String expiryMonthStr = expiryMonthField.getText().trim();
            String expiryYearStr = expiryYearField.getText().trim();
            String cvv = cvvField.getText().trim();

            String username = LoginGUI.getLoggedInUsername();
            if (username == null) {
                statusLabel.setText("You must be logged in to make a payment.");
                return;
            }

            String phoneNumber = UserDB.getUserPhoneNumber(username);

            // Basic validations
            if (address.isEmpty()) { statusLabel.setText("Please enter your address."); return; }
            if (cardHolder.isEmpty()) { statusLabel.setText("Please enter card holder name."); return; }
            if (!CardValidator.isValidCardNumber(cardNumber)) { statusLabel.setText("Invalid card number."); return; }
            if (!CardValidator.isValidCVV(cvv)) { statusLabel.setText("CVV must be exactly 3 digits."); return; }

            int month, year;
            try {
                month = Integer.parseInt(expiryMonthStr);
                year = Integer.parseInt(expiryYearStr);
            } catch (NumberFormatException ex) {
                statusLabel.setText("Expiry month/year must be numeric."); return;
            }

            if (month < 1 || month > 12) { statusLabel.setText("Expiry month must be between 1 and 12."); return; }

            YearMonth now = YearMonth.now();
            YearMonth expiry = YearMonth.of(year, month);
            if (expiry.isBefore(now)) { statusLabel.setText("Card expiry date is in the past."); return; }

            // Store billing info
            boolean stored = storeBillingInformation(username, phoneNumber, address);
            statusLabel.setText(stored ? "Payment successful! Billing info saved." : "Error storing billing information.");
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Billing Address:"), addressField,
                new Separator(),
                new Label("Card Details (validation only):"),
                cardHolderField, cardNumberField,
                new HBoxSpacing(expiryMonthField, expiryYearField),
                cvvField,
                submitPaymentButton, statusLabel
        );

        Scene scene = new Scene(layout, 380, 450);
        primaryStage.setTitle("Payment");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean storeBillingInformation(String username, String phoneNumber, String address) {
        String query = "INSERT INTO Billing (username, phone_number, address) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, address);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) { launch(args); }

    private static class HBoxSpacing extends javafx.scene.layout.HBox {
        HBoxSpacing(javafx.scene.Node left, javafx.scene.Node right) { super(8, left, right); }
    }
}
