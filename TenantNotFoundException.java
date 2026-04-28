package isp.lab7.safehome;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String message) {
        super("Locatarul nu a fost gasit.");
    }
}
