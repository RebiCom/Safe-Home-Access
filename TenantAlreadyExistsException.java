package isp.lab7.safehome;

public class TenantAlreadyExistsException extends RuntimeException {
    public TenantAlreadyExistsException(String message) {
        super("Locatarul exista deja in sistem.");
    }
}
