package ru.kpfu.itis.others;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */

public enum RouteMove {
    UP("UP", 0),
    DOWN("DOWN", 1),
    RIGHT("RIGHT", 2),
    LEFT("LEFT", 3),
    STOP("STOP", 4);

    private String name;
    private int b;

    RouteMove(String name, int b) {
        this.name = name;
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public byte getByte() {
        return (byte) b;
    }

    private static final Map<Byte, RouteMove> byteRouteMap;
    private static final Map<String, RouteMove> routeMap;
    static {
        byteRouteMap = new HashMap<>(RouteMove.values().length);
        routeMap = new HashMap<>(RouteMove.values().length);
        for (RouteMove r : RouteMove.values()) {
            byteRouteMap.put(r.getByte(), r);
            routeMap.put(r.getName(), r);
        }
    }

    public static RouteMove getRouteMove(String route) {
        return routeMap.get(route);
    }

    public static RouteMove getRouteMove(byte b) {
        return byteRouteMap.get(b);
    }
}
