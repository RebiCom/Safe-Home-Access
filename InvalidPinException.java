package isp.lab7.safehome;

public class InvalidPinException extends RuntimeException {
    public InvalidPinException(String message) {
        super("PIN-ul introdus este incorect.");
    }
}
