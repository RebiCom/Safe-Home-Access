package isp.lab7.safehome;
import java.time.LocalDateTime;

public class AccessLog {
    private String tenantName;
    private LocalDateTime dateTime;
    private String operation;
    private DoorStatus doorStatus;
    private String errorMessage;

    public AccessLog(String tenantName, String operation, DoorStatus doorStatus, String errorMessage) {
        this.tenantName = tenantName;
        this.dateTime = LocalDateTime.now();
        this.operation = operation;
        this.doorStatus = doorStatus;
        this.errorMessage = errorMessage;
    }
        @Override
        public String toString() {
            return String.format("[%s] User: %-10s | Op: %-10s | Status: %-7s | Error: %s",
                    dateTime, tenantName, operation, doorStatus, errorMessage == null ? "None" : errorMessage);
        }
}
