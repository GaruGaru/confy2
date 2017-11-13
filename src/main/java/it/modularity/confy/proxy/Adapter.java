package it.modularity.confy.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Adapter {

    private static Map<Class, Class> castMap = new HashMap<>();

    private static Map<Class, Function<String, Object>> adaptMap = new HashMap<>();

    static {
        castMap.put(Double.class, Double.class);
        castMap.put(Integer.class, Integer.class);
        castMap.put(Float.class, Float.class);
        castMap.put(Boolean.class, Boolean.class);
        castMap.put(double.class, Double.class);
        castMap.put(int.class, Integer.class);
        castMap.put(float.class, Float.class);
        castMap.put(boolean.class, Boolean.class);
        castMap.put(String.class, String.class);
    }

    static {
        adaptMap.put(boolean.class, Boolean::parseBoolean);
        adaptMap.put(float.class, Float::parseFloat);
        adaptMap.put(int.class, Integer::parseInt);
        adaptMap.put(double.class, Double::parseDouble);
        adaptMap.put(Boolean.class, Boolean::parseBoolean);
        adaptMap.put(Float.class, Float::parseFloat);
        adaptMap.put(Integer.class, Integer::parseInt);
        adaptMap.put(Double.class, Double::parseDouble);
        adaptMap.put(String.class, String::valueOf);
    }

    public static Object cast(Object source, Class destination) {
        return castMap.get(destination).cast(source);
    }

    public static Object adapt(Object source, Class destination) {
        if (castMap.get(source.getClass()).equals(destination)) {
            return cast(source, destination);
        } else {
            return adaptMap.get(destination).apply(String.valueOf(source));
        }
    }



}
