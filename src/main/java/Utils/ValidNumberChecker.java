package Utils;

public class ValidNumberChecker {

    public static boolean isValidBangladeshPhoneNumber(String phoneNumber) {
        // Bangladesh mobile numbers start with 01 followed by 3-9, then 8 digits
        return phoneNumber.matches("^(\\+8801|01)[3-9]\\d{8}$");
    }
}
