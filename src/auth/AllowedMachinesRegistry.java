package auth;

import java.util.Set;
import java.util.TreeSet;

public class AllowedMachinesRegistry {
    private static Set<String> allowedIPs;

    static {
        allowedIPs = new TreeSet<>();
        allowedIPs.add("192.168.1.215");
        allowedIPs.add("127.0.0.1");
    }


    public static boolean isIPAllowed(String ip) {
        return allowedIPs.contains(ip);
    }

}
