package isp.lab7.safehome;

import java.util.*;

public class DoorLockController implements ControllerInterface {
    private Map<Tenant, AccessKey> validAccess = new HashMap<>();
    private List<AccessLog> logs = new ArrayList<>();
    private Door door = new Door();
    private int failedAttempts = 0;

    @Override
    public DoorStatus enterPin(String pin) throws Exception {
        // Regula 2: Verificăm dacă sistemul este deja blocat (3 încercări eșuate)
        if (failedAttempts >= 3 && !pin.equals(ControllerInterface.MASTER_KEY)) {
            logs.add(new AccessLog("Unknown", "enterPin", door.getStatus(), "TooManyAttemptsException"));
            throw new TooManyAttemptsException("Sistem blocat! Folosiți Master Key.");
        }

        // Regula 3: Verificăm Master Key - resetează încercările și schimbă starea ușii
        if (pin.equals(ControllerInterface.MASTER_KEY)) {
            failedAttempts = 0;
            toggleDoor();
            logs.add(new AccessLog(ControllerInterface.MASTER_TENANT_NAME, "enterPin", door.getStatus(), null));
            return door.getStatus();
        }

        // Regula 4: Verificăm PIN-urile locatarilor
        for (Map.Entry<Tenant, AccessKey> entry : validAccess.entrySet()) {
            if (entry.getValue().getPin().equals(pin)) {
                failedAttempts = 0;
                toggleDoor();
                logs.add(new AccessLog(entry.getKey().getName(), "enterPin", door.getStatus(), null));
                return door.getStatus();
            }
        }

        // Regula 1 & 2: Dacă PIN-ul este greșit
        failedAttempts++;
        if (failedAttempts >= 3) {
            logs.add(new AccessLog("Unknown", "enterPin", door.getStatus(), "TooManyAttemptsException"));
            throw new TooManyAttemptsException("Sistemul s-a blocat după 3 încercări.");
        } else {
            logs.add(new AccessLog("Unknown", "enterPin", door.getStatus(), "InvalidPinException"));
            throw new InvalidPinException("PIN incorect.");
        }
    }

    // Metodă pentru a schimba starea ușii (Toggle) conform cerinței #4
    private void toggleDoor() {
        if (door.getStatus() == DoorStatus.OPEN) {
            door.lockDoor();
        } else {
            door.unlockDoor();
        }
    }

    @Override
    public void addTenant(String pin, String name) throws Exception {
        // Regula 5: Verificăm dacă locatarul există deja
        for (Tenant t : validAccess.keySet()) {
            if (t.getName().equals(name)) {
                throw new TenantAlreadyExistsException("Locatarul există deja.");
            }
        }
        validAccess.put(new Tenant(name), new AccessKey(pin));
    }

    @Override
    public void removeTenant(String name) throws Exception {
        // Regula 6: Căutăm și ștergem locatarul
        Tenant target = null;
        for (Tenant t : validAccess.keySet()) {
            if (t.getName().equals(name)) {
                target = t;
                break;
            }
        }

        if (target == null) {
            throw new TenantNotFoundException("Locatarul nu a fost găsit.");
        }
        validAccess.remove(target);
    }

    @Override
    public List<AccessLog> getAccessLogs() {
        // Regula 7: Returnăm lista de log-uri
        return logs;
    }
}