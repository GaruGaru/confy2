package it.modularity.confy;

import it.modularity.confy.factory.ConfigFactory;
import it.modularity.confy.proxy.ConfyProxy;

import java.util.HashMap;
import java.util.Map;

public class Confy {

    public static <T> T implement(Class<T> tClass) {
        return ConfyProxy.proxying(tClass, create());
    }

    public static <T> T implement(Class<T> tClass, Confy confy) {
        return ConfyProxy.proxying(tClass, confy);
    }

    public static Confy fromMap(Map<String, Object> map) {
        return new Confy(map);
    }

    public static Confy fromEnv() {
        return fromMap(ConfigFactory.fromEnv());
    }

    public static Confy empty() {
        return new Confy();
    }

    public static Confy fromProperty(String props) {
        return create(props, true);
    }

    public static Confy fromArgs(String... args) {
        return fromMap(ConfigFactory.fromArgs(args));
    }

    public static Confy create() {
        return create("configuration", true);
    }

    private static Confy create(String properties, boolean useEnv) {
        return fromMap(useEnv ? ConfigFactory.fromEnvAndProperties(properties) : ConfigFactory.fromProperty(properties));
    }

    private Map<String, Object> configMap;

    private Confy() {
        this(new HashMap<>());
    }

    private Confy(Map<String, Object> configMap) {
        setConfigMap(configMap);
    }

    public Confy withEnv() {
        setConfigMap(ConfigFactory.merge(this.configMap, ConfigFactory.fromEnv()));
        return this;
    }

    public Confy withArgs(String... args) {
        setConfigMap(ConfigFactory.merge(this.configMap, ConfigFactory.fromArgs(args)));
        return this;
    }

    public Confy withProperty(String property) {
        setConfigMap(ConfigFactory.fromProperty(property));
        return this;
    }

    public <T> T to(Class<T> tClass) {
        return implement(tClass, this);
    }

    private void setConfigMap(Map<String, Object> map) {
        this.configMap = normalize(map);
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

    public void clear() {
        this.configMap.clear();
    }

    private Map<String, Object> normalize(Map<String, Object> source) {
        Map<String, Object> normalized = new HashMap<>();
        for (Map.Entry<String, Object> e : source.entrySet()) {
            final String key = normalizeKey(e.getKey());
            normalized.put(key, e.getValue());
        }
        return normalized;
    }

    public String normalizeKey(String key) {
        return key
                .replaceAll("([a-z])([A-Z]+)", "$1_$2")
                .replaceAll("_", ".")
                .toLowerCase();
    }

    @Override
    public String toString() {
        return "Confy{" +
                "config=" + configMap +
                '}';
    }


}
