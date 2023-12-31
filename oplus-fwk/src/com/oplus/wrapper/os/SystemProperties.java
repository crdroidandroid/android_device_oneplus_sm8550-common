package com.oplus.wrapper.os;

public class SystemProperties {

    public static String get(String key) {
        return SystemProperties.get(key, "");
    }

    public static String get(String key, String def) {
        return SystemProperties.get(key, def);
    }

    public static int getInt(String key, int def) {
        return SystemProperties.getInt(key, def);
    }

    public static boolean getBoolean(String key, boolean def) {
        return SystemProperties.getBoolean(key, def);
    }

    public static void set(String key, String val) {
        SystemProperties.set(key, val);
    }
}
