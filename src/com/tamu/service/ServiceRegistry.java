package com.tamu.service;
import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {

    private static final Map<String, Integer> services = new HashMap<>();

    public static void registerService(String serviceName, int port) {
        services.put(serviceName, port);
    }

    public static int getServicePort(String serviceName) {
        return services.get(serviceName);
    }
}
