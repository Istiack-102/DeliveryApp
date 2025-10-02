package Utils;

public class CardValidator {

    // Method to validate the card number using the Luhn algorithm
    public static boolean isValidCardNumber(String cardNumber) {
        // Remove spaces or dashes from the card number
        cardNumber = cardNumber.replaceAll("[^0-9]", "");

        // Check if the card number is empty or not numeric
        if (cardNumber.isEmpty() || !cardNumber.matches("\\d+")) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;

        // Traverse the card number in reverse order
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));

            // Double every second digit starting from the right
            if (alternate) {
                digit *= 2;
                // If doubling results in a number greater than 9, subtract 9
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }

        // If the sum modulo 10 is 0, the card number is valid
        return sum % 10 == 0;
    }

    // Method to validate if the CVV is a 3-digit number
    public static boolean isValidCVV(String cvv) {
        // Check if CVV is exactly 3 digits
        return cvv != null && cvv.matches("\\d{3}");
    }


}
