package isp.lab7.safehome;

import java.util.List;

public interface ControllerInterface {
    /**
     * Master key used for emergency unlock and retry reset.
     */
    String MASTER_KEY = "0000";

    /**
     * Default name for the master user.
     */
    String MASTER_TENANT_NAME = "Admin";

    /**
     * Lock / unlock door based on pin input.
     * If pin is wrong, InvalidPinException will be thrown.
     * If 3 consecutive fail attempts are made, TooManyAttemptsException will be thrown.
     * If MASTER_KEY is used, retries reset to 0 and door toggles state.
     *
     * @param pin - pin value
     * @return current DoorStatus status
     * @throws Exception
     */
    DoorStatus enterPin(String pin) throws Exception;

    /**
     * Add new tenant in the system.
     * When tenant with this name already exists, TenantAlreadyExistsException will be thrown.
     *
     * @param pin  - pin to be added
     * @param name - tenant name to be added
     * @throws Exception
     */
    void addTenant(String pin, String name) throws Exception;

    /**
     * Remove existing tenant from the system.
     * When tenant with this name not found, TenantNotFoundException will be thrown.
     *
     * @param name - tenant name to be removed
     * @throws Exception
     */
    void removeTenant(String name) throws Exception;

    /**
     * Returns the list of all access attempts (successful or not).
     * Required by functional requirement #7.
     * * @return List of AccessLog objects
     */
    List<AccessLog> getAccessLogs();
}