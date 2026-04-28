package isp.lab7.safehome;

import java.util.Scanner;

public class SafeHome {
    public static void main(String[] args) {
        ControllerInterface controller = new DoorLockController();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SAFE HOME SYSTEM ---");
            System.out.println("1. Admin Mode\n2. Tenant Mode\n3. Exit");
            System.out.print("Choice: ");
            String mainChoice = sc.nextLine();

            if (mainChoice.equals("1")) {
                System.out.println("a. Add | r. Remove | v. Logs");
                String opt = sc.nextLine();
                try {
                    if (opt.equals("a")) {
                        System.out.print("Name: "); String n = sc.nextLine();
                        System.out.print("PIN: "); String p = sc.nextLine();
                        controller.addTenant(p, n);
                    } else if (opt.equals("r")) {
                        System.out.print("Name: "); String n = sc.nextLine();
                        controller.removeTenant(n);
                    } else if (opt.equals("v")) {
                        controller.getAccessLogs().forEach(System.out::println);
                    }
                } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
            } else if (mainChoice.equals("2")) {
                System.out.print("Enter PIN: ");
                String p = sc.nextLine();
                try {
                    DoorStatus s = controller.enterPin(p);
                    System.out.println("Door is now " + s);
                } catch (Exception e) { System.out.println("Denied: " + e.getMessage()); }
            } else break;
        }
    }
}