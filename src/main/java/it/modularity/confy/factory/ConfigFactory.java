package it.modularity.confy.factory;

import java.util.*;

public class ConfigFactory {

    public static Map<String, Object> empty() {
        return new HashMap<>();
    }

    public static Map<String, Object> fromEnvAndProperties(String properties){
        return merge(fromProperty(properties), fromEnv());
    }

    public static Map<String, Object> fromEnv() {
        return merge(generify(System.getenv()), mapOf(System.getProperties()));
    }

    public static Map<String, Object> fromProperty(String name) {
        Optional<Properties> properties = PropertiesLoader.load(name);
        return properties.isPresent() ? mapOf(properties.get()) : empty();
    }

    public static Map<String, Object> mapOf(Properties properties) {
        Map<String, Object> map = new HashMap<>();
        for (String name : properties.stringPropertyNames())
            map.put(name, properties.getProperty(name));
        return map;
    }

    public static Map<String, Object> generify(Map<String, String> stringMap) {
        return new HashMap<>(stringMap);
    }

    @SafeVarargs
    public static Map<String, Object> merge(Map<String, Object>... maps) {
        Map<String, Object> configuration = new HashMap<>();
        for (Map<String, Object> map : maps)
            configuration.putAll(map); // The last element override the others
        return configuration;
    }

}
