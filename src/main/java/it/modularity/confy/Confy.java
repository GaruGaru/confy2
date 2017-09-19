package it.modularity.confy;

import it.modularity.confy.factory.ConfigFactory;
import it.modularity.confy.proxy.ConfyProxy;

import java.util.HashMap;
import java.util.Map;

public class Confy {

    public static <T> T implement(Class<T> tClass, Confy confy) {
        return ConfyProxy.proxying(tClass, confy);
    }

    public static <T> T implement(Class<T> tClass, String propName, boolean useEnv) {
        return implement(tClass, Confy.create(propName, useEnv));
    }

    public static <T> T implement(Class<T> tClass, String properties) {
        return implement(tClass, properties, true);
    }

    public static <T> T implement(Class<T> tClass) {
        return implement(tClass, "configuration");
    }

    public static Confy create(String properties, boolean useEnv) {
        return create(useEnv ? ConfigFactory.fromEnvAndProperties(properties) : ConfigFactory.fromProperty(properties));
    }

    public static Confy empty() {
        return create(new HashMap<>());
    }

    public static Confy create(String props) {
        return create(props, true);
    }

    public static Confy create() {
        return create("configuration", true);
    }

    public static Confy create(Map<String, Object> map) {
        return new Confy(map);
    }

    private Map<String, Object> configMap;

    private Confy(Map<String, Object> configMap) {
        this.configMap = configMap;
    }

    private Confy() {
        this(new HashMap<>());
    }

    public Object get(String key) {
        return configMap.get(key);
    }

    public Object get(String key, Object defaultValue) {
        return configMap.getOrDefault(key, defaultValue);
    }

    public void put(String key, Object value) {
        this.configMap.put(key, value);
    }

    public void putIfNotPresent(String key, Object value) {
        this.configMap.putIfAbsent(key, value);
    }

    public Map<String, Object> toMap() {
        return new HashMap<>(configMap);
    }

    @Override
    public String toString() {
        return "Confy{" +
                "config=" + configMap +
                '}';
    }


}
