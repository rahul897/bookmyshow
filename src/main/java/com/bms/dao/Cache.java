package com.bms.dao;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    public static Set<String> locations = new HashSet<>();

    public static Map<Long,Set<String>> seatCache = new ConcurrentHashMap<>();
}
