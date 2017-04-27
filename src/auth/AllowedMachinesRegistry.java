package auth;

import java.util.Set;
import java.util.TreeSet;

public class AllowedMachinesRegistry {
    private Set<String> allowedIPs;

    public AllowedMachinesRegistry() {
        allowedIPs = new TreeSet<>();
        allowedIPs.add("192.168.1.215");
        allowedIPs.add("127.0.0.1");
    }

    public boolean isIPAllowed(String ip) {
        return allowedIPs.contains(ip);
    }

}
